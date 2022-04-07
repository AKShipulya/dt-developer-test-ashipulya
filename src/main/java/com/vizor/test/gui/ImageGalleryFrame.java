package com.vizor.test.gui;

import javax.swing.*;
import java.awt.*;

public class ImageGalleryFrame extends JFrame {

    private final Integer width;
    private final Integer height;
    private final JButton addButton;

    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;

    public ImageGalleryFrame(String title, Integer width, Integer height) {
        super(title);
        this.width = width;
        this.height = height;

        addButton = new JButton("Add image");

        initTopPanel();
        initMiddlePanel();
        initCenterPanel();
        initBottomPanel();
        initContainer();
    }

    private void initTopPanel() {
        topPanel = new JPanel();
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    }

    private void initMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setBackground(Color.DARK_GRAY);
        middlePanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 4, 4));

        JPanel gridPanel = new JPanel();
        gridPanel.setBackground(Color.DARK_GRAY);
        gridPanel.setLayout(new GridLayout(4, 1, 5, 5));
        gridPanel.add(addButton);
        middlePanel.add(gridPanel);
    }

    private void initCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.DARK_GRAY);
        centerPanel.setLayout(new GridLayout(5, 5, 4, 4));
//        List<Image> images = imageController.getImageList();
//        initImageList(images);
    }

    private void initBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);
//        bottomPanel.add(previousPageButton, BorderLayout.CENTER);
//        bottomPanel.add(page, BorderLayout.CENTER);
//        bottomPanel.add(nextPageButton, BorderLayout.CENTER);
    }

    private void initContainer() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout(8, 6));
        container.setBackground(Color.DARK_GRAY);

        container.add(new JPanel());
        container.add(topPanel, BorderLayout.NORTH);
        container.add(middlePanel, BorderLayout.WEST);
        container.add(centerPanel);
        container.add(bottomPanel, BorderLayout.SOUTH);
    }
}
