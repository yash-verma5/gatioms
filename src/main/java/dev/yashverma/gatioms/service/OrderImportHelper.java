package dev.yashverma.gatioms.service;

import dev.yashverma.gatioms.domain.entity.*;
import dev.yashverma.gatioms.dto.shopify.ShopifyLineItemDTO;
import dev.yashverma.gatioms.dto.shopify.ShopifyOrderDTO;
import dev.yashverma.gatioms.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderImportHelper {

    private final ShopifyShopOrderRepository shopifyShopOrderRepository;
    private final PartyRepository partyRepository;
    private final PersonRepository personRepository;
    private final ContactMechRepository contactMechRepository;
    private final PostalAddressRepository postalAddressRepository;
    private final OrderHeaderRepository orderHeaderRepository;
    private final OrderItemRepository orderItemRepository;
    private final GoodIdentificationRepository goodIdentificationRepository;
    private final ProductRepository productRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean importSingleOrder(ShopifyOrderDTO order, ShopifyConfig config) {
        String shopifyOrderIdStr = order.getId().toString();

        // a. DEDUP
        boolean exists = shopifyShopOrderRepository.existsByShopifyOrderIdAndShopId(shopifyOrderIdStr, config.getShopId());
        if (exists) {
            log.info("Order already exists, skipping. shopifyOrderId: {}", shopifyOrderIdStr);
            return false;
        }

        // b. PARTY (Customer)
        String partyId = UUID.randomUUID().toString().substring(0, 20);
        String email = order.getCustomer() != null ? order.getCustomer().getEmail() : null;

        Party party = new Party();
        party.setPartyId(partyId);
        party.setPartyTypeId("PERSON");
        party = partyRepository.save(party);

        if (order.getCustomer() != null) {
            Person person = new Person();
            person.setPartyId(partyId);
            person.setParty(party);
            person.setFirstName(order.getCustomer().getFirstName());
            person.setLastName(order.getCustomer().getLastName());
            personRepository.save(person);
        }

        // c. EMAIL ContactMech
        if (email != null && !email.isEmpty()) {
            String emailContactMechId = UUID.randomUUID().toString().substring(0, 20);
            ContactMech emailContactMech = new ContactMech();
            emailContactMech.setContactMechId(emailContactMechId);
            emailContactMech.setContactMechTypeId("EMAIL_ADDRESS");
            emailContactMech.setInfoString(email);
            contactMechRepository.save(emailContactMech);
        }

        // d. POSTAL ADDRESS ContactMech
        if (order.getShippingAddress() != null) {
            String postalContactMechId = UUID.randomUUID().toString().substring(0, 20);
            ContactMech postalContactMech = new ContactMech();
            postalContactMech.setContactMechId(postalContactMechId);
            postalContactMech.setContactMechTypeId("POSTAL_ADDRESS");
            postalContactMech = contactMechRepository.save(postalContactMech);

            PostalAddress postalAddress = new PostalAddress();
            postalAddress.setContactMechId(postalContactMechId);
            postalAddress.setContactMech(postalContactMech);
            postalAddress.setToName(order.getShippingAddress().getName());
            postalAddress.setAddress1(order.getShippingAddress().getAddress1());
            postalAddress.setAddress2(order.getShippingAddress().getAddress2());
            postalAddress.setCity(order.getShippingAddress().getCity());
            postalAddress.setStateProvinceGeoId(order.getShippingAddress().getProvinceCode());
            postalAddress.setPostalCode(order.getShippingAddress().getZip());
            postalAddress.setCountryGeoId(order.getShippingAddress().getCountryCode());
            postalAddressRepository.save(postalAddress);
        }

        // e. ORDER HEADER
        String orderId = "ORD-" + System.currentTimeMillis();

        ProductStore store = new ProductStore();
        store.setProductStoreId(config.getProductStore().getProductStoreId());

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setOrderId(orderId);
        orderHeader.setCustomerParty(party);
        orderHeader.setProductStore(store);

        LocalDateTime orderDate = LocalDateTime.now();
        if (order.getCreatedAt() != null) {
            try {
                orderDate = ZonedDateTime.parse(order.getCreatedAt()).toLocalDateTime();
            } catch (Exception e) {
                log.warn("Could not parse createdAt date {}, defaulting to now", order.getCreatedAt());
            }
        }
        orderHeader.setOrderDate(orderDate);
        orderHeader.setStatusId("ORDER_APPROVED");
        orderHeader.setCurrencyUom(order.getCurrency());
        orderHeader.setGrandTotal(new BigDecimal(order.getTotalPrice() != null ? order.getTotalPrice() : "0"));
        orderHeaderRepository.save(orderHeader);

        // f. ORDER ITEMS
        if (order.getLineItems() != null) {
            for (int i = 0; i < order.getLineItems().size(); i++) {
                ShopifyLineItemDTO item = order.getLineItems().get(i);
                String orderItemSeqId = String.format("%05d", i + 1);

                OrderItem orderItem = new OrderItem();
                orderItem.setId(new OrderItemId(orderId, orderItemSeqId));
                orderItem.setOrderHeader(orderHeader);
                orderItem.setItemDescription(item.getTitle());
                orderItem.setQuantity(BigDecimal.valueOf(item.getQuantity() != null ? item.getQuantity() : 0));
                orderItem.setUnitPrice(new BigDecimal(item.getPrice() != null ? item.getPrice() : "0"));
                orderItem.setStatusId("ITEM_APPROVED");

                String variantIdStr = item.getVariantId() != null ? item.getVariantId().toString() : "";
                String productId = "UNK_" + variantIdStr;
                if (productId.length() > 20) {
                    productId = productId.substring(0, 20);
                }

                if (!variantIdStr.isEmpty()) {
                    var goodIdOpt = goodIdentificationRepository.findByIdValueAndGoodIdentificationTypeId(variantIdStr, "SHOPIFY_VARIANT_ID");
                    if (goodIdOpt.isPresent()) {
                        productId = goodIdOpt.get().getProduct().getProductId();
                    }
                }

                Product product = productRepository.findById(productId).orElse(null);
                if (product == null) {
                    product = new Product();
                    product.setProductId(productId);
                    product.setProductName(item.getTitle() != null ? item.getTitle() : "Unknown Shopify Product");
                    product = productRepository.save(product);
                }

                orderItem.setProduct(product);

                orderItemRepository.save(orderItem);
            }
        }

        // g. SHOPIFY SHOP ORDER
        ShopifyShopOrder shopifyShopOrder = new ShopifyShopOrder();
        shopifyShopOrder.setId(new ShopifyShopOrderId(shopifyOrderIdStr, config.getShopId()));
        shopifyShopOrder.setOrderHeader(orderHeader);
        shopifyShopOrder.setShopifyConfig(config);
        shopifyShopOrder.setImportDate(LocalDateTime.now());
        shopifyShopOrderRepository.save(shopifyShopOrder);

        log.info("Successfully imported order. shopifyOrderId: {}, internalOrderId: {}", shopifyOrderIdStr, orderId);
        return true;
    }
}