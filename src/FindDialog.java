import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindDialog extends JDialog implements ActionListener {
    private JTextField findTextField;
    private JButton findButton;
    private JTextArea textArea;

    public FindDialog(JFrame parent, JTextArea textArea) {
        super(parent, "Find", true);
        setLayout(new FlowLayout());

        this.textArea = textArea;

        findTextField = new JTextField(20);
        add(findTextField);

        findButton = new JButton("Find");
        findButton.addActionListener(this);
        add(findButton);

        setSize(300, 100);
        setLocationRelativeTo(parent);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String searchText = findTextField.getText();
        String text = textArea.getText();

        Highlighter highlighter = textArea.getHighlighter();
        highlighter.removeAllHighlights(); // Clear previous highlights

        int index = text.indexOf(searchText);
        while (index >= 0) {
            int endIndex = index + searchText.length();
            try {
                highlighter.addHighlight(index, endIndex, new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
            index = text.indexOf(searchText, endIndex);
        }
    }
}
