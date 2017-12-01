package com.github.usc.graph;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.github.usc.util.FileContent;

public class BroadGraphTest {
	
	private BroadGraph broad;
	
    @Before
    public void setup() {
        broad = new BroadGraph();
    }

	
	@Test
	public void testGraph_test_1() {
		loadGraph(broad, "/test_1.txt");
		HashSet<Integer> nodesToBroadcast = broad.msgBroadcast();
		boolean actual = broad.checkIfMsgIsBroadcastToAllNodes(nodesToBroadcast);
		assertEquals(true, actual);
	}
	
	@Test
	public void testGraph_twitter_1000() {
		loadGraph(broad, "/twitter_1000.txt");
		HashSet<Integer> nodesToBroadcast = broad.msgBroadcast();
		boolean actual = broad.checkIfMsgIsBroadcastToAllNodes(nodesToBroadcast);
		assertEquals(true, actual);
	}
	
	@Test
	public void testGraph_twitter_combined() {
		loadGraph(broad, "/twitter_combined.txt");
		HashSet<Integer> nodesToBroadcast = broad.msgBroadcast();
		boolean actual = broad.checkIfMsgIsBroadcastToAllNodes(nodesToBroadcast);
		assertEquals(true, actual);
	}
	
	@Test
	public void testGraph_twitter_higgs() {
		loadGraph(broad, "/twitter_higgs.txt");
		HashSet<Integer> nodesToBroadcast = broad.msgBroadcast();
		boolean actual = broad.checkIfMsgIsBroadcastToAllNodes(nodesToBroadcast);
		assertEquals(true, actual);
	}
	
	private void loadGraph(BroadGraph g, String filename) {
		try {
        	FileContent fc = new FileContent();
        	String str = fc.getFile(filename);
        	String[] lines = str.split("\\n");
        	for (String line : lines) {
        		String[] l = line.split(" ");
        		int v1 = Integer.parseInt(l[0]);
                int v2 = Integer.parseInt(l[1]);
                g.addVertex(v1);
                g.addEdge(v1, v2);
        	}
        }
        catch (Exception e) {
        	e.printStackTrace();
            return;
        }
	}
}
