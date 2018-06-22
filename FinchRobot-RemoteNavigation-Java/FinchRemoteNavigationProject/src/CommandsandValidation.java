import java.util.ArrayList;
import java.util.Scanner; 
//importing array list and scanner utilities
public class CommandsandValidation{
public static ArrayList<int[]> FinchMovements = new ArrayList<int[]>(); 
//constructing an array list of integer arrays 
static String inputOne, inputTwo, inputThree, inputFour; 
static int parseTwo, parseThree, parseFour;	
//declaring public string and integer variables
public static void Input()		{  
Scanner in = new Scanner(System.in); 
//scanner object constructor
try { //try/catch exception for no such element exception and scanner close
	String input = in.nextLine();
	String commandindex []= input.split(" ");	 
	switch (commandindex.length)	{ 		
	//storing the user input that is separated by a space in between 
	//and a switch is created according to the number of input values
	//case 1 =Stop, case 2 =Backtrack, case 3 =Forward, case 4= right/left direction
	case 1: 		
		inputOne = commandindex[0]; 
		StopRunValidation(inputOne);	
		//if input is one value then its stored in first element of 
		//the array and passed as a parameter to the StopRunValidation method
	break; case 2: 	
		inputOne = commandindex[0]; 	 
		inputTwo = commandindex[1];	
		try { parseTwo = Integer.parseInt(inputTwo); } //
		catch (Exception NumberFormatException) { 
			System.out.println("You have entered incorrectly, please follow the instructions and Try Again"); }
		Backtrack(inputOne, parseTwo);		
		//if the input value is two then the inputs are stored accordingly into 
		//the array and the second value should be an integer therefore parsed
		//as an integer, and passed as parameters to the Backtrack method
		//Try/catch method is used to catch string/char values in place of an integer value
	break; case 3:
		inputOne = commandindex[0];
		inputTwo = commandindex [1];
		inputThree = commandindex[2];
		try { parseTwo = (Integer.parseInt(inputTwo)) * 1000;
			  parseThree = Integer.parseInt(inputThree); }
		catch (Exception NumberFormatException) {
			System.out.println("You have entered incorrectly, please follow the instructions and Try Again"); }
		ForwardValidation(inputOne, parseTwo, parseThree);
		//if the input is 3 then the inputs are stored accordingly into 
		//the array and second and third value are parsed to integers
		//second value should be duration which the Finch reads in milliseconds therefore
		//that variable needs to be multiplied by 1000
		//try/catch exception used to handle number format exception
		//inputs passed as parameters to ForwardValidation method
	break; case 4: 
		inputOne = commandindex[0];
		inputTwo = commandindex [1];
		inputThree = commandindex[2];
		inputFour = commandindex[3];
		try { parseTwo = (Integer.parseInt(inputTwo)) * 1000;
			  parseThree = Integer.parseInt(inputThree);
			  parseFour = Integer.parseInt(inputFour); }
		catch (Exception NumberFormatException) { 
			System.out.println("You have entered incorrectly, please follow the instructions and Try Again"); }
		RLValidation(inputOne, parseTwo, parseThree, parseFour);
		//first two values are the directional move and duration, just like previous cases
		//last two input values represent the right and left wheel 
		//speed velocities, try/catch method used for number format exception
		//input values as parameters to RLValidation method
	break;		
	}//breaks placed to break out of switch (reach to relevant methods)
	if (commandindex.length > 4 || in!= null) 	{
		System.out.println("Invalid command Please Enter Direction, Duration, and Speed");
		System.out.println("(For right or left direction, enter right wheel then left wheel speed)"); }
	Input();//validation check for more than 4 inputs by the user, error message displayed
} catch (Exception NoSuchElementException)	{ in.close(); }
}//catching no such element exception and close scanner

private static void StopRunValidation(String StopRunMove) 	{ 
//stop and run method (run to trigger Finch movement, counted as a single input value-no spaces)
	boolean stop = (StopRunMove.equalsIgnoreCase("S"));		 
	boolean run =  (StopRunMove.equals("RUN") || (StopRunMove.equals("run"))); 
	//StopRunMethod, initialising movement of Finch
if (stop == true) {
	int duration = 500;
	int leftwheel = 0;
	int rightwheel = 0;
	FinchMovements.add(new int[] {leftwheel, rightwheel, duration});
	//add to array list as one index (one index is 1 dimensional array of 3 values ^ ([0],[1],[2]))
	System.out.println("Added to arraylist."); 
	Input(); }
	//stopping variables added to array list if stop is true, go back to input method
else if (run ==true) {
		Menu.Run(FinchMovements); }
	//if run is true then pass the array list to Menu class and make Finch move accordingly
else if (run == false || stop == false)	{
		System.out.println("Please enter the Directional move and two values for Duration and Speed for the Finch robot"); 
		Input(); }
	//if neither of the inputs equal to run or stop, then display error message and return to input method
}
private static void ForwardValidation(String ForwardMove, int ForwardDuration,int ForwardSpeed) { 
//forward movement validation
	boolean move = (ForwardMove.equalsIgnoreCase("F"));
	boolean duration = (ForwardDuration>999 && ForwardDuration<6001);
	boolean speed = (ForwardSpeed < 101 && ForwardSpeed > 0);
	//initialising the conditions for direction, duration, and speed (according to specification)
if (move == false || duration == false || speed == false) {		
	System.out.println("Please enter the Directional move and two values for Duration and Speed for the Finch robot");
		Input(); }
	//if all conditions are false, then display error message and return to input method
else if (move == true || duration == true || speed == true) 		{
	int leftwheel = ForwardSpeed;
	int rightwheel = ForwardSpeed;
		FinchMovements.add(new int[] {leftwheel, rightwheel, ForwardDuration});
		//add to array list as one index (one index is 1 dimensional array of 3 values ^ ([0],[1],[2]))
		System.out.println("Added to arraylist.");
	Input(); }	
	//if all conditions are met then forward movement variables added to array list
	//and return to input method
}
private static void RLValidation(String RLMove, int RLDuration,int RSpeed, int LSpeed) { 
//right and left movement validation, followed by two more methods for validation (see below)
	boolean Rmove = (RLMove.equalsIgnoreCase("R"));										 
	boolean Lmove = (RLMove.equalsIgnoreCase("L"));	
	boolean duration = (RLDuration>999 && RLDuration<6001);
	boolean Rspeed = (RSpeed < 101 && RSpeed > 0);
	boolean Lspeed = (LSpeed < 101 && LSpeed > 0);
	//initialising the conditions for left or right movement as well and duration and speed for both wheels
if (Lmove == false && Rmove == false || duration == false|| Rspeed == false || Lspeed == false)		{
	System.out.println("Please enter the Directional move and two values for Duration and Speed for the Finch robot");
		Input(); }	
	//if all conditions are false then display error message and return to input
else if (Lmove == true || Rmove ==true && duration == true && Rspeed ==true && Lspeed == true )		{ 
		RLWheelValidation(RLMove, LSpeed, RSpeed, RLDuration); }
	//if all conditions are true then send variables as parameters to RLWheelValidation method
}
private static void RLWheelValidation(String RLWheelmove, int leftWheel, int rightWheel, int Duration) { 
//right and left wheel validation
if (RLWheelmove.equalsIgnoreCase("L")) 										{		
		if (LeftWheelValidation(leftWheel, rightWheel) ==true) {
			FinchMovements.add(new int[] {leftWheel, rightWheel, Duration});
			//add to array list as one index (one index is 1 dimensional array of 3 values ^ ([0],[1],[2]))
			System.out.println("Added to arraylist.");
		Input(); }	
	}
//if the directional move is left, and if the LeftWheelValidation is true then
//add the left, right wheel velocity and duration in the the array list, return to input method
//For LeftWheelValidation, see below method
else if (RLWheelmove.equalsIgnoreCase("R")) 								 { 
		if (RightWheelValidation(leftWheel, rightWheel) == true) {
			FinchMovements.add(new int[] {leftWheel, rightWheel, Duration});
			//add to array list as one index (one index is 1 dimensional array of 3 values ^ ([0],[1],[2]))
			System.out.println("Adding to arraylist.");
		Input(); }
	}
//if the directional move is right, and if the RightWheelValidation is true then
//add the left, right wheel velocity and duration in the the array list, return to input method
//For the RightWheelValidation, see below method
}
private static boolean LeftWheelValidation(int leftW, int rightW) {
//method checks to make sure the wheels velocities can move orthogonally 
//for turning right
if (rightW == leftW)	{
	System.out.println("If you want to turn Left then, left wheel speed MUST NOT equal to right wheel speed"); 
	return false; }
//if the wheels are equal to each other then display the message
if (leftW >= rightW)	{
	System.out.println("If you want to turn Left then, left wheel speed MUST be smaller than right wheel speed");	
	return false; }
//if the left wheel is bigger than the right wheel then display the message
return true;
//if all are false, then the this method returns true (passes full validation)
}
private static boolean RightWheelValidation(int LWheel, int RWheel) {
//method checks to make sure the wheels velocities can move orthogonally 
//for turning left
if (RWheel == LWheel)	{
	System.out.println("If you want to turn Right then, right wheel speed MUST NOT equal to left wheel speed"); 	
	return false; }
//if the wheels are equal to each other then display the message
if (RWheel >= LWheel)   {
	System.out.println("If you want to turn Right then, right wheel speed MUST be smaller than left wheel speed"); 
	return false; }
//if the right wheel is bigger than the left wheel then display the message
return true;
//if all are false, then the this method returns true (passes full validation)
}
private static void Backtrack(String Bmove, int Bval) {
//backtracking method
ArrayList<int[]> SubFinchMovements = new ArrayList<int[]>(); 
//constructor for array list SubFinchMovements
int minus = 1;
//variable used in if statement within this if statement (nested)
	if (!Bmove.equalsIgnoreCase("B") || !(Bval <= FinchMovements.size())) 			{
		System.out.println("Please enter B to backtrack, correct amount of commands to retrace please"); 
		Input(); }
	//if first input value and second value doesn't meet the correct conditions
	//then a message will be displayed and return to input method
	else if (Bmove.equalsIgnoreCase("B") && Bval <= FinchMovements.size()) { 
	//if conditions are met
		 for(int highest = FinchMovements.size() - 1; highest >= 0; highest-- ) 		{
		 //do from the last element of FinchMovements to 0 working backwards
				SubFinchMovements.add(new int[] {FinchMovements.get(highest)[0],FinchMovements.get(highest)[1],FinchMovements.get(highest)[2]});
				//(last element of FinchMovements will be stored as the first element in SubFinchMovements
				//add to SubFinchMovements from FinchMovements as one index (one index is 1 dimensional array of 3 values ^ ([0],[1],[2]))
				if (Bval == minus) { break; }
				//if Bval equals to minus then break out of for loop and stop adding commands to SubFinchMovements
				else { minus+=1; }
				//otherwise add 1 to minus
	} 
	//Loops until Bval equals to minus, making sure that only a given amount of commands are added in SubFinchMovements
	System.out.println("Retrace previous " + Bval + " commands" );
	//Printing the amount of Bval
    	for(int lowest = 0; lowest < SubFinchMovements.size(); lowest++ ) 	{
    		FinchMovements.add(new int[] {SubFinchMovements.get(lowest)[0],SubFinchMovements.get(lowest)[1],SubFinchMovements.get(lowest)[2]}); }
    		//add to SubFinchMovements from FinchMovements as one index (one index is 1 dimensional array of 3 values ^ ([0],[1],[2]))
    		//For loop used to add all commands in ascending order to FinchMovements
    	System.out.println("The Finch will follow " + FinchMovements.size() + " commands");
    	//Printing the amount of commands in FinchMovements (if they want to backtrack more commands, then they know the value
    	//they should not exceed to backtrack
    	Input(); }	
	}	//finally, return to input
}

	