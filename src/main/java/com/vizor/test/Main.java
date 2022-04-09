package com.vizor.test;

import com.vizor.test.exception.ControllerException;
import com.vizor.test.gui.ImageGalleryFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final String FRAME_TITLE = "DT Developer Test";

    public void run() {
        try {
            JFrame frame = new ImageGalleryFrame(FRAME_TITLE, WIDTH, HEIGHT);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        } catch (ControllerException exception) {
            LOGGER.error(exception.getMessage());
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main()::run);
    }
}
