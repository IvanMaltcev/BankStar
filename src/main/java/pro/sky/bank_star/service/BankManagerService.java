package pro.sky.bank_star.service;

import pro.sky.bank_star.dto.BankProduct;

import java.util.List;
import java.util.Map;

public interface BankManagerService {

    Map<String, List<BankProduct>> getListProductsBank(String id);
}
