import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Algorithms {
// RANDOM GLOBAL VARIABLE
private static Random randomGeneratedNumber = new Random();

// THIS IS THE RANDOM MUTATION HILL CLIMBING METHOD 
	public static double RandomMutationHillClimber (int TotalNumberOfCities, double[][] DistanceMatrix, int Iteration, boolean Print, boolean PrintAll)	
	{		
		//GETTING A RANDOM PERMUTATION USING THE RANDOM PERMUTATION
		ArrayList<Integer> Tour = TSP.RandomPermutation(TotalNumberOfCities); 
		
		//CALLING THE FITNESS FUNCTION FOR THE FIRST PERMUTATION
		double Fitness = TSP.TSPFitness(TotalNumberOfCities, Tour, DistanceMatrix);
		
		
		//FOR LOOP RUNNING TO FIND THE BEST FITNESS
		for(int i=0; i<Iteration; i++){
				
			//CLONING THE PREVIOUS TOUR
			ArrayList<Integer> ClonedTour = (ArrayList<Integer>) Tour.clone();
			
			//DOING THE SMALL CHANGE ON THE NEW TOUR
			ClonedTour = TSP.SmallChangeSwap(ClonedTour);
			
			//GETTING THE FITNESS OF THE NEW TOUR
			Fitness = TSP.TSPFitness(TotalNumberOfCities, Tour, DistanceMatrix); 
			//if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("Old Fitness= " +Fitness);}
			
			//GETTING THE FITNESS OF THE CLONED TOUR
			double newFitness = TSP.TSPFitness(TotalNumberOfCities, ClonedTour, DistanceMatrix);
			//if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("New Fitness= " +Fitness);}
			
			//IF THE NEW FITNESS IS BETTER THAN THE OLD THEN CLONE THE OLD TOUR WITH THE NEW
			if(newFitness < Fitness)
			{
				//TOUR USED FOR FUTURE ITERATIONS
				Tour = (ArrayList<Integer>) ClonedTour.clone();
				
				//PRINTING CONDITION FOR ALL FITNESSES GENERATED
				if (PrintAll == true) {TSP.PrintFitness(TotalNumberOfCities, Tour, DistanceMatrix, i);}
			}			
		}	
		
		//PRINTING FINAL FITNESS IF CONDITION MET
		if (Print == true) {TSP.PrintFinalFitness(TotalNumberOfCities, Tour, DistanceMatrix);}
			
		return TSP.TSPFitness(TotalNumberOfCities, Tour, DistanceMatrix);
	}
	
// THIS METHOD IS THE STOCHASTIC HILL CLIMBER
	public static void StochasticHillClimber(int TotalNumberOfCities, double[][] DistanceMatrix, double T, int Iteration, boolean Print, boolean PrintAll) 
	{	
		//GETTING A RANDOM PERMUTATION USING THE RANDOM PERMUTATION
		ArrayList<Integer> Tour = TSP.RandomPermutation(TotalNumberOfCities); 
	
		//CALLING THE FITNESS FUNCTION FOR THE FIRST PERMUTATION
		double Fitness = TSP.TSPFitness(TotalNumberOfCities, Tour, DistanceMatrix); 
		
		
		//FOR LOOP RUNNING TO FIND THE BEST FITNESS
		for (int i=0; i<Iteration; i++)
		{
			//CLONING THE PREVIOUS TOUR
			ArrayList<Integer> ClonedTour = (ArrayList<Integer>) Tour.clone();
		
			//DOING THE SMALL CHANGE ON THE NEW TOUR
			ClonedTour = TSP.SmallChangeSwap(ClonedTour);  
			
			//GETTING THE FITNESS OF THE NEW TOUR
			Fitness = TSP.TSPFitness(TotalNumberOfCities, Tour, DistanceMatrix);
			//if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("Old Fitness= " +Fitness);}
			
			//GETTING THE FITNESS OF THE CLONED TOUR
			double newFitness = TSP.TSPFitness(TotalNumberOfCities, ClonedTour, DistanceMatrix);
			//if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("New Fitness= " +Fitness);}
				
			//GENERATING A RANDOM NUMBER AND GETTING THE PROBABILITY BASED ON FORMULA
			double randomNumber = randomGeneratedNumber.nextDouble();
			double probability = 1/(1+ Math.exp((newFitness - Fitness)/T)); 
			
			//IF THE PROBABILITY IS BIGGER THAN THE RANDOM NUMBER THEN THEN CLONE THE OLD TOUR WITH THE NEW
			if (probability > randomNumber) 
			{ 
				//TOUR USED FOR FUTURE ITERATIONS
				Tour = (ArrayList<Integer>) ClonedTour.clone();		
				
				//IF CONDITION MET THEN DISPLAY DATA
				if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("Probability= " +probability);}
				if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("Random Number= " +randomNumber);}
				
				//PRINTING CONDITION FOR ALL FITNESSES GENERATED
				if (PrintAll == true) {TSP.PrintFitness(TotalNumberOfCities, Tour, DistanceMatrix, i);}
			}			
		}	
		//PRINTING FINAL FITNESS IF CONDITION MET
		if (Print == true) {TSP.PrintFinalFitness(TotalNumberOfCities, Tour, DistanceMatrix);}

	}	
	
