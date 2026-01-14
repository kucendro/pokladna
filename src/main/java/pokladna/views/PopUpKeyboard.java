package pokladna.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PopUpKeyboard extends JDialog implements ActionListener {
    private JTextField txt;
    private boolean isNumericOnly = false;

    public PopUpKeyboard(JTextField txt) {
        this.txt = txt;
        this.isNumericOnly = true;
        initKeyboard();
    }

    public PopUpKeyboard(JTextField txt, boolean numericOnly) {
        this.txt = txt;
        this.isNumericOnly = numericOnly;
        initKeyboard();
    }

    private void initKeyboard() {
        setUndecorated(false);
        setTitle("Keyboard");
        setModal(false);
        setLayout(new BorderLayout());

        if (isNumericOnly) {
            createNumericKeyboard();
        } else {
            createFullKeyboard();
        }

        pack();
    }

    private void createNumericKeyboard() {
        JPanel panel = new JPanel(new GridLayout(4, 3, 5, 5));

        for (int i = 1; i <= 9; i++) {
            createButton(Integer.toString(i), panel);
        }
        createButton("CLR", panel);
        createButton("0", panel);
        createButton("OK", panel);

        add(panel, BorderLayout.CENTER);
    }

    private void createFullKeyboard() {
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));

        JPanel row1 = new JPanel(new GridLayout(1, 10, 3, 3));
        String[] numbers = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
        for (String num : numbers) {
            createButton(num, row1);
        }

        JPanel row2 = new JPanel(new GridLayout(1, 10, 3, 3));
        String[] letters1 = { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P" };
        for (String letter : letters1) {
            createButton(letter, row2);
        }

        JPanel row3 = new JPanel(new GridLayout(1, 9, 3, 3));
        String[] letters2 = { "A", "S", "D", "F", "G", "H", "J", "K", "L" };
        for (String letter : letters2) {
            createButton(letter, row3);
        }

        JPanel row4 = new JPanel(new GridLayout(1, 7, 3, 3));
        String[] letters3 = { "Z", "X", "C", "V", "B", "N", "M" };
        for (String letter : letters3) {
            createButton(letter, row4);
        }

        JPanel row5 = new JPanel(new GridLayout(1, 3, 3, 3));
        createButton("SPACE", row5);
        createButton("DEL", row5);
        createButton("OK", row5);

        mainPanel.add(row1, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 3, 3));
        centerPanel.add(row2);
        centerPanel.add(row3);
        centerPanel.add(row4);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(row5, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void createButton(String label, JPanel panel) {
        JButton btn = new JButton(label);
        btn.addActionListener(this);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(80, 80));
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setForeground(Color.BLACK);

        if (label.equals("OK")) {
            btn.setBackground(new Color(46, 204, 113));
        } else if (label.equals("DEL") || label.equals("CLR")) {
            btn.setBackground(new Color(231, 76, 60));
        } else {
            btn.setBackground(new Color(52, 152, 219));
        }

        panel.add(btn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        String currentText = txt.getText();

        if (actionCommand.equals("DEL")) {
            if (currentText.length() > 0) {
                txt.setText(currentText.substring(0, currentText.length() - 1));
            }
        } else if (actionCommand.equals("CLR")) {
            txt.setText("");
        } else if (actionCommand.equals("OK")) {
            setVisible(false);
        } else if (actionCommand.equals("SPACE")) {
            txt.setText(currentText + " ");
        } else {
            txt.setText(currentText + actionCommand);
        }
    }
}
