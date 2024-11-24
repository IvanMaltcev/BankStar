package pro.sky.bank_star.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.bank_star.dto.BankProduct;
import pro.sky.bank_star.repository.BankManagerRepository;

import java.util.Optional;

@Component
public class RecommendationRuleSetTopSaving implements RecommendationRuleSet {

    @Autowired
    private BankManagerRepository bankManagerRepository;

    private final String productId = "59efc529-2fff-41af-baff-90ccd7402925";
    private final String productName = "Top Saving";
    private final String productDescription = "Откройте свою собственную «Копилку» с нашим банком! " +
            "«Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать " +
            "деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем! " +
            "Преимущества «Копилки»: " +
            "Накопление средств на конкретные цели. Установите лимит и срок накопления, " +
            "и банк будет автоматически переводить определенную сумму на ваш счет." +
            "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления " +
            "и корректируйте стратегию при необходимости. " +
            "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен " +
            "только через мобильное приложение или интернет-банкинг. " +
            "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!";

    @Override
    public Optional<BankProduct> findUserRecommendations(String id) {
        boolean isFirstRule = bankManagerRepository.isUserOf(id, "DEBIT");
        boolean isSecondRule = (bankManagerRepository.transactionSumCompare(id, "DEBIT",
                "DEPOSIT", ">=", 50000))
                || bankManagerRepository.transactionSumCompare(id, "SAVING",
                "DEPOSIT", ">=", 50000);
        boolean isThirdRule = bankManagerRepository.transactionSumCompareDepositWithdraw(id,
                "DEBIT", ">");
        if (isFirstRule && isSecondRule && isThirdRule) {
            return Optional.of(new BankProduct(productName, productId, productDescription));
        }
        return Optional.empty();
    }
}
