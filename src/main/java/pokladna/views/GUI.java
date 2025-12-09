package pokladna.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class GUI extends JFrame {

    private PopUpKeyboard keyboard;
    private String title = "";

    public GUI() {
        initGUI();
    }

    private void initGUI() {
        title = "Přihlášení pokladníka";
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle(title);
        cashierLogin(true);
    }

    private void cashierLogin(boolean visible) {
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginPanel.setSize(400, 200);
        loginPanel.setBorder(BorderFactory.createTitledBorder("Login"));
        JLabel welcomeLabel = new JLabel("Welcome back. Work harder!");
        JPasswordField password = new JPasswordField(20);
        Font fontpass = password.getFont();
        float size = fontpass.getSize() + 15.0f;
        password.setFont(fontpass.deriveFont(size));
        welcomeLabel.setFont(fontpass.deriveFont(size));

        keyboard = new PopUpKeyboard(password);
        keyboard.setLocationRelativeTo(this);

        password.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Point p = password.getLocationOnScreen();
                p.y += 30;
                keyboard.setLocation(p);
                keyboard.setVisible(true);
            }
        });
        setLayout(new FlowLayout());
        loginPanel.add(welcomeLabel, BorderLayout.CENTER);
        loginPanel.add(password, BorderLayout.CENTER);
        add(loginPanel, BorderLayout.CENTER);
    }
}
