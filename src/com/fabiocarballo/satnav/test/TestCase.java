package com.fabiocarballo.satnav.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fabiocarballo.satnav.impl.FindAllRoutes;
import com.fabiocarballo.satnav.impl.RoadJunction;
import com.fabiocarballo.satnav.impl.RouteLengthCalculator;
import com.fabiocarballo.satnav.impl.ShortestRouteCalculator;
import com.fabiocarballo.satnav.impl.conditions.UsesAtMostXRoads;
import com.fabiocarballo.satnav.impl.conditions.UsesExactlyXRoads;
import com.fabiocarballo.satnav.impl.conditions.WithDistanceSmallerThanX;
import com.fabiocarballo.satnav.impl.exceptions.RoadJunctionDoesntExistException;

/**
 * Tests for the Sat Nav
 *
 * @author fabiocarballo
 */
@RunWith(JUnit4.class)
public class TestCase {

	public static final String A = "A";
	public static final String B = "B";
	public static final String C = "C";
	public static final String D = "D";
	public static final String E = "E";

	HashMap<String,RoadJunction> roadJunctionsMap = new HashMap<String,RoadJunction>();

	/* Setting up the test environment with the given information */
	/* AB5, BC4, CD7, DC8, DE6, AD5, CE2, EB3, AE7*/
	@Before
	public void setUp() {
		RoadJunction a = new RoadJunction(A);
		RoadJunction b = new RoadJunction(B);
		RoadJunction c = new RoadJunction(C);
		RoadJunction d = new RoadJunction(D);
		RoadJunction e = new RoadJunction(E);

		a.addRoadToRoadJunction(b, 5);
		a.addRoadToRoadJunction(d, 5);
		a.addRoadToRoadJunction(e, 7);

		b.addRoadToRoadJunction(c, 4);

		c.addRoadToRoadJunction(d, 7);
		c.addRoadToRoadJunction(e, 2);

		d.addRoadToRoadJunction(c, 8);
		d.addRoadToRoadJunction(e, 6);

		e.addRoadToRoadJunction(b, 3);


		roadJunctionsMap.put(A,a);
		roadJunctionsMap.put(B,b);
		roadJunctionsMap.put(C,c);
		roadJunctionsMap.put(D,d);
		roadJunctionsMap.put(E,e);
	}

	// 1. Distance for route A-B-C. Expected output 9
	@Test
	public void testRouteDistanceCalculatorFirstCase() {
		//prepare
		RouteLengthCalculator calc = new RouteLengthCalculator();
		List<String> routePath = new ArrayList<String>();
		routePath.add(A);
		routePath.add(B);
		routePath.add(C);

		String expected = "9";

		//act
		String routeLength = calc.getLengthOfRoute(roadJunctionsMap, routePath);

		//assert
		Assert.assertEquals(expected, routeLength);
	}

	// 2. Distance for route A-D. Expected output 5
	@Test
	public void testRouteDistanceCalculatorSecondCase() {
		//prepare
		RouteLengthCalculator calc = new RouteLengthCalculator();
		List<String> routePath = new ArrayList<String>();
		routePath.add(A);
		routePath.add(D);

		String expected = "5";

		//act
		String routeLength = calc.getLengthOfRoute(roadJunctionsMap, routePath);

		//assert
		Assert.assertEquals(expected, routeLength);

	}

	// 3. Distance for route A-D-C. Expected output 13
	@Test
	public void testRouteDistanceCalculatorThirdCase() {
		//prepare
		RouteLengthCalculator calc = new RouteLengthCalculator();
		List<String> routePath = new ArrayList<String>();
		routePath.add(A);
		routePath.add(D);
		routePath.add(C);

		String expected = "13";

		//act
		String routeLength = calc.getLengthOfRoute(roadJunctionsMap, routePath);

		//assert
		Assert.assertEquals(expected, routeLength);

	}

	// 4. Distance for route A-E-B-C-D. Expected output 21
	@Test
	public void testRouteDistanceCalculatorFourthCase() {
		//prepare
		RouteLengthCalculator calc = new RouteLengthCalculator();
		List<String> routePath = new ArrayList<String>();
		routePath.add(A);
		routePath.add(E);
		routePath.add(B);
		routePath.add(C);
		routePath.add(D);

		String expected = "21";
		//act
		String routeLength = calc.getLengthOfRoute(roadJunctionsMap, routePath);

		//assert
		Assert.assertEquals(expected, routeLength);

	}

