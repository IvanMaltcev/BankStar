package pro.sky.bank_star.dto;


import java.util.List;
import java.util.Objects;

public class ProductDataDto {
    private Long id;
    private String productName;
    private String productId;
    private String productText;
    private List<RuleDto> rulesDto;

    public ProductDataDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public List<RuleDto> getRulesDto() {
        return rulesDto;
    }

    public void setRulesDto(List<RuleDto> rulesDto) {
        this.rulesDto = rulesDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDataDto that = (ProductDataDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(productName, that.productName)
                && Objects.equals(productId, that.productId)
                && Objects.equals(productText, that.productText)
                && Objects.equals(rulesDto, that.rulesDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productId, productText, rulesDto);
    }

    @Override
    public String toString() {
        return "\"data\": " +
                "\"id\": " + id +
                ", \"product_name\": " + productName +
                ", \"product_id\": " + productId +
                ", \"product_text\": " + productText +
                ", \"rule\": " + rulesDto;
    }
}
