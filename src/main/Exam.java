package main;

import java.util.Date;

public class Exam implements Comparable<Exam>{

	private String title; //Title of the exam
	private String modCode; //Exam/Module code for the exam
	private Date date; //Date of the exam
	private boolean complete; //Has the exam been complete? True if yes.
	
	/**
	 * Constructor for a new exam
	 * @param modCode - The code of the exam
	 * @param title - Title of the exam
	 * @param date - Date of the exam
	 * @param complete - True if the exam has been complete, false otherwise
	 */
	public Exam(String modCode, String title, Date date, boolean complete) {
		this.title = title;
		this.modCode = modCode;
		this.date = date;
		this.complete = complete;
	}
	
	/**
	 * Returns the title of the exam
	 * @return - A string containing the title of the exam
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Returns the module/exam code for this exam
	 * @return - A string containing the module/exam code for this exam
	 */
	public String getModCode() {
		return modCode;
	}
	
	/**
	 * Returns a copy of the date for this exam
	 * @return - A new date object showing the time of the exam
	 */
	public Date getDate() {
		return new Date(date.getTime());
	}
	
	/**
	 * Returns a string of the exam, used primarily for saving and loading into text files
	 * @return - A string containing all information about this exam
	 */
	@Override
	public String toString() {
		return modCode + "," + title + "," + Constants.DATE_FORMAT.format(date) + "," + complete;
	}
	
	/**
	 * Returns true if the exam is complete
	 * @return - A boolean that is true if the exam is complete, returns false otherwise
	 */
	public boolean isComplete() {
		return complete;
	}
	
	/**
	 * Changes the complete boolean into the opposite version, then saves
	 */
	public void toggleComplete() {
		this.complete = !this.complete;
		Storage.saveExams();
	}
	
	/**
	 * Compares two exams and returns true if they have the same title and module/exam code
	 * @param other - The Exam to compare to
	 * @return - A boolean returning true if the exams are "equal"
	 */
	public boolean equals(Exam other) {
		return (title.toLowerCase().equals(other.getTitle().toLowerCase()) && 
				modCode.toLowerCase().equals(other.getModCode().toLowerCase()));
	}

	@Override
	/**
	 * Compares two exams for sorting. Will sort by date, so sorts in ascending order of exam occuring
	 */
	public int compareTo(Exam other) {
		return other.date.before(date)?1:-1;
	}
	
}
