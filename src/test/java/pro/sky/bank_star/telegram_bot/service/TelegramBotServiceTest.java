package pro.sky.bank_star.telegram_bot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import pro.sky.bank_star.dto.BankProduct;
import pro.sky.bank_star.dto.User;
import pro.sky.bank_star.repository.BankManagerRepository;
import pro.sky.bank_star.service.BankManagerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TelegramBotServiceTest {
    @Mock
    private BankManagerRepository bankManagerRepositoryMock;

    @Mock
    private BankManagerService bankManagerServiceMock;

    @Autowired
    @InjectMocks
    private TelegramBotServiceImp telegramBotServiceImp;

    private User user;
    private BankProduct bankProduct1;
    private BankProduct bankProduct2;
    private BankProduct bankProduct3;
    Map<String, List<BankProduct>> listRecommendations;


    @BeforeEach
    public void setUp() {

        telegramBotServiceImp = new TelegramBotServiceImp();

        MockitoAnnotations.openMocks(this);

        user = new User("123", "john.falko", "John", "Falko");

        bankProduct1 = new BankProduct("Простой кредит", "ab138afb", "Текст");
        bankProduct2 = new BankProduct("Top Saving", "59efc529", "Текст");
        bankProduct3 = new BankProduct("Super invest", "hg364hc9", "Текст");

        listRecommendations = new HashMap<>();
        listRecommendations.put("123", List.of(bankProduct1, bankProduct2, bankProduct3));
    }

    @Test
    public void getRecommendationsForUserTesting() {
        String message = "/recommend john.falko";

        when(bankManagerRepositoryMock.getUser(any(String.class))).thenReturn(user);
        when(bankManagerServiceMock.getListProductsBank(any(String.class))).thenReturn(listRecommendations);

        String expected = "Здравствуйте, John Falko!\n\n" +
                "Новые продукты для вас:\n\n" +
                "Простой кредит\n" +
                "Текст\n\n" +
                "Top Saving\n" +
                "Текст\n\n" +
                "Super invest\n" +
                "Текст\n";

        String actual = telegramBotServiceImp.getRecommendationsForUser(message);

        assertEquals(expected, actual);
    }

    @Test
    public void getRecommendationsForUserNotExistTesting() {
        String message = "/recommend jack.min";

        when(bankManagerRepositoryMock.getUser(any(String.class))).thenReturn(null);

        String expected = "";

        String actual = telegramBotServiceImp.getRecommendationsForUser(message);

        assertEquals(expected, actual);
    }
}
