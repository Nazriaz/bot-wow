package com.nazriaz.botwow.controller;

import com.nazriaz.botwow.serivices.FishingService;
import com.nazriaz.botwow.serivices.GameActionService;
import org.opencv.core.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FishingController {
    @Autowired
    private FishingService fishingService;
    @Autowired
    private GameActionService gameActionService;

    @GetMapping("/fishing")
    public void setFishing() throws InterruptedException, IOException {
        while (true) {
            gameActionService.cursorMove(100, 100);
            gameActionService.fishing();
            Thread.sleep(3000L);
            Point floatXYGuaranteed = fishingService.getFloatXYGuaranteed();
            if (floatXYGuaranteed.x < 0) {
                System.out.println("Поплывок не найден");
            }
            System.out.println("Поплывок найден x: " + floatXYGuaranteed.x + ", y: " + floatXYGuaranteed.y);
            boolean floatBited = fishingService.checkFloatBited(floatXYGuaranteed);
            if (!floatBited) {
                System.out.println("Не клюет!");
            }
            System.out.println("Клюет!");
            gameActionService.cursorMove((int) floatXYGuaranteed.x + 5, (int) floatXYGuaranteed.y + 5);
            gameActionService.shiftPlusRight();


        }
    }
}
