package com.fabiocarballo.satnav.impl.conditions;

import java.util.List;

import com.fabiocarballo.satnav.impl.Road;
import com.fabiocarballo.satnav.impl.RoadJunction;

/**
 * 
 * @author fabiocarballo
 *
 * Class that represents a truth condition over a specific path along several road junctions. 
 * The "isTrue" method is true when the path has a distance 
 * smaller than or equal to X (specified in the constructor)
 */
public class WithLengthSmallerThanX extends Condition {

	private int distance;

	public WithLengthSmallerThanX(int distance) {
		this.distance = distance;
	}

	@Override
	public boolean isTrue(List<RoadJunction> path) {

		int currDistance = 0;

		for(int i = 0; i < path.size() - 1; i++) {
			RoadJunction curr = path.get(i);
			RoadJunction next = path.get(i + 1);

			Road currToNextRoute = curr.getRoads().get(next.getIdentifier());
			currDistance += currToNextRoute.getDistanceToTarget();
		}

		return currDistance < distance;
	}

	@Override
	public boolean canContinue(List<RoadJunction> path) {
		return isTrue(path);
	}
}