// THIS METHOD IS THE RRHC - USES THE RMHC METHOD
	public static void RandomRestartHillClimber (int TotalNumberOfCities, double[][] DistanceMatrix, int Iteration, boolean Print, boolean PrintAll)
	{ 		
		//CREATING A FITNESS STORAGE, NEW ITERATION SET (10), AND DECLARING FITNESS
		ArrayList<Double> FitnessStorage = new ArrayList<Double>(); 
		int newIter = Iteration/10;
		double Fitness = 0;
		boolean tempPrint = false;
		boolean tempPrintall = true;
		
		if (Print ==true) {tempPrint =true;}
		else if (Print == false ) {tempPrint =false;}
		else if (PrintAll ==false) {tempPrintall =false;}
		else if (PrintAll ==true) {tempPrintall =true;}
		
		//THE LOOP WILL RUN FOR 10 ITERATIONS
		for(int i=0; i<10; i++)
		{
			tempPrint= false; tempPrintall = false;
			//THE RMHC WILL RUN FOR ONE SET OF INNER ITERATIONS AND STORE ITS BEST FITNESS IN THE ARRAYLIST
			Fitness = RandomMutationHillClimber(TotalNumberOfCities, DistanceMatrix, newIter, tempPrint,  tempPrintall); 
			FitnessStorage.add(Fitness);
		}
		
		//PRINTING CONDITION FOR ALL (10) FITNESSES GENERATED FROM ARRAYLIST
		if (PrintAll == true) {System.out.println(FitnessStorage);}

		//GETTING THE BEST FITNESS FROM THE ARRAYLIST
		int minIndex =FitnessStorage.indexOf(Collections.min(FitnessStorage));
		
		//PRINTING FINAL FITNESS IF CONDITION MET
		if (Print == true) {System.out.println(FitnessStorage.get(minIndex));}

	}
		
// THIS METHOD IS A TEST ALGORITHM CALLED SIMULATED ANNEALING
	
	public static void SimulatedAnnealingAlgorithm (int TotalNumberOfCities, double[][] DistanceMatrix, int Temperature, int Iteration, boolean Print, boolean PrintAll)
	{		
		//GETTING A RANDOM TOUR USING THE RANDOM PERMUATION METHOD,
		ArrayList<Integer> Tour = TSP.RandomPermutation(TotalNumberOfCities); 
		
		//CALCULATING THE COOLING RATE WHICH USES THE 
		double coolingRate = Lamda(Temperature, Iteration); 
				
		//LOOP TO DISCOVER THE BEST FITNESS
		for (int i=0; i<Iteration; i++){
			
			//GENERATING THE FITNESS UDING TSPFITNESS METHOD OF THE CURRENT TOUR
			double Fitness = TSP.TSPFitness(TotalNumberOfCities, Tour, DistanceMatrix);
			//if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("Old Fitness= " +Fitness);}
			
			//CLONING THE CURRENT TOUR
			ArrayList<Integer> ClonedTour = (ArrayList<Integer>) Tour.clone();
			
			//MAKING A SMALL CHANGE TO THE CLONED TOUR
			ClonedTour = TSP.SmallChangeSwap(ClonedTour);
			
			//GETTING THE FITNESS OF THE CLONED TOUR
			double newFitness = TSP.TSPFitness(TotalNumberOfCities, ClonedTour, DistanceMatrix);
			//if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("New Fitness= " +Fitness);}
			 
			//IF THE NEW FITNESS IS BETTER THAN THE OLD THEN CLONE THE OLD TOUR WITH THE NEW
			if (newFitness < Fitness)
			{
				//TOUR USED FOR FUTURE ITERATIONS
				Tour = (ArrayList<Integer>)ClonedTour.clone();
				
				//PRINTING CONDITION FOR ALL FITNESSES GENERATED
				if (PrintAll == true) {TSP.PrintFitness(TotalNumberOfCities, Tour, DistanceMatrix, i);}
				
				//IF CONDITION MET THEN DISPLAY DATA
				if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("No Probability (First Condition Met)");}
				if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("No Random Number (First Condition Met)");}
				if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("Cooling Rate= " +coolingRate);}
				if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("Temperature= " +Temperature);}
			}
				//ELSE WORK OUT THE PROBABILITY AND COMPARE IT WITH A RANDOM NUMBER
				else 
				{   //GENERATE A RANDOM NUMBER 
					double randomNumber = randomGeneratedNumber.nextDouble(); 
					
					//GETTING A NEGATIVE FITNESS DIFFERENCE (AS THE FORMULA SAYS)
					double negativeFitnessDifference = Fitness - newFitness; 
					
					//PRODUCING A PROBABILITY BASED ON THE FITNESS DIFFERENCE AND TEMPERATURE
					double probability = Math.exp(negativeFitnessDifference/Temperature); 
					
					//PRINTING CONDITION FOR ALL FITNESSES GENERATED
					if (PrintAll == true) {TSP.PrintFitness(TotalNumberOfCities, Tour, DistanceMatrix, i);}
					
					//IF CONDITION MET THEN DISPLAY DATA
					if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("Probability= " +probability);}
					if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("Random Number= " +randomNumber);}
					if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("Cooling Rate= " +coolingRate);}
					if (Lab15Main.PrintAlgorithmVitals ==true) { System.out.println("Temperature= " +Temperature);}
					
					//IF THE PROBABILITY IS BIGGER THAN THE RANDOM NUMBER THEN CLONE THE NEW TOUR WITH THE OLD
					if (probability > randomNumber) 
					{
						//TOUR USED FOR FUTURE ITERATIONS
						Tour = (ArrayList<Integer>) ClonedTour.clone();
					} 
				}
			
			//LET THE TEMPERATURE BE EQUAL TO THE TEMPERATURE MULTIPLIED BY THE COOLING RATE (FOR EACH ITERATION)
			Temperature *= coolingRate; //System.out.println("Temp is  : "+Temperature);
		}
		
		//PRINTING FINAL FITNESS IF CONDITION MET
		if (Print == true) {TSP.PrintFinalFitness(TotalNumberOfCities, Tour, DistanceMatrix);}
		//System.out.println();
	}

// THIS METHOD IS CALLED FROM THE SA METHOD AND CALCULATES LAMBDA TO FORM A COOLING RATE
	public static double Lamda (double initialTemperature, int Iteration)
	{
		//DECLARING THE CONSTANT TEMPERATURE USED IN THE LAMBA CALCULATION
		double constantTemp = 0.0000001;
		
		//TAKING THE CONSTANT TEMPERATURE AWAY FROM THE GIVEN TEMPERATURE
		double x = Math.log(constantTemp)-Math.log(initialTemperature);
		
		//DIVIDING THIS BY THE ITERATION VALUE
		x/=Iteration;
			
		//GENERATES THE EXPONENTIAL VALUE OF X WHICH EQUALS LAMBA, RETURNS LAMBDA TO SA METHOD
		double Lamda = Math.exp(x);
		return Lamda;
	}
	
	
}
