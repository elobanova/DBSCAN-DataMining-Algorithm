package Algorithms;

import DataStructures.Point3D;

public class ManhattanDistanceMeasure extends AbstractDistanceMeasure {

	@Override
	protected double dist(Point3D p1, Point3D p2) {
		return Math.abs(p1.getX() - p2.getX())
				+ Math.abs(p1.getY() - p2.getY())
				+ Math.abs(p1.getZ() - p2.getZ());
	}

}
