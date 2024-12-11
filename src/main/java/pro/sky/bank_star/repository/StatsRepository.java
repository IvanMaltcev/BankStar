package pro.sky.bank_star.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.bank_star.model.Stats;

public interface StatsRepository extends JpaRepository<Stats, Long> {

    Stats findStatsByRuleId(String ruleId);
}
