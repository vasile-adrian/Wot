package Utilities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.time.LocalDateTime;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart extends ApplicationFrame {

	public static DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	static String title = "";

	public LineChart(String title) {
		super(title);
		this.title = title;
		JPanel chartPanel = createDemoPanel();
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(chartPanel);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.hide();
	}

	private static JFreeChart createChart(CategoryDataset dataset) {
		// create the chart...
		JFreeChart chart = ChartFactory.createLineChart(title, // chart title
				"Ticks", // domain axis label
				"Values", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
		);
		LocalDateTime myObj = LocalDateTime.now();
		chart.addSubtitle(new TextTitle(myObj.toString()));
		TextTitle source = new TextTitle("PetriNet");
		source.setFont(new Font("Arial", Font.BOLD, 20));
		source.setPosition(RectangleEdge.BOTTOM);
		source.setHorizontalAlignment(HorizontalAlignment.RIGHT);

		chart.addSubtitle(source);
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setRangePannable(true);
		plot.setRangeGridlinesVisible(true);
		plot.setBackgroundAlpha(0.4f);
//		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// CategoryAxis catAxis = (CategoryAxis) plot.getDomainAxis();

		// customise the renderer...
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setDefaultShapesVisible(true);
		renderer.setDrawOutlines(true);
		renderer.setUseFillPaint(true);
		renderer.setDefaultFillPaint(Color.white);
		renderer.setSeriesStroke(0, new BasicStroke(1.0f));
		renderer.setSeriesOutlineStroke(0, new BasicStroke(5.0f));
		renderer.setSeriesShape(0, new Ellipse2D.Double(-5.0, -5.0, 10.0, 10.0));

		chart.setBackgroundPaint(new Color(173, 216, 230));
		CategoryPlot catPlot = chart.getCategoryPlot();
		CategoryAxis domainAxis = catPlot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
		// domainAxis.setVerticalTickLabels(true);
		return chart;
	}

	public static JPanel createDemoPanel() {
		JFreeChart chart = createChart(dataset);
		ChartPanel panel = new ChartPanel(chart);
		// panel.setBackground(Color.BLACK);
		panel.setMouseWheelEnabled(true);
		panel.setCursor(new Cursor(HAND_CURSOR));

		return panel;
	}

}
