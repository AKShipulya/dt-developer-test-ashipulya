package com.vizor.test.gui;

import javax.swing.*;
import java.awt.*;

public class ImageGalleryFrame extends JFrame {

    private final Integer width;
    private final Integer height;
    private final JButton addButton;
    private final JScrollBar scrollBar;

    private JPanel headerPanel;
    private JPanel mainContentPanel;
    private JPanel sidePanel;

    public ImageGalleryFrame(String title, Integer width, Integer height) {
        super(title);
        this.width = width;
        this.height = height;

        addButton = new JButton("Add image");
        scrollBar = new JScrollBar();

        initHeaderPanel();
        initMainContentPanel();
        initSidePanel();
        initContainer();
    }


    private void initHeaderPanel() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 4));

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(1, 1, 2, 2));
        addButton.setBackground(Color.LIGHT_GRAY);
        gridPanel.add(addButton);
        headerPanel.add(gridPanel);
    }

    private void initMainContentPanel() {
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new GridLayout(5, 5, 4, 4));
        mainContentPanel.setBackground(Color.RED);
//        List<Image> images = imageController.getImageList();
//        initImageList(images);
    }

    private void initSidePanel() {
        sidePanel = new JPanel();
        sidePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        sidePanel.add(scrollBar);
    }

    private void initContainer() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout(2,2));

        container.add(new JPanel());
        container.add(headerPanel, BorderLayout.NORTH);
        container.add(mainContentPanel);
        container.add(sidePanel, BorderLayout.EAST);
    }
}
