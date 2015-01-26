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
package openqcm_dev3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
    
    public ChartDynamicData() {
        // init combined XY plot
        CombinedDomainXYPlot plotComb = new CombinedDomainXYPlot(new DateAxis("Time (hh:mm:ss)"));
        XYPlot plot = new XYPlot();
        
        // init real time chart
        TimeSeries seriesFrequency = new TimeSeries("Frequency (Hz)");
        
        // add primary axis frequency
        datasetFrequency = new TimeSeriesCollection(seriesFrequency);
        NumberAxis rangeAxisF = new NumberAxis("Frequency (Hz)");
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
        plot.setRenderer(1, new StandardXYItemRenderer());   
        
        plot.mapDatasetToRangeAxis(1, 1);
        plotComb.add(plot);
        plotComb.setBackgroundPaint(Color.white);
        plotComb.setDomainGridlinePaint(Color.white);
        plotComb.setRangeGridlinePaint(Color.white);
                
        ValueAxis domainAxis = plotComb.getDomainAxis();
        domainAxis.setAutoRange(true);
        domainAxis.setFixedAutoRange(300000.0);  // 5 minute
        
        // init the JFreeChart
        JFreeChart chart = new JFreeChart(plotComb);
        chart.setBorderPaint(Color.lightGray);
        chart.setBorderVisible(true);
        chart.setBackgroundPaint(Color.white);
        
        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.TOP);
        legend.setItemFont(new Font("Dialog", Font.PLAIN, 9));
        
        // add real time chart to the frame
        ChartPanel chartPanel = new ChartPanel(chart);
        this.setLayout (new BorderLayout());
        this.add(chartPanel);
        this.validate();
    }
    
    // add and display frequency data to real time chart
    public void addFrequencyData(double frequency){
        datasetFrequency.getSeries(0).add(new Millisecond(), frequency);
    }
    
    // add and display temperarture data to real time chart
    public void addTemperatureData(double temperature){
        datasetTemperature.getSeries(0).add(new Millisecond(), temperature);
    }
    
    // clear the frequency and temperature chart
    public void clearChart(){
        datasetFrequency.getSeries(0).clear();
        datasetTemperature.getSeries(0).clear();
    }
    
  
}