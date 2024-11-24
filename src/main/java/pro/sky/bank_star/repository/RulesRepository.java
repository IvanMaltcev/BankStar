package pro.sky.bank_star.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.bank_star.model.Rule;

public interface RulesRepository extends JpaRepository<Rule, Long> {
}
