package pro.sky.bank_star.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.bank_star.dto.BankProduct;
import pro.sky.bank_star.model.Rule;
import pro.sky.bank_star.model.Stats;
import pro.sky.bank_star.repository.BankManagerRepository;
import pro.sky.bank_star.repository.RecommendationsRepository;
import pro.sky.bank_star.repository.StatsRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecommendationRuleSetDynamic {

    @Autowired
    private RecommendationsRepository recommendationsRepository;
    @Autowired
    private BankManagerRepository bankManagerRepository;
    @Autowired
    private StatsRepository statsRepository;

    private final int LIMIT_TRANSACTIONS = 5;

    public List<BankProduct> findUserRecommendations(String id) {
        List<Boolean> checkingRules = new ArrayList<>();
        List<BankProduct> bankProducts = new ArrayList<>();
        recommendationsRepository.findAll()
                .forEach(productData -> {
                    if (statsRepository.findStatsByRuleId(productData.getProductId()) == null) {
                        statsRepository.save(new Stats(productData.getProductId(), 0));
                    }
                    productData.getRules()
                            .forEach(rule -> checkingRules.add(checkRecommendations(id, rule)));
                    if (!checkingRules.contains(false)) {
                        BankProduct bankProduct = new BankProduct(productData.getProductName(),
                                productData.getProductId(), productData.getProductText());
                        bankProducts.add(bankProduct);
                        Stats stats = statsRepository.findStatsByRuleId(productData.getProductId());
                        stats.incrementCount();
                        statsRepository.save(stats);
                    }
                    checkingRules.clear();
                });
        return bankProducts;
    }

    private boolean checkRecommendations(String id, Rule rule) {
        return switch (rule.getQuery()) {
            case USER_OF -> bankManagerRepository
                    .isUserOf(id, rule.getArguments().get(0)) == !rule.isNegate();

            case ACTIVE_USER_OF -> bankManagerRepository
                    .isActiveUserOf(id, rule.getArguments().get(0), LIMIT_TRANSACTIONS) == !rule.isNegate();

            case TRANSACTION_SUM_COMPARE -> bankManagerRepository
                    .transactionSumCompare(id, rule.getArguments().get(0),
                            rule.getArguments().get(1), rule.getArguments().get(2),
                            Integer.parseInt(rule.getArguments().get(3))) == !rule.isNegate();

            case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW -> bankManagerRepository
                    .transactionSumCompareDepositWithdraw(id, rule.getArguments().get(0),
                            rule.getArguments().get(1)) == !rule.isNegate();
        };
    }
}
