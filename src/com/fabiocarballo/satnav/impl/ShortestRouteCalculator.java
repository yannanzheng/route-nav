package com.fabiocarballo.satnav.impl;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


public class ShortestRouteCalculator {

	PriorityQueue<RoadJunction> queue;

	// road junction id  => road junction Id; the value of each entry corresponds to the element that comes behind of the key element in the optimal path
	HashMap<String, String> previous;

	// map that stores each road junction distance to the defined source . ( road junction id => distance to source ) 
	HashMap<String, Integer> distancesToSource;
	
	private static final int INFINITY = Integer.MAX_VALUE;

	/**
	 * I tried to create my implementation based on the idea of the Dijsktra algorithm. I assumed that distances between road junctions
	 * are always greater than zero. 
	 */
	public int shortestRouteBetween(HashMap<String, RoadJunction> allRoadJunctions, String source, String destiny) {

		previous = new HashMap<String, String>();
		distancesToSource = new HashMap<String, Integer>();
		
		queue = new PriorityQueue<RoadJunction>(allRoadJunctions.size(), distanceToSourceComparator);

		for(RoadJunction roadJunction : allRoadJunctions.values()) {
			String roadJunctionId = roadJunction.getIdentifier();
			if(roadJunctionId.equals(source))  {
				queue.add(roadJunction);
				distancesToSource.put(roadJunctionId, 0); //set distance to source equals to 0
			} else {
				distancesToSource.put(roadJunctionId, INFINITY); // any junctions distances to the source are set to infinity
			}
		}

		// calculate path costs
		while(!queue.isEmpty()) {
			RoadJunction curr = queue.poll();

			String currId = curr.getIdentifier();
			int currDistanceToSource = distancesToSource.get(curr.getIdentifier());

			// if we get to our destiny OR if the distance to the source is infinity
			if(currId.equals(destiny) && currDistanceToSource != 0) {
				// deals with the special case of searching for the shortest route between the same points
				return currDistanceToSource;
			} 

			for(Road road : curr.getRoads().values()) {
				RoadJunction target = road.getDestiny();
				String targetId = target.getIdentifier();
				
				int newDistance = currDistanceToSource + road.getDistanceToTarget();
				  // if the new distance is smaller           OR  if the distance to the source is zero (i.e, we're looking into the source road junction)
				
				int targetDistanceToSource = distancesToSource.get(target.getIdentifier());
				if(newDistance < targetDistanceToSource || targetDistanceToSource == 0) {
					// update the distances and the queue (needs to remove and reinsert)
					queue.remove(target);

					distancesToSource.put(targetId, newDistance);
					previous.put(targetId, currId);

					queue.add(target);
				}
			}
		}

		return -1; // error code: should'nt happen
	}

	private HashMap<String, Integer> getDistancesToSource() {
		return distancesToSource;
	}
	
	//Comparator used in the priority queue
	Comparator<RoadJunction> distanceToSourceComparator = new Comparator<RoadJunction>(){

		@Override
		public int compare(RoadJunction rjA, RoadJunction rjB) {
			HashMap<String, Integer> distancesToSource = getDistancesToSource();
			
			String rjA_id = rjA.getIdentifier();
			String rjB_id = rjB.getIdentifier();
			
			return  distancesToSource.get(rjA_id) - distancesToSource.get(rjB_id);
		}
	};
}
