import java.util.ArrayList;

public class Lab10 {
	public static void main(String args[]) {
		
		System.out.println("10.3 Exercise 1: The CannonSolution Class");
		System.out.println("(Implemented the code on the labsheet, See code below & Spreadsheet 1)");
		System.out.println();
		
		double r = Cannon.GetMaxRange(40.0,1575.0);
		System.out.println("Range= "+r);
		ArrayList<Double> xt = Cannon.GetX();
		ArrayList<Double> yt = Cannon.GetY();
		System.out.println("X= "+xt.size());
		System.out.println("Y= "+yt.size()); System.out.println();
		
		System.out.println("______________________"); System.out.println();
		System.out.println("10.4 Exercise 2: Hill Climbing Method"); 
		System.out.println("Maximum and Minimum range of K12: (Q1 & Q2)");
		System.out.println("Demonstrate how to display the maximum and minimum ranges of K12");
		System.out.println("(Show Spreasheet 2 average after 1000 iterations for max and min)");
		System.out.println(); RMHCMinAndMax(1000); 
		System.out.println("______________________");System.out.println();
				
		System.out.println("For Q3, Q4, and Q5 show Spreadsheet 3 and CanonSolution");
		RMHC(1000, 95000); System.out.println();
		System.out.println("______________________"); System.out.println();
		
		System.out.println("10.4.5 (Show Spreadsheet 4 - Appriate number of iterations comparison)");

	}	
//______________________________________________________________________________________
		
	//CANNON OBJECT GOES TO CANNONSOLUTION CLASS AND INITIALISES THEM A RANDOM VALUE
	private static void RMHC(int iter, double target){
			CannonSolution cannon = new CannonSolution();
			cannon.RandomStartingPoint();
			
		//LOCAL VARIABLES
		double angle = cannon.angle;
		double mvel = cannon.mvel;
			
		//COMPARISON FITNESS
		double fitness = cannon.FitnessFunction(angle, mvel, target);
		
		//RUNNING THE RMHC FOR 'ITER' ITERATIONS
		for (int i=0; i<iter;++i) {
			//SMALL CHANGE TO THE VARIABLES
			double paramChange = cannon.SmallChangeOperator(angle, mvel);

			//CALCULATING THE FITNESS
			double newFitness = cannon.FitnessFunction(angle, mvel, target);
			
			if(newFitness < fitness){
				fitness = newFitness;
				
//				System.out.println("Fitness " + fitness);
//				System.out.println("Angle: " + angle);
//				System.out.println("Muzzle velocity: " + mvel);
//				System.out.println();
				System.out.println(fitness);
				
			}
			
			if(cannon.angleChanged == true){
				angle = paramChange;
			}
			else {
				mvel = paramChange;
			}
			cannon.angleChanged = false;
		}
		
	}
//______________________________________________________________________________________
	public static void RMHCMinAndMax(int iter){
			CannonSolution cannon = new CannonSolution();
			cannon.RandomStartingPoint();
			
			double angle = cannon.angle;
			double mvel = cannon.mvel;
			
			// Fitness Function
			double fitness = cannon.MinAndMaxFitness(angle, mvel);

			for(int i=0; i<iter; i++){
				// Small Change Operator
				double paramChange = cannon.SmallChangeOperator(angle, mvel);
				double newFitness = cannon.MinAndMaxFitness(angle, mvel);

				//CALCULATING THE FITNESS
				if(newFitness < fitness){
					fitness = newFitness;
//					System.out.println("Fitness " + fitness);
//					System.out.println("Angle: " + angle);
//					System.out.println("Muzzle velocity: " + mvel);
//					System.out.println();
					System.out.println(fitness);
				}
				
				//UPDATING THE VALUES FOR THE NEXT ITERATION
				if(paramChange <= 55){
					angle = paramChange;
				}
				else if(paramChange >= 1500){
					mvel = paramChange;
				}
			}
			System.out.println();
		}
}
