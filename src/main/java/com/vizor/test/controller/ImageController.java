package com.vizor.test.controller;

import com.vizor.test.model.Image;
import com.vizor.test.service.ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

public class ImageController {

    private static final Logger LOGGER = LogManager.getLogger();

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    public void addImage(File file) {
        imageService.addImage(file);
        LOGGER.debug("Controller: image {} has been added", file.getName());
    }

    public List<Image> getImageList() {
        LOGGER.debug("Controller: list of images has been received");
        return imageService.getImageList();
    }
}
