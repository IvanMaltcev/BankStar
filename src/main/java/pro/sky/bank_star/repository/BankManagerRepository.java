package pro.sky.bank_star.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BankManagerRepository {

    private final JdbcTemplate jdbcTemplate;

    public BankManagerRepository(@Qualifier("recommendationsJdbcTemplate")
                                 JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getDebitTransactionAmount(String id) {
        Integer result;
        try {
            result = jdbcTemplate.queryForObject(
                    "SELECT amount " +
                            "FROM transactions t " +
                            "INNER JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? " +
                            "AND p.type = \'DEBIT\' LIMIT 1",
                    Integer.class,
                    id);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
        return result != null ? result : 0;
    }

    public int getInvestTransactionAmount(String id) {
        Integer result;
        try {
            result = jdbcTemplate.queryForObject(
                    "SELECT amount " +
                            "FROM transactions t " +
                            "INNER JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? " +
                            "AND p.type = \'INVEST\' LIMIT 1",
                    Integer.class,
                    id);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }

        return result != null ? result : 0;
    }

    public int getSumDepositSavingTransaction(String id) {
        Integer result;
        try {
            result = jdbcTemplate.queryForObject(
                    "SELECT sum(t.amount) " +
                            "FROM transactions t " +
                            "INNER JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? " +
                            "AND p.type = \'SAVING\' " +
                            "AND t.type = \'DEPOSIT\' LIMIT 1",
                    Integer.class,
                    id);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
        return result != null ? result : 0;
    }

    public int getSumDepositDebitTransaction(String id) {
        Integer result;
        try {
            result = jdbcTemplate.queryForObject(
                    "SELECT sum(t.amount) " +
                            "FROM transactions t " +
                            "INNER JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? " +
                            "AND p.type = \'DEBIT\' " +
                            "AND t.type = \'DEPOSIT\' LIMIT 1",
                    Integer.class,
                    id);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
        return result != null ? result : 0;
    }

    public int getSumWithdrawDebitTransaction(String id) {
        Integer result;
        try {
            result = jdbcTemplate.queryForObject(
                    "SELECT sum(t.amount) " +
                            "FROM transactions t " +
                            "INNER JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? " +
                            "AND p.type = \'DEBIT\' " +
                            "AND t.type = \'WITHDRAW\' LIMIT 1",
                    Integer.class,
                    id);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
        return result != null ? result : 0;
    }

    public int getCreditTransactionAmount(String id) {
        Integer result;
        try {
            result = jdbcTemplate.queryForObject(
                    "SELECT amount " +
                            "FROM transactions t " +
                            "INNER JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? " +
                            "AND p.type = \'CREDIT\' LIMIT 1",
                    Integer.class,
                    id);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
        return result != null ? result : 0;
    }

}
