import java.util.ArrayList;

public class Lab12 {

	public static void main(String[] args) {
		
		//______________________
		System.out.println("12.3 Exercise 1: Modifying the GA");
		System.out.println("(Explain the steps you took on the labsheet to tranform");
		System.out.println("in to the OneMax Solution)");
		System.out.println();
		System.out.println("12.4: Experiments ");
		System.out.println("(Show UniformCrossover and OnePointCrossover graphs)");
		System.out.println();
		//_______________________
		
		for(int i=0;i<10;++i)
		{
			//Reset the fitness count
			OneMaxChrome.ClearFC();
			//The following parameters are not very good!
			//These are the ones you should try and optimise!
			int popsize = 1000;
			double mrate = 0.001;  //SINCE THE MRATE HAS TO BE 0-10%
			double crate =  0.75;  //SINCE THE CRATE HAS TO BE 50-100%
			//You will not need to change the following
			SimpleGeneticAlgorithm ga = new SimpleGeneticAlgorithm(popsize,1000,1000,mrate,crate);
																//population, Generation, number of weights, mutation rate, crossover rate
			//Run the GA for 10 function calls
			double f = ga.RunSGA(10000, false).GetFitness();
			///10000 stands for  nfc = the number of fitness calls
			//System.out.print(i + " ");
			System.out.println(f);
		}

	}

}
