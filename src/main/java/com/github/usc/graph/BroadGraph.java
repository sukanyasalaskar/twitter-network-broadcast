package com.github.usc.graph;

import java.util.ArrayList;
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
	private HashSet<Integer> nodesInGraph;

	public BroadGraph() {
		// TODO Auto-generated constructor stub
		nodes = new HashMap<>();
		nodesInGraph = new HashSet<>();
	}

	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		if (!nodes.containsKey(num)) {
			nodes.put(num, new HashSet<>());
		}
		if (!nodesInGraph.contains(num)) {
			nodesInGraph.add(num);
		}
	}

	@Override
	public void addEdge(int vertex, int follower) {
		// TODO Auto-generated method stub
		nodes.get(vertex).add(follower);
		if (!nodes.containsKey(follower)) {
			nodes.put(follower, new HashSet<>());
		}
		if (!nodesInGraph.contains(follower)) {
			nodesInGraph.add(follower);
		}
	}
	
	public HashSet<Integer> msgBroadcast() {
		
		HashSet<Integer> nodesToBroadcast = new HashSet<>();
		HashMap<Integer, GraphNode> nodesToVisit = new HashMap<>();
		
		TreeSet<GraphNode> treeSet = new TreeSet<>(new Comparator<GraphNode>(){
			@Override
			public int compare(GraphNode g1, GraphNode g2) {
				if (g1.getNeighborCount() < g2.getNeighborCount())		return -1;
				else if (g1.getNeighborCount() > g2.getNeighborCount())	return 1;
				else if (g1.getNeighborCount() == g2.getNeighborCount()) {
					if (g1.getNodeValue() < g2.getNodeValue())			return -1;
					else if (g1.getNodeValue() > g2.getNodeValue())		return 1;
				}
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
				if (nodesToVisit.containsKey(neighbor))
					treeSet.remove(nodesToVisit.get(neighbor));
			}
			treeSet.remove(nodesToVisit.get(element));
		}
		
		return nodesToBroadcast;
	}
	
	public boolean checkIfMsgIsBroadcastToAllNodes (HashSet<Integer> nodesToBroadcast) {
		HashSet<Integer> checkNodes = new HashSet<>();
		for (Integer n : nodesInGraph) {
			checkNodes.add(n);
		}
		//System.out.println(nodesInGraph.size()+" "+checkNodes.size());
		for (Integer n : nodesToBroadcast) {
			if (checkNodes.contains(n))
				checkNodes.remove(n);
			for (Integer val : nodes.get(n)) {
				if (checkNodes.contains(val))
					checkNodes.remove(val);
			}
		}
		//System.out.println(nodesInGraph.size());
		return checkNodes.size() == 0;
	}
	
	public void deleteVertex(int vertex) {
		if (!nodes.containsKey(vertex)) {
			try {
				throw new Exception("No such vertex in graph "+" "+vertex);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		nodes.remove(vertex);
		nodesInGraph.remove(vertex);
	}
	
	public int nodeWithHighestFollowers() {
		TreeSet<GraphNode> treeSet = new TreeSet<>(new Comparator<GraphNode>(){
			@Override
			public int compare(GraphNode g1, GraphNode g2) {
				if (g1.getNeighborCount() < g2.getNeighborCount())		return -1;
				else if (g1.getNeighborCount() > g2.getNeighborCount())	return 1;
				else if (g1.getNeighborCount() == g2.getNeighborCount()) {
					if (g1.getNodeValue() < g2.getNodeValue())			return -1;
					else if (g1.getNodeValue() > g2.getNodeValue())		return 1;
				}
				return 0;
			}
		});
		
		for (int num : nodes.keySet()) {
			GraphNode graphNode = new GraphNode(num, nodes.get(num).size());
			treeSet.add(graphNode);
		}
		return treeSet.last().getNodeValue();
	}
	
	public List<Integer> getFollowers(int vertex) {
		List<Integer> res = new ArrayList<>(nodes.get(vertex));
		return res;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test Started=======");
		Graph thegraph = new BroadGraph();
		GraphLoader.loadGraph(thegraph, "/test_1.txt");
		thegraph.deleteVertex(3);
		HashSet<Integer> nodesToBroadcast = thegraph.msgBroadcast();
		System.out.println();
		System.out.println("Number of nodes required to broadcast data to network : "+nodesToBroadcast.size());
		//System.out.println("Nodes to broadcast data to network");
		//for (int node : nodesToBroadcast) {
		//	System.out.println(node);
		//}
		System.out.println();
		boolean check = thegraph.checkIfMsgIsBroadcastToAllNodes(nodesToBroadcast);
		System.out.println("Message received by all nodes? : "+check);
		System.out.println();
		System.out.println("Node with highest followers : "+" "+thegraph.nodeWithHighestFollowers());
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
