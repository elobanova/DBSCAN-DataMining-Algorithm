package DataStructures;

public class Point3D {
	public static final int SPACE_DIMENSION = 3;

	private final double x;
	private final double y;
	private final double z;

	private int clusterIdentifier;

	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;

		clusterIdentifier = -1;
	}

	/**
	 * Returns the x coordinate of the 3D point
	 * 
	 * @return x
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Returns the y coordinate of the 3D point
	 * 
	 * @return y
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Returns the z coordinate of the 3D point
	 * 
	 * @return z
	 */
	public double getZ() {
		return this.z;
	}

	/**
	 * Assigns the point to cluster identifier
	 * 
	 * @param clusterIdentifier
	 *            the given cluster identifier
	 */
	public void setClusterIdentifier(int clusterIdentifier) {
		if (this.clusterIdentifier != clusterIdentifier) {
			this.clusterIdentifier = clusterIdentifier;
		}
	}

	/**
	 * Returns the assigned cluster identifier
	 * 
	 * @return cluster identifier
	 */
	public int getClusterIdentifier() {
		return this.clusterIdentifier;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Point3D))
			return false;
		Point3D otherPoint3D = (Point3D) other;
		return this.x == otherPoint3D.x && this.y == otherPoint3D.y
				&& this.z == otherPoint3D.z;
	}
}
