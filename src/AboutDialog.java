import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog{
    public static void main(String[] args) {
        JFrame frame = new JFrame("About Dialog Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton aboutButton = new JButton("Show About Dialog");
        aboutButton.addActionListener(e -> {
            JDialog aboutDialog = new JDialog(frame, "About", true);
            aboutDialog.setLayout(new BorderLayout());
            
            JLabel nameLabel = new JLabel("Your Software Name");
            nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            aboutDialog.add(nameLabel, BorderLayout.NORTH);
            
            JTextArea descriptionArea = new JTextArea("Your software description goes here...");
            descriptionArea.setEditable(false);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            aboutDialog.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
            
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(closeEvent -> aboutDialog.dispose());
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(closeButton);
            aboutDialog.add(buttonPanel, BorderLayout.SOUTH);
            
            aboutDialog.setSize(300, 200);
            aboutDialog.setLocationRelativeTo(frame);
            aboutDialog.setVisible(true);
        });
        
        frame.add(aboutButton, BorderLayout.CENTER);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
