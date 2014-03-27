package com.fabiocarballo.satnav.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fabiocarballo.satnav.impl.conditions.Condition;
import com.fabiocarballo.satnav.impl.exceptions.RoadJunctionDoesntExistException;

public class FindAllRoutes {

	// list of paths, where each path is a string (a concatenation of each road junction identifier, separated
	// by "-" ) 
	private List<String> paths;

	/**
	 * 
	 * This method is only used to set up the recursive search and return the list of found paths.
	 */
	public List<String> findAllRoutesBetweenTwoPoints(HashMap<String, RoadJunction> roadJunctionsMap,
			String src, String destiny, Condition condition) throws RoadJunctionDoesntExistException {

		paths = new ArrayList<String>();

		RoadJunction srcRj = roadJunctionsMap.get(src);
		if(srcRj == null) 
			throw new RoadJunctionDoesntExistException();

		RoadJunction dstRj = roadJunctionsMap.get(destiny);
		if(dstRj == null) {
			throw new RoadJunctionDoesntExistException();
		}

		List<RoadJunction> visited = new ArrayList<RoadJunction>();
		visited.add(srcRj);
		
		recursivePathSearch(srcRj, destiny, condition,visited);

		return paths;

	}

	/**
	 * 
	 * @param current - the current road junction (the focus)
	 * @param destiny - the destiny identifier (to identify the end of a path)
	 * @param condition - the condition that must be held
	 * @param visited - list of visited road junctions until this moment
	 * 
	 * This method is a depth first search over the roads to find paths that hold the condition and end on the "destiny" road junction.
	 * When a path is found it is stored on the global "paths" attribute;
	 */
	public void recursivePathSearch(RoadJunction current, String destiny, Condition condition, List<RoadJunction> visited) {

		for(Road road : current.getRoads().values()) { // try to explore each outgoing road
			RoadJunction rj = road.getDestiny();

			List<RoadJunction> visited_tmp = new ArrayList<RoadJunction>(visited);
			visited_tmp.add(rj);

			if(condition == null || condition.isTrue(visited_tmp)) { // if the path is valid under the specified condition (if any condition is specified)

				if(rj.getIdentifier().equals(destiny)) {
					String sucessfulPath = getPathFromRoadJunctionList(visited_tmp);
					paths.add(sucessfulPath);
				}

			}

			if(condition.canContinue(visited_tmp)) {
				recursivePathSearch(rj, destiny, condition, visited_tmp);
			}
		}
	}

	/**
	 * 
	 * Transforms a list of road junctions (that represent a path) into a string 
	 * representative of that path (concatenation of each road junction identifier, separated by "-")
	 * 
	 */
	public String getPathFromRoadJunctionList(List<RoadJunction> roadJunctionsList) {

		String path = "";

		for(RoadJunction rj : roadJunctionsList) {
			path += rj.getIdentifier() + "-";
		}

		path = path.substring(0, path.length() - 1); // to cut the extra "-" in the end

		return path;
	}
}
