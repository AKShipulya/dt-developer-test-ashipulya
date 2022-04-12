package com.vizor.test.service.impl;

import com.vizor.test.exception.ServiceException;
import com.vizor.test.model.Image;
import com.vizor.test.repository.ImageRepository;
import com.vizor.test.service.ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageServiceImpl implements ImageService {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FILE_NAME_REGEX = "\\.";

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void addImage(File file) {
        String name = file.getName().split(FILE_NAME_REGEX, 2)[0];
        try {
            imageRepository.addImage(name, ImageIO.read(file));
        } catch (IOException exception) {
            LOGGER.error(String.format("Error during new image saving %s", exception.getMessage()));
            throw new ServiceException(exception);
        }
        LOGGER.debug("Service: image {} has been added", file.getName());
    }

    @Override
    public List<Image> getImageList() {
        LOGGER.debug("Service: list of images has been received");
        return imageRepository.getAllImages();
    }
}
