package pro.sky.bank_star.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.bank_star.dto.User;

@Repository
public class BankManagerRepository {

    private final JdbcTemplate jdbcTemplate;

    public BankManagerRepository(@Qualifier("recommendationsJdbcTemplate")
                                 JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Cacheable(value = "isUserOf", key = "#id + #type")
    public boolean isUserOf(String id, String type) {
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
            return result != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Cacheable(value = "isActiveUserOf", key = "#id + #type + #limit")
    public boolean isActiveUserOf(String id, String type, int limit) {
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
            return result != null && result >= limit;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Cacheable(value = "transactionSumCompare", key = "#id + #typeProduct + #typeTransactions + " +
            "#comparisonType + #number")
    public boolean transactionSumCompare(String id, String typeProduct, String typeTransactions,
                                         String comparisonType, int number) {
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
            return result != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Cacheable(value = "transactionSumCompareDepositWithdraw", key = "#id + #typeProduct + #comparisonType")
    public boolean transactionSumCompareDepositWithdraw(String id, String typeProduct, String comparisonType) {
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
            return result != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public User getUser(String userName) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * " +
                            "FROM users u " +
                            "WHERE u.username = ? ",
                    (resultSet, rowNum) -> {
                        User user = new User();
                        user.setId(resultSet.getString("ID"));
                        user.setUserName(resultSet.getString("USERNAME"));
                        user.setFirstName(resultSet.getString("FIRST_NAME"));
                        user.setLastName(resultSet.getString("LAST_NAME"));
                        return user;
                    },
                    userName
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
