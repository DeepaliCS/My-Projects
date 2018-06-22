package tictactoe;import java.awt.BorderLayout;import java.awt.Color;import java.awt.Font;
import java.awt.GridLayout;import java.awt.event.ActionEvent;import java.awt.event.ActionListener;
import java.util.Random;import javax.swing.JButton;import javax.swing.JFrame;
import javax.swing.JOptionPane;import javax.swing.JPanel;

public class tictactoe extends JFrame{

//Creating JPanel attributes: first two panels, array of buttons (3 by 3), and reset button
JPanel firstPanel; 	JPanel secondPanel; JButton Buttons[] = new JButton[9]; JButton reset; 
	
//Declaring computer and human moves, random object 
String Comp, Human; Random random = new Random(); 

public tictactoe()	{//This method shows all the components
super("Tic Tac Toe"); //Title of JFrame

events object2 = new events(); //Creates an object for events class

firstPanel = new JPanel(new GridLayout(3, 3)); //Sets the first panel to a grid layout
secondPanel = new JPanel(new BorderLayout()); //Sets the second panel (south) to a border layout
JButton reset = new JButton("Restart The Game"); //This is the reset button
reset.addActionListener(object2); //Carries out commands in the events class
reset.setActionCommand("reset"); 
reset.setFont(new Font("Arial", Font.BOLD, 35));//Font and format of the text
reset.setForeground(Color.WHITE); //Colour of the text
reset.setBackground(Color.BLACK); //Colour the background of the restart button
				
for(int i = 0; i < Buttons.length; i++)	{ //Placing the button array with Buttons
	Buttons[i] = new JButton(); //Creates the buttons
	Buttons[i].setText("."); //Sets the text to a full stop, indicating an empty box
	firstPanel.add(Buttons[i]); //Adds all the buttons to the first panel
	Buttons[i].addActionListener(object2); //Allows all buttons respond to events class
}

//adds colour to each individual grid box (blue, red alternating)
Buttons[0].setBackground(Color.blue);Buttons[1].setBackground(Color.red); Buttons[2].setBackground(Color.blue);Buttons[3].setBackground(Color.red);
Buttons[4].setBackground(Color.blue);Buttons[5].setBackground(Color.red);Buttons[6].setBackground(Color.blue);Buttons[7].setBackground(Color.red);
Buttons[8].setBackground(Color.blue);
	
secondPanel.add(firstPanel, BorderLayout.CENTER); //The main panel remains in the middles (grid)
secondPanel.add(reset, BorderLayout.SOUTH);//Adds the second panel south (restart button)
		
add(secondPanel); //Adds the second panel to the gui
Object options[] = {"Computer", "Human"}; //Adds the options on gui

int firstPlayer = JOptionPane.showOptionDialog(null, "Please choose FIRST PLAYER", "First Player", 
		JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, "Computer");

//If the computer is the first player clicked, set x and o accordingly (first player = x)
	if (firstPlayer == 0)	{
		Comp = "X"; Human = "O"; 
		int compMove = random.nextInt(8); //Creates a random number between 0 and 8 due to the array

		Buttons[compMove].setText(Comp); //Computer makes the move
		Buttons[compMove].setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 60));//The text that will appear in this format
		Buttons[compMove].setEnabled(false); //disable button
		JOptionPane.showMessageDialog(null, "The COMPUTER has made a move, please make YOUR MOVE."); //display message informing the user that it's their turn

	}else if(firstPlayer == 1)	{ // if Human is first player.. .set x and o accordingly (first player = x)
		Comp = "O"; Human = "X"; 
	}

}

