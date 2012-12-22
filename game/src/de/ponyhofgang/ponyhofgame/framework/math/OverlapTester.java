package de.ponyhofgang.ponyhofgame.framework.math;

public class OverlapTester {
	
	
	public static boolean direction = false;
	
	public static boolean overlapCircles(Circle c1, Circle c2) {
		float distance = c1.center.distSquared(c2.center);
		float radiusSum = c1.radius + c2.radius;
		return distance <= radiusSum * radiusSum;
	}

	public static boolean overlapRectangles(Rectangle r1, Rectangle r2) {
		if (r1.lowerLeft.x < r2.lowerLeft.x + r2.width
				&& r1.lowerLeft.x + r1.width > r2.lowerLeft.x
				&& r1.lowerLeft.y < r2.lowerLeft.y + r2.height
				&& r1.lowerLeft.y + r1.height > r2.lowerLeft.y)
			return true;
		else
			return false;
	}

	public static boolean overlapCubes(Cube c1, Cube c2) {
		if (c1.lowerLeft.x < c2.lowerLeft.x + c2.width
				&& c1.lowerLeft.x + c2.width > c2.lowerLeft.x
				&& c1.lowerLeft.y < c2.lowerLeft.y + c2.height
				&& c1.lowerLeft.y + c1.height > c2.lowerLeft.y
				&& c1.lowerLeft.z < c2.lowerLeft.z + c2.depth
				&& c1.lowerLeft.z + c2.depth > c2.lowerLeft.z)

			return true;
		else
			return false;
	}

	/**
	 * Intersects the two line segments and returns the intersection point in
	 * intersection.
	 * 
	 * @param p1
	 *            The first point of the first line segment
	 * @param p2
	 *            The second point of the first line segment
	 * @param p3
	 *            The first point of the second line segment
	 * @param p4
	 *            The second point of the second line segment
	 * @param intersection
	 *            The intersection point (optional)
	 * @return Whether the two line segments intersect
	 */
	public static boolean intersectSegments(Vector2 p1, Vector2 p2, Vector2 p3,
			Vector2 p4) {
		float x1 = p1.x, y1 = p1.y, x2 = p2.x, y2 = p2.y, x3 = p3.x, y3 = p3.y, x4 = p4.x, y4 = p4.y;

		float d = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
		if (d == 0)
			return false;

		float ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / d;
		float ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / d;

		if (ua < 0 || ua > 1)
			return false;
		if (ub < 0 || ub > 1)
			return false;

		// if (intersection != null) intersection.set(x1 + (x2 - x1) * ua, y1 +
		// (y2 - y1) * ua);
		return true;
	}

	public static boolean intersectSegmentRectangles(LineRectangle r1,
			LineRectangle r2) {

		if (intersectSegments(r1.p1, r1.p2, r2.p1, r2.p2)) {
			direction = true;
			return true;
		}
		if (intersectSegments(r1.p1, r1.p2, r2.p2, r2.p3)) {
			direction = false;
			return true;
		}
		if (intersectSegments(r1.p1, r1.p2, r2.p3, r2.p4)) {
			direction = true;
			return true;
		}
		if (intersectSegments(r1.p1, r1.p2, r2.p4, r2.p1)) {
			direction = false;
			return true;
		}

		if (intersectSegments(r1.p2, r1.p3, r2.p1, r2.p2)) {
			direction = true;
			return true;
		}
		if (intersectSegments(r1.p2, r1.p3, r2.p2, r2.p3)) {
			direction = false;
			return true;
		}
		if (intersectSegments(r1.p2, r1.p3, r2.p3, r2.p4)) {
			direction = true;
			return true;
		}
		if (intersectSegments(r1.p2, r1.p3, r2.p4, r2.p1)) {
			direction = false;
			return true;
		}

		if (intersectSegments(r1.p3, r1.p4, r2.p1, r2.p2)) {
			direction = true;
			return true;
		}
		if (intersectSegments(r1.p3, r1.p4, r2.p2, r2.p3)) {
			direction = false;
			return true;
		}
		if (intersectSegments(r1.p3, r1.p4, r2.p3, r2.p4)) {
			direction = true;
			return true;
		}
		if (intersectSegments(r1.p3, r1.p4, r2.p4, r2.p1)) {
			direction = false;
			return true;
		}

		if (intersectSegments(r1.p4, r1.p1, r2.p1, r2.p2)) {
			direction = true;
			return true;
		}
		if (intersectSegments(r1.p4, r1.p1, r2.p2, r2.p3)) {
			direction = false;
			return true;
		}
		if (intersectSegments(r1.p4, r1.p1, r2.p3, r2.p4)) {
			direction = true;
			return true;
		}
		if (intersectSegments(r1.p4, r1.p1, r2.p4, r2.p1)) {
			direction = false;
			return true;
		}

		return false;
	}

