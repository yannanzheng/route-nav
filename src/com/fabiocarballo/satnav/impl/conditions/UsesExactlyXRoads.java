package com.fabiocarballo.satnav.impl.conditions;

import java.util.List;

import com.fabiocarballo.satnav.impl.RoadJunction;

/**
 * 
 * @author fabiocarballo
 *
 * Class that represents a truth condition over a specific path along several road junctions. The "isTrue" method is true 
 * when the path is composed by exactly X roads .(specified in the constructor)
 * Note: the roads can be repeated along the path.
 * 
 * The number of roads used is the value of the path size - 1. 
 * 
 * Example : 
 * Path: C - D - C - D
 * 
 * We used the roads :
 *  C -> D
 *  D -> C
 *  C -> D
 *  
 *  The number of nodes in the path is 4 .
 *  
 *  4 - 1 = 3 
 *  
 *  3 is the number of roads used.
 */
public class UsesExactlyXRoads extends Condition {
	
	private int numberOfRoads;
	
	public UsesExactlyXRoads(int num) {
		this.numberOfRoads = num;
	}

	@Override
	public boolean isTrue(List<RoadJunction> path) {
		return (path.size() - 1) == numberOfRoads;
	}
	
	@Override
	public boolean canContinue(List<RoadJunction> path) {
		return (path.size() - 1) < numberOfRoads;
	}
}
