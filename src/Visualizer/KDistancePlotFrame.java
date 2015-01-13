package Visualizer;

import java.util.List;

import javax.swing.JFrame;

import Algorithms.DBSCANAnalyzer;
import DataStructures.Line;

public class KDistancePlotFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int LOCATION_OFFSET = 20;

	public KDistancePlotFrame(int width, int height, List<Line> lines,
			DBSCANAnalyzer analyzer) throws IllegalArgumentException {
		if (width < 0 || height < 0 || lines == null) {
			String errorMessage = "The frame's arguments are not valid.";
			throw new IllegalArgumentException(errorMessage);
		}

		this.setSize(width, height);
		this.setLocation(LOCATION_OFFSET, LOCATION_OFFSET);
		this.add(new KDistancePlotPanel(width, height, lines, analyzer));
	}
}
