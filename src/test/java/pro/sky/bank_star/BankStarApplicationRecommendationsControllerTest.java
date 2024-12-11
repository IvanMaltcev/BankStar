package pro.sky.bank_star;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.bank_star.controller.RecommendationsController;
import pro.sky.bank_star.model.ProductData;
import pro.sky.bank_star.model.Rule;
import pro.sky.bank_star.model.Stats;
import pro.sky.bank_star.repository.RecommendationsRepository;
import pro.sky.bank_star.repository.RulesRepository;
import pro.sky.bank_star.repository.StatsRepository;
import pro.sky.bank_star.service.BankManagerService;
import pro.sky.bank_star.service.ManagementService;
import pro.sky.bank_star.service.RecommendationsServiceImp;
import pro.sky.bank_star.utils.MappingUtils;
import pro.sky.bank_star.utils.Query;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pro.sky.bank_star.utils.Query.*;

@WebMvcTest
public class BankStarApplicationRecommendationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationsRepository recommendationsRepository;
    @MockBean
    private RulesRepository rulesRepository;
    @MockBean
    private StatsRepository statsRepository;
    @MockBean
    private BankManagerService bankManagerService;
    @MockBean
    private ManagementService managementService;
    @SpyBean
    private MappingUtils mappingUtils;
    @SpyBean
    private RecommendationsServiceImp recommendationsServiceImp;
    @InjectMocks
    private RecommendationsController recommendationsController;

    private ProductData productData;

    private Rule rule1;
    private Rule rule2;
    private Rule rule3;

    private Long id;

    private String productName;
    private String productId;
    private String productText;
    private Query query1;
    private List<String> arguments1;
    private boolean negate1;
    private Query query2;
    private List<String> arguments2;
    private boolean negate2;
    private Query query3;
    private List<String> arguments3;
    private boolean negate3;

    List<Rule> rules;

    private Stats stats;

    @BeforeEach
    public void setUp() {

        query1 = USER_OF;
        arguments1 = List.of("CREDIT");
        negate1 = true;
        rule1 = new Rule();
        rule1.setQuery(query1);
        rule1.setArguments(arguments1);
        rule1.setNegate(negate1);

        query2 = TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW;
        arguments2 = List.of("DEBIT", ">");
        negate2 = false;
        rule2 = new Rule();
        rule2.setQuery(query2);
        rule2.setArguments(arguments2);
        rule2.setNegate(negate2);

        query3 = TRANSACTION_SUM_COMPARE;
        arguments3 = List.of("DEBIT", "DEPOSIT", ">", "100000");
        negate3 = false;
        rule3 = new Rule();
        rule3.setQuery(query3);
        rule3.setArguments(arguments3);
        rule3.setNegate(negate3);
        rules = new ArrayList<>();
        rules.add(rule1);
        rules.add(rule2);
        rules.add(rule3);

        id = 1L;
        productName = "Простой кредит";
        productId = "ab138afb";
        productText = "Текст";
        productData = new ProductData();
        productData.setId(id);
        productData.setProductName(productName);
        productData.setProductId(productId);
        productData.setProductText(productText);
        productData.setRules(List.of(rule1, rule2, rule3));

        stats = new Stats(productId, 0);

    }

    @Test
    public void createRecommendationRuleTesting() throws Exception {
        JSONObject ruleObject = new JSONObject();
        JSONArray argumentsObject = new JSONArray();
        JSONArray rulesObject = new JSONArray();
        for (int i = 0; i < 3; i++) {
            ruleObject.put("query", rules.get(i).getQuery());
            for (int j = 0; j < rules.get(i).getArguments().size(); j++) {
                argumentsObject.put(j, rules.get(i).getArguments().get(j));
            }
            ruleObject.put("arguments", argumentsObject);
            ruleObject.put("negate", rules.get(i).isNegate());
            rulesObject.put(ruleObject);
            ruleObject = new JSONObject();
            argumentsObject = new JSONArray();
        }
        JSONObject recommendationObject = new JSONObject();
        recommendationObject.put("productName", productName);
        recommendationObject.put("productId", productId);
        recommendationObject.put("productText", productText);
        recommendationObject.put("rules", rulesObject);

        when(recommendationsRepository.save(any(ProductData.class))).thenReturn(productData);
        when(rulesRepository.save(any(Rule.class))).thenReturn(rule1).thenReturn(rule2).thenReturn(rule3);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/rule")
                        .content(recommendationObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.productName").value(productName))
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.productText").value(productText))
                .andExpect(jsonPath("$.rulesDto.[0]").value(rule1))
                .andExpect(jsonPath("$.rulesDto.[1]").value(rule2))
                .andExpect(jsonPath("$.rulesDto.[2]").value(rule3));
    }

    @Test
    public void getListOfRecommendationRulesTesting() throws Exception {
        JSONObject ruleObject = new JSONObject();
        JSONArray argumentsObject = new JSONArray();
        JSONArray rulesObject = new JSONArray();
        for (int i = 0; i < 3; i++) {
            ruleObject.put("query", rules.get(i).getQuery());
            for (int j = 0; j < rules.get(i).getArguments().size(); j++) {
                argumentsObject.put(j, rules.get(i).getArguments().get(j));
            }
            ruleObject.put("arguments", argumentsObject);
            ruleObject.put("negate", rules.get(i).isNegate());
            rulesObject.put(ruleObject);
            ruleObject = new JSONObject();
            argumentsObject = new JSONArray();
        }
        JSONObject recommendationObject = new JSONObject();
        recommendationObject.put("productName", productName);
        recommendationObject.put("productId", productId);
        recommendationObject.put("productText", productText);
        recommendationObject.put("rules", rulesObject);

        when(recommendationsRepository.findAll()).thenReturn(List.of(productData));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/rule")
                        .content(recommendationObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(id))
                .andExpect(jsonPath("$.[0].productName").value(productName))
                .andExpect(jsonPath("$.[0].productId").value(productId))
                .andExpect(jsonPath("$.[0].productText").value(productText))
                .andExpect(jsonPath("$.[0].rulesDto.[0]").value(rule1))
                .andExpect(jsonPath("$.[0].rulesDto.[1]").value(rule2))
                .andExpect(jsonPath("$.[0].rulesDto.[2]").value(rule3));
    }

    @Test
    public void deleteRecommendationRule() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/rule/1"))
                .andExpect(status().isOk());
        verify(recommendationsRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void getStatsRecommendationRules() throws Exception {

        when(statsRepository.findAll()).thenReturn(List.of(stats));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/rule/stats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].ruleId").value(productId))
                .andExpect(jsonPath("$.[0].count").value(0));


    }

}
