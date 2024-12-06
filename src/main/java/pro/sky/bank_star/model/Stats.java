package pro.sky.bank_star.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ruleId;
    private int count;

    public Stats() {
    }

    public Stats(String ruleId, int count) {
        this.ruleId = ruleId;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public String getRuleId() {
        return ruleId;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stats stats = (Stats) o;
        return count == stats.count
                && Objects.equals(id, stats.id)
                && Objects.equals(ruleId, stats.ruleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ruleId, count);
    }

    @Override
    public String toString() {
        return "\"stats\": {" +
                "\"rule_id\": " + ruleId +
                ", \"count\": " + count +
                '}';
    }
}
