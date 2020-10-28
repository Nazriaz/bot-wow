package com.nazriaz.botwow.serivices;


import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.KeyEvent;

@Service
public class GameActionService {
    private Robot robot;

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void fishing() {
        robot.keyPress(KeyEvent.VK_5);
        robot.keyRelease(KeyEvent.VK_5);
    }

    public void shiftPlusRight() {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.mousePress(KeyEvent.BUTTON3_MASK);
        robot.mouseRelease(KeyEvent.BUTTON3_MASK);
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }

    public void cursorMove(Integer x, Integer y) {
        robot.mouseMove(x, y);
    }
}
