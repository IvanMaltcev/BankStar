package pro.sky.bank_star.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import pro.sky.bank_star.dto.BankProduct;
import pro.sky.bank_star.utils.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankManagerServiceTest {

    @Mock
    private RecommendationRuleSetTopSaving recommendationRuleSetTopSavingMock;
    @Mock
    private RecommendationRuleSetSimpleCredit recommendationRuleSetSimpleCreditMock;
    @Mock
    private RecommendationRuleSetForInvest500 recommendationRuleSetForInvest500Mock;
    @Mock
    private RecommendationRuleSetDynamic recommendationRuleSetDynamicMock;

    @Autowired
    @InjectMocks
    private BankManagerServiceImp bankManagerService;

    private BankProduct bankProduct1;
    private BankProduct bankProduct2;
    private BankProduct bankProduct3;

    @BeforeEach
    public void setUp() {

        bankManagerService = new BankManagerServiceImp(List.of(
                recommendationRuleSetSimpleCreditMock,
                recommendationRuleSetForInvest500Mock,
                recommendationRuleSetTopSavingMock
        ));

        bankProduct1 = new BankProduct("Простой кредит", "ab138afb", "Текст");
        bankProduct2 = new BankProduct("Top Saving", "59efc529", "Текст");
        bankProduct3 = new BankProduct("Super invest", "hg364hc9", "Текст");

        when(recommendationRuleSetSimpleCreditMock.findUserRecommendations("12345"))
                .thenReturn(Optional.of(bankProduct1));
        when(recommendationRuleSetForInvest500Mock.findUserRecommendations("12345"))
                .thenReturn(Optional.empty());
        when(recommendationRuleSetTopSavingMock.findUserRecommendations("12345"))
                .thenReturn(Optional.of(bankProduct2));
    }

    @Test
    public void getListProductsBank() {
        MockitoAnnotations.openMocks(this);
        String id = "12345";
        when(recommendationRuleSetDynamicMock.findUserRecommendations(id)).thenReturn(List.of(bankProduct3));

        Map<String, List<BankProduct>> expected = new HashMap<>();
        expected.put(id, List.of(bankProduct1, bankProduct2, bankProduct3));

        Map<String, List<BankProduct>> actual = bankManagerService.getListProductsBank(id);

        assertEquals(expected, actual);

    }
}