//This is where all the actions occur 
private class events implements ActionListener 		{ //Events class is implemented here
public void actionPerformed(ActionEvent event)	{
	JButton button = (JButton) event.getSource(); //Creates a JButton for the action listener
		
	//reset button
	if(event.getActionCommand().equals("reset"))  { //if the action commanded is equal to reset (reset button is pressed)
		for(int i = 0; i < Buttons.length; i++){ //for loop for the array
			Buttons[i].setEnabled(true); //enable all the buttons 
			Buttons[i].setText(".");//set text to a full stop (empty spots)
		}
	}
			
	for(int i = 0; i < Buttons.length; i++)	{ //for loop for the buttons clicked

		if(button == Buttons[i])	{ //If any button in the grid is pressed

		//Human moves
		Buttons[i].setText(Human);//Human makes a move
		Buttons[i].setEnabled(false); //Disables the button once the move has been made
		Buttons[i].setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 60)); //Sets the x and o format
		JOptionPane.showMessageDialog(null, "The HUMAN has made a move, Press OK for COMPUTER to make move."); //Displays this message 
		
		if(Winner() == true) { break; }	//If statement to check if the winning move has been made, if it has then breaks the loop	
		boolean trueorfalse = false; // Declaring a variable for no winner
		
		//If the computer makes move
			while(trueorfalse == false)	{//The game will keep going on, while there's no winner
				int placecomp = random.nextInt(8); //create random number between 0 to 8 (AI computer moves)

					if (Buttons[placecomp].getText() == ".")	{ //adds the full stop 
						Buttons[placecomp].setText(Comp); //If the computer moves
						Buttons[placecomp].setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 60));//Sets the format 
						Buttons[placecomp].setEnabled(false); //Disables the button which the computer has made a move
						JOptionPane.showMessageDialog(null, "The COMPUTER has made a move, please make YOUR MOVE"); //Message for Humans move
											
						trueorfalse = true; //boolean to see winning move
					}
				}
				if(Winner() == true){break;} //break if the winner is declared
			} 
		}
	} 
}

public boolean Winner()	{ //checking for a winner
	boolean finishwinner = false; 
		
	//if possible moves have been made in the grid, find a possible winning streak
	if((Buttons[0].getText() == "X" && Buttons[1].getText() == "X" && Buttons[2].getText() == "X") //top row
			|| (Buttons[3].getText() == "X" && Buttons[4].getText() == "X" && Buttons[5].getText() == "X") //middle row
			|| (Buttons[6].getText() == "X" && Buttons[7].getText() == "X" && Buttons[8].getText() == "X") //bottom row
			|| (Buttons[0].getText() == "X" && Buttons[3].getText() == "X" && Buttons[6].getText() == "X") //first column
			|| (Buttons[1].getText() == "X" && Buttons[4].getText() == "X" && Buttons[7].getText() == "X") //second column
			|| (Buttons[2].getText() == "X" && Buttons[5].getText() == "X" && Buttons[8].getText() == "X") //third column
			|| (Buttons[0].getText() == "X" && Buttons[4].getText() == "X" && Buttons[8].getText() == "X") //top left diagonal
			|| (Buttons[2].getText() == "X" && Buttons[4].getText() == "X" && Buttons[6].getText() == "X")){ //top right diagonal
	finishwinner = true; //if there is a win (x) then the below message will appear
	JOptionPane.showMessageDialog(null, "Player X has Won! Please press 'Restart the Game' button to play again");
}
	if((Buttons[0].getText() == "O" && Buttons[1].getText() == "O" && Buttons[2].getText() == "O") //top row
			|| (Buttons[3].getText() == "O" && Buttons[4].getText() == "O" && Buttons[5].getText() == "O") //middle row
			|| (Buttons[6].getText() == "O" && Buttons[7].getText() == "O" && Buttons[8].getText() == "O") //bottom row
			|| (Buttons[0].getText() == "O" && Buttons[3].getText() == "O" && Buttons[6].getText() == "O") //first column
			|| (Buttons[1].getText() == "O" && Buttons[4].getText() == "O" && Buttons[7].getText() == "O") //second column
			|| (Buttons[2].getText() == "O" && Buttons[5].getText() == "O" && Buttons[8].getText() == "O") //third column
			|| (Buttons[0].getText() == "O" && Buttons[4].getText() == "O" && Buttons[8].getText() == "O") //top left diagonal
			|| (Buttons[2].getText() == "O" && Buttons[4].getText() == "O" && Buttons[6].getText() == "O")){ //top right diagonal
	finishwinner = true; //if there's a win (o) then the message will appear
	JOptionPane.showMessageDialog(null, "Player O has Won! Please press 'Restart the Game' button to play again");
}

	int counter = 0; //counter is set to 0
	for(int i = 0; i < Buttons.length; i++)	{
		if(finishwinner == true) {Buttons[i].setEnabled(false);}//disables all the buttons
		if(Buttons[i].isEnabled() == false){counter++;} //if a button is disabled, counter increments each time
		if(counter == 9 && finishwinner == false){JOptionPane.showMessageDialog(null, "The game has DRAWN! Please 'Restart The Game' button to play again");}
		//Drawn game message ^
		}
		return finishwinner; //returns winner and game ends
	}
}