	public static boolean intersectSegmentRectanglesLine(LineRectangle r2,
			Line l1) {

		if (intersectSegments(l1.p1, l1.p2, r2.p1, r2.p2)
				|| intersectSegments(l1.p1, l1.p2, r2.p2, r2.p3)
				|| intersectSegments(l1.p1, l1.p2, r2.p3, r2.p4)
				|| intersectSegments(l1.p1, l1.p2, r2.p4, r2.p1))

			return true;
		else
			return false;
	}

	public static boolean overlapCircleRectangle(Circle c, Rectangle r) {
		float closestX = c.center.x;
		float closestY = c.center.y;
		if (c.center.x < r.lowerLeft.x) {
			closestX = r.lowerLeft.x;
		} else if (c.center.x > r.lowerLeft.x + r.width) {
			closestX = r.lowerLeft.x + r.width;
		}
		if (c.center.y < r.lowerLeft.y) {
			closestY = r.lowerLeft.y;
		} else if (c.center.y > r.lowerLeft.y + r.height) {
			closestY = r.lowerLeft.y + r.height;
		}
		return c.center.distSquared(closestX, closestY) < c.radius * c.radius;
	}

	public static boolean overlapSpheres(Sphere s1, Sphere s2) {
		float distance = s1.center.distSquared(s2.center);
		float radiusSum = s1.radius + s2.radius;
		return distance <= radiusSum * radiusSum;
	}

	public static boolean pointInSphere(Sphere c, Vector3 p) {
		return c.center.distSquared(p) < c.radius * c.radius;
	}

	public static boolean pointInSphere(Sphere c, float x, float y, float z) {
		return c.center.distSquared(x, y, z) < c.radius * c.radius;
	}

	public static boolean pointInCircle(Circle c, Vector2 p) {
		return c.center.distSquared(p) < c.radius * c.radius;
	}

	public static boolean pointInCircle(Circle c, float x, float y) {
		return c.center.distSquared(x, y) < c.radius * c.radius;
	}

	public static boolean pointInRectangle(Rectangle r, Vector2 p) {
		return r.lowerLeft.x <= p.x && r.lowerLeft.x + r.width >= p.x
				&& r.lowerLeft.y <= p.y && r.lowerLeft.y + r.height >= p.y;
	}

	public static boolean pointInCube(Cube c, Vector3 p) {
		return c.lowerLeft.x <= p.x && c.lowerLeft.x + c.width >= p.x
				&& c.lowerLeft.y <= p.y && c.lowerLeft.y + c.height >= p.y
				&& c.lowerLeft.z <= p.z && c.lowerLeft.z + c.depth >= p.z;
	}

	public static boolean pointInCube(Cube c, float x, float y, float z) {
		return c.lowerLeft.x <= x && c.lowerLeft.x + c.width >= x
				&& c.lowerLeft.y <= y && c.lowerLeft.y + c.height >= y
				&& c.lowerLeft.z <= z && c.lowerLeft.z + c.depth >= z;
	}

	public static boolean pointInRectangle(Rectangle r, float x, float y) {
		return r.lowerLeft.x <= x && r.lowerLeft.x + r.width >= x
				&& r.lowerLeft.y <= y && r.lowerLeft.y + r.height >= y;
	}
}
