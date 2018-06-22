import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//Some useful code for the CS2004 (2016-2017) Travelling Salesman Worksheet
public class TSP {
// RANDOM GLOBAL VARIABLE
static private Random randomGeneratedNumber;

	
// THIS METHOD USED TO PRINT THE DISTANCEMATRIX OR MST MATRIX
	static public void PrintArray(double x[][])
	{
		for(int i=0;i<x.length;++i)
		{
			for(int j=0;j<x[i].length;++j)
			{
				System.out.print(x[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
// THIS METHOD IS USED TO READ IN THE DISTANCE MATRIX INTO A DOUBLE 2D ARRAY	
	static public double[][] ReadArrayFile(String filename,String sep)
	{
		double res[][] = null;
		try
		{
			BufferedReader input = null;
			input = new BufferedReader(new FileReader(filename));
			String line = null;
			int ncol = 0;
			int nrow = 0;
			
			while ((line = input.readLine()) != null) 
			{
				++nrow;
				String[] columns = line.split(sep);
				ncol = Math.max(ncol,columns.length);
			}
			res = new double[nrow][ncol];
			input = new BufferedReader(new FileReader(filename));
			int i=0,j=0;
			while ((line = input.readLine()) != null) 
			{
				
				String[] columns = line.split(sep);
				for(j=0;j<columns.length;++j)
				{
					res[i][j] = Double.parseDouble(columns[j]);
				}
				++i;
			}
		}
		catch(Exception E)
		{
			System.out.println("+++ReadArrayFile: "+E.getMessage());
		}
	    return(res);
	}

// THIS METHOD IS USED TO READ THE TOUR/PERMUTATION INTO AN INTEGER ARRAYLIST	
	static public ArrayList<Integer> ReadIntegerFile(String filename)
	{
		ArrayList<Integer> res = new ArrayList<Integer>();
		Reader r;
		try
		{
			r = new BufferedReader(new FileReader(filename));
			StreamTokenizer stok = new StreamTokenizer(r);
			stok.parseNumbers();
			stok.nextToken();
			while (stok.ttype != StreamTokenizer.TT_EOF) 
			{
				if (stok.ttype == StreamTokenizer.TT_NUMBER)
				{
					res.add((int)(stok.nval));
				}
				stok.nextToken();
			}
		}
		catch(Exception E)
		{
			System.out.println("+++ReadIntegerFile: "+E.getMessage());
		}
	    return(res);
	}

// THIS METHOD CREATES A RANDOM PERMUTATION BASED ON A GIVEN NUMBER OF CITIES IN A TOUR/PERMUTATION
	public static ArrayList<Integer> RandomPermutation(int n)	
	{
		
		//READING THE NUMBERS IN ORDER TO FORM A PERMUTATION
		ArrayList<Integer> permutation = new ArrayList<Integer>();
		for(int i=0; i<n; i++){
			permutation.add(i);
		}
		
		//CREATING A NEW ARRAYLIST
		ArrayList<Integer> newPermutation = new ArrayList<Integer>();
		
		//GETTING A RANDOM NUMBER IN THE PREVIOUS ARRAYLIST AND STORING IT FROM TOP DOWN IN THE NEW ARRAYLIST
		//THEN DELETING THE PREVIOUS ARRAYLIST ELEMENT AFTER ITS MOVED/COPIED
		while(permutation.size() > 0)
		{
			int i = RandomIntegerBetween(0,(permutation.size()-1)); 
			int permutationElement = permutation.get(i);
			newPermutation.add(permutationElement);
			permutation.remove(i);
		}
		
		//RETURNING THE NEW PERMUTATION
		return newPermutation;
	}
	
// THIS METHOD IS USED AS A SMALL CHANGE FOR TESTING ALGORITHMS TO CHANGE THE PERMUTATION FOR FITNESSES
	public static ArrayList<Integer> SmallChangeSwap(ArrayList<Integer> permutation)	
	{
		
		//INITIALISING VARIABLES, SIZE AND SWAP VARIABLES
		int n = permutation.size();
		int i=0, j=0;

		//CONDITION IF I AND J IS SAME, THEN GET A RANDOM NUMBER FOR BOTH (AS THEY CANNOT BE THE SAME)
		while(i==j){
			i = RandomIntegerBetween(0, n-1);
			j = RandomIntegerBetween(0, n-1);
		}
		
		//USING COLLECTIONS SWAP METHOD TO SWAP I AND J 
		Collections.swap(permutation, i, j); 
		
		//RETURNING THE PROCESSED PERMUTATION
		return permutation; 
	}
		
// THIS METHOD GENERATED A RANDOM NUMBER BETWEEN TWO GIVEN VALUES
	static public int RandomIntegerBetween(int aa,int bb) {
		
		//INITIALISING TWO VARIABLES AS THE SMALLER AND BIGGER NUMBER
		int smallerValue = Math.min(aa,bb);
		int biggerValue = Math.max(aa,bb);
			
		//IF THERE IS NO STORED RANDOM NUMBER THEN CREATE A NEW RANDOM NUMBER
		if (randomGeneratedNumber == null) 			
		{
			randomGeneratedNumber = new Random();
			randomGeneratedNumber.setSeed(System.nanoTime());
		}
		
		//GETTING THE DIFFERENCE BETWEEN THE VALUES AND GETTING A RANDOM NUMBER IN BETWEEN
		int differenceOfValues = biggerValue - smallerValue + 1;
		int randomInteger = randomGeneratedNumber.nextInt(differenceOfValues) + smallerValue;
		
		//RETURN THE RANDOM VALUE GENERATED
		return(randomInteger);
	}
	
// THIS METHOD IS USED TO CALCULATE THE FITNESS, USED BY THE TEST ALGORITHMS
	static public double TSPFitness(int TotalNumberOfCities, ArrayList<Integer> Tour, double[][] DistanceMatrix) 
	{
		
		//INITIALISING FITNESS
		double Fitness = 0;
		
		//ADDING UP THE DISTANCES BASED ON THE TOUR
		for (int i =0; i<TotalNumberOfCities-1; i++) {
			
			int row = Tour.get(i); //System.out.println(row);
			int col = Tour.get(i+1); //System.out.println(col);
		
			Fitness = Fitness+ DistanceMatrix[row][col];		
			
		}
		
		//ADDING THE DISTANCE FROM THE END CITY AND FIRST (AS A LOOP)
		int endCity = Tour.get(Tour.size() -1);
		int startCity = Tour.get(0);
		Fitness= Fitness + DistanceMatrix[endCity][startCity];
		
		//RETURNING THE FINAL FITNESS
		return Fitness;
		
	}
	
	//PRINT METHOD TO PRINT FINAL FITNESS - WITH OR WITHOUT LABELS ACCORDING TO OPTIONS SET IN MAIN METHOD
	static public void PrintFinalFitness(int TotalNumberOfCities, ArrayList<Integer> Tour, double[][] DistanceMatrix) 
	{
		String Space ="";
		if (Lab15Main.BetterDisplay() == true) {Space = "Final Fitness: ";}
		System.out.println(Space+TSP.TSPFitness(TotalNumberOfCities, Tour, DistanceMatrix));
		//System.out.println(Tour);
	}
	
	//PRINT METHOD TO PRINT ALL FITNESSES 
	static public void PrintFitness(int TotalNumberOfCities, ArrayList<Integer> Tour, double[][] DistanceMatrix, int Iteration) 
	{
		int Space = 0;
		if (Lab15Main.IterationView() == true) {Space = Iteration;}
		System.out.println(Space+ " " + TSP.TSPFitness(TotalNumberOfCities, Tour, DistanceMatrix));
		//System.out.println(Tour);
	}
}

