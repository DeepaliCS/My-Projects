import java.util.ArrayList;

public class Lab15Main {
//FILE INFORMATION FOR TESTING EACH DATASET
static String AlgorithmInitials = "SHC";
static String NumberOfCities = "100";
static int Iterations = 100000;

//DISPLAY OPTIONS FOR FITNESS AND OTHER INFORMATION
static boolean PrintFinalFitnesses = true;
static boolean PrintAllFitnesses = false;
static boolean VitalChecks = false;
static boolean IterationView = true;
static boolean PrintAlgorithmVitals = false;

//VARIABLE USED FOR AUTOMATIC READING OF FILENAME
static String file = "TSP_" + NumberOfCities;	

	public static void main(String[] args) {
		
	//PARSING INPUT NUMBER OF CITY VALUE TO INTEGER TO COMPUTE IN THE FUTURE
	int CityNumber = Integer.parseInt(NumberOfCities);
		
	//CREATING AN ARRAYLIST AND 2D ARRAY FOR TOUR AND DISTANCE MATRIX
	ArrayList <Integer> Tour = new  ArrayList <Integer>(); double[][] Distance = null;

	//CALLING METHODS ACCORDINGLY
	Tour = ReadingTour(Tour); Distance = ReadingMatrix(Distance);
	
	//CREATING THE T AND TEMPERATURE FOR SA AND SHC S
	double SHCT = CityNumber / 12; int SATemp = 3000;
	
	//PERFORMING ALL VITAL S CHECKS
	if (VitalChecks == true) {System.out.println("All Vitals:");PerformVitalChecks(CityNumber, Tour, Distance); System.out.println("______" +'\n');}
	
	//TEST THE S 
	//(RUN ALGORITM RUN 20 FINAL FITNESS ITERATIONS)
	//COMPARE  RUNS 1 ITERATION OF FINAL FITNESS FOR ALL S
	RunAlgorithm(AlgorithmInitials, CityNumber, Distance, SHCT, SATemp);
	//CompareAlgorithms(AlgorithmInitials, CityNumber, Distance, SHCT, SATemp);
	   
	}
	
	//RUNNING ONE  FOR 20 ITERATION FINAL FITNESS
	public static void RunAlgorithm (String AlgorithmInitials, int CityNumber, double[][] DistanceMatrix, double SHCT, int Temperature )
	{			 
		String FinalFitnessDisplay = "Printing Final Fitnesses";
		String AllFitnessDisplay = "Printing All Fitnesses";
		int FinalIterations = 20;
		
			 switch(AlgorithmInitials) {
			 case "RMHC": 
				 System.out.println("Running RHMC" + '\n');
				 if (PrintFinalFitnesses == true) { System.out.println(FinalFitnessDisplay);}
				 if (PrintAllFitnesses == true) {System.out.println(AllFitnessDisplay);} System.out.println();
				 for (int i =0; i< FinalIterations; i++) { Algorithms.RandomMutationHillClimber(CityNumber, DistanceMatrix, Iterations, PrintFinalFitnesses, PrintAllFitnesses);}
			 break;
			 case "SHC": System.out.println("Running SHC" + '\n');
			 	if (PrintFinalFitnesses == true) { System.out.println(FinalFitnessDisplay);}
			 	if (PrintAllFitnesses == true) {System.out.println(AllFitnessDisplay);} System.out.println();
			 	for (int i =0; i< FinalIterations; i++) {Algorithms.StochasticHillClimber(CityNumber, DistanceMatrix, SHCT, Iterations, PrintFinalFitnesses, PrintAllFitnesses);}
			 break;
			 case "RRHC": System.out.println("Running RRHC" + '\n');
			 	if (PrintFinalFitnesses == true) { System.out.println(FinalFitnessDisplay);}
			 	if (PrintAllFitnesses == true) {System.out.println(AllFitnessDisplay);} System.out.println();
			 	for (int i =0; i< FinalIterations; i++) {Algorithms.RandomRestartHillClimber(CityNumber, DistanceMatrix, Iterations, PrintFinalFitnesses, PrintAllFitnesses);}
			 break;
			 case "SA": System.out.println("Running SA" + '\n');
			 	if (PrintFinalFitnesses == true) { System.out.println(FinalFitnessDisplay);}
			 	if (PrintAllFitnesses == true) {System.out.println(AllFitnessDisplay);} System.out.println();
			 	for (int i =0; i< FinalIterations; i++) {Algorithms.SimulatedAnnealingAlgorithm(CityNumber, DistanceMatrix, Temperature, Iterations, PrintFinalFitnesses, PrintAllFitnesses);}
			 	break;
		 default:
             throw new IllegalArgumentException("The Only Options are RMHC, SHC, RRHC And, SA");
			 }
			 
	}
	
	//PRINTING THE DATASET INFORMATION..METHOD SPEAK FOR THEMSELVES
	public static void PerformVitalChecks (int TotalNumberOfCities, ArrayList initialTour, double[][] DistanceMatrix)
	{

		System.out.println("OptimalSolutionCheck");//GETTING THE OPTIMAL SOLUTION OF THE INITIAL TOUR - USED FOR SHC FITNESS
		OptimalSolutionCheck(TotalNumberOfCities, initialTour, DistanceMatrix); System.out.println();
		
		System.out.println("RandomPermCheck");//CHECKING IF THE  RANDOM PERMUTATION METHOD WORKS 
		RandomPermCheck(TotalNumberOfCities,initialTour); System.out.println();

		System.out.println("SwapCheck");//CHECKING IF THE SWAP METHOD WORKS
		SwapCheck(TotalNumberOfCities,initialTour); System.out.println();

		System.out.println("FitnessCheck");//CHECKING IF THE FITNESS METHOD WORKS
		FitnessCheck(TotalNumberOfCities, initialTour, DistanceMatrix); System.out.println();
		
		System.out.println("MSTCalculation");//CHECKING IF MST WORKS
		MSTCalculation(DistanceMatrix); System.out.println();
	}
	public static ArrayList<Integer> ReadingTour (ArrayList<Integer> Tour)
	{
		Tour = TSP.ReadIntegerFile(file + "_OPT.txt");
		//System.out.println(Tour);System.out.println();
		return Tour;
	}
	public static double[][] ReadingMatrix (double[][] DistanceMatrix)
	{
		DistanceMatrix = TSP.ReadArrayFile(file + ".txt", " ");
		//TSP.PrintArray(DistanceMatrix);
		return DistanceMatrix;
	}
	public static void OptimalSolutionCheck (int TotalNumberOfCities, ArrayList initialTour, double[][] DistanceMatrix)
	{
		System.out.println(TSP.TSPFitness(TotalNumberOfCities, initialTour, DistanceMatrix));
	}
	public static double OptimalSolutionReturn (int TotalNumberOfCities, ArrayList initialTour, double[][] DistanceMatrix)
	{
		return TSP.TSPFitness(TotalNumberOfCities, initialTour, DistanceMatrix);
	}
	public static void RandomPermCheck (int TotalNumberOfCities, ArrayList initialTour)
	{
		System.out.println(initialTour);
		initialTour = TSP.RandomPermutation(TotalNumberOfCities);
		System.out.println(initialTour);
	}
	
	public static void SwapCheck (int TotalNumberOfCities, ArrayList initialTour)
	{
		System.out.println(initialTour);
		initialTour = TSP.SmallChangeSwap(initialTour);
		System.out.println(initialTour);
	}
	public static void FitnessCheck (int TotalNumberOfCities, ArrayList initialTour, double[][] DistanceMatrix) 
	{
		System.out.println(initialTour);
		initialTour = TSP.RandomPermutation(TotalNumberOfCities);
		System.out.println(TSP.TSPFitness(TotalNumberOfCities, initialTour, DistanceMatrix) + '\n');
		
		System.out.println(initialTour);
		initialTour = TSP.RandomPermutation(TotalNumberOfCities);
		System.out.println(TSP.TSPFitness(TotalNumberOfCities, initialTour, DistanceMatrix));
	}
	
	public static void MSTCalculation (double[][] DistanceMatrix)
	{
		System.out.println(MinimumSpanningTree.MSTCount(MinimumSpanningTree.MST(MinimumSpanningTree.MST(DistanceMatrix))));	
	}
	//METHOD TO SET OPTIONS TO PRINT FINAL OR ALL FITNESSES - LINKED TO TSP CLASS
	public static boolean BetterDisplay ()
	{
		boolean Space;
	 	if (PrintFinalFitnesses == true && PrintAllFitnesses == true) {Space = true;}
	 	else {Space = false;}
	 	
		return Space;
	}
	public static boolean PrintAlgoVitals ()
	{
		return PrintAlgorithmVitals;
	}
	public static boolean IterationView ()
	{
		return IterationView;
	}
	
	//RUNNING ALL S FOR 1 INTERATION - FINAL FITNESS
	public static void CompareAlgorithms (String AlgorithmInitials, int CityNumber, double[][] DistanceMatrix, double SHCT, int Temperature )
	{

System.out.println("Running RHMC");
//				 if (PrintFinalFitnesses == true) { System.out.println(FinalFitnessDisplay);}
//				 if (PrintAllFitnesses == true) {System.out.println(AllFitnessDisplay);} System.out.println();
				 for (int i =0; i< 1; i++) { Algorithms.RandomMutationHillClimber(CityNumber, DistanceMatrix, Iterations, PrintFinalFitnesses, PrintAllFitnesses);}
				 System.out.println();
 System.out.println("Running SHC");
//			 	if (PrintFinalFitnesses == true) { System.out.println(FinalFitnessDisplay);}
//			 	if (PrintAllFitnesses == true) {System.out.println(AllFitnessDisplay);} System.out.println();
			 	for (int i =0; i< 1; i++) {Algorithms.StochasticHillClimber(CityNumber, DistanceMatrix, SHCT, Iterations, PrintFinalFitnesses, PrintAllFitnesses);}
			 	System.out.println();
System.out.println("Running RRHC");
//			 	if (PrintFinalFitnesses == true) { System.out.println(FinalFitnessDisplay);}
//			 	if (PrintAllFitnesses == true) {System.out.println(AllFitnessDisplay);} System.out.println();
			 	for (int i =0; i< 1; i++) {Algorithms.RandomRestartHillClimber(CityNumber, DistanceMatrix, Iterations, PrintFinalFitnesses, PrintAllFitnesses);}
			 	System.out.println();
 System.out.println("Running SA");
//			 	if (PrintFinalFitnesses == true) { System.out.println(FinalFitnessDisplay);}
//			 	if (PrintAllFitnesses == true) {System.out.println(AllFitnessDisplay);} System.out.println();
			 	for (int i =0; i< 1; i++) {Algorithms.SimulatedAnnealingAlgorithm(CityNumber, DistanceMatrix, Temperature, Iterations, PrintFinalFitnesses, PrintAllFitnesses);}
	}
	
}