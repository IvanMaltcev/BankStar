package pro.sky.bank_star.telegram_bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.bank_star.dto.BankProduct;
import pro.sky.bank_star.dto.User;
import pro.sky.bank_star.repository.BankManagerRepository;
import pro.sky.bank_star.service.BankManagerService;

import java.util.List;
import java.util.Map;

@Service
public class TelegramBotServiceImp implements TelegramBotService {

    @Autowired
    private BankManagerRepository bankManagerRepository;

    @Autowired
    private BankManagerService bankManagerService;

    @Override
    public String getRecommendationsForUser(String message) {
        String userName = message.split(" ")[1];
        if (userName == null) {
            return "";
        }
        User user = bankManagerRepository.getUser(userName);
        if (user != null) {
            String fullName = user.getFirstName() + " " + user.getLastName();
            String listRecommendations = getListRecommendations(user.getId());
            return "Здравствуйте, " + fullName + "!" + "\n\n" + listRecommendations;
        }
        return "";
    }

    private String getListRecommendations(String userId) {
        Map<String, List<BankProduct>> listRecommendations = bankManagerService.getListProductsBank(userId);
        StringBuilder textToSend = new StringBuilder();
        textToSend.append("Новые продукты для вас:\n");
        listRecommendations.get(userId).forEach(bankProduct -> textToSend
                .append("\n").append(bankProduct.getName())
                .append("\n").append(bankProduct.getDescription()).append("\n"));
        return textToSend.toString();
    }
}
