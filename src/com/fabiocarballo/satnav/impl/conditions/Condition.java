package com.fabiocarballo.satnav.impl.conditions;

import java.util.List;

import com.fabiocarballo.satnav.impl.RoadJunction;

// Abstract class to represent a truth condition over a specific path

public abstract class Condition {
	
	// the isTrue method verifies if a certain condition holds for the given path
	public abstract boolean isTrue(List<RoadJunction> path);

	// the canContinue method verifies if it is possible for a condition to be true given the path characteristics
	public abstract boolean canContinue(List<RoadJunction> path);

}
