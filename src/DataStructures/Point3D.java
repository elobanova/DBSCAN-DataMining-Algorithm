package DataStructures;

public class Point3D {
	public static final int SPACE_DIMENSION = 3;
	public final static int NO_CLUSTER_ASSIGNED = -1;

	private final double x;
	private final double y;
	private final double z;

	private int clusterIdentifier;

	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;

		clusterIdentifier = NO_CLUSTER_ASSIGNED;
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
				&& this.z == otherPoint3D.z
				&& this.clusterIdentifier == otherPoint3D.clusterIdentifier;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (int) this.x;
		hash = 53 * hash + (int) this.y;
		hash = 53 * hash + (int) this.z;
		return hash;
	}

	/**
	 * Checks if this point is located within the interval between ax and (ax -
	 * dx)
	 * 
	 * @param ax
	 *            the coordinate of one side of the interval
	 * @param dx
	 *            the direction to another side of the interval
	 * 
	 * @return true is the point's x coordinate belongs to the interval
	 */
	public boolean isXBetween(float ax, float dx) {
		return dx >= 0 ? this.x >= ax - dx && this.x <= ax : this.x >= ax
				&& this.x <= ax - dx;
	}

	/**
	 * Checks if this point is located within the interval between ay and (ay -
	 * dy)
	 * 
	 * @param ay
	 *            the coordinate of one side of the interval
	 * @param dy
	 *            the direction to another side of the interval
	 * 
	 * @return true is the point's y coordinate belongs to the interval
	 */
	public boolean isYBetween(float ay, float dy) {
		return dy >= 0 ? this.y >= ay - dy && this.y <= ay : this.y >= ay
				&& this.y <= ay - dy;
	}

	/**
	 * Checks if this point is located within the interval between az and (az -
	 * dz)
	 * 
	 * @param az
	 *            the coordinate of one side of the interval
	 * @param dz
	 *            the direction to another side of the interval
	 * 
	 * @return true is the point's z coordinate belongs to the interval
	 */
	public boolean isZBetween(float az, float dz) {
		return dz >= 0 ? this.z >= az - dz && this.z <= az : this.z >= az
				&& this.z <= az - dz;
	}
}
