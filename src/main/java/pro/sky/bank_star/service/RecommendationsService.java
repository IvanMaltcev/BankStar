package pro.sky.bank_star.service;

import pro.sky.bank_star.dto.ProductDataDto;
import pro.sky.bank_star.model.ProductData;
import pro.sky.bank_star.model.Stats;

import java.util.List;

public interface RecommendationsService {

    ProductDataDto createRecommendationRule(ProductData productData);

    List<ProductDataDto> getListRecommendationRules();

    void deleteRecommendationRule(Long id);

    List<Stats> getStatsRecommendationRules();
}
