package com.github.usc.graph;

import java.util.HashSet;

public interface Graph {
	
	/*
	 * Create graph vertex
	 */
	public void addVertex(int num);
	
	/*
	 * Add edge between 2 vertices in graph
	 */
	public void addEdge(int from, int to);
	
	public HashSet<Integer> msgBroadcast();
}