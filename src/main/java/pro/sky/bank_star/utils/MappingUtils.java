package pro.sky.bank_star.utils;

import org.springframework.stereotype.Service;
import pro.sky.bank_star.dto.ProductDataDto;
import pro.sky.bank_star.dto.RuleDto;
import pro.sky.bank_star.model.ProductData;
import pro.sky.bank_star.model.Rule;

@Service
public class MappingUtils {

    public RuleDto mapToRuleDto(Rule rule) {
        RuleDto dto = new RuleDto();
        dto.setQuery(rule.getQuery());
        dto.setArguments(rule.getArguments());
        dto.setNegate(rule.isNegate());
        return dto;
    }

    public ProductDataDto mapToProductDataDto(ProductData productData) {
        ProductDataDto dto = new ProductDataDto();
        dto.setId(productData.getId());
        dto.setProductName(productData.getProductName());
        dto.setProductId(productData.getProductId());
        dto.setProductText(productData.getProductText());
        return dto;
    }
}
