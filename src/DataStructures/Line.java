package DataStructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple class to store line information
 *
 */
public class Line {
	public static final int NUMBER_OF_SECTIONS_IN_LINE = 100;

	// Absolute coordinates of the line end points
	private final float m_absX;
	private final float m_absY;
	private final float m_absZ;

	// Directional coordinates from the line start point
	private final float m_dirX;
	private final float m_dirY;
	private final float m_dirZ;

	// Assigns cluster to the line
	// -1 - No cluster assigned / outlier
	// >= 0 - Cluster assigned (0,1,2,3,...)
	private int m_clusterIdentifier;

	/**
	 * Line constructor
	 * 
	 * @param absX
	 *            absolute x coordinate
	 * @param absY
	 *            absolute y coordinate
	 * @param absZ
	 *            absolute z coordinate
	 * @param dirX
	 *            directional x coordinate
	 * @param dirY
	 *            directional y coordinate
	 * @param dirZ
	 *            directional z coordinate
	 */
	public Line(float absX, float absY, float absZ, float dirX, float dirY,
			float dirZ) {
		this.m_absX = absX;
		this.m_absY = absY;
		this.m_absZ = absZ;
		this.m_dirX = dirX;
		this.m_dirY = dirY;
		this.m_dirZ = dirZ;

		// Initially no cluster assigned
		this.m_clusterIdentifier = -1;
	}

	/**
	 * Gets the absolute X coordinate
	 * 
	 * @return absolute X coordinate
	 */
	public float getAx() {
		return m_absX;
	}

	/**
	 * Gets the absolute Y coordinate
	 * 
	 * @return absolute Y coordinate
	 */
	public float getAy() {
		return m_absY;
	}

	/**
	 * Gets the absolute Z coordinate
	 * 
	 * @return absolute Z coordinate
	 */
	public float getAz() {
		return m_absZ;
	}

	/**
	 * Gets the directional X coordinate
	 * 
	 * @return directional X coordinate
	 */
	public float getDx() {
		return m_dirX;
	}

	/**
	 * Gets the directional Y coordinate
	 * 
	 * @return directional Y coordinate
	 */
	public float getDy() {
		return m_dirY;
	}

	/**
	 * Gets the directional Z coordinate
	 * 
	 * @return directional Z coordinate
	 */
	public float getDz() {
		return m_dirZ;
	}

	/**
	 * Returns the assigned cluster ID
	 * 
	 * @return cluster ID
	 */
	public int getCluster() {
		return this.m_clusterIdentifier;
	}

	/**
	 * Assigns the line to cluster ID
	 * 
	 * @param cluster
	 *            the given cluster ID
	 */
	public void setCluster(int cluster) {
		this.m_clusterIdentifier = cluster;
	}

	/**
	 * Splits the line into points according to the number of sections in a line
	 * 
	 * @return a list of points which belong to this line
	 */
	public List<Point3D> getPointPartitioning() {
		List<Point3D> points = new ArrayList<Point3D>();
		for (int i = 0; i < NUMBER_OF_SECTIONS_IN_LINE + 1; i++) {
			Point3D currentPoint = new Point3D(getDx()
					/ NUMBER_OF_SECTIONS_IN_LINE * i + (getAx() - getDx()),
					getDy() / NUMBER_OF_SECTIONS_IN_LINE * i
							+ (getAy() - getDy()), getDz()
							/ NUMBER_OF_SECTIONS_IN_LINE * i
							+ (getAz() - getDz()));
			points.add(currentPoint);
		}
		return points;
	}

	/**
	 * Checks if the given point is on the line
	 * 
	 * @param point
	 *            the point to be checked
	 * @return true is the point is located on the line
	 */
	public boolean containsPoint(Point3D point) {
		List<Point3D> points = getPointPartitioning();
		for (Point3D linesPoint : points) {
			if (linesPoint.equals(point)) {
				return true;
			}
		}
		return false;
	}
}
