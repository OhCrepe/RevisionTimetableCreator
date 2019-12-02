package main;

import main.gui.DisplayFrame;

public class Main {

	/**
	 * Sets up the Storage and DisplayFrame frame
	 * @param args - Command line arguments, do nothing in this case
	 */
	public static void main(String[] args) {
		Storage.loadExams();
		Storage.loadTasks();
		DisplayFrame frame = new DisplayFrame("Stress");
		frame.setVisible(true);
	}
	
}
