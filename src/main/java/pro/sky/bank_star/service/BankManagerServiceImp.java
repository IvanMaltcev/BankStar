package pro.sky.bank_star.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.bank_star.dto.BankProduct;
import pro.sky.bank_star.utils.RecommendationRuleSet;
import pro.sky.bank_star.utils.RecommendationRuleSetDynamic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankManagerServiceImp implements BankManagerService {

    private final List<RecommendationRuleSet> recommendationRuleSets;

    public BankManagerServiceImp(List<RecommendationRuleSet> recommendationRuleSets) {
        this.recommendationRuleSets = recommendationRuleSets;
    }

    @Autowired
    private RecommendationRuleSetDynamic recommendationRuleSetDynamic;

    @Override
    public Map<String, List<BankProduct>> getListProductsBank(String id) {
        List<BankProduct> recommendationProducts = new ArrayList<>();
        for (RecommendationRuleSet recommendationRuleSet : recommendationRuleSets) {
            Optional<BankProduct> recommendation = recommendationRuleSet.findUserRecommendations(id);
            recommendation.ifPresent(recommendationProducts::add);
        }
        List<BankProduct> dynamicRecommendations = recommendationRuleSetDynamic.findUserRecommendations(id);
        if (!dynamicRecommendations.isEmpty()) {
            recommendationProducts.addAll(dynamicRecommendations);
        }
        return recommendationProducts.stream()
                .collect(Collectors.groupingBy(
                        it -> id,
                        Collectors.mapping(
                                it -> new BankProduct(it.getName(), it.getId(), it.getDescription()),
                                Collectors.toList()
                        )
                ));
    }
}
