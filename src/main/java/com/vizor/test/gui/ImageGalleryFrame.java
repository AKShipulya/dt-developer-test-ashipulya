package com.vizor.test.gui;

import com.vizor.test.controller.ImageController;
import com.vizor.test.model.Image;
import com.vizor.test.model.builder.ImageBuilder;
import com.vizor.test.repository.ImageRepository;
import com.vizor.test.repository.impl.ImageRepositoryImpl;
import com.vizor.test.service.ImageService;
import com.vizor.test.service.impl.ImageServiceImpl;
import com.vizor.test.util.ImageResizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageGalleryFrame extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ADD_BUTTON_NAME = "Add image";
    private static final String SEARCH_BUTTON_NAME = "Search image";
    private static final String RESET_BUTTON_NAME = "Reset search";
    private static final int LARGE_IMAGE_DIMENSION_WIDTH = 1024;
    private static final int LARGE_IMAGE_DIMENSION_HEIGHT = 768;

    private final JButton addButton;
    private final JButton searchButton;
    private final JButton searchResetButton;
    private final JTextField searchField;
    private final ImageController imageController;
//    private final JButton nextPageButton;
//    private final JButton previousPageButton;

    private JScrollPane scrollPane;
    private JPanel headerPanel;
    private JPanel mainContentPanel;
    private JPanel bottomPanel;


    public ImageGalleryFrame(String title) {
        super(title);
        ImageRepository repository = new ImageRepositoryImpl();
        ImageService service = new ImageServiceImpl(repository);
        imageController = new ImageController(service);

        addButton = new JButton(ADD_BUTTON_NAME);
        searchButton = new JButton(SEARCH_BUTTON_NAME);
        searchResetButton = new JButton(RESET_BUTTON_NAME);
        searchField = new JTextField(30);
//        nextPageButton = new JButton(">");
//        previousPageButton = new JButton("<");

        headerPanelInitialization();
        mainContentPanelInitialization();
        bottomPanelInitialization();
        contentContainerInitialization();
        addNewImageActionListener();
        searchButtonActionListener();
        searchResetActionListener();
//        nextPageActionListener();
    }


    private void headerPanelInitialization() {
        headerPanel = new JPanel();
        JPanel blankPanel = new JPanel();
        blankPanel.setBorder(new EmptyBorder(0, 0, 0, 300));
        blankPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        headerPanel.setBackground(Color.WHITE);
        addButton.setBackground(Color.WHITE);
        searchButton.setBackground(Color.WHITE);
        searchResetButton.setBackground(Color.WHITE);

        headerPanel.add(addButton);
        headerPanel.add(blankPanel);
        headerPanel.add(searchField);
        headerPanel.add(searchButton);
        headerPanel.add(searchResetButton);
    }

    private void mainContentPanelInitialization() {
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new GridLayout(0, 4, 2, 2));
        mainContentPanel.setBackground(Color.WHITE);
        List<Image> images = imageController.getImageList();
        imageListInitialization(images);
    }

    private void bottomPanelInitialization() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
//        previousPageButton.setBackground(Color.WHITE);
//        nextPageButton.setBackground(Color.WHITE);
//        bottomPanel.add(previousPageButton, BorderLayout.CENTER);
//        bottomPanel.add(nextPageButton, BorderLayout.CENTER);
    }

    private void contentContainerInitialization() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout(0, 1));
        container.setBackground(Color.GRAY);
        container.add(new JPanel());
        container.add(headerPanel, BorderLayout.NORTH);
        container.add(mainContentPanel);
        scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
        container.add(scrollPane);
        container.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void createSeparateButtonForImage(Image image) {
        JButton imageButton = new JButton();
        imageButton.setIcon(new ImageIcon(ImageResizer.getResizedImageForButton(image.getBufferedImage())));
        imageButton.setOpaque(false);
        imageButton.setContentAreaFilled(false);
        imageButton.addActionListener(e -> {
            JFrame frame = new JFrame(image.getName());
            JPanel picturePanel = new JPanel();
            //frame settings for large images - if image is too large here it will resize to 1024x768
            if (image.getBufferedImage().getHeight() > 768 && image.getBufferedImage().getWidth() > 1024) {
                frame.add(picturePanel.add(new JLabel(new ImageIcon(ImageResizer.getResizedImageForLargeImage(image.getBufferedImage())))));
                frame.setSize(new Dimension(LARGE_IMAGE_DIMENSION_WIDTH, LARGE_IMAGE_DIMENSION_HEIGHT));
                frame.setMinimumSize(new Dimension(800, 600));
            } else {
                frame.add(picturePanel.add(new JLabel(new ImageIcon(image.getBufferedImage()))));
                frame.setSize(new Dimension(image.getBufferedImage().getWidth() + 20, image.getBufferedImage().getHeight() + 45));
                frame.setMinimumSize(new Dimension(image.getBufferedImage().getWidth(), image.getBufferedImage().getHeight()));
            }
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });

        JPanel imageBox = new JPanel();
        imageBox.setBackground(Color.WHITE);
        imageBox.setLayout(new BorderLayout(2, 2));
        imageBox.add(imageButton, BorderLayout.CENTER);
        imageBox.add(new JLabel(image.getName()), BorderLayout.SOUTH);
        mainContentPanel.add(imageBox);
    }

    private void imageListInitialization(List<Image> images) {
        for (Image image : images) {
            createSeparateButtonForImage(image);
        }
        mainContentPanel.revalidate();
    }

    private void addNewImageActionListener() {
        addButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter(".png", "png"));
            int choice = fileChooser.showDialog(null, ADD_BUTTON_NAME);
            if (choice == JFileChooser.APPROVE_OPTION) {
                try {
                    imageController.addImage(fileChooser.getSelectedFile());
                    BufferedImage newBufferedImage = ImageIO.read(fileChooser.getSelectedFile());
                    Image newImage = new ImageBuilder().setName(fileChooser.getSelectedFile().getName())
                            .setBufferedImage(newBufferedImage)
                            .build();
                    createSeparateButtonForImage(newImage);
                    mainContentPanel.revalidate();
                } catch (IOException exception) {
                    LOGGER.error("\"Add new image\" action listener error: {}", exception.getMessage());
                }
            }
        });
    }

    private void searchButtonActionListener() {
        searchButton.addActionListener(e -> {
            String searchName = searchField.getText();
            List<Image> images = new ArrayList<>();
            for (Image image : imageController.getImageList()) {
                if (image.getName().toLowerCase().contains(searchName.toLowerCase())) {
                    mainContentPanel.removeAll();
                    images.add(image);
                    imageListInitialization(images);
                    mainContentPanel.revalidate();
                    LOGGER.debug("Image {} has been found", image.getName());
                }
            }
        });
    }

    private void searchResetActionListener() {
        searchResetButton.addActionListener(e -> {
            mainContentPanel.removeAll();
            List<Image> images = imageController.getImageList();
            imageListInitialization(images);
            mainContentPanel.revalidate();
        });
    }

//    private void nextPageActionListener() {
//        nextPageButton.addActionListener(e -> {
//        });
//    }
}
