package Utilities;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class SimpleChart {
	public static DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	public SimpleChart()
	{
		JFreeChart chart = ChartFactory.createLineChart("Value", "Time", "Range", dataset);

//		chart.addSubtitle(new TextTitle(this.name));
//		TextTitle source = new TextTitle("test chart");
//		source.setFont(new Font("SansSerif", Font.PLAIN, 10));
//		source.setPosition(RectangleEdge.BOTTOM);
//		source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
//		chart.addSubtitle(source);
//
//		CategoryPlot plot = (CategoryPlot) chart.getPlot();
//		plot.setRangePannable(true);
//		plot.setRangeGridlinesVisible(false);
//
//		// customise the range axis...
//		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//
//		// ChartUtilities.applyCurrentTheme(chart);
//
//		// customise the renderer...
//		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//		// renderer.setBaseShapesVisible(true);
//		renderer.setDrawOutlines(true);
//		renderer.setUseFillPaint(true);
//		// renderer.setBaseFillPaint(Color.white);
//		renderer.setSeriesStroke(0, new BasicStroke(1.0f));
//		renderer.setSeriesOutlineStroke(0, new BasicStroke(5.0f));
//		renderer.setSeriesShape(0, new Ellipse2D.Double(-5.0, -5.0, 10.0, 10.0));

		ChartPanel chartPanel = new ChartPanel(chart);
//		chartPanel.setDomainZoomable(true);
//		chartPanel.setRangeZoomable(true);
//		Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4),
//				BorderFactory.createEtchedBorder());
//		chartPanel.setBorder(border);

		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setContentPane(chartPanel);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
