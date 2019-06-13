/*remove

package org.processmining.stream.reader.views;
/*From Andrea
 * 
 */
/*remove
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTitleAnnotation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;

/**
* 
* @author Andrea Burattin
*/
/*remove
public class FitnessPrecisionVisualizer extends JPanel {

	private static final long serialVersionUID = -2481373568509521714L;
	private TimeSeries fitness;
	private TimeSeries precision;

	/**
	 * 
	 * @param background
	 */
/*remove
	public FitnessPrecisionVisualizer(Color background) {
		super(new BorderLayout());

		// create two series that automatically discard data more than 30 seconds old...
		this.fitness = new TimeSeries("Fitness");
		this.precision = new TimeSeries("Precision");
//		this.total.setHistoryCount(30000);
//		fitness.setMaximumItemAge(30000);
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(fitness);
		dataset.addSeries(precision);

		DateAxis domain = new DateAxis();
		NumberAxis range = new NumberAxis();

		XYItemRenderer renderer = new DefaultXYItemRenderer();
		renderer.setSeriesPaint(0, Color.red.brighter());
		renderer.setSeriesPaint(1, Color.green.brighter());
		renderer.setSeriesShape(2, new Rectangle());
		renderer.setSeriesStroke(0, new BasicStroke(2f));
		
		XYPlot xyplot = new XYPlot(dataset, domain, range, renderer);
		xyplot.setBackgroundPaint(Color.darkGray);

		domain.setAutoRange(true);
		domain.setLowerMargin(0.0);
		domain.setUpperMargin(0.0);
		domain.setTickLabelsVisible(false);

		range.setTickMarksVisible(true);
		range.setTickLabelsVisible(true);
		range.setLowerBound(0.0);
		range.setUpperBound(1.0);
		
		LegendTitle lt = new LegendTitle(xyplot);
		lt.setPosition(RectangleEdge.BOTTOM);
		lt.setBackgroundPaint(new Color(40, 40, 40, 200));
		lt.setItemPaint(Color.lightGray);
		lt.setPosition(RectangleEdge.BOTTOM);
		XYTitleAnnotation ta = new XYTitleAnnotation(1, 0, lt, RectangleAnchor.BOTTOM_RIGHT);
		xyplot.addAnnotation(ta);

		JFreeChart chart = new JFreeChart(null, null, xyplot, false);
		chart.setBackgroundPaint(background);
		ChartPanel chartPanel = new ChartPanel(chart);
		add(chartPanel);
	}

	/**
	 * Adds an observation to the fitness time series.
	 *
	 * @param fitness the fitness
	 */
/*remove
	public void addFitnessObservation(double fitness) {
		this.fitness.add(new Millisecond(), fitness);
	}
	
	/**
	 * Adds an observation to the precision time series.
	 *
	 * @param precision the precision
	 */
/*remove
public void addPrecisionObservation(double precision) {
		this.precision.add(new Millisecond(), precision);
	}
	
	/**
	 * Adds an observation to the precision and to the fitness time series.
	 *
	 * @param fitness the fitness
	 * @param precision the precision
	 */
/*remove
public void addFitnessPrecisionObservation(double fitness, double precision) {
		Millisecond time = new Millisecond();
		this.fitness.add(time, fitness);
		this.precision.add(time, fitness);
	}
}
/*To Andrea
 * 
 */