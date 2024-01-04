import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class GUI implements ActionListener{
	JFrame window;
	JTextArea textArea;
	JScrollPane scrollPane;
	boolean wordWrapOn = false;
	JMenuBar menuBar;
	JMenu menuFile, menuEdit, menuFormat, menuColor, menuHelp;
	JMenuItem iNew, iOpen, iSave, iSaveAs, iExit;
	JMenuItem iUndo, iRedo, iFind, iSearch, iReplace;
	JMenuItem iOpenSource, iAbout;
	// FORMAT MENU
	JMenuItem iWrap, iFontArial, iFontCSMS, iFontTnr, iFontSize8, iFontSize12, iFontSize16, iFontSize20, iFontSize24, iFontSize28;
	JMenu menuFont, menuFontSize;
	FindDialog findDialog;
//	SearchDialog searchDialog;
	ReplaceDialog replaceDialog;
	
	JMenuItem iColor1, iColor2, iColor3;
	
	Function_File file = new Function_File(this);
	Function_Format format = new Function_Format(this);
	Function_Color color = new Function_Color(this);
	Function_Edit edit = new Function_Edit(this);
	KeyHandler kHandler = new KeyHandler(this);
	
	UndoManager um = new UndoManager();
	
	public static void main(String[] args) {
		new GUI();
	}
	
	public GUI() {
		createWindow();
		createTextArea();
		createMenuBar();
		createFileMenu();
		createEditMenu();
		createFormatMenu();
		createColorMenu();
		createHelpMenu();
		
		format.selectedFont = "Arial";
		format.createFont(16);
		format.wordWrap();
		color.changeColor("White");
		window.setVisible(true);
		
		findDialog = new FindDialog(window,textArea);
		replaceDialog = new ReplaceDialog(window, textArea);
//		searchDialog = new SearchDialog(window, textArea);
	}
	
	public void createWindow() {
		window = new JFrame("JitPad");
		window.setSize(800, 600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void createTextArea() {
		textArea = new JTextArea();
		textArea.setFont(format.arial);
		
		textArea.addKeyListener(kHandler);
		
		textArea.getDocument().addUndoableEditListener(
				new UndoableEditListener() {
					public void undoableEditHappened(UndoableEditEvent e) {
						um.addEdit(e.getEdit());
					}
				});
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		window.add(scrollPane);
//		window.add(textArea);
	}
	
	public void createMenuBar() {
		
		menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);
		
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);
		
		menuFormat = new JMenu("Format");
		menuBar.add(menuFormat);
		
		menuColor = new JMenu("Color");
		menuBar.add(menuColor);
		
		menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		
	}
	
	public void createFileMenu() {
		iNew = new JMenuItem("New");
		iNew.addActionListener(this);
		iNew.setActionCommand("New");
		menuFile.add(iNew);
		
		iOpen = new JMenuItem("Open");
		iOpen.addActionListener(this);
		iOpen.setActionCommand("Open");
		menuFile.add(iOpen);
		
		iSave = new JMenuItem("Save");
		iSave.addActionListener(this);
		iSave.setActionCommand("Save");
		menuFile.add(iSave);
		
		iSaveAs = new JMenuItem("Save As");
		iSaveAs.addActionListener(this);
		iSaveAs.setActionCommand("SaveAs");
		menuFile.add(iSaveAs);
		
		iExit = new JMenuItem("Exit");
		iExit.addActionListener(this);
		iExit.setActionCommand("Exit");
		menuFile.add(iExit);
	}
	
	public void createEditMenu() {
		iUndo = new JMenuItem("Undo");
		iUndo.addActionListener(this);
		iUndo.setActionCommand("Undo");
		menuEdit.add(iUndo);
		
		iRedo = new JMenuItem("Redo");
		iRedo.addActionListener(this);
		iRedo.setActionCommand("Redo");
		menuEdit.add(iRedo);
		
		iFind = new JMenuItem("Find");
		iFind.addActionListener(this);
		iFind.setActionCommand("Find");
		menuEdit.add(iFind);
		
		iSearch = new JMenuItem("Search");
		iSearch.addActionListener(this);
		iSearch.setActionCommand("Find");
		menuEdit.add(iSearch);
		
		iReplace = new JMenuItem("Replace");
		iReplace.addActionListener(this);
		iReplace.setActionCommand("Replace");
		menuEdit.add(iReplace);
		
	}
	public void createFormatMenu() {
		iWrap = new JMenuItem("Word Wrap: Off");
		iWrap.addActionListener(this);
		iWrap.setActionCommand("Word Wrap");
		menuFormat.add(iWrap);
		
		menuFont = new JMenu("Font");
		menuFormat.add(menuFont);
		
		iFontArial = new JMenuItem("Arial");
		iFontArial.addActionListener(this);
		iFontArial.setActionCommand("Arial");
		menuFont.add(iFontArial);
		
		iFontCSMS = new JMenuItem("Comic Sans MS");
		iFontCSMS.addActionListener(this);
		iFontCSMS.setActionCommand("Comic Sans MS");
		menuFont.add(iFontCSMS);
		
		iFontTnr = new JMenuItem("Times New Roman");
		iFontTnr.addActionListener(this);
		iFontTnr.setActionCommand("Times New Roman");
		menuFont.add(iFontTnr);
		
		menuFontSize = new JMenu("Font Size");
		menuFormat.add(menuFontSize);
		
		iFontSize8 = new JMenuItem("8");
		iFontSize8.addActionListener(this);
		iFontSize8.setActionCommand("size8");
		menuFontSize.add(iFontSize8);
		
		iFontSize12 = new JMenuItem("12");
		iFontSize12.addActionListener(this);
		iFontSize12.setActionCommand("size12");
		menuFontSize.add(iFontSize12);
		
		iFontSize16 = new JMenuItem("16");
		iFontSize16.addActionListener(this);
		iFontSize16.setActionCommand("size16");
		menuFontSize.add(iFontSize16);
		
		iFontSize20 = new JMenuItem("20");
		iFontSize20.addActionListener(this);
		iFontSize20.setActionCommand("size20");
		menuFontSize.add(iFontSize20);
		
		iFontSize24 = new JMenuItem("24");
		iFontSize24.addActionListener(this);
		iFontSize24.setActionCommand("size24");
		menuFontSize.add(iFontSize24);
		
		iFontSize28 = new JMenuItem("28");
		iFontSize28.addActionListener(this);
		iFontSize28.setActionCommand("size28");
		menuFontSize.add(iFontSize28);
	}
	
	public void createColorMenu() {
		iColor1 = new JMenuItem("White");
		iColor1.addActionListener(this);
		iColor1.setActionCommand("White");
		menuColor.add(iColor1);
		
		iColor2 = new JMenuItem("Black");
		iColor2.addActionListener(this);
		iColor2.setActionCommand("Black");
		menuColor.add(iColor2);
		
		iColor3 = new JMenuItem("Blue");
		iColor3.addActionListener(this);
		iColor3.setActionCommand("Blue");
		menuColor.add(iColor3);
	}
	
	public void createHelpMenu() {
		iOpenSource = new JMenuItem("GitHub");
		iOpenSource.addActionListener(e -> {
			try {
				openURL("https://github.com/Cyber-Zypher/JitPad-Text-Editor");
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
		});
		iOpenSource.setActionCommand("GitHub");
		menuHelp.add(iOpenSource);
		
		iAbout = new JMenuItem("About");
		iAbout.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                showAboutDialog(window);
            }
		});
		iAbout.setActionCommand("About");
		menuHelp.add(iAbout);
	}
	
	private static void openURL(String url) throws IOException, URISyntaxException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(url));
        } else {
            System.out.println("Desktop not supported, cannot open URL");
        }
    }
	
	private static void showAboutDialog(JFrame parent) {
        JDialog aboutDialog = new JDialog(parent, "About", true);
        aboutDialog.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("JitPad");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        aboutDialog.add(nameLabel, BorderLayout.NORTH);

        JTextArea descriptionArea = new JTextArea("This software was created as a hobby with no commercial ideas in mind. Feel free to develop it further according to your needs.. \n This Software is developed by Sidharth Prabhu from Frissco Creative Labs on 2024.");
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
        aboutDialog.setLocationRelativeTo(parent);
        aboutDialog.setVisible(true);
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		switch(command) {
		case "New":
			file.newFile();
			break;
		case "Open":
			file.open();
			break;
		case "SaveAs":
			file.saveAs();
			break;
		case "Save":
			file.save();
			break;
		case "Exit":
			file.exit();
			break;
		case "Undo":
			edit.undo();
			break;
		case "Redo":
			edit.redo();
			break;
		case "Find":
			findDialog.setVisible(true);
			break;
		case "Search":
//			searchDialog.setVisible(true);
			break;
		case "Replace":
			replaceDialog.setVisible(true);
			break;
		case "Word Wrap":
			format.wordWrap();
			break;
		case "Arial":
			format.setFont(command);
		case "Comic Sans MS":
			format.setFont(command);
		case "Times New Roman":
			format.setFont(command);
		case "size8":
			format.createFont(8);
			break;
		case "size12":
			format.createFont(12);
			break;
		case "size16":
			format.createFont(16);
			break;
		case "size20":
			format.createFont(20);
			break;
		case "size24":
			format.createFont(24);
			break;
		case "size28":
			format.createFont(28);
			break;
		case "White":
			color.changeColor(command);
			break;
		case "Black":
			color.changeColor(command);
			break;
		case "Blue":
			color.changeColor(command);
			break;
		}
	}
}
