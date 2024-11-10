package pro.sky.bank_star.dto;

import java.util.Objects;

public class BankProduct {

    private final String name;
    private final String id;
    private final String description;

    public BankProduct(String name, String id, String description) {
        this.name = name;
        this.id = id;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankProduct that = (BankProduct) o;
        return Objects.equals(name, that.name) && Objects.equals(id, that.id) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, description);
    }

    @Override
    public String toString() {
        return "\"recommendations\": \"name\": " + name +
                ", \"id\": " + id +
                ", \"text\": " + description;
    }
}
