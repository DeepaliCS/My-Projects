package tictactoe;
import javax.swing.JFrame;
public class Main extends JFrame{
 public static void main(String args[]){
  tictactoe gui = new tictactoe(); //Run the gui Object in the tictactoe class
  
  gui.setVisible(true); //Viewable
  gui.setSize(400, 400); //sets the size of the gui
  gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exits the program properly when i click the X button
  gui.setResizable(true); //if the gui is maximised 
  }
}

