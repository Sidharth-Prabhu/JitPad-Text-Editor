import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReplaceDialog extends JDialog implements ActionListener {
    private JTextField findTextField;
    private JTextField replaceTextField;
    private JButton replaceButton;
    private JButton replaceAllButton;
    private JTextComponent textComponent;

    public ReplaceDialog(JFrame parent, JTextComponent textComponent) {
        super(parent, "Replace", true);
        setLayout(new FlowLayout());

        this.textComponent = textComponent;

        findTextField = new JTextField(20);
        replaceTextField = new JTextField(20);

        add(findTextField);
        add(replaceTextField);

        replaceButton = new JButton("Replace");
        replaceButton.addActionListener(this);
        add(replaceButton);

        replaceAllButton = new JButton("Replace All");
        replaceAllButton.addActionListener(this);
        add(replaceAllButton);

        setSize(300, 150);
        setLocationRelativeTo(parent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String findText = findTextField.getText();
        String replaceText = replaceTextField.getText();
        String text = textComponent.getText();

        if (e.getSource() == replaceButton) {
            int index = text.indexOf(findText, textComponent.getCaretPosition());
            if (index != -1) {
                textComponent.select(index, index + findText.length());
                textComponent.replaceSelection(replaceText);
            } else {
                JOptionPane.showMessageDialog(this, "Text not found!");
            }
        } else if (e.getSource() == replaceAllButton) {
            textComponent.setText(text.replace(findText, replaceText));
        }
    }
}
