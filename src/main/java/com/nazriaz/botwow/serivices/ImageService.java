package com.nazriaz.botwow.serivices;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

import java.awt.*;

/**
 * Сервис отрисовки
 */
@Service
public class ImageService {

    /**
     * Рисует прямоугольник по координатам x y
     * шириной и высотой переданного прямоугольника в копию mat чтоб не портить исходное изображение
     */
    public Mat markRectangle(Mat mat, Rectangle rectangle) {
        Mat output = new Mat();
        mat.copyTo(output);
        double x = (double) rectangle.x;
        double y = (double) rectangle.y;
        double width = (double) rectangle.width;
        double height = (double) rectangle.height;
        Imgproc.rectangle(output, new Point(x, y), new Point(width, height), new Scalar(255, 240, 0));
        return output;
    }
}
