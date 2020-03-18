import java.awt.GridLayout;

import javax.swing.JFrame;

public class Window extends JFrame {

    Screen screen;

    public Window(int width, int height, String title) {
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setGridLayout();
    }

    public void setGridLayout() {
        setLayout(new GridLayout(1,1,0,0));
        screen = new Screen(this);

        add(screen);
        setVisible(true);
    }
}