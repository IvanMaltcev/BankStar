package pro.sky.bank_star.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import pro.sky.bank_star.dto.ProductDataDto;
import pro.sky.bank_star.model.ProductData;
import pro.sky.bank_star.model.Rule;
import pro.sky.bank_star.model.Stats;
import pro.sky.bank_star.repository.RecommendationsRepository;
import pro.sky.bank_star.repository.RulesRepository;
import pro.sky.bank_star.repository.StatsRepository;
import pro.sky.bank_star.utils.MappingUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pro.sky.bank_star.utils.Query.*;

@ExtendWith(MockitoExtension.class)
public class RecommendationsServiceTest {

    @Mock
    private RecommendationsRepository recommendationsRepositoryMock;

    @Mock
    private RulesRepository rulesRepositoryMock;

    @Mock
    private StatsRepository statsRepositoryMock;

    @Spy
    private MappingUtils mappingUtils;
    @Autowired
    @InjectMocks
    private RecommendationsServiceImp recommendationsService;

    private ProductData productData;

    private Rule rule1;
    private Rule rule2;
    private Rule rule3;

    private ProductDataDto productDataDto;

    private Stats stats;


    @BeforeEach
    public void setUp() {

        recommendationsService = new RecommendationsServiceImp();

        mappingUtils = new MappingUtils();

        MockitoAnnotations.openMocks(this);

        rule1 = new Rule(USER_OF, List.of("CREDIT"), true);
        rule2 = new Rule(TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW, List.of("DEBIT", ">"), false);
        rule3 = new Rule(TRANSACTION_SUM_COMPARE, List.of("DEBIT", "DEPOSIT", ">", "100000"), false);

        productData = new ProductData("Простой кредит", "ab138afb", "Текст", List.of(
                rule1,
                rule2,
                rule3
        ));

        productDataDto = new ProductDataDto();
        productDataDto.setProductName("Простой кредит");
        productDataDto.setProductId("ab138afb");
        productDataDto.setProductText("Текст");
        productDataDto.setRulesDto(List.of(
                mappingUtils.mapToRuleDto(rule1),
                mappingUtils.mapToRuleDto(rule2),
                mappingUtils.mapToRuleDto(rule3)
        ));

        stats = new Stats("ab138afb", 0);

    }

    @Test
    public void createRecommendationRuleTesting() {

        when(recommendationsRepositoryMock.save(productData)).thenReturn(productData);
        when(rulesRepositoryMock.save(any(Rule.class))).thenReturn(rule1).thenReturn(rule2).thenReturn(rule3);

        ProductDataDto actual = recommendationsService.createRecommendationRule(productData);

        assertEquals(productDataDto, actual);
    }

    @Test
    public void getListRecommendationRulesTesting() {

        when(recommendationsRepositoryMock.findAll()).thenReturn(List.of(productData));

        List<ProductDataDto> expected = List.of(productDataDto);
        List<ProductDataDto> actual = recommendationsService.getListRecommendationRules();

        assertEquals(expected, actual);

    }

    @Test
    public void getStatsRecommendationRulesTesting() {

        when(statsRepositoryMock.findAll()).thenReturn(List.of(stats));

        List<Stats> expected = List.of(stats);

        List<Stats> actual = recommendationsService.getStatsRecommendationRules();

        assertEquals(expected, actual);

    }
}
