package com.vizor.test.model;

import java.awt.image.BufferedImage;

public class Image {

    private String name;
    private BufferedImage bufferedImage;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets buffered image
     *
     * @return the buffered image
     */
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    /**
     * Sets buffered image
     *
     * @param bufferedImage the buffered image
     */
    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Image image = (Image) o;
        if (!name.equals(image.name)) {
            return false;
        }
        return bufferedImage.equals(image.bufferedImage);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + bufferedImage.hashCode();
        return result;
    }
}
