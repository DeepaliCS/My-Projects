import java.util.ArrayList;
import java.util.Scanner;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;
//importing scanner, array list and Finch robot utilities
public class Menu {
	public static void main(String[] args)	{
		System.out.println("Instructions:"); 
		System.out.println("-Forward direction: 'F 4 100', F for direction, 4 for seconds (1-6), and 100 wheel velocity (1-100)"); 
		System.out.println("-Right direction: 'R 4 50 100', R for direction, 4 for seconds (1-6), and 50 right wheel velocity, and 100 left wheel velocity (1-100)"); 
		System.out.println("-Left direction: 'L 4 100 50', R for direction, 4 for seconds (1-6), and 100 right wheel velocity, and 50 left wheel velocity (1-100)"); 
		System.out.println("-Backtracking: 'B 2', B for backtrack instruction, 2 for how many commands Finch should retrace "); 
		System.out.println(" (Please make sure the retrace value is not more than the amount of commands entered)");
		System.out.println("-Run the Program: 'Run', enter run after entering commands for the Finch to follow your commands"); 
		System.out.println();
		System.out.println("Input Commands: ");
		CommandsandValidation.Input();
		//instructions for the user for inputting forward, backwards, right, left movement with duration and speed
		//Running Finch instructions and backtracking warning
		//going to input method in CommandsandValidation class to start entering commands
}
public static void Run (ArrayList<int[]> RunFinch) {
	//Command to Finch method
	Finch myf = new Finch();
	//Finch constructor
		 for( int lowest = 0; lowest < RunFinch.size(); lowest++ )    {
			    myf.setWheelVelocities(RunFinch.get(lowest)[0],RunFinch.get(lowest)[1],RunFinch.get(lowest)[2]); }
	//Finch iterating through the array list to run all commands 
	RunFinch.clear();
	System.out.println("Would you like to start again? (Please enter 'yes' to continue or 'no' to stop)");
	StartAgain();
	//Clearing the array list and calling the StartAgain method, and going to StartAgain method
}
private static void StartAgain(){
	Scanner in = new Scanner(System.in);
	String YesNo = in.nextLine();
	//Scanner to for user to input yes or no to the question
	boolean yes =(YesNo.equals("YES") || (YesNo.equals("yes")));
	boolean no = (YesNo.equals("NO") || (YesNo.equals("no")));
	//boolean none = (!(yes) || !(no));
	//conditions for the inputs yes or no
	if (yes == true)		 {
		//RunFinch.clear();
		System.out.println("Input Commands: ");
		CommandsandValidation.Input(); }
	//if yes is true then input method in CommandsandValidation method called
	else if (no == true){
		System.exit(0);	}
	//if no is true then program closes, successfully
	else if (yes == false && no == false) 	{
	System.out.println("Please enter 'yes' to continue or 'no' to stop");
	StartAgain(); }
	//if neither yes or no is entered then question is asked again
	in.close(); }
}