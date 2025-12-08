package pokladna.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame {

    private JTextField txt;
    private PopUpKeyboard keyboard;
    private String title = "";

    public GUI() {
        initGUI();
    }

    private void initGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        cachierLogin();
    }

    private void cachierLogin() {
        JPanel loginPanel = new JPanel(new FlowLayout());
        txt = new JTextField(20);
        keyboard = new PopUpKeyboard(txt);
        keyboard.setLocationRelativeTo(this);

        txt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Point p = txt.getLocationOnScreen();
                p.y += 30;
                keyboard.setLocation(p);
                keyboard.setVisible(true);
            }
        });
        setLayout(new FlowLayout());
        loginPanel.add(txt);
        add(loginPanel, BorderLayout.CENTER);

        title = "Příhlášení pokladníka";
    }
}
