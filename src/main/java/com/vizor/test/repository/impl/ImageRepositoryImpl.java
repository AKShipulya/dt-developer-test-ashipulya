package com.vizor.test.repository.impl;

import com.vizor.test.exception.ApplicationException;
import com.vizor.test.exception.DataException;
import com.vizor.test.model.Image;
import com.vizor.test.model.builder.ImageBuilder;
import com.vizor.test.repository.ImageRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageRepositoryImpl implements ImageRepository {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PATH = "assets";

    @Override
    public void addImage(String name, BufferedImage bufferedImage) throws DataException {
        try {
            File file = new File(PATH.concat("\\").concat(name).concat(".png"));
            File[] files = new File(PATH).listFiles(path -> {
                String imageName = path.getName();
                return path.isFile() &&
                        imageName.equals(name.concat(".png")) &&
                        imageName.endsWith(".png");
            });
            if (files != null && files.length != 0) {
                throw new DataException("Image with this name is exists");
            }
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException exception) {
            LOGGER.error(String.format("Error %s", exception.getMessage()));
            throw new DataException(exception);
        }
        LOGGER.info("New image added");
    }

    @Override
    public List<Image> getAllImages() throws DataException {
        File[] files = new File(PATH).listFiles(path -> {
            String imageName = path.getName().toLowerCase();
            return path.isFile() && imageName.endsWith(".png");
        });
        LOGGER.info("List of images has been received");
        return initImageList(files);
    }

    private List<Image> initImageList(File[] files) throws DataException {
        List<Image> images = new ArrayList<>();
        if (files == null) {
            return images;
        }
        int count = 0;
        while (count < files.length) {
            try {
                String name = files[count].getName().split("\\.", 2)[0];
                images.add(new ImageBuilder().setName(name)
                        .setBufferedImage(ImageIO.read(files[count]))
                        .build());
            } catch (IOException exception) {
                LOGGER.error(String.format("Images uploading error %s", exception.getMessage()));
                throw new DataException(exception);
            }
            count++;
        }
        return images;
    }
}
