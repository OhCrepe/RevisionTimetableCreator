package main;

import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ExamSelect {

	Exam exam; //The exam this select represents
	
	/**
	 * Constructor for new ExamSelect (used in JComboBox)
	 * @param e - Exam this select represents
	 */
	public ExamSelect(Exam e) {
		this.exam = e;
	}
	
	/**
	 * Returns a reference to the exam this select is storing
	 * @return - Reference to Exam object this examSelect represents
	 */
	public Exam getExam() {
		return exam;
	}
	
	/**
	 * Returns a string of the exams exam/module code, used for JComboBox
	 * @return - A string containing the module/exam code of the exam this select stores
	 */
	public String toString() {
		return exam.getModCode();
	}
	
}
