package com.vizor.test.model.builder;

import com.vizor.test.model.Image;

import java.awt.image.BufferedImage;

public class ImageBuilder {

    /**
     * The image
     */
    private final Image image;

    /**
     * Instantiates a new ImageBuilder.
     */
    public ImageBuilder() {
        this.image = new Image();
    }

    /**
     * Sets name
     * @param name the name
     * @return the name
     */
    public ImageBuilder setName(String name) {
        image.setName(name);
        return this;
    }

    /**
     * Sets buffered image
     * @param bufferedImage the buffered image
     * @return the buffered image
     */
    public ImageBuilder setBufferedImage(BufferedImage bufferedImage) {
        image.setBufferedImage(bufferedImage);
        return this;
    }

    /**
     * Builds image
     * @return the image
     */
    public Image build() {
        return image;
    }
}
