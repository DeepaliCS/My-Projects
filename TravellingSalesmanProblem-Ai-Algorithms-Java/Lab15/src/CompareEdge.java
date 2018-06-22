//Compare edge weights - used to sort an ArrayList of edges
public class CompareEdge implements java.util.Comparator 
{
	public int compare(Object a, Object b) 
	{
		// if edge a is less than edge b then return -1
		if (((Edge)a).w < ((Edge)b).w) return(-1);
		// if edge a is greater than edge b then return 1
		if (((Edge)a).w > ((Edge)b).w) return(1);
		return(0);
	}
}
