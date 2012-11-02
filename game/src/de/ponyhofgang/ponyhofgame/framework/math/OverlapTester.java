package de.ponyhofgang.ponyhofgame.framework.math;

public class OverlapTester {
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
				&& c1.lowerLeft.z + c2.depth > c2.lowerLeft.z )
				
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
				&& c.lowerLeft.z <= p.z && c.lowerLeft.z + c.depth >= p.z
				;
	}
	
	public static boolean pointInCube(Cube c, float x, float y, float z) {
		return c.lowerLeft.x <= x && c.lowerLeft.x + c.width >= x
				&& c.lowerLeft.y <= y && c.lowerLeft.y + c.height >= y
				&& c.lowerLeft.z <= z && c.lowerLeft.z + c.depth >= z
				;
	}

	public static boolean pointInRectangle(Rectangle r, float x, float y) {
		return r.lowerLeft.x <= x && r.lowerLeft.x + r.width >= x
				&& r.lowerLeft.y <= y && r.lowerLeft.y + r.height >= y;
	}
}
