package main;

import java.util.Date;

public class Task implements Comparable<Task>{

	private Exam exam; //Exam this task is for
	private String task; //The actual task to do
	private Date due; //The due date for the task to be complete
	private boolean complete; //Whether this task has been complete
	
	/**
	 * Constructor to create a new task
	 * @param exam - Exam this new task is for
	 * @param task - The task to complete
	 * @param dueDate - The due date for this new task
	 * @param complete - Whether this task has been complete
	 */
	public Task(Exam exam, String task, Date dueDate, boolean complete) {
		this.exam = exam;
		this.task = task;
		this.due = dueDate;
		this.complete = complete;
	}
	
	/**
	 * Returns the exam this task is for
	 * @return - Reference to exam this task is for
	 */
	public Exam getExam() {
		return exam;
	}
	
	/**
	 * Returns the task details
	 * @return - String containing task details
	 */
	public String getTask() {
		return task;
	}
	
	/**
	 * Returns the date this task is due
	 * @return - Copy of the date object containing the due date for this task
	 */
	public Date getDueDate() {
		return new Date(due.getTime());
	}

	/**
	 * Returns a boolean showing whether this task is complete
	 * @return - Boolean value that is true if the task is complete, false otherwise
	 */
	public boolean isComplete() {
		return complete;
	}
	
	/**
	 * Toggles whether or not this task is stored as complete
	 */
	public void toggleComplete() {
		complete = !complete;
	}
	
	@Override
	/**
	 * Returns a string of the task, used primarily for saving and loading into text files
	 * @return - A string containing all information about this task
	 */
	public String toString() {
		return exam.getModCode() + "," + task + "," + Constants.DATE_FORMAT.format(due) + "," + complete;
	}
	
	/**
	 * Compares two tasks and returns true if they have the same exam and task description
	 * @param other - The Task to compare to
	 * @return - A boolean returning true if the tasks are "equal"
	 */
	public boolean equals(Task other) {
		return (other.getExam().getModCode().equals(exam.getModCode()) && other.getTask().equals(getTask()));
	}
	
	@Override
	/**
	 * Compares two tasks. Used for sorting them in order of due date.
	 */
	public int compareTo(Task other) {
		return other.due.before(due)?1:-1;
	}
	
}
