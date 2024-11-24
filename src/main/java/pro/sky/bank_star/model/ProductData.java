package pro.sky.bank_star.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class ProductData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productName;
    private String productId;
    private String productText;
    @OneToMany(mappedBy = "productData", fetch = FetchType.EAGER)
    private List<Rule> rules;

    public ProductData(String productName, String productId, String productText, List<Rule> rules) {
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
        this.rules = rules;
    }

    public ProductData() {
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductText() {
        return productText;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductData data = (ProductData) o;
        return Objects.equals(id, data.id)
                && Objects.equals(productName, data.productName)
                && Objects.equals(productId, data.productId)
                && Objects.equals(productText, data.productText)
                && Objects.equals(rules, data.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productId, productText, rules);
    }

    @Override
    public String toString() {
        return "\"data\": " +
                "\"id\": " + id +
                ", \"product_name\": " + productName +
                ", \"product_id\": " + productId +
                ", \"product_text\": " + productText +
                ", \"rule\": " + rules;
    }
}
