package pro.sky.bank_star.service;

import pro.sky.bank_star.dto.ProductDataDto;
import pro.sky.bank_star.model.ProductData;

import java.util.List;

public interface RecommendationsService {

    ProductDataDto createRecommendationRule(ProductData productData);

    List<ProductDataDto> getListRecommendationRules();

    void deleteRecommendationRule(Long id);
}
