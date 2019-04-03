package src;

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/*
 * Class: QuestionBuilder
 * Description: -	Gets all question files in a specified directory and
 * 					reads them into new Question objects
 * Authors: Robin MacDonald
 */
public class QuestionBuilder {
	private File dir;
	private HashMap<String, Question> builtQuestions;
	
	/*
	 * Constructor
	 * Inputs:		-	Name of directory as string
	 * Description: -	Takes directory name and creates a file named "dir".
	 * 				-	Initializes ArrayList "builtQuestions" to store all Question objects.
	 */
	public QuestionBuilder(String folderName) {
		this.dir = new File(folderName);
		this.builtQuestions = new HashMap<>();
	}
	
	//Returns builtQuestions
	public  HashMap<String, Question> getBuiltQuestions() {return this.builtQuestions;}
	
	/*
	 * Method: buildQuestions
	 * Description: -	Traverses directory once
	 * 				-	Reads each question file into a new Question object
	 * 				-	Stores each new Question into builtQuestions
	 */
	public void buildQuestions() {		
		Question temp;
		String tempLine;
		
		//Main loop
		for(File file: this.dir.listFiles()) {
			try {
				Scanner s = new Scanner(file);
				temp = new Question();
				
				//Read BranchIDs
				tempLine = s.nextLine();
				for(int i=0; i<tempLine.length(); i++) {
					temp.addBranch(Character.getNumericValue(tempLine.charAt(i)));
				}
				
				//Read Question Priority
				tempLine = s.nextLine();
				temp.setQuestionPriority(Integer.parseInt(tempLine));
				
				//Read Question
				temp.setQuestion(s.nextLine());
				
				//Read inputRange
				//tempLine = s.nextLine();
				temp.setInputRange(Integer.parseInt(tempLine));
				
				//Read Answers
				while(s.hasNextLine()) {
					temp.addAnswer(s.nextLine());
				}
				
				builtQuestions.put(file.getName(), temp);
				temp = null;
				
				s.close();	
				
			} catch(FileNotFoundException ex) {
				ex.printStackTrace();
				
				System.out.println("Specified directory not found.");
			}
		}
	}
	
}
