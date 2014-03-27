package com.fabiocarballo.satnav.impl;

/**
 * 
 * @author fabiocarballo
 *
 * This class represents a road.
 * 
 * It is described by a distance (the lenght of the road) to a target road junction. The origin of the road 
 * is the road junction that will held the road in a list of outgoing roads.
 */
public class Road {

	public int distanceToTarget;
	public RoadJunction target;
	
	public Road(RoadJunction target, int distanceToTarget) {
		this.target = target;
		this.distanceToTarget = distanceToTarget;
	}
	
	public int getDistanceToTarget() {
		return distanceToTarget;
	}
	
	public RoadJunction getDestiny() {
		return target;
	}
	
}
