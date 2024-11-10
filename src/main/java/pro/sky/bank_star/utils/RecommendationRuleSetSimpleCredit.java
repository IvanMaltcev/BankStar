package pro.sky.bank_star.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.bank_star.dto.BankProduct;
import pro.sky.bank_star.repository.BankManagerRepository;

import java.util.Optional;

@Component
public class RecommendationRuleSetSimpleCredit implements RecommendationRuleSet {
    @Autowired
    private BankManagerRepository bankManagerRepository;

    private final String productId = "ab138afb-f3ba-4a93-b74f-0fcee86d447f";
    private final String productName = "Простой кредит";
    private final String productDescription = "Откройте мир выгодных кредитов с нами! " +
            "Ищете способ быстро и без лишних хлопот получить нужную сумму? " +
            "Тогда наш выгодный кредит — именно то, что вам нужно! " +
            "Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту. " +
            "Почему выбирают нас: " +
            "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает " +
            "всего несколько часов. " +
            "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении. " +
            "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: " +
            "покупку недвижимости, автомобиля, образование, лечение и многое другое. " +
            "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!";

    @Override
    public Optional<BankProduct> findUserRecommendations(String id) {
        int creditTransactionAmount = bankManagerRepository.getCreditTransactionAmount(id);
        int sumDepositDebitTransaction = bankManagerRepository.getSumDepositDebitTransaction(id);
        int sumWithdrawDebitTransaction = bankManagerRepository.getSumWithdrawDebitTransaction(id);
        if (creditTransactionAmount == 0
                && sumDepositDebitTransaction > sumWithdrawDebitTransaction
                && sumWithdrawDebitTransaction > 100000) {
            return Optional.of(new BankProduct(productName, productId, productDescription));
        }
        return Optional.empty();
    }
}
