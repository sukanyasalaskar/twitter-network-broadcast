package com.github.usc.graph;

import java.util.HashSet;
import java.util.List;

public interface Graph {
	
	/*
	 * Create graph vertex
	 */
	public void addVertex(int num);
	
	/*
	 * Add edge between 2 vertices in graph
	 */
	public void addEdge(int from, int to);
	
	/*
	 * Find nodes needed to broadcast the message
	 */
	public HashSet<Integer> msgBroadcast();
	
	/*
	 * Method used to find if all nodes in graph receive the message
	 */
	public boolean checkIfMsgIsBroadcastToAllNodes(HashSet<Integer> nodesToBroadcast);
	
	/*
	 * Delete a vertex from graph
	 */
	public void deleteVertex(int vertex);

	/*
	 * Find vertex with highest number of followers. If 2 nodes has number of neighbors,
	 * vertex with highest value is returned.
	 */
	public int nodeWithHighestFollowers();
	
	/*
	 * Get followers of a particular node in graph
	 */
	public List<Integer> getFollowers(int vertex);
}