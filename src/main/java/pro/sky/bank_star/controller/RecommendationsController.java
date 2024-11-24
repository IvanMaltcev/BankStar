package pro.sky.bank_star.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.bank_star.dto.ProductDataDto;
import pro.sky.bank_star.model.ProductData;
import pro.sky.bank_star.service.RecommendationsService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("rule")
public class RecommendationsController {

    @Autowired
    private RecommendationsService recommendationsService;

    @PostMapping
    public ResponseEntity<ProductDataDto> createRecommendationRule(@RequestBody ProductData productData) {
        return ResponseEntity.ok(recommendationsService.createRecommendationRule(productData));
    }

    @GetMapping
    public ResponseEntity<Collection<ProductDataDto>> getListOfRecommendationRules() {
        return ResponseEntity.ok(Collections
                .unmodifiableCollection(recommendationsService.getListRecommendationRules()));
    }

    @DeleteMapping("{rule_id}")
    public ResponseEntity<ProductData> deleteRecommendationRule(@PathVariable(value = "rule_id") Long id) {
        recommendationsService.deleteRecommendationRule(id);
        return ResponseEntity.ok().build();
    }


}
