package com.vizor.test.helper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageManagerHelper {
    private static final int IMAGE_WIDTH = 300;
    private static final int IMAGE_HEIGHT = 140;

    private ImageManagerHelper() {
    }

    public static BufferedImage getThumbnailImage(BufferedImage image) {
        BufferedImage thumbnailImage = new BufferedImage(IMAGE_WIDTH,
                IMAGE_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = thumbnailImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(image, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
        graphics.dispose();
        return thumbnailImage;
    }
}
