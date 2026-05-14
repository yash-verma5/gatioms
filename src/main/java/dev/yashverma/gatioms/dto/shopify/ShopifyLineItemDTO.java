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
public class ShopifyLineItemDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("price")
    private String price;

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("variant_id")
    private Long variantId;

    @JsonProperty("variant_title")
    private String variantTitle;

    @JsonProperty("vendor")
    private String vendor;

    @JsonProperty("tax_lines")
    private List<ShopifyTaxLineDTO> taxLines;
}
