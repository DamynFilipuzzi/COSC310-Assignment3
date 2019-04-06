package src;

import java.util.Scanner;

import SpellChecker.SpellCheck;
import gui.Interface;
/*
 * Class: UserInput
 * Description: -	Initializes a Scanner to read user's input via the Console.
 * Authors: Daulton Baird, Damyn Filipuzzi (edits)
 */
public class UserInput {
	Scanner input;
	String user;
	Interface gui;
	SpellCheck check;
	/*
	 * Method: getInput (String)
	 * Inputs: 		-	None
	 * Description: -	Initializes a new Scanner "input
	 * 				-	Assigns the user's input to String "user" 
	 * 				-   "user" contains nextLine() because the user may enter
	 * 				-   more text than necessary
	 * 				-	returns "user" to be used in Decision Matrix
	 */
	public String getInput() {
		input = new Scanner(System.in);
		user = input.nextLine();
		return user.toLowerCase();
	}
	
	public String getInputGui(Interface gui) {
	    check = new SpellCheck(gui);
	    input = new Scanner(gui.getInput());
        if(input.hasNext()) {
            String result = "";
            int i = 0;
            String inp = input.nextLine();            
            String[] words = inp.split(" ");
            for (String word: words) {
                String spellCheck = check.compare(word.toLowerCase());
                if (spellCheck.equals(word)) {
                    result += words[i] + " ";
                }
                result += spellCheck;
                i++;
            }
            return result.toLowerCase();
        } else {
            return "";
        }
	}
	
	public void setInput(String user) {
	    this.user = user;
	}
	
	/*
	 * Method: close
	 * Inputs: 		-	None
	 * Description: -	Closes the Scanner once the program is complete
	 */
	public void close() {
		input.close();
	}
	
}
