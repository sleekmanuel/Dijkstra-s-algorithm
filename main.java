/*
* Data Structures - Graph Theory - Dijkstra's algorithm
*
* Project is an effective way to use dijkstra's algorithm to send an encrypted message  to a receiver
*  split into two through different computers (paths) to avoid hackers intercepting the message. If message
* cannot be sent through different paths, the use will be alerted when trying to send second message across
*/

import java.io.*;
import java.util.*;


public class Algorithm {
	
	static int a;
	static int b;
	static int c;
	
	static int Fl;
	static int Sl;
	static int Totaltime;
	
	static String[] array;
	static int matrix [][];
	private int adjacencyMatrix[][];

	private int distances[];
	private int number_of_nodes;
	private Set<Integer> settled;
    private Set<Integer> unsettled;
    private Set<Integer> path;
    
	
	private static Scanner x;
	static Scanner reader = new Scanner(System.in);
	
	
	public static void main(String[] args) throws IOException {
		// Calls reader method
		Reader();
	}

	/*
	 * Asks user for file and reads it. Puts the information about the computers in a
	 * matrix to be read by algorithm used. Performs algorithms for the first and second paths
	 */
	 public static void Reader() throws IOException{
		  boolean success = false;
		  while(!success)
		  try{
			  System.out.println("Please enter name of the test file (with .txt): ");
		      String fileName = reader.nextLine();
			  x = new Scanner(new File(fileName));		 
			  success=true;
		  }
		  catch(Exception e){
			  System.out.println(" File not Found");
		  }	 
		  String line1 = x.nextLine();
		  Fl = Integer.parseInt(line1);
		  String line2 = x.nextLine();
		  Sl = Integer.parseInt(line2);
		  // Setting all cells in matrix to infinity
			matrix = new int[Fl][Fl];
			for (int y = 0; y < Fl;y++){
				for(int z = 0; z< Fl;z++)
					matrix[z][y] = Integer.MAX_VALUE;
				
					
			}
			

		 for(int w =0; w<Sl;w++){
			  
			  String line = x.nextLine();
			  if (!(line.isEmpty())){
			    array = line.split(" ");
			    a = Integer.parseInt(array[0]);		 
			    b = Integer.parseInt(array[1]);
			    c = Integer.parseInt(array[2]);
			    matrix[a-1][b-1] = c;
			    matrix[b-1][a-1] = c;			    
			  }
			
		  }
		  // Runs First algorithm on users information
		  Algorithm dijkstrasAlgorithm = new Algorithm(Fl);
          dijkstrasAlgorithm.dijkstra_algorithm(matrix, 1);
          for (int i = 1; i <= dijkstrasAlgorithm.distances.length - 1; i++)
          {
              if (i ==Fl)
            	  Totaltime = dijkstrasAlgorithm.distances[i];
          }
   // Removes already used path by setting it to infinity and reruns algorithm for second path and time
          Object [] array = dijkstrasAlgorithm.path.toArray();
          for(int x =0; x<dijkstrasAlgorithm.path.size()-1;x++){
        	  int init = (int) array[x];
        	  int fin = (int) array[x+1];
          matrix[init-1][fin-1] = Integer.MAX_VALUE;
          matrix[fin-1] [init-1]= Integer.MAX_VALUE;
          }
          
          Algorithm dijkstrasAlgorithm2 = new Algorithm(Fl);
          dijkstrasAlgorithm2.dijkstra_algorithm(matrix, 1);
          
          for (int i = 1; i <= dijkstrasAlgorithm2.distances.length - 1; i++)
          {
        	 // if second algorithm doesn't exists, sends message to sender after the first message is sent.
              if (i ==Fl)
            	  if(dijkstrasAlgorithm2.distances[i] != Integer.MAX_VALUE){
            		  Totaltime = Totaltime + dijkstrasAlgorithm2.distances[i];
            		  System.out.println("Total Time: "+ Totaltime);
            		  System.out.println("Path 1: " + dijkstrasAlgorithm.path);
            		  System.out.println("Path 2: "+ dijkstrasAlgorithm2.path);
            	  }else{
            		  System.out.println("Cannot send Message in Halves");
            		  System.out.print("Total Time: " + Totaltime);
            		  System.out.println("Path1: "+ dijkstrasAlgorithm.path);      		  
            	  }   
          }
		  x.close();
	 }
	 
	  // Constructor for Algorithm
	    public Algorithm(int number_of_nodes)
	    {
	        this.number_of_nodes = number_of_nodes;
	        distances = new int[number_of_nodes+1];
	        settled = new HashSet<Integer>();
	        unsettled = new HashSet<Integer>();
	        path = new HashSet<Integer>();
	        adjacencyMatrix = new int[number_of_nodes+1][number_of_nodes+1];
	    }
	 
	    // Dijkstra's algorithm. it takes the matrix information
	    //and the initial computer as .
	    public void dijkstra_algorithm(int adjacency_matrix[][], int source)
	    {
	        int evaluationNode;
	        for (int i = 0; i <= number_of_nodes-1; i++)
	            for (int j = 0; j <= number_of_nodes-1; j++)
	                adjacencyMatrix[i+1][j+1] = adjacency_matrix[i][j];
	 
	        for (int i = 1; i <= number_of_nodes; i++)
	        {
	            distances[i] = Integer.MAX_VALUE;
	        }	 
	        unsettled.add(source);
	        distances[source] = 0;
	        while (!unsettled.isEmpty())
	        {
	            evaluationNode = getNodeWithMinimumDistanceFromUnsettled();
	            unsettled.remove(evaluationNode);
	            settled.add(evaluationNode);
	            evaluateNeighbours(evaluationNode);
	            path.add(Fl);            
	        }
	    }
	    // finds node with smallest distance from connected nodes of currently visiting node
	    private int getNodeWithMinimumDistanceFromUnsettled()
	    {
	        int min;
	        int node = 0;	 
	        Iterator<Integer> iterator = unsettled.iterator();
	        node = iterator.next();
	        min = distances[node];
	        for (int i = 1; i <= distances.length; i++)
	        {
	            if (unsettled.contains(i))
	            {
	                if (distances[i] <= min)
	                {
	                    min = distances[i];
	                    node = i;	                    
	                }
	            }
	        }
	        return node;
	    }
	    /// Evaluates weights of the visiting node
	    private void evaluateNeighbours(int evaluationNode)
	    {									
	        int edgeDistance = -1;
	        int newDistance = -1; 
	        for (int destinationNode = 1; destinationNode <= number_of_nodes; destinationNode++)
	        {
	            if (!settled.contains(destinationNode))
	            {
	                if (adjacencyMatrix[evaluationNode][destinationNode] != Integer.MAX_VALUE)
	                {
	                    edgeDistance = adjacencyMatrix[evaluationNode][destinationNode];
	                    newDistance = distances[evaluationNode] + edgeDistance;
	                    if (newDistance < distances[destinationNode])
	                    {
	                        distances[destinationNode] = newDistance;
	                        path.add(evaluationNode);
	                    }
	                    unsettled.add(destinationNode);
	                }
	            }
	        }
	    }
}
	 
