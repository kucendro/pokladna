package pokladna.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

public class PopUpKeyboard extends JDialog implements ActionListener {
    private JTextField txt;

    public PopUpKeyboard(JTextField txt) {
        this.txt = txt;
        setLayout(new GridLayout(4, 3));
        for (int i = 1; i <= 12; i++)
            if (i == 10)
                createButton("0");
            else if (i == 11)
                createButton("DEL");
            else if (i == 12)
                createButton("OK");
            else
                createButton(Integer.toString(i));
        pack();
    }

    private void createButton(String label) {
        JButton btn = new JButton(label);
        btn.addActionListener(this);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(100, 100));
        Font font = btn.getFont();
        float size = font.getSize() + 15.0f;
        btn.setFont(font.deriveFont(size));
        add(btn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("DEL")) {
            String currentText = txt.getText();
            if (currentText.length() > 0 && !currentText.equals("DEL")) {
                txt.setText(currentText.substring(0, currentText.length() - 1));
            }
        } else if (actionCommand.equals("OK")) {
            setVisible(false);
        } else {
            txt.setText(txt.getText() + actionCommand);
        }
    }
}
