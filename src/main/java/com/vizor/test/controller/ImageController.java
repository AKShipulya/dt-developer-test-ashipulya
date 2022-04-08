package com.vizor.test.controller;

import com.vizor.test.exception.ControllerException;
import com.vizor.test.exception.ServiceException;
import com.vizor.test.model.Image;
import com.vizor.test.service.ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

public class ImageController {

    private final static Logger LOGGER = LogManager.getLogger();

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    public void addImage(File file) throws ControllerException, ServiceException {
        imageService.addImage(file);
    }

    public List<Image> getImageList() throws ControllerException {
        try {
            return imageService.getImageList();
        } catch (ServiceException exception) {
            LOGGER.error(String.format("Images list cannot be received %s", exception.getMessage()));
            throw new ControllerException(exception);
        }
    }
}
