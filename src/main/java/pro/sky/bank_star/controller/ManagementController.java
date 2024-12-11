package pro.sky.bank_star.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.bank_star.dto.Info;
import pro.sky.bank_star.service.ManagementService;

@RestController
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    ManagementService managementService;

    @PostMapping("/clear-caches")
    public void clearCaches() {
        managementService.evictAllCaches();
    }

    @GetMapping("/info")
    public Info getInfo() {
        return managementService.getInfo();
    }
}
