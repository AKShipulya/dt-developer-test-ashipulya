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
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImageGalleryFrame extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger();

    private final JButton addButton;
    private final ImageController imageController;

    private JScrollPane scrollPane;
    private JPanel headerPanel;
    private JPanel mainContentPanel;


    public ImageGalleryFrame(String title) {
        super(title);
        ImageRepository repository = new ImageRepositoryImpl();
        ImageService service = new ImageServiceImpl(repository);
        imageController = new ImageController(service);

        addButton = new JButton("Add image");

        headerPanelInitialization();
        mainContentPanelInitialization();
        contentContainerInitialization();
        addNewImageWithFileChooser();
    }


    private void headerPanelInitialization() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(1, 1));
        addButton.setBackground(Color.WHITE);
        gridPanel.add(addButton);
        headerPanel.add(gridPanel);
    }

    private void mainContentPanelInitialization() {
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new GridLayout(0, 4, 2, 2));
        mainContentPanel.setBackground(Color.WHITE);
        List<Image> images = imageController.getImageList();
        imageListInitialization(images);
    }

    private void contentContainerInitialization() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout(0, 1));
        container.setBackground(Color.GRAY);
        container.add(new JPanel());
        container.add(headerPanel, BorderLayout.NORTH);
        container.add(mainContentPanel);
        scrollPane = new JScrollPane(mainContentPanel);
        container.add(scrollPane);
    }

    private void openImageSeparateWindow(Image image) {
        JButton imageButton = new JButton();
        imageButton.setIcon(new ImageIcon(ImageResizer.getResizedImageForButton(image.getBufferedImage())));
        imageButton.addActionListener(e -> {
            JFrame frame = new JFrame(image.getName());
            JPanel picturePanel = new JPanel();

            if (image.getBufferedImage().getHeight() > 768 && image.getBufferedImage().getWidth() > 1024) {
                frame.add(picturePanel.add(new JLabel(new ImageIcon(ImageResizer.getResizedImageForLargeImage(image.getBufferedImage())))));
                frame.setSize(new Dimension(1024, 768));
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
        imageBox.setLayout(new BorderLayout(2, 2));
        imageBox.add(imageButton, BorderLayout.CENTER);
        imageBox.add(new JLabel(image.getName()), BorderLayout.SOUTH);
        mainContentPanel.add(imageBox);
    }

    private void imageListInitialization(List<Image> images) {
        for (Image image : images) {
            openImageSeparateWindow(image);
        }
        mainContentPanel.revalidate();
    }

    private void addNewImageWithFileChooser() {
        addButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter(".png", "png"));
            int ret = fileChooser.showDialog(null, "Add image");
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    imageController.addImage(fileChooser.getSelectedFile());
                    BufferedImage newBufferedImage = ImageIO.read(fileChooser.getSelectedFile());
                    Image newImage = new ImageBuilder().setName(fileChooser.getSelectedFile().getName())
                            .setBufferedImage(newBufferedImage)
                            .build();
                    openImageSeparateWindow(newImage);
                    mainContentPanel.revalidate();
                } catch (Exception exception) {
                    LOGGER.error(String.format("Action listener error %s", exception.getMessage()));
                }
            }
        });
    }
}
