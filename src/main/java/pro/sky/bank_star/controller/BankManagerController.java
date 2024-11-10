package pro.sky.bank_star.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.bank_star.dto.BankProduct;
import pro.sky.bank_star.service.BankManagerService;

import java.util.List;
import java.util.Map;

@RestController
public class BankManagerController {

    @Autowired
    private BankManagerService bankManagerService;

    @GetMapping("/recommendation/{user_id}")
    public ResponseEntity<Map<String, List<BankProduct>>> getListProductsBank(@PathVariable(value = "user_id")
                                                                                  String id) {
        return ResponseEntity.ok(bankManagerService.getListProductsBank(id));
    }
}
