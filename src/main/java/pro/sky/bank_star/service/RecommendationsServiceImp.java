package pro.sky.bank_star.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.bank_star.dto.ProductDataDto;
import pro.sky.bank_star.dto.RuleDto;
import pro.sky.bank_star.model.ProductData;
import pro.sky.bank_star.model.Rule;
import pro.sky.bank_star.repository.RecommendationsRepository;
import pro.sky.bank_star.repository.RulesRepository;
import pro.sky.bank_star.utils.MappingUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationsServiceImp implements RecommendationsService {

    @Autowired
    private RecommendationsRepository recommendationsRepository;
    @Autowired
    private RulesRepository rulesRepository;
    @Autowired
    private MappingUtils mappingUtils;

    @Override
    public ProductDataDto createRecommendationRule(ProductData productData) {
        ProductData saveProductData = recommendationsRepository.save(productData);
        List<RuleDto> rulesDto = new ArrayList<>();
        productData.getRules()
                .forEach(rule -> {
                    rule.setProductData(productData);
                    Rule saveRule = rulesRepository.save(rule);
                    RuleDto ruleDto = mappingUtils.mapToRuleDto(saveRule);
                    rulesDto.add(ruleDto);
                });
        ProductDataDto productDataDto = mappingUtils.mapToProductDataDto(saveProductData);
        productDataDto.setRulesDto(rulesDto);
        return productDataDto;
    }

    @Override
    public List<ProductDataDto> getListRecommendationRules() {
        List<ProductDataDto> listRecommendationRules = new ArrayList<>();
        recommendationsRepository.findAll().forEach(productData -> {
            List<RuleDto> rulesDto = productData.getRules().stream()
                    .map(rule -> mappingUtils.mapToRuleDto(rule)).toList();
            ProductDataDto productDataDto = mappingUtils.mapToProductDataDto(productData);
            productDataDto.setRulesDto(rulesDto);
            listRecommendationRules.add(productDataDto);
        });
        return listRecommendationRules;
    }

    @Override
    public void deleteRecommendationRule(Long id) {
        recommendationsRepository.deleteById(id);
    }
}
