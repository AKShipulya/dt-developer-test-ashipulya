package com.vizor.test.service;

import com.vizor.test.exception.ServiceException;
import com.vizor.test.model.Image;

import java.io.File;
import java.util.List;

public interface ImageService {

    /**
     * Add image
     * @param file the file
     */
    void addImage(File file) throws ServiceException;

    /**
     * Gets images list
     * @return the images list
     * @throws ServiceException
     */
    List<Image> getImageList() throws ServiceException;
}
