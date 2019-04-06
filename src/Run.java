package src;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import gui.Interface;

/*
 * Class: Run
 * Description: -	Main class to run the Chatbot
 * 				-	Incorporates all other classes (directly or indirectly) 
 * 				
 * 	Dependencies- 	UserInput.java	
 * 				-	DecisionMatrix.java
 * 				-	Hashmap.java
 * 
 * 	Parameters	-   name -> class -> (type)
 * 				-
 * 				- 	selection -> (int)
 * 				-	user -> UserInput -> (String)
 * 				-	ui -> (UserInput)
 * 				-	file -> (String) = File Name
 * 				-	questions -> QuestionBuilder -> (Hashmap)
 * 				-	d -> (DecisionMatrix)
 * 	
 * Authors: Daulton Baird
 */

public class Run {
		int selection;
		String user;
		UserInput ui;
		String file;
		HashMap<String, Question> questions;
		DecisionMatrix d;
		StackHandler sh;
		Stack<String> convo;
		Stack<String> fileStack;
		Interface gui;
		Boolean inputReady = false;
		

		public Run(Interface gui) {
		    this.gui = gui;
			sh = new StackHandler();
			convo = sh.initConversationLog();
			fileStack = sh.initFileLog();
		}
/*
 * Method: initialize
 * Outputs:		-	Initial Tree
 * 
 * Description:	-	Creates Tree that asks the first Question
 * 				-	sets int selection to 0
 * 				-	while loop makes method loop until user gives correct input (Defensive Programming)
 * 				-	Prints the First Question
 * 				-	Assigns UserInput ui to new UserInput
 *  			-	Assigns String user to the user's input
 * 				-	Assigns selection based on the new Tree to be built
 * 				-	If input is invalid print that it is invalid				
 */
	
	public void initialize() throws InterruptedException, IOException {
		Tree start = new Tree(0);
		ArrayList<Question> initial = new ArrayList<>(start.getNextQuestion().values());
		setSelection(0);
        setUI(new UserInput());
        int counter = 0;
        while(true) {
            if(counter>0) {
                gui.printBotOutput("Did not understand that, please try again.");
            }
            gui.printBotOutput(initial.get(0));
            counter++;
            convo.push("Chatbot: "+initial.get(0).getQuestion());
            Thread.sleep(4500);                 // give user 4.5 Seconds to respond
            inputReady = gui.getInputBool();    // boolean storing whether user has inputed
            if(inputReady) {                    // makes sure user input is not null
                ui.setInput(gui.getInput());
                setUser(ui.getInputGui(gui));
                convo.push("User: "+getUser());
                if (getUser().contains("internet")) {
                    setSelection(1);
                    initializeTree();
                    break; 
                } else if (getUser().contains("phone")) {
                    setSelection(2);
                    initializeTree();
                    break; 
                } else if (getUser().contains("tv")) {
                    setSelection(3);
                    initializeTree();
                    break;
                } else {
                    gui.printBotOutput("I Did not understand that, please try again."); 
                    counter=0;
                }
            }
        }
	}
	
	/*
	 * Method: initializeTree
	 * Outputs:		-	"Internet" Tree or "Phone" Tree
	 * 
	 * Description:	-	Creates Tree based on input from initialize Method
	 * 				-	Sets File to the initial file of the Folder 
	 * 				-	Sets Hashmap questions via the nextQuestion method from the Tree
	 * 				-	Sets DecisionMatrix d to new DecisionMatrix			
	 */
	
	public void initializeTree() throws FileNotFoundException {
		Tree bot = new Tree(getSelection());
		setFile("0-0.txt");
		fileStack.push(getFile());
		setQuestions(bot.getNextQuestion());
		setDecisionMatrix(new DecisionMatrix(this));
	}
	
	/*
	 * Method: runLoop
	 * Outputs:		-	Chatbot and User Conversation
	 * 
	 * Description:	-	while loop to continue until solution is found
	 * 				-	If the current file is the loop file, break out of the while loop (goes back to top of outer while loop)
	 * 				-	If the current file is the end file, print the "Thank you" string and then exit the program
	 * 				-	Otherwise Print current question
	 * 				-	Set String user to the user's input
	 *  			-	Decide the next file via DecisionMatrix d			
	 */
	
	public void runLoop() throws IOException, InterruptedException {
	    inputReady = false;
		while (true) {
			if(getFile().equals("loop-0.txt")) {
				initialize();       // Restart the process
			}else if(getFile().equals("end-0.txt")) {
			    gui.printBotOutput(getQuestions().get(getFile()).getQuestion()); //set bot to print question
				convo.push("Chatbot: "+getQuestions().get(getFile()).getQuestion());
				sh.conToFile();     // chat log to file
				sh.pathToFile();    // file log path to file
				gui.exit();         // exits the program
			}
			
			inputReady = gui.getInputBool();
			if (inputReady) {
			    bot();
			    Thread.sleep(4500);
			    user();
			    file();
			    gui.setInput("");
			}
		}
	}
	// adds bot output to conversation log
	public void bot() {
	    gui.printBotOutput(getQuestions().get(getFile()));
        convo.push("Chatbot: "+getQuestions().get(getFile()).getQuestion());
	}
	// adds user input to conversation log
	private void user() {
	    setUser(ui.getInputGui(gui));
        convo.push("User: "+getUser());     
	}
	// adds file paths travered to file log
	private void file() throws IOException {
	    file = d.Decision(gui, getUser(), getFile(), getSelection());
        fileStack.push(getFile()); 
	}
	
	//setters (only used locally)
	private void setSelection(int selection) {this.selection=selection;}
	private void setUser(String user) {this.user= user;}
	private void setUI(UserInput ui) {this.ui= ui;}	
	private void setFile(String file) {this.file= file;}
	private void setQuestions(HashMap<String, Question> questions) {this.questions=questions;}
	private void setDecisionMatrix(DecisionMatrix decisionMatrix) {this.d=decisionMatrix;}
	
	//getters (only used locally)
	private int getSelection() {return this.selection;}
	private String getUser() {return this.user;}
	private String getFile() {return this.file;}
	private HashMap<String, Question> getQuestions(){return this.questions;}
	
}
