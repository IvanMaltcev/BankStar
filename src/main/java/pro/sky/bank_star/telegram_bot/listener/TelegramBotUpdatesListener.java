package pro.sky.bank_star.telegram_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.bank_star.telegram_bot.service.TelegramBotService;

import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    TelegramBotService telegramBotService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String message = update.message().text();
            long chatId = update.message().chat().id();
            if (message.equals("/start")) {
                String textToSend = "Добро пожаловать в бот для получения рекомендаций от банка \"Стар\"!\n" +
                        "Для получения рекомендаций наших продуктов отправьте команду: \n/recommend username";
                SendMessage answer = new SendMessage(chatId, textToSend);
                telegramBot.execute(answer);
                return;
            }
            String recommendationsForUser = telegramBotService.getRecommendationsForUser(message);
            if (recommendationsForUser.isEmpty()) {
                String textToSend = "Пользователь не найден!";
                SendMessage answer = new SendMessage(chatId, textToSend);
                telegramBot.execute(answer);
                return;
            }
            SendMessage answer1 = new SendMessage(chatId, recommendationsForUser);
            telegramBot.execute(answer1);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
