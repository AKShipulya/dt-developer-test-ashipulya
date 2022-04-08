package com.vizor.test.service.impl;

import com.vizor.test.exception.DataException;
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

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void addImage(File file) throws ServiceException {
        String name = file.getName().split("\\.", 2)[0];
        try {
            imageRepository.addImage(name, ImageIO.read(file));
        } catch (IOException | DataException exception) {
            LOGGER.error(String.format("The file cannot be read %s", exception.getMessage()));
            throw new ServiceException(exception);
        }
    }

    @Override
    public List<Image> getImageList() throws ServiceException {
        try {
            return imageRepository.getAllImages();
        } catch (DataException exception) {
            LOGGER.error(String.format("Images list cannot be loaded %s", exception.getMessage()));
            throw new ServiceException(exception);
        }
    }
}
