package pro.sky.bank_star.model;

import jakarta.persistence.*;
import pro.sky.bank_star.utils.Query;

import java.util.List;
import java.util.Objects;

@Entity
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Query query;
    private List<String> arguments;
    private boolean negate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_id")
    private ProductData productData;

    public Rule(Query query, List<String> arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    public Rule() {
    }

    public Query getQuery() {
        return query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public ProductData getData() {
        return productData;
    }


    public void setQuery(Query query) {
        this.query = query;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    public void setProductData(ProductData productData) {
        this.productData = productData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return negate == rule.negate
                && query == rule.query
                && Objects.equals(arguments, rule.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, arguments, negate);
    }

    @Override
    public String toString() {
        return "{\"query\":" + "\"" + query + "\"" +
                ",\"arguments\":" + arguments +
                ",\"negate\":" + negate + "}";
    }
}
