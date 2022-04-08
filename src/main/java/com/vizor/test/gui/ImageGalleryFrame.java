package com.vizor.test.gui;

import com.vizor.test.controller.ImageController;
import com.vizor.test.exception.ApplicationException;
import com.vizor.test.exception.ControllerException;
import com.vizor.test.exception.ServiceException;
import com.vizor.test.model.Image;
import com.vizor.test.repository.ImageRepository;
import com.vizor.test.repository.impl.ImageRepositoryImpl;
import com.vizor.test.service.ImageService;
import com.vizor.test.service.impl.ImageServiceImpl;
import com.vizor.test.util.ImageResizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.List;

public class ImageGalleryFrame extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Integer width;
    private final Integer height;
    private final JButton addButton;
    private final ImageController imageController;

    private JScrollPane scrollPane;
    private JPanel headerPanel;
    private JPanel mainContentPanel;


    public ImageGalleryFrame(String title, Integer width, Integer height) throws ControllerException { // TODO: 08.04.2022 Bad idea add exception in constructor!!!
        super(title);
        this.width = width;
        this.height = height;
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

    private void mainContentPanelInitialization() throws ControllerException {
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new GridLayout(0,4,2,2));
        mainContentPanel.setBackground(Color.WHITE);
        List<Image> images = imageController.getImageList();
        fillingTheContentSectionWithImages(images);
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
        imageButton.setIcon(new ImageIcon(ImageResizer.getResizedImage(image.getBufferedImage())));
        imageButton.addActionListener(e -> {
            JFrame frame = new JFrame(image.getName());
            JPanel picturePanel = new JPanel();
            frame.add(picturePanel.add(new JLabel(new ImageIcon(image.getBufferedImage()))));
            frame.setSize(new Dimension(image.getBufferedImage().getWidth() + 20, image.getBufferedImage().getHeight() + 45));
            frame.setMinimumSize(new Dimension(image.getBufferedImage().getWidth(), image.getBufferedImage().getHeight()));
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(2, 2));
        panel.add(imageButton, BorderLayout.CENTER);
        panel.add(new JLabel(image.getName()), BorderLayout.SOUTH);
        mainContentPanel.add(panel);
    }

    private void fillingTheContentSectionWithImages(List<Image> images) {
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
                } catch (ServiceException exception) {
                    LOGGER.error(String.format("Action listener error %s", exception.getMessage()));
                }
            }
            try {
                mainContentPanel.removeAll();
                List<Image> images = imageController.getImageList();
                fillingTheContentSectionWithImages(images);
                mainContentPanel.revalidate();
            } catch (ControllerException exception) {
                LOGGER.error(String.format("Error during new file saving process %s", exception.getMessage()));
            }
        });
    }
}
