package pro.sky.bank_star.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import pro.sky.bank_star.dto.Info;

@Service
public class ManagementServiceImp implements ManagementService {

    @Autowired
    CacheManager cacheManager;
    @Autowired
    BuildProperties buildProperties;

    @Override
    public void evictAllCaches() {
        cacheManager.getCacheNames()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }

    @Override
    public Info getInfo() {
        return new Info(buildProperties.getName(), buildProperties.getVersion());
    }
}