	// 5. Distance for route A-E-D. Expected output NO SUCH ROUTE
	@Test
	public void testRouteDistanceCalculatorWithInexistantRoute() {
		//prepare
		RouteLengthCalculator calc = new RouteLengthCalculator();
		List<String> routePath = new ArrayList<String>();
		routePath.add(A);
		routePath.add(E);
		routePath.add(D);

		String expected = "NO SUCH ROUTE";

		//act
		String routeLength = calc.getLengthOfRoute(roadJunctionsMap, routePath);

		//assert
		Assert.assertEquals(expected, routeLength);

	}

	/* 6. The number of routes starting at C and ending at C with a maximum of 3
	junctions. In the sample data below, there are two such routes: C-D-C (2
	junctions) and C-E-B-C (3 junctions). Expected output 2 */ 
	@Test
	public void testFindAllRoutesFirstCase() {
		//prepare
		FindAllRoutes findAllRoutes = new FindAllRoutes();
		UsesAtMostXRoads hasAtMostThreeRoadJunctions = new UsesAtMostXRoads(3);

		int expectedNumberOfRoutes = 2;

		//act
		int numberOfRoutes = -1;
		try {
			numberOfRoutes = findAllRoutes.findNumberOfRoutesBetweenTwoPoints(roadJunctionsMap, C, C, hasAtMostThreeRoadJunctions);
		} catch (RoadJunctionDoesntExistException e) {
			e.printStackTrace();
		}

		//assert
		Assert.assertEquals(expectedNumberOfRoutes, numberOfRoutes);
	}

	/* 7. The number of routes starting at A and ending at C with exactly 4 junctions.
	In the sample data below, there are three such routes: A to C (via B,C,D); A
	to C (via D,C,D); and A to C (via D,E,B). Expected output 3 */
	@Test
	public void testFindAllRoutesSecondCase() {
		//prepare
		FindAllRoutes findAllRoutes = new FindAllRoutes();
		UsesExactlyXRoads hasExactlyFourRoadJunctions = new UsesExactlyXRoads(4);

		int expectedNumberOfRoutes = 3;

		//act
		int numberOfRoutes = -1;
		try {
			numberOfRoutes = findAllRoutes.findNumberOfRoutesBetweenTwoPoints(roadJunctionsMap, A, C, hasExactlyFourRoadJunctions);
		} catch (RoadJunctionDoesntExistException e) {
			e.printStackTrace();
		}

		//assert
		Assert.assertEquals(expectedNumberOfRoutes, numberOfRoutes);
	}
	
	/*8. The length of the shortest route (in terms of distance to travel) from A
	to C. Expected output 9 */
	@Test
	public void testShortestRouteCalculatorFirstCase() {
		//prepare
		ShortestRouteCalculator calc = new ShortestRouteCalculator();
		String src = A;
		String dst = C;

		int expected = 9;

		//act
		int routeLength = calc.shortestRouteBetween(roadJunctionsMap, src, dst);

		//assert
		Assert.assertEquals(expected, routeLength);

	}

	/* 9. The length of the shortest route (in terms of distance to travel) from B
	to B. Expected output 9 */
	@Test
	public void testShortestRouteCalculatorSecondCase() {
		//prepare
		ShortestRouteCalculator calc = new ShortestRouteCalculator();
		String src = B;
		String dst = B;

		int expected = 9;

		//act
		int routeLength = calc.shortestRouteBetween(roadJunctionsMap, src, dst);

		//assert
		Assert.assertEquals(expected, routeLength);

	}

	/* 10. The number of different routes from C to C with a distance of less than30. In the test data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC,
	CEBCEBC, CEBCEBCEBC, CDEBCEBC, CEBCDEBC. Expected output 9  */
	@Test
	public void testFindAllRoutesThirdCase() {
		//prepare
		FindAllRoutes findAllRoutes = new FindAllRoutes();
		WithDistanceSmallerThanX withLengthSmallerThanThirty = new WithDistanceSmallerThanX(30);

		int expectedNumberOfRoutes = 9;

		//act
		int numberOfRoutes = -1;
		try {
			numberOfRoutes = findAllRoutes.findNumberOfRoutesBetweenTwoPoints(roadJunctionsMap, C, C, withLengthSmallerThanThirty);
		} catch (RoadJunctionDoesntExistException e) {
			e.printStackTrace();
		}

		//assert
		Assert.assertEquals(expectedNumberOfRoutes, numberOfRoutes);
	}
}