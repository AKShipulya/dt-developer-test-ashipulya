package com.vizor.test.gui;

import com.vizor.test.controller.ImageController;
import com.vizor.test.exception.ControllerException;
import com.vizor.test.exception.ServiceException;
import com.vizor.test.helper.ImageManagerHelper;
import com.vizor.test.model.Image;
import com.vizor.test.repository.ImageRepository;
import com.vizor.test.repository.impl.ImageRepositoryImpl;
import com.vizor.test.service.ImageService;
import com.vizor.test.service.impl.ImageServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageGalleryFrame extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();

    private final Integer width;
    private final Integer height;
    private final JButton addButton;
    private final ImageController imageController;

    private JPanel headerPanel;
    private JPanel mainContentPanel;
    private boolean loaded = false;


    public ImageGalleryFrame(String title, Integer width, Integer height) throws ControllerException {
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
        actionListenerInitialization();
    }


    private void headerPanelInitialization() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 4));

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(1, 1, 2, 2));
        addButton.setBackground(Color.LIGHT_GRAY);
        gridPanel.add(addButton);
        headerPanel.add(gridPanel);
    }

    private void mainContentPanelInitialization() throws ControllerException {
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new GridLayout(5, 5, 10, 10));
        mainContentPanel.setBackground(Color.WHITE);

        List<Image> images = imageController.getImageList();
        imageListInitialization(images);
    }

    private void contentContainerInitialization() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout(2, 2));

        container.add(new JPanel());
        container.add(headerPanel, BorderLayout.NORTH);
        container.add(mainContentPanel);
    }

    private void imageInitialization(Image image) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(ImageManagerHelper.getThumbnailImage(image.getBufferedImage())));
        button.addActionListener(e -> {
            JFrame frame = new JFrame(image.getName());
            JScrollPane scrollPane = new JScrollPane(new JLabel(new ImageIcon(image.getBufferedImage())));
            frame.add(scrollPane);
            frame.setMinimumSize(new Dimension(width, height));
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(2, 2));
        panel.add(button, BorderLayout.CENTER);
        panel.add(new JLabel(image.getName()), BorderLayout.SOUTH);
        mainContentPanel.add(panel);
    }

    private void imageListInitialization(List<Image> images) {
        if (!loaded) {
            loaded = true;
            THREAD_POOL.submit(() -> {
                for (Image image : images) {
                    imageInitialization(image);
                }
                mainContentPanel.revalidate();
                loaded = false;
            });
        }
    }

    private void actionListenerInitialization() {
        addButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter(".png", "png"));
            int ret = fileChooser.showDialog(null, "Add image");
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    imageController.addImage(fileChooser.getSelectedFile());
                } catch (ControllerException | ServiceException exception) {
                    LOGGER.error(String.format("Action listener error %s", exception.getMessage()));
                }
            }
            mainContentPanel.revalidate();
        });
    }
}
