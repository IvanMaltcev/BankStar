package pro.sky.bank_star.dto;

import pro.sky.bank_star.utils.Query;

import java.util.List;
import java.util.Objects;

public class RuleDto {

    private Query query;
    private List<String> arguments;
    private boolean negate;

    public RuleDto() {
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleDto ruleDto = (RuleDto) o;
        return negate == ruleDto.negate
                && query == ruleDto.query
                && Objects.equals(arguments, ruleDto.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, arguments, negate);
    }

    @Override
    public String toString() {
        return "\"query\":" + query +
                ", \"arguments\": " + arguments +
                ", \"negate\": " + negate;
    }
}
