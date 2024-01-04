
public class Function_Edit {
	GUI gui;
	
	public Function_Edit(GUI gui) {
		
		this.gui = gui;
		
	}
	
	public void undo() {
		gui.um.undo();
	}
	
	public void redo() {
		gui.um.redo();
	}
}
