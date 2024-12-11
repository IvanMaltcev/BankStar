package pro.sky.bank_star.service;

import pro.sky.bank_star.dto.Info;

public interface ManagementService {

    void evictAllCaches();

    Info getInfo();
}
