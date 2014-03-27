package com.fabiocarballo.satnav.impl;

import java.util.HashMap;


public class RoadJunction {

	private String identifier;
	
	// map that stores the road information (distance, destiny road junction) for each possible outgoing road
	// the keys are the identifiers of the road junctions that we can reach
	private HashMap<String, Road> roads = new HashMap<String, Road>();
	
	public RoadJunction(String identifier) {
		this.identifier = identifier;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public HashMap<String, Road> getRoads() {
		return roads;
	}

	public void setRoads(HashMap<String, Road> roads) {
		this.roads = roads;
	}

	public void addRoadToRoadJunction(RoadJunction rj, int distance) {
		Road route = new Road(rj, distance);
		roads.put(rj.getIdentifier(), route);
	}
	
}
