package pro.sky.bank_star.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.bank_star.dto.BankProduct;
import pro.sky.bank_star.repository.BankManagerRepository;

import java.util.Optional;

@Component
public class RecommendationRuleSetForInvest500 implements RecommendationRuleSet {

    @Autowired
    private BankManagerRepository bankManagerRepository;

    private final String productId = "147f6a0f-3b91-413b-ab99-87f081d60d5a";
    private final String productName = "Invest 500";
    private final String productDescription = "Откройте свой путь к успеху с " +
            "индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами " +
            "и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета " +
            "на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, " +
            "снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте " +
            "ближе к финансовой независимости!";

    @Override
    public Optional<BankProduct> findUserRecommendations(String id) {
        int debitTransactionAmount = bankManagerRepository.getDebitTransactionAmount(id);
        int investTransactionAmount = bankManagerRepository.getInvestTransactionAmount(id);
        int sumDepositSavingTransaction = bankManagerRepository.getSumDepositSavingTransaction(id);
        if (debitTransactionAmount != 0
                && investTransactionAmount == 0
                && sumDepositSavingTransaction > 1000) {
            return Optional.of(new BankProduct(productName, productId, productDescription));
        }
        return Optional.empty();
    }
}