package org.processmining.stream.util.views;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.data.time.DateRange;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Minute;
import org.processmining.stream.util.DateUtils;

/**
 * Class for construction of a (Vertical) time series bar chart
 */
public class DynamicTimeSeriesBarChart {

	/**
	 * Chart variables
	 */
	private JFreeChart chart;
	private XYPlot plot;
	private ValueAxis domain;
	private ValueAxis range;
	private ClusteredXYBarRenderer renderer;

	/**
	 * Time-series veriables
	 */
	// size of visualization window, > 1.
	private int windowSize;
	// time series object
	private DynamicTimeSeriesCollection timeSeries;
	// value of minimal time-stamp in visualization
	private Date minimalTimeStamp;
	// index of current time slot within the time-series object
	private int currentTimeSlotIndex;
	// current timeslot of new data
	private int currentTimeSlot = currentTimeSlotIndex = 0;

	/**
	 * Administration
	 */
	private Map<String, Integer> categoryCount = new HashMap<String, Integer>();
	private Map<String, Integer> categorySeriesMapping = new HashMap<String, Integer>();
	private List<Integer> maxima = new ArrayList<Integer>();

	public DynamicTimeSeriesBarChart(int windowSize, int nrOfTimeSeries, String chartName, String xAxisDesc,
			String yAxisDesc) {
		assert (windowSize > 1);
		this.windowSize = windowSize;
		this.minimalTimeStamp = DateUtils.floorMinutes(new Date());
		this.maxima.addAll(Collections.nCopies(this.windowSize, 0));
		this.timeSeries = new DynamicTimeSeriesCollection(nrOfTimeSeries, this.windowSize, new Minute());
		this.timeSeries.setTimeBase(new Minute(this.minimalTimeStamp));
		this.initializeChart(chartName, xAxisDesc, yAxisDesc);
	}

	private void initializeChart(String chartName, String xAxisDesc, String yAxisDesc) {

		this.chart = ChartFactory.createXYBarChart(chartName, xAxisDesc, true, yAxisDesc, this.timeSeries,
				PlotOrientation.VERTICAL, true, false, false);

		this.configureChart();

		this.plot = this.chart.getXYPlot();

		this.domain = this.plot.getDomainAxis();
		this.configureDomain();

		this.range = this.plot.getRangeAxis();
		this.configureRange();

		this.renderer = new ClusteredXYBarRenderer();
		this.configureRenderer();
		plot.setRenderer(this.renderer);
		plot.setBackgroundPaint(new Color(0,0,0,0));
	}

	private void configureChart() {
		assert (this.chart != null);
		this.chart.setBackgroundPaint(new Color(0, 0, 0, 0));
	}

	private void configureDomain() {
		assert (this.domain != null);
		domain.setRange(new DateRange(this.minimalTimeStamp, DateUtils.addMinutes(this.minimalTimeStamp,
				this.windowSize)));
	}

	private void configureRange() {
		assert (this.range != null);
		this.range.setRange(0, 1);
		NumberAxis numberAxis = (NumberAxis) range;
		numberAxis.setTickUnit(new NumberTickUnit(1d));
	}

	private void configureRenderer() {
		assert (this.renderer != null);
		renderer.setBarPainter(new StandardXYBarPainter());
		renderer.setMargin(0.05);
		renderer.setShadowVisible(false);
	}

	public void addCategoryDataPoint(Date date, String category) {

		this.processCategory(category);

		Date flooredDate = DateUtils.floorMinutes(date);
		int newTimeSlot = DateUtils.differenceInMinutes(flooredDate, this.minimalTimeStamp);
		if (newTimeSlot > this.currentTimeSlot) {
			this.advanceToNewTimeSlot(flooredDate, newTimeSlot);
		}

		this.categoryCount.put(category, this.categoryCount.get(category) + 1);
		this.storeMaximumValue(newTimeSlot);
		this.evaluateRange();

		this.timeSeries.addValue(this.categorySeriesMapping.get(category), this.currentTimeSlotIndex,
				this.categoryCount.get(category));
	}

	private void advanceToNewTimeSlot(Date newDate, int newTimeSlot) {

		this.resetCategoryCounters();
		if (newTimeSlot > this.windowSize - 1) {
			this.timeSeries.advanceTime();
			this.minimalTimeStamp = DateUtils.addMinutes(this.minimalTimeStamp, newTimeSlot - (this.windowSize - 1));
			this.domain.setRange(new DateRange(this.minimalTimeStamp, DateUtils.addMinutes(newDate, newTimeSlot
					- (this.windowSize - 1))));
			this.currentTimeSlot = this.windowSize - 1;
			this.currentTimeSlotIndex = this.timeSeries.getNewestIndex();
		} else {
			this.currentTimeSlot = newTimeSlot;
			this.currentTimeSlotIndex = this.currentTimeSlot;
		}
	}

	private void storeMaximumValue(int timeSlot) {
		int maxValue = Collections.max(this.categoryCount.values());
		if (timeSlot <= this.windowSize - 1) {
			this.maxima.remove(timeSlot);
			this.maxima.add(timeSlot, maxValue);
		} else {
			this.maxima.remove(0);
			this.maxima.add(maxValue);
		}
	}

	private void resetCategoryCounters() {
		for (Map.Entry<String, Integer> entry : this.categoryCount.entrySet()) {
			entry.setValue(0);
		}
	}

	private void processCategory(String category) {
		if (!this.categorySeriesMapping.containsKey(category)) {
			this.createNewCategorySeries(category);
		}
	}

	private void createNewCategorySeries(String category) {
		int chartIndex = this.timeSeries.getSeriesCount();
		this.categorySeriesMapping.put(category, chartIndex);
		this.categoryCount.put(category, 0);
		this.timeSeries.addSeries(new float[this.windowSize], chartIndex, category);
	}

	public void evaluateRange() {
		int max;
		try {
			max = Collections.max(this.maxima);
		} catch (NoSuchElementException e) {
			max = 1;
		}
		if (max != this.range.getUpperBound()) {
			this.range.setRange(0, max);
			NumberAxis numberAxis = (NumberAxis) range;
			numberAxis.setAutoTickUnitSelection(true);
		}
	}

	public JFreeChart getChartInstance() {
		return this.chart;
	}

}
