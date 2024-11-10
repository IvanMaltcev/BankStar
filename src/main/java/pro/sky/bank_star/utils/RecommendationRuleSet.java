package pro.sky.bank_star.utils;

import pro.sky.bank_star.dto.BankProduct;

import java.util.Optional;

public interface RecommendationRuleSet {

    Optional<BankProduct> findUserRecommendations(String id);
}
