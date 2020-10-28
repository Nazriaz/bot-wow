package com.nazriaz.botwow.serivices;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Сервис делает скриншоты
 */
@Service
public class ScreenshotService {
    static {
        System.setProperty("java.awt.headless", "false");
    }

    private Robot robot = new Robot();
    private GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
    private Rectangle rectangle = graphicsDevice.getDefaultConfiguration().getBounds();

    public ScreenshotService() throws AWTException {
        rectangle.setSize(rectangle.width * 100 / 50, rectangle.height * 100 / 50);
    }

    public byte[] getScreenshot() throws IOException {

        BufferedImage screenCapture = robot.createScreenCapture(rectangle);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(screenCapture, "jpg", baos);
        baos.flush();
        return baos.toByteArray();
    }
}
