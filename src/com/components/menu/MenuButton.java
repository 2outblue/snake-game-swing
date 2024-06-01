package com.components.menu;

public class MenuButton extends BaseMenuButton {

//    private Image image;
//
//    private final int radius;
//    private boolean isHovered = false;
//    private boolean isClicked = false;

    public MenuButton(String path) {
        super(path);
//        this.radius = 20;
//
//
//        try {
//            this.image = ImageIO.read(new File(ComponentConst.BUTTON_1_PLAY));
////            System.out.println("loaded");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        this.setHorizontalTextPosition(JButton.CENTER);
////        this.setVerticalTextPosition(JButton.CENTER);
//
//
////        this.setBorder(new RoundedBorder(50));
////        this.setBorderPainted(false);
////        this.setContentAreaFilled(false);
//        setFocusPainted(false);
//        setContentAreaFilled(false);
//        setBorderPainted(false);
//        initMouseListeners();
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        Graphics2D g2 = (Graphics2D) g.create();
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        // Draw the background image within the rounded rectangle
//        if (image != null) {
//            g2.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
//            g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
//        }
//
//        g2.dispose();
//    }
//
//    @Override
//    protected void paintBorder(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g.create();
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        // Set border color based on hover or click state
//        if (isClicked) {
//            g2.setColor(Color.DARK_GRAY);
//        } else if (isHovered) {
//            g2.setColor(Color.LIGHT_GRAY);
//        } else {
//            g2.setColor(getForeground());
//        }
//
//        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
//        g2.dispose();
//    }
//
//    private void initMouseListeners() {
//        this.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                isHovered = true;
//                repaint();
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                isHovered = false;
//                repaint();
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                isClicked = true;
//                repaint();
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                isClicked = false;
//                repaint();
//            }
//        });
//    }
}
