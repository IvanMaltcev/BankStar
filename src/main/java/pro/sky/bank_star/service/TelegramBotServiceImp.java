package pro.sky.bank_star.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.bank_star.dto.BankProduct;
import pro.sky.bank_star.dto.User;
import pro.sky.bank_star.repository.BankManagerRepository;

import java.util.List;
import java.util.Map;

@Service
public class TelegramBotServiceImp implements TelegramBotService {

    @Autowired
    private BankManagerRepository bankManagerRepository;

    @Autowired
    private BankManagerServiceImp bankManagerServiceImp;

    private String userId;

    @Override
    public String getUserFullName(String message) {
        String userName = message.split(" ")[1];
        if (userName == null) {
            return "";
        }
        User user = bankManagerRepository.getUser(userName);
        if (user != null) {
            userId = user.getId();
            return user.getFirstName() + " " + user.getLastName();
        }
        return "";
    }

    public String getListRecommendations() {
        Map<String, List<BankProduct>> listRecommendations = bankManagerServiceImp.getListProductsBank(userId);
        StringBuilder textToSend = new StringBuilder();
        textToSend.append("Новые продукты для вас: ");
        listRecommendations.get(userId).forEach(bankProduct -> textToSend
                .append("\n").append(bankProduct.getName()).append("\n").append(bankProduct.getDescription()));
        return textToSend.toString();
    }
}
