package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import src.Question;
import src.Run;
import src.UserInput;

public class Interface extends JFrame {

    private static final long serialVersionUID = 1L;
    // 
    public JButton button;
    public JTextArea mainTxt;
    public JScrollPane scroll;
    public JTextField inpTxt;
    private int width = 450;
    private int height = 700;
    // 
    private String userInput;
    private String botOutput;
    static boolean setBool;
    
    UserInput ui;

    public static void main(String[] args) throws IOException, InterruptedException {
        Interface frame = new Interface();
        Run run = new Run(frame);
        frame.setVisible(true);
        run.initialize();
        run.runLoop();
    }
    
    public Interface() {
        setTitle("TECH-BOT");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        
        mainTxt = new JTextArea();
        mainTxt.setEditable(false);
        scroll = new JScrollPane(mainTxt);
        scroll.setPreferredSize(new Dimension(430, 640));
        
        inpTxt = new JTextField();
        inpTxt.setPreferredSize(new Dimension(350, 50));
        
        ui = new UserInput();
        
        button = new JButton("Send");
        button.setPreferredSize(new Dimension(100, 50));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInput = inpTxt.getText();
                if (userInput.length() > 0) {
                    setInput(userInput);
                    ui.setInput(userInput);
                    mainTxt.append("You:  " + userInput + "\n");
                    inpTxt.setText("");
                } else {
                    printBotOutput("I did not understand that, please try again.");                
                }              
            }       
        });
        
        add(scroll, BorderLayout.NORTH);
        add(inpTxt, BorderLayout.WEST);
        add(button, BorderLayout.EAST);
        
        setLocationRelativeTo(null);
        setResizable(false);   
        pack();
               
        String intro = "Hello, I am Chatbot. I will be assisting you today.";
        printBotOutput(intro);
        setInput("");
        
    }
    
    public void printBotOutput(String out) {
        mainTxt.append("Tech-Bot:  " + out + "\n");
    }
    
    public void printBotOutput(Question q) {
        String out = q.getQuestion();
        mainTxt.append("Tech-Bot:  " + out + "\n");
    }
    
    public void stopBotOutput() {
        mainTxt.append("");
    }
    
    public void exit() throws InterruptedException {
        mainTxt.append("System Exiting");
        Thread.sleep(3000);
        System.exit(0);
    }

    // Getter Methods for input/output
    public String getInput() {return this.userInput;}
    public String getBotOutput() {return this.botOutput;}
    public Boolean getInputBool() {return (getInput() != null);}
    public JTextArea getTextArea() {return this.mainTxt;}
    // Setter Methods for input/output
    public void setInput(String userInput) {this.userInput = userInput;}
    public void setOutput(String botOutput) {this.botOutput = botOutput;}
    public static void setInputBool(Boolean bool) {setBool = bool;}
    public void setTextArea(JTextArea ta) {this.mainTxt = ta;}
   
}