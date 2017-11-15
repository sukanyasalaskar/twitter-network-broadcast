package com.github.usc.graph;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import com.github.usc.util.GraphLoader;

/*
 * BroadGraph class implements Graph interface.
 * The main idea of this class is to find minimum
 * number of nodes from the given graph and broad-
 * cast the message to the entire network using 
 * these nodes. Greedy approach is implemented
 * to find the nodes required to broadcast.
 */

public class BroadGraph implements Graph {
	
	private HashMap<Integer, HashSet<Integer>> nodes;

	public BroadGraph() {
		// TODO Auto-generated constructor stub
		nodes = new HashMap<>();
	}

	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		if (!nodes.containsKey(num))
			nodes.put(num, new HashSet<>());

	}

	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		nodes.get(from).add(to);
	}
	
	public HashSet<Integer> msgBroadcast() {
		
		HashSet<Integer> nodesToBroadcast = new HashSet<>();
		HashMap<Integer, GraphNode> nodesToVisit = new HashMap<>();
		
		TreeSet<GraphNode> treeSet = new TreeSet<>(new Comparator<GraphNode>(){
			@Override
			public int compare(GraphNode g1, GraphNode g2) {
				if (g1.getNeighborCount() < g2.getNeighborCount())		return -1;
				else if (g1.getNeighborCount() > g2.getNeighborCount())	return 1;
				return 0;
			}
		});
		
		for (int num : nodes.keySet()) {
			GraphNode graphNode = new GraphNode(num, nodes.get(num).size());
			treeSet.add(graphNode);
			nodesToVisit.put(num, graphNode);
		}
		
		while (treeSet.size() != 0) {
			int element = treeSet.last().getNodeValue();
			nodesToBroadcast.add(element);
			for (int neighbor : nodes.get(element)) {
				//System.out.println(element+" "+neighbor);
				if (nodesToVisit.containsKey(neighbor))
					treeSet.remove(nodesToVisit.get(neighbor));
			}
			treeSet.remove(nodesToVisit.get(element));
		}
		
		return nodesToBroadcast;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test Started=======");
		Graph thegraph = new BroadGraph();
		GraphLoader.loadGraph(thegraph, "/twitter_higgs.txt");
		HashSet<Integer> nodesToBroadcast = thegraph.msgBroadcast();
		System.out.println();
		System.out.println("Nodes to broadcast data to network");
		for (int node : nodesToBroadcast) {
			System.out.println(node);
		}
		System.out.println();
		System.out.println("Test Finished!");
	}

}

/*
 * Class to represent each node of graph and its neighbor
 * count.
 */
class GraphNode {
	
	private int nodeVal, neighborCount;
	
	public GraphNode(int num, int val) {
		this.nodeVal = num;
		this.neighborCount = val;
	}
	
	public int getNodeValue() {
		return this.nodeVal;
	}
	
	public int getNeighborCount() {
		return this.neighborCount;
	}
}
