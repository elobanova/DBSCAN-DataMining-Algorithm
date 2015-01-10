package Algorithms;

import DataStructures.Point3D;

public class EucledianDistanceMeasure extends AbstractDistanceMeasure {

	@Override
	protected double dist(Point3D p1, Point3D p2) {
		return Math.sqrt((p1.getX() - p2.getX()) * (p1.getX() - p2.getX())
				+ (p1.getY() - p2.getY()) * (p1.getY() - p2.getY())
				+ (p1.getZ() - p2.getZ()) * (p1.getZ() - p2.getZ()));
	}
}
