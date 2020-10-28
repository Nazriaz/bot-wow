package com.nazriaz.botwow.serivices;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Сервис ищет шаблон в изображении
 */

@Service
public class TemplateService {

    public Mat FLOAT_MAT = Imgcodecs.imread("/home/nazriaz/Programming/Projects/BotForWow/src/main/resources/1.jpg");

    public TemplateService() throws IOException {
    }

    public Core.MinMaxLocResult matchTemplate(Mat sourceMat, Mat templateMat) {
        Mat outputImage = new Mat();
        Imgproc.matchTemplate(sourceMat, templateMat, outputImage, Imgproc.TM_CCOEFF_NORMED);
        return Core.minMaxLoc(outputImage);
    }
}
