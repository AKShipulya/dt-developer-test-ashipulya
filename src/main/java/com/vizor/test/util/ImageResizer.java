package com.vizor.test.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageResizer {

    private static final int ICON_IMAGE_WIDTH = 180;
    private static final int ICON_IMAGE_HEIGHT = 180;
    private static final int LARGE_IMAGE_WIDTH = 1024;
    private static final int LARGE_IMAGE_HEIGHT = 768;

    private ImageResizer() {
    }

    public static BufferedImage getResizedImageForButton(BufferedImage image) {
        return getResizedImage(image, ICON_IMAGE_WIDTH, ICON_IMAGE_HEIGHT);
    }

    public static BufferedImage getResizedImageForLargeImage(BufferedImage image) {
        return getResizedImage(image, LARGE_IMAGE_WIDTH, LARGE_IMAGE_HEIGHT);
    }

    private static BufferedImage getResizedImage(BufferedImage image, int largeImageWidth, int largeImageHeight) {
        BufferedImage resizedImage = new BufferedImage(largeImageWidth, largeImageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(image, 0, 0, largeImageWidth, largeImageHeight, null);
        graphics.dispose();
        return resizedImage;
    }
}
