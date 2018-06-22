import java.util.Random;

public class CannonSolution
{
//_____________________________________________________________________________________
	//RELEVANT VARAIBLES
	static private Random rand;
	public double angle;
	public double mvel;
	public boolean angleChanged;
//_____________________________________________________________________________________
	
	//GENERATING A RANDOM NUMBER BETWEEN THE VARIABLES MAX AND MIN
	public CannonSolution(){
		angle = UR(25,55);
		mvel = UR(1500,1650);	
	}
//_____________________________________________________________________________________
	//RANDOM NUMBERS AS ANGLE AND MVEL FOR A STARTING POINT
	public void RandomStartingPoint(){
		angle = UR(25, 55);
		mvel = UR(1500, 1650);
	}
//_____________________________________________________________________________________
	
	//Create a uniformly distributed random double between a and b inclusive
	static public double UR(double a,double b)
	{
		if (rand == null) 
		{
			rand = new Random();
			rand.setSeed(System.nanoTime());
		}
		return((b-a)*rand.nextDouble()+a);
	}
//______________________________________________________________________________________
	//USING THE GET MAXRANGE MEHOD TO GET THE RANGE/FITNESS OF ANGLE AND MVEL VALYES
	public double MinAndMaxFitness(double angle, double mvel)	{
		double k12 = Cannon.GetMaxRange(angle, mvel);
		//System.out.println("K12 = " + k12 + "\nAngle: " + angle + "\nMuzzle velocity: " + mvel);
		return k12;
	}
//______________________________________________________________________________________
	//FITNESS FUNCTION GIVEN FROM THE LABSHEET
	public double FitnessFunction(double theta, double vzero, double targetRange){
		double range = Cannon.GetMaxRange(theta, vzero);
		double fitness = Math.abs(range - targetRange);
		return fitness;
	}
//______________________________________________________________________________________

	//SMALL CHANGE METHOD, IF CHANGE IT TRUE THEN ANGLE WILL CHANGE OTHERWISE MVEL WILL
	public double SmallChangeOperator(double angle, double mvel){
		boolean change = rand.nextBoolean();		

		if(change == true){
			double smallAngle = UR(-1,1); //±x
			angle += smallAngle;
			
			//ANGLE CHECKS FOR LIMITS
			if(angle<25){
				angle = 25;
			}
			else if(angle>55){
				angle = 55;
			}
			angleChanged = true;
			return angle;
		}
		// CONDITION FOR MVEL
		else { 
			double smallAngle = UR(-2.5,2.5);//±x
			angle += smallAngle;
			//MVEL CHECKS FOR LIMITS
			if(mvel < 1500){
				mvel = 1500;
			}
			else if(mvel>1650){
				mvel = 1650;
			}
			angleChanged = false;
			return mvel;
		}
	}
//______________________________________________________________________________________
	//CHANGING THE PARAMETER AFTER EACH ITERATION
	public static double ChangeAParam(double n){
		rand = new Random();
		boolean positive = rand.nextBoolean();
		double percentage = (n * 1)/100;


		if(positive == false){
			percentage = n*-1;
		}
		return percentage;
	}
//______________________________________________________________________________________
	//Display the string without a new line
	public void print(double angle, double mvel)
	{
		System.out.print("angle: " + angle + " muzzle velocity: " + mvel);
	}
}