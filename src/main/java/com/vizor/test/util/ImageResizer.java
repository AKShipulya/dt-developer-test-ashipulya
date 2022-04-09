package com.vizor.test.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageResizer {

    private static final int IMAGE_WIDTH = 180;
    private static final int IMAGE_HEIGHT = 180;
    private static final int LARGE_IMAGE_WIDTH = 1024;
    private static final int LARGE_IMAGE_HEIGHT = 768;

    private ImageResizer() {
    }

    public static BufferedImage getResizedImageForButton(BufferedImage image) {
        BufferedImage resizedImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(image, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
        graphics.dispose();
        return resizedImage;
    }

    public static BufferedImage getResizedImageForLargeImage(BufferedImage image) {
        BufferedImage resizedImage = new BufferedImage(LARGE_IMAGE_WIDTH, LARGE_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(image, 0, 0, LARGE_IMAGE_WIDTH, LARGE_IMAGE_HEIGHT, null);
        graphics.dispose();
        return resizedImage;
    }
}
