package pro.sky.bank_star.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class BankManagerRepository {

    private final JdbcTemplate jdbcTemplate;

    public BankManagerRepository(@Qualifier("recommendationsJdbcTemplate")
                                 JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isUserOf(String id, String type) {
        Cache<String[], Boolean> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
        String[] key = {id, type};
        try {
            Integer result = jdbcTemplate.queryForObject(
                    "SELECT amount " +
                            "FROM transactions t " +
                            "INNER JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? " +
                            "AND p.type = ? LIMIT 1",
                    Integer.class,
                    id,
                    type);
            cache.put(key, result != null);
        } catch (EmptyResultDataAccessException e) {
            cache.put(key, false);
        }
        return cache.getIfPresent(key);
    }

    public boolean isActiveUserOf(String id, String type, int limit) {
        Cache<String[], Boolean> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
        String[] key = {id, type, String.valueOf(limit)};
        try {
            Integer result = jdbcTemplate.queryForObject(
                    "SELECT count(amount) " +
                            "FROM transactions t " +
                            "INNER JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? " +
                            "AND p.type = ? LIMIT 1",
                    Integer.class,
                    id,
                    type);
            cache.put(key, result != null && result >= limit);
        } catch (EmptyResultDataAccessException e) {
            cache.put(key, false);
        }
        return cache.getIfPresent(key);
    }

    public boolean transactionSumCompare(String id, String typeProduct, String typeTransactions,
                                         String comparisonType, int number) {
        Cache<String[], Boolean> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
        String[] key = {id, typeProduct, typeTransactions, comparisonType, String.valueOf(number)};
        try {
            Integer result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) " +
                            "FROM transactions t " +
                            "INNER JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE p.type = ? " +
                            "AND t.type = ? " +
                            "GROUP BY t.user_id " +
                            "HAVING t.user_id = ? " +
                            "AND SUM(t.AMOUNT) " + comparisonType + " ? LIMIT 1",
                    Integer.class,
                    typeProduct,
                    typeTransactions,
                    id,
                    number
            );
            cache.put(key, result != null);
        } catch (EmptyResultDataAccessException e) {
            cache.put(key, false);
        }
        return cache.getIfPresent(key);
    }

    public boolean transactionSumCompareDepositWithdraw(String id, String typeProduct, String comparisonType) {
        Cache<String[], Boolean> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
        String[] key = {id, typeProduct, comparisonType};
        try {
            Integer result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(" +
                            "(SELECT " +
                            "SUM(t.AMOUNT) " +
                            "FROM TRANSACTIONS " +
                            "WHERE t.type = 'DEPOSIT') " +
                            comparisonType +
                            " (SELECT " +
                            "SUM(t.AMOUNT) " +
                            "FROM TRANSACTIONS " +
                            "WHERE t.type = 'WITHDRAW')) " +
                            "FROM transactions t " +
                            "INNER JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? " +
                            "AND p.type = ? LIMIT 1",
                    Integer.class,
                    id,
                    typeProduct
            );
            cache.put(key, result != null);
        } catch (EmptyResultDataAccessException e) {
            cache.put(key, false);
        }
        return cache.getIfPresent(key);
    }
}
