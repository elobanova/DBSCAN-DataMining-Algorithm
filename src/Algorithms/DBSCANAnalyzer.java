package Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataStructures.Line;
import DataStructures.Point3D;

public class DBSCANAnalyzer {
	private final double eps;
	private final double minPts;
	private final AbstractDistanceMeasure distanceMeasure;

	public DBSCANAnalyzer(AbstractDistanceMeasure distanceMeasure, double eps) {
		this.distanceMeasure = distanceMeasure;
		this.eps = eps;
		this.minPts = countMinPts(Point3D.SPACE_DIMENSION);
	}

	/**
	 * Clusters the given lines into classes by applying DBSCAN algorithm on the
	 * points of the lines
	 * 
	 * @param lines
	 *            the lines to cluster
	 * @throws IllegalArgumentException
	 *             if the list of lines provided is null
	 */
	public void performClustering(List<Line> lines)
			throws IllegalArgumentException {
		if (lines == null) {
			String errorMessage = "The lines argument is not valid.";
			throw new IllegalArgumentException(errorMessage);
		}
		List<Point3D> dataset = partitionLinesIntoPoints(lines);
		performClusteringOnPoints(dataset);
		for (Point3D point : dataset) {
			for (Line line : lines) {
				if (line.getCluster() == -1 && line.containsPoint(point)) {
					line.setCluster(point.getClusterIdentifier());
				}
			}
		}
	}

	/**
	 * Clusters the points into classes by applying DBSCAN algorithm
	 * 
	 * @param dataset
	 *            the points to cluster
	 * @throws IllegalArgumentException
	 *             if the list of points provided is null
	 */
	public void performClusteringOnPoints(List<Point3D> dataset)
			throws IllegalArgumentException {
		if (dataset == null) {
			String errorMessage = "The dataset argument is not valid.";
			throw new IllegalArgumentException(errorMessage);
		}
		Map<Point3D, Integer> visited = new HashMap<Point3D, Integer>();
		int clusterIdentifier = -1;
		for (Point3D point : dataset) {

			// object is not yet classified
			if (visited.get(point) == null) {
				List<Point3D> neighbours = getNeighbours(point, dataset);

				// point is a core-object
				if (neighbours.size() >= this.minPts) {

					// collect all objects density-reachable from point and
					// assign them to a new cluster
					clusterIdentifier++;
					collectDensityReachableObjects(point, clusterIdentifier,
							neighbours, dataset, visited);
				} else {
					visited.put(point, -1);
				}
			}
		}
	}

	/**
	 * Finds and collects all objects density-reachable from the given point
	 * 
	 * @param point
	 *            the core point
	 * @param clusterIdentifier
	 *            the current cluster identifier
	 * @param neighbours
	 *            the list of a core point's neighbours
	 * @param dataset
	 *            the points to cluster
	 * @param visited
	 *            the set of already clustered points
	 */
	private void collectDensityReachableObjects(Point3D point,
			int clusterIdentifier, List<Point3D> neighbours,
			List<Point3D> dataset, Map<Point3D, Integer> visited) {
		point.setClusterIdentifier(clusterIdentifier);
		List<Point3D> neighboursCopy = new ArrayList<Point3D>(neighbours);
		for (int i = 0; i < neighboursCopy.size(); i++) {
			Point3D current = neighboursCopy.get(i);
			Integer currentClusterIdentifier = visited.get(current);

			// only check those points which haven't been visited yet
			if (currentClusterIdentifier == null) {
				final List<Point3D> currentNeighbors = getNeighbours(current,
						dataset);
				if (currentNeighbors.size() >= minPts) {
					// found the density-reachable, connect it with the set
					neighboursCopy = union(neighboursCopy, currentNeighbors);
				}
			}

			if (currentClusterIdentifier == null
					|| currentClusterIdentifier == -1) {
				visited.put(current, clusterIdentifier);
				current.setClusterIdentifier(clusterIdentifier);
			}
		}

	}

