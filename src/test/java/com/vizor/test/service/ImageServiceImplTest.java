package com.vizor.test.service;

import com.vizor.test.model.Image;
import com.vizor.test.model.builder.ImageBuilder;
import com.vizor.test.repository.ImageRepository;
import com.vizor.test.repository.impl.ImageRepositoryImpl;
import com.vizor.test.service.impl.ImageServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    private static final String FILE_PATH = "src/test/resources/TEST_IMAGE.png";
    private static final ImageBuilder IMAGE_BUILDER = new ImageBuilder();
    private static final File TEST_FILE = new File(FILE_PATH);
    private static final List<Image> EXPECTED_IMAGE_LIST = new ArrayList<>();

    @Before
    public void init() throws IOException {
        BufferedImage bufferedImage = ImageIO.read(TEST_FILE);
        IMAGE_BUILDER.setName(TEST_FILE.getName());
        IMAGE_BUILDER.setBufferedImage(bufferedImage);
        EXPECTED_IMAGE_LIST.add(IMAGE_BUILDER.build());
    }

    @Test
    public void testAddImageShouldAddImageFromFile() {
        //given
        ImageRepository repository = Mockito.mock(ImageRepositoryImpl.class);
        doNothing().when(repository).addImage(anyString(), anyObject());
        ImageService service = new ImageServiceImpl(repository);
        //when
        service.addImage(TEST_FILE);
        //then
        Mockito.verify(repository, times(1)).addImage(anyString(), anyObject());
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testGetImageListShouldAddImageFromFile() {
        //given
        ImageRepository repository = Mockito.mock(ImageRepositoryImpl.class);
        when(repository.getAllImages()).thenReturn(EXPECTED_IMAGE_LIST);
        ImageService service = new ImageServiceImpl(repository);
        //when
        List<Image> actual = service.getImageList();
        //then
        Assert.assertEquals(EXPECTED_IMAGE_LIST, actual);
        Mockito.verify(repository, times(1)).getAllImages();
        verifyNoMoreInteractions(repository);
    }
}
