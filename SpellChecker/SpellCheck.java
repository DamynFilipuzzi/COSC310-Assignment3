package SpellChecker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import gui.Interface;

public class SpellCheck {
    
    Interface gui;
    ArrayList<String> list;
    static ArrayList<String> testList;

    public SpellCheck(Interface gui) {
        this.gui = gui;
        list = new ArrayList<String>();
        try {
            getDict();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    // Gets the dictionary of possible answers
    public void getDict() throws IOException {
        Scanner in = new Scanner(new File("dictionary.txt"));
        String word = in.nextLine();
        while (!(word.equals(""))) {
            list.add(word);
            word = in.nextLine();
        }
    }
    // Compares user input to words in dictionary
    public String compare(String str) {
        String correct = "";
        ArrayList<Integer> numMatches = new ArrayList<>();
        // iterate through each word in the dictionary
        for (int i = 0; i < list.size(); i++) {
            String dictWord = list.get(i);
            int size = 0;
            if (dictWord.length() < str.length()) {
                size = dictWord.length();
            } else {
                size = str.length();
            }
            numMatches.add(i,0);
            // Iterate through each character
            for (int k = 0; k < size; k++) {
                if (str.charAt(k) == dictWord.charAt(k)) {
                    int curr = numMatches.get(i);
                    numMatches.set(i, ++curr);
                }
            }
            
        System.out.println(numMatches.get(i) + "   :   " + list.get(i));
        }
        int max = 0;
        int idx = 0;
        for (int match: numMatches) {
            if (match > max) {
                max = match;
                idx = numMatches.indexOf(match);
            }
        }
        return list.get(idx);
    }
}
