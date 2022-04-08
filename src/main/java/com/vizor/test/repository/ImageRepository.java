package com.vizor.test.repository;

import com.vizor.test.exception.DataException;
import com.vizor.test.model.Image;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ImageRepository {

    /**
     * Add image
     * @param name the name
     * @param bufferedImage the buffered image
     */
    void addImage(String name, BufferedImage bufferedImage) throws DataException;

    /**
     * Gets images list
     * @return the images list
     */
    List<Image> getAllImages() throws DataException;
}
