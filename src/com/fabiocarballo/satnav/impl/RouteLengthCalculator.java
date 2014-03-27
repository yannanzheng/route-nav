package com.fabiocarballo.satnav.impl;
import java.util.HashMap;
import java.util.List;


public class RouteLengthCalculator {

	// The output is a string representative of the lenght of the route (for example, a route with lenght of 6 
	// will be represented as "6". When  a route doesn't exist is represented as "NO SUCH ROUTE") 
	public String getLengthOfRoute(HashMap<String, RoadJunction> allRoadJunctions, List<String> path) {
		
		int total = 0;
		
		for(int i = 0; i < path.size() - 1; i++) {
			String src = path.get(i);
			RoadJunction src_rj = allRoadJunctions.get(src);
			
			String target = path.get(i + 1);
			
			Road roadToTarget = src_rj.getRoads().get(target);
			if(roadToTarget == null) {
				return "NO SUCH ROUTE";
			} else {
				RoadJunction tgt = roadToTarget.getDestiny();
				total += roadToTarget.getDistanceToTarget();
				src_rj = tgt;
			}
		}

		return total + "";
	}
}
