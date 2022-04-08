package com.vizor.test.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageHelper {
    private static final int IMAGE_WIDTH = 300;
    private static final int IMAGE_HEIGHT = 140;

    private ImageHelper() {
    }

    public static BufferedImage getResizedImage(BufferedImage image) {
        BufferedImage resizedImage = new BufferedImage(IMAGE_WIDTH,
                IMAGE_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(image, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
        graphics.dispose();
        return resizedImage;
    }
}
