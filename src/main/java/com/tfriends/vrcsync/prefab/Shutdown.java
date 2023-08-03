package com.tfriends.vrcsync.prefab;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Shutdown {

    @Scheduled(cron = "30 10 21 * * *")
    public void autoClose() {
        System.exit(0);
    }
}
