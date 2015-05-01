/* 
 * Copyright (C) 2015 Marco
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package openqcm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleEdge;


/**
 *
 * @author Marco
 */
public class ChartDynamicData extends JPanel{
    private final TimeSeriesCollection datasetFrequency;
    private final TimeSeriesCollection datasetTemperature;
    private XYPlot plot = new XYPlot();
    private XYItemRenderer rendererT = new StandardXYItemRenderer();
    private NumberAxis rangeAxisF = new NumberAxis();
    private NumberAxis rangeAxisT = new NumberAxis();
    
    CombinedDomainXYPlot plotComb = new CombinedDomainXYPlot(new DateAxis("Time (hh:mm:ss)"));
    ValueAxis domainAxis = plotComb.getDomainAxis();
    
    public ChartDynamicData() {
        
        // add primary axis frequency
        TimeSeries seriesFrequency = new TimeSeries("Frequency (Hz)");
        datasetFrequency = new TimeSeriesCollection(seriesFrequency);
        rangeAxisF = new NumberAxis("Frequency (Hz)");
        XYItemRenderer renderer = new StandardXYItemRenderer();
        renderer.setSeriesPaint(0, new Color(0, 142, 192));
        rangeAxisF.setAutoRangeIncludesZero(false);
        rangeAxisF.setAutoRange(true);
        rangeAxisF.setAutoRangeMinimumSize(50);
        
        plot.setDataset(0, datasetFrequency);
        plot.setRangeAxis(0, rangeAxisF);
        plot.setRangeAxisLocation(0, AxisLocation.BOTTOM_OR_LEFT);
        plot.setRenderer(0, renderer);  
        plot.mapDatasetToRangeAxis(0, 0);
        
        // add secondary axis temperature
        TimeSeries seriesTemperature = new TimeSeries("Temperature (°C)");
        datasetTemperature = new TimeSeriesCollection(seriesTemperature);
        plot.setDataset(1, datasetTemperature);
        NumberAxis rangeAxisT = new NumberAxis("Temperature (°C)");
        rangeAxisT.setAutoRangeIncludesZero(false);
        rangeAxisT.setAutoRange(true);
        rangeAxisT.setAutoRangeMinimumSize(5);
        plot.setRangeAxis(1, rangeAxisT);
        plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
        // custom renderer for dinamically changing temperaure
        rendererT.setSeriesPaint(0, new Color(255, 128, 0)); 
        plot.setRenderer(1, rendererT);
        
        plot.mapDatasetToRangeAxis(1, 1);
        plotComb.add(plot);
        plotComb.setBackgroundPaint(Color.white);
        plotComb.setDomainGridlinePaint(Color.white);
        plotComb.setRangeGridlinePaint(Color.white);
        // enable panning for both axis
        plotComb.setRangePannable(true);
        plotComb.setDomainPannable(true);
                
        // set time axis properties
        // format time axis as hh:mm:ss
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        DateAxis axis = (DateAxis) plotComb.getDomainAxis();
        axis.setDateFormatOverride(format);
        // default auto range
        domainAxis.setAutoRange(true);
        
        // init the JFreeChart
        JFreeChart chart = new JFreeChart(plotComb);
        chart.setBorderPaint(Color.lightGray);
        chart.setBorderVisible(true);
        chart.setBackgroundPaint(Color.white);
        
        // set legend properties
        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.TOP);
        legend.setItemFont(new Font("Dialog", Font.PLAIN, 9));
        
        // constructor for org.jfree.chart.ChartPanel
        // ChartPanel(JFreeChart chart, boolean properties, boolean save, boolean print, boolean zoom, boolean tooltips)
        ChartPanel chartPanel = new ChartPanel(chart, false, true, true, true, true);
        // enable mouse wheel support for the chart panel
        chartPanel.setMouseWheelEnabled(true);
        
        this.setLayout (new BorderLayout());
        // add real time chart to the frame
        this.add(chartPanel);
        this.validate();
    }
    
    // add and display frequency data to real time chart
    public void addFrequencyData(double frequency){
        datasetFrequency.getSeries(0).add(new Millisecond(), frequency);
    }
    
    // add and display temperature data to real time chart
    public void addTemperatureData(double temperature){
        datasetTemperature.getSeries(0).add(new Millisecond(), temperature);
    }
    
    // clear the frequency and temperature chart
    public void clearChart(){
        datasetFrequency.getSeries(0).clear();
        datasetTemperature.getSeries(0).clear();
        domainAxis.setFixedAutoRange(0);
        domainAxis.setAutoRange(true);
        rangeAxisF.setAutoRange(true);
        rangeAxisF.setAutoRangeMinimumSize(50);
        rangeAxisT.setAutoRangeIncludesZero(false);
        rangeAxisT.setAutoRange(true);
        rangeAxisT.setAutoRangeMinimumSize(5);
    }
   
    // hide temperature chart by setting colour line transparency 
    public void hideChartTemperature(){
        rendererT.setSeriesPaint(0, new Color(0, 0, 255, 0));
        plot.setRenderer(1, rendererT);
    }
    
    // show temperature by colouring the line
    public void  showChartTemperature(){
        rendererT.setSeriesPaint(0, new Color(255, 128, 0));
        plot.setRenderer(1, rendererT);
    }
    
    // check the lenght of domain axis
    public void checkDomainAxis(){
        // fixed domain time interval 10 minutes TODO you can take more time dude !
        int domainTime = 600000; 
        double domainTimeCurrent = domainAxis.getRange().getLength();
        //System.out.println(domainTimeCurrent);
        if (domainTimeCurrent > domainTime) domainAxis.setFixedAutoRange(domainTime);
    }
}