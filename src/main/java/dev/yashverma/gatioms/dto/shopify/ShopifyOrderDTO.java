package dev.yashverma.gatioms.dto.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyOrderDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("financial_status")
    private String financialStatus;

    @JsonProperty("fulfillment_status")
    private String fulfillmentStatus;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("subtotal_price")
    private String subtotalPrice;

    @JsonProperty("total_price")
    private String totalPrice;

    @JsonProperty("total_tax")
    private String totalTax;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("customer")
    private ShopifyCustomerDTO customer;

    @JsonProperty("shipping_address")
    private ShopifyAddressDTO shippingAddress;

    @JsonProperty("billing_address")
    private ShopifyAddressDTO billingAddress;

    @JsonProperty("line_items")
    private List<ShopifyLineItemDTO> lineItems;

    @JsonProperty("tax_lines")
    private List<ShopifyTaxLineDTO> taxLines;
}
