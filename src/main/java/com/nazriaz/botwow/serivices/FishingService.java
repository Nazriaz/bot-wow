package com.nazriaz.botwow.serivices;

import com.nazriaz.botwow.mapper.MatMapper;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FishingService {
    @Autowired
    private ScreenshotService screenshotService;
    @Autowired
    private MatMapper matMapper;
    @Autowired
    private TemplateService templateService;
    private int floatTolerance = 5;
    private long checkSleep = 200L;
    private int missBeforePeck = 3;

    public Point getFloatXY() throws IOException {
        byte[] screenshotBytes = screenshotService.getScreenshot();
        Mat screenshotMat = matMapper.matFromArray(screenshotBytes);
        Core.MinMaxLocResult matchResult = templateService.matchTemplate(screenshotMat, templateService.FLOAT_MAT);
        Point maxLoc = matchResult.maxLoc;
        return maxLoc;
    }

    public Point getFloatXYGuaranteed() throws IOException, InterruptedException {
        int timesRemain = 5;
        Point floatXY = getFloatXY();
        Thread.sleep(200L);
        Point floatXY1 = getFloatXY();
        boolean result = checkPointsClose(floatXY, floatXY1);
        while (!result && timesRemain > 0) {
            floatXY = getFloatXY();
            Thread.sleep(200L);
            floatXY1 = getFloatXY();
            timesRemain--;
            result = checkPointsClose(floatXY, floatXY1);
        }
        return result ? floatXY : new Point(-1, -1);
    }

    public boolean checkFloatBited(Point foundedFloat) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
        boolean result = false;
        while (System.currentTimeMillis() - startTime < 30000 && !result) {
            Point currentFloatPosition = getFloatXY();
            if (!checkPointsClose(foundedFloat, currentFloatPosition)) {
                int triesCount = missBeforePeck * 2;
                int missCount = 1;
                for (int i = 0; i < triesCount; i++) {
                    Thread.sleep(checkSleep / triesCount);
                    Point tempFloat = getFloatXY();
                    if (!checkPointsClose(foundedFloat, tempFloat)) {
                        missCount++;
                    }
                }
                result = missCount >= missBeforePeck;
            }
            System.out.println("Координаты поплывка x: " + currentFloatPosition.x + ", y: " + currentFloatPosition.y);
            Thread.sleep(checkSleep);
        }
        return result;
    }

    private boolean checkPointsClose(Point first, Point second) {
        long xInRange = floatTolerance - Math.abs(Math.round(first.x - second.x));
        long yInRange = floatTolerance - Math.abs(Math.round(first.y - second.y));
        return xInRange > 0 && yInRange > 0;
    }
}