	/**
	 * Searches for the neighbours of a given point in the area with radius
	 * equal to eps
	 * 
	 * @param point
	 *            the center point to locate the nearest neighbours to
	 * @param points
	 *            the set of data points
	 * @return the list of all the neighbours in the eps-radius sphere
	 */
	private List<Point3D> getNeighbours(Point3D point, List<Point3D> points) {
		List<Point3D> neighbours = new ArrayList<Point3D>();
		for (Point3D neighbour : points) {
			if (point != neighbour
					&& distanceMeasure.dist(neighbour, point) <= this.eps) {
				neighbours.add(neighbour);
			}
		}
		return neighbours;
	}

	/**
	 * Performs the union of two set of points
	 * 
	 * @param firstSet
	 *            the set to be filled with the points
	 * @param secondSet
	 *            the set to get the points from
	 * @return resulting set consisting of the points from both sets
	 */
	private List<Point3D> union(List<Point3D> firstSet, List<Point3D> secondSet) {
		for (Point3D point : secondSet) {
			if (!firstSet.contains(point)) {
				firstSet.add(point);
			}
		}
		return firstSet;
	}

	/**
	 * Delegates the splitting of lines into points for the future clustering
	 * according to the number of sections in a line
	 * 
	 * @param lines
	 *            lines to break into the points
	 * @return a list of points which belong to the lines
	 */
	private List<Point3D> partitionLinesIntoPoints(List<Line> lines) {
		List<Point3D> dataset = new ArrayList<Point3D>();
		for (Line line : lines) {
			dataset.addAll(line.getPointPartitioning());
		}
		return dataset;
	}

	/**
	 * Computes the MinPts value based on dimension of the data space
	 * 
	 * @param dimension
	 *            dimension of the data space
	 * @return MinPts
	 * @throws IllegalArgumentException
	 *             if the dimension is negative
	 */
	private int countMinPts(int dimension) throws IllegalArgumentException {
		if (dimension > 0) {
			return 2 * dimension - 1;
		}

		String errorMessage = "The dimension argument is negative.";
		throw new IllegalArgumentException(errorMessage);
	}

	/**
	 * Performs the k-th distance calculation from the given point
	 * 
	 * @param dataset
	 *            a list of points to be clustered
	 * @param o
	 *            the center point to locate the distance from
	 * @param k
	 *            the order of the nearest neighbour
	 * @return the k-th distance value
	 * @throws IllegalArgumentException
	 *             if a set of data points is null or the order of the nearest
	 *             neighbour exceeds the cardinality of a data set
	 */
	private double calculateKDistance(List<Point3D> dataset, Point3D o, int k)
			throws IllegalArgumentException {
		if (dataset == null) {
			String errorMessage = "The dataset argument is null.";
			throw new IllegalArgumentException(errorMessage);
		}

		if (k > dataset.size()) {
			String errorMessage = "The order of the nearest neighbour can not exceed the cardinality of a set";
			throw new IllegalArgumentException(errorMessage);
		}

		double[] distances = new double[dataset.size()];
		for (int i = 0; i < dataset.size(); i++) {
			if (dataset.get(i) != o) {
				distances[i] = distanceMeasure.dist(dataset.get(i), o);
			}
		}
		Arrays.sort(distances);
		return distances[k];
	}

	/**
	 * Calculates the MinPts-distance values for the user to select the
	 * "border object"
	 * 
	 * @param dataset
	 *            a list of points to be clustered
	 * @return MinPts-distance plot values
	 * @throws IllegalArgumentException
	 *             if a set of data points is null
	 */
	public Double[] getDistanceValuesForEpsilonEstimate(List<Point3D> dataset)
			throws IllegalArgumentException {
		if (dataset != null) {
			final Double[] kDistancePlotValues = new Double[dataset.size()];
			for (int i = 0; i < dataset.size(); i++) {
				kDistancePlotValues[i] = calculateKDistance(dataset,
						dataset.get(i), countMinPts(Point3D.SPACE_DIMENSION));
			}
			Arrays.sort(kDistancePlotValues, new Comparator<Double>() {
				@Override
				public int compare(Double o1, Double o2) {
					if (o1 == o2) {
						return 0;
					} else if (o1 < o2) {
						return 1;
					} else {
						return -1;
					}
				}
			});
			return kDistancePlotValues;
		}

		String errorMessage = "The dataset argument is not valid.";
		throw new IllegalArgumentException(errorMessage);
	}
}
