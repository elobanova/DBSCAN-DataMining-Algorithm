package Visualizer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import Algorithms.DBSCANAnalyzer;
import DataStructures.Line;

public class KDistancePlotPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int POINT_WIDTH = 3;
	private static final int AXIS_OFFSET = 100;
	private static final int LABELS_OFFSET = 80;
	private static final int SCALING_FACTOR = 5;
	private final int width;
	private final int height;

	private final List<Line> lines;
	private final DBSCANAnalyzer analyzer;

	public KDistancePlotPanel(int width, int height, List<Line> lines,
			DBSCANAnalyzer analyzer) {
		this.width = width;
		this.height = height;
		this.lines = lines;
		this.analyzer = analyzer;
		this.setBackground(Color.white);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		g.drawString("K-distance plot for epsilon parameter estimation",
				AXIS_OFFSET * 2, LABELS_OFFSET);
		g.setColor(Color.BLACK);
		g.drawLine(AXIS_OFFSET, 0, AXIS_OFFSET, this.height);
		g.drawLine(this.width, this.height - AXIS_OFFSET, 0, this.height
				- AXIS_OFFSET);

		g.setColor(Color.BLUE);
		Double[] points = analyzer.getDistanceValuesForEpsilonEstimate(analyzer
				.partitionLinesIntoPoints(lines));
		int datasetLength = points.length;
		for (int i = 0; i < datasetLength; i++) {
			int xCoord = AXIS_OFFSET + i
					/ (datasetLength / (width - AXIS_OFFSET));
			int intValue = points[i].intValue();
			g.drawOval(xCoord, this.height - AXIS_OFFSET - intValue
					* SCALING_FACTOR, POINT_WIDTH, POINT_WIDTH);
			if (i % (datasetLength / 8) == 0) {
				g.drawString(Integer.toString(intValue), xCoord, this.height
						- LABELS_OFFSET);
			}
		}
	}

}
