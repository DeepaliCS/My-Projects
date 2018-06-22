import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class MinimumSpanningTree {
	
	//THIS METHOD COUNTS ALL DISTANCES IN A TABLE AND DIVIDES BY 2 FOR THE FINAL MST VALUE
	static public double MSTCount(double x[][])
	{
		double add = 0;
		for(int i=0;i<x.length;++i)
		{
			for(int j=0;j<x[i].length;++j)
			{
				add += (x[i][j]);
			}
		}
		return add/2;
	}
	
	//METHOD COLLECTS LOWEST DISTANCE VALUES AND LEAVES A ZERO FOR REJECTIONS
	public static double[][] MST(double[][] d)
	{
		int i,j,n = d.length;
		double res[][] = new double[n][n];
		//Store edges as an ArrayList
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for(i=0;i<n-1;++i)
		{
			for(j=i+1;j<n;++j)
			{
				//Only non zero edges
				if (d[i][j] != 0.0) edges.add(new Edge(i,j,d[i][j]));
			}
		}
		//Sort the edges by weight
		Collections.sort(edges,new CompareEdge());
		//Don't do anything more if all the edges are zero
		
		if (edges.size() == 0) return(res);
		//List of variables that have been allocated
		ArrayList<Integer> v = new ArrayList<Integer>();
		//Pick cheapest edge
		v.add(edges.get(0).i);
		//Loop while there are still nodes to connect
		while(v.size() != n)
		{
			Edge e = LocateEdge(v,edges);
			if (v.indexOf(e.i) == -1) v.add(e.i);
			if (v.indexOf(e.j) == -1) v.add(e.j);
			res[e.i][e.j] = e.w;
			res[e.j][e.i] = e.w;
		}

		return(res);
	}
	
	//USED BY THE MST METHOD
	static private Edge LocateEdge(ArrayList<Integer> v,ArrayList<Edge> edges) {
		
		for (Iterator<Edge> it = edges.iterator(); it.hasNext();) {
			Edge e = it.next();
			int x = e.i;
			int y = e.j;
			int xv = v.indexOf(x);
			int yv = v.indexOf(y);
			if (xv > -1 && yv ==-1)
			{
				return(e);
			}
			
			if (xv == -1 && yv > -1)
			{
				return(e);
			}
		}
		//Error condition
		return (new Edge(-1,-1,0.0));	
	}
	
}
