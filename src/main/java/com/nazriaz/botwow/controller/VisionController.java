package com.nazriaz.botwow.controller;

import com.nazriaz.botwow.mapper.MatMapper;
import com.nazriaz.botwow.serivices.ImageService;
import com.nazriaz.botwow.serivices.ScreenshotService;
import com.nazriaz.botwow.serivices.TemplateService;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.io.IOException;

@Controller
public class VisionController {

    private MatMapper matMapper;
    private TemplateService templateService;
    private ScreenshotService screenshotService;
    private ImageService imageService;

    public VisionController(MatMapper matMapper,
                            TemplateService templateService,
                            ScreenshotService screenshotService,
                            ImageService imageService) {
        this.matMapper = matMapper;
        this.templateService = templateService;
        this.screenshotService = screenshotService;
        this.imageService = imageService;
    }

    @GetMapping(value = "/vision", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    byte[] getImage() throws IOException {
        return screenshotService.getScreenshot();
    }

    @GetMapping(value = "/float", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    byte[] showFloat() throws IOException {
        byte[] screenShotBytes = screenshotService.getScreenshot();
        Mat floatShotMat = templateService.FLOAT_MAT;
        Mat screenShotMat = matMapper.matFromArray(screenShotBytes);
        Core.MinMaxLocResult result = templateService.matchTemplate(screenShotMat, floatShotMat);

        int x = (int) result.maxLoc.x;
        int y = (int) result.maxLoc.y;
        int width = x + floatShotMat.width();
        int height = y + floatShotMat.height();
        System.out.print("height = " + height);
        System.out.println(" width = " + width);
        Rectangle rectangleToMark = new Rectangle(x, y, width, height);
        Mat resultMat = imageService.markRectangle(screenShotMat, rectangleToMark);

        return matMapper.arrayFromMat(resultMat);

    }

    @GetMapping(value = "float2", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    byte[] showFloat2() throws IOException {
        for (int i = 0; i < 100; i++) {
            showFloat();
            System.out.println("i = " + i);
        }
        return showFloat();
    }
}