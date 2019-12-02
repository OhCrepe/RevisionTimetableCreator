package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.JFrame;

import main.gui.DisplayFrame;
import main.gui.ExamDisplayPanel;

public class Storage {

	public static ArrayList<Exam> exams; //List of all exams
	public static ArrayList<Task> tasks; //List of all tasks
	private static File examFile = new File("exams.txt"); //File containing the list of exams
	private static File taskFile = new File("tasks.txt"); //File containing the list of tasks
	private static DisplayFrame frame; //Reference to the DisplayFrame
	
	//Calls ArrayList constructor for exams
	static {
		exams = new ArrayList<Exam>();
		tasks = new ArrayList<Task>();
	}
	
	/**
	 * Changes the DisplayFrame reference in storage
	 * @param f - The frame Storage should have a reference to
	 */
	public static void setDisplayFrame(DisplayFrame f) {
		frame = f;
	}
	
	/**
	 * Load list of exams from the examFile
	 */
	public static void loadExams(){
		
		Scanner scanner;
		try {
			scanner = new Scanner(examFile);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] exam = line.split(",");
				try {
					Exam e = new Exam(exam[0], exam[1], Constants.DATE_FORMAT.parse(exam[2]), Boolean.parseBoolean(exam[3]));
					exams.add(e);
				} catch (ParseException e) {
					System.out.println("There was an error parsing a date provided in exams.txt");
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("No file for exams found, creating file...");
			try {
				examFile.createNewFile();
			} catch (IOException e1) {
				System.out.println("An error occured trying to create exams.txt");
			}
		}

	}
	
	/**
	 * Load list of tasks from the taskFile
	 */
	public static void loadTasks(){
		
		Scanner scanner;
		try {
			scanner = new Scanner(taskFile);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] task = line.split(",");
				try {
					String examCode = task[0];
					Exam exam = null;
					for(Exam e: exams) {
						if(e.getModCode().equals(examCode)) {
							exam = e;
						}
					}
					if(exam == null) {
						System.out.println("There was an error loading the given tasks - Invalid Exam found");
					}
					Task t = new Task(exam, task[1], Constants.DATE_FORMAT.parse(task[2]), Boolean.parseBoolean(task[3]));
					tasks.add(t);
				} catch (ParseException e) {
					System.out.println("There was an error parsing a date provided in exams.txt");
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("No file for tasks found, creating file...");
			try {
				taskFile.createNewFile();
			} catch (IOException e1) {
				System.out.println("An error occured trying to create exams.txt");
			}
		}

	}
	
	/**
	 * Adds an exam to the list of exams, then updates the exams file accordingly
	 * @param exam - Exam to be added to the list
	 */
	public static synchronized void addExam(Exam exam) {
		exams.add(exam);
		Collections.sort(exams);
		saveExams();
	}
	
	/**
	 * Adds a task to the list of tasks, then updates the task file accordingly
	 * @param task - Task to be added to the list
	 */
	public static synchronized void addTask(Task task) {
		tasks.add(task);
		Collections.sort(tasks);
		saveTasks();
	}
	
	/**
	 * Removes an exam from the list of exams, then updates the exams file accordingly
	 * @param exam - Exam to be removed from the list
	 */
	public static synchronized void removeExam(Exam exam) {
		for(int i = 0; i < exams.size(); i++) {
			if(exams.get(i).equals(exam)) {
				for(int j = 0; j < tasks.size();j++) {
					if(tasks.get(j).getExam().getModCode().equals(exams.get(i).getModCode())) {
						removeTask(tasks.get(j));
						j--;
					}
				}
				exams.remove(i);
				break;
			}
		}
		saveExams();
	}
	
	/**
	 * Removes a task from the list of tasks, then updates the tasks file accordingly
	 * @param task - Task to be removed from the list
	 */
	public static synchronized void removeTask(Task task) {
		for(int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).equals(task)) {
				tasks.remove(i);
				break;
			}
		}
		saveTasks();
	}
	
	/**
	 * Updates the exam file with what is currently stored in the exams List
	 */
	public static void saveExams() {
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(examFile));
			for(Exam e: exams) {
				writer.append(e.toString());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("There was an error updating the exams.txt file");
		}
		frame.refreshOutput();
		
	}
	
	/**
	 * Updates the task file with what is currently stored in the tasks List
	 */
	public static void saveTasks() {
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(taskFile));
			for(Task t: tasks) {
				writer.append(t.toString());
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("There was an error updating the tasks.txt file");
		}
		frame.refreshOutput();
		
	}
	
	/**
	 * Checks to see if an exam exists in memory
	 * @param exam - The exam to check for
	 * @return - Boolean, returns true if exam is found in List with the same name and date as the provided exam
	 */
	public static boolean exists(Exam exam) {
		for(Exam e: exams) {
			if(e.equals(exam))return true;
		}
		return false;
	}
	
	/**
	 * Checks to see if a task exists in memory
	 * @param exam - The task to check for
	 * @return - Boolean, returns true if task is found in List with the same name and date as the provided task
	 */
	public static boolean exists(Task task) {
		for(Task t: tasks) {
			if(t.equals(task))return true;
		}
		return false;
	}
	
}
