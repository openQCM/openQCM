/* 
 * Copyright (C) 2014 openQCM
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
 *
 */

package openqcm;

/**
 *
 * @authors Marco Mauro and Luciano Zu
 * version  basic 
 * date     oct 2014
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.GridBagLayout; 
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.zu.ardulink.Link;
import org.zu.ardulink.RawDataListener;
import org.zu.ardulink.gui.ConnectionStatus;
import org.zu.ardulink.gui.SerialConnectionPanel;

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

public class RawMonitor implements RawDataListener {

    private JFrame frmArdulinkAnalogMonitor;
    private SerialConnectionPanel serialConnectionPanel;
    private JButton btnConnect;
    private JButton btnDisconnect;
    private JButton btnOpenFile;
    private JButton btnClearDataSet; // clear graph
    private JLabel fileLabel;
    private JLabel data0Name;
    private JLabel data0Value;
    private JLabel data1Name;
    private JLabel data1Value;
    private File sf;
    private String fileName;

    private Link link = Link.getDefaultInstance();

    /**
     * The number of subplots.
     */
    public static final int SUBPLOT_COUNT = 2;

    /**
     * The datasets.
     */
    private TimeSeriesCollection[] datasets;

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(NimbusLookAndFeel.class.getCanonicalName());
                    RawMonitor window = new RawMonitor();
                    window.frmArdulinkAnalogMonitor.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public RawMonitor() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmArdulinkAnalogMonitor = new JFrame();
        frmArdulinkAnalogMonitor.setTitle("openQCM version 0");
        frmArdulinkAnalogMonitor.setBounds(100, 100, 800, 600);
        frmArdulinkAnalogMonitor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the contents of the frame.
        JPanel controlPanel = new JPanel();
        JPanel dataPanel = new JPanel(new GridLayout(2, 2));
        frmArdulinkAnalogMonitor.getContentPane().add(controlPanel, BorderLayout.SOUTH);
        frmArdulinkAnalogMonitor.getContentPane().add(dataPanel, BorderLayout.EAST);

        // Add Grid Layout 
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        // Serial connection GUI
        serialConnectionPanel = new SerialConnectionPanel();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        controlPanel.add(serialConnectionPanel, c);
        
        // Serial Connect button
        btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String comPort = serialConnectionPanel.getConnectionPort();
                String baudRateS = serialConnectionPanel.getBaudRate();
                if (comPort == null || "".equals(comPort)) {
                    JOptionPane.showMessageDialog(
                            btnConnect, "Invalid COM PORT setted.", "Error", JOptionPane.ERROR_MESSAGE
                    );
                } else if (baudRateS == null || "".equals(baudRateS)) {
                    JOptionPane.showMessageDialog(
                            btnConnect, "Invalid baud rate setted. Advice: set " + Link.DEFAULT_BAUDRATE, "Error", JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    try {
                        int baudRate = Integer.parseInt(baudRateS);

                        boolean connected = link.connect(comPort, baudRate);
                        if (connected) {
                            btnConnect.setEnabled(false);
                            serialConnectionPanel.setEnabled(false);
                            btnDisconnect.setEnabled(true);
                            btnOpenFile.setEnabled(true);
                            btnClearDataSet.setEnabled(true);
                        }
                    } catch (Exception ex) {
                        
                        ex.printStackTrace();
                        String message = ex.getMessage();
                        if (message == null || message.trim().equals("")) {
                            message = "Generic Error on connection";
                        }
                        JOptionPane.showMessageDialog(btnConnect, message, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        controlPanel.add(btnConnect, c);
        
        // Serial Disconnect button
        btnDisconnect = new JButton("Disconnect");
        btnDisconnect.setEnabled(false);
        btnDisconnect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean disconnected = link.disconnect();
                if (disconnected) {
                    btnConnect.setEnabled(true);
                    serialConnectionPanel.setEnabled(true);
                    btnDisconnect.setEnabled(false);
                    btnOpenFile.setEnabled(false);
                    btnClearDataSet.setEnabled(false);
                }
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        controlPanel.add(btnDisconnect, c);
        
        // Connection Status
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 0;
        ConnectionStatus connectionStatus = new ConnectionStatus();
        controlPanel.add(connectionStatus, c);
        
        // Save Data File Button
        btnOpenFile = new JButton("Save File");
        fileLabel = new JLabel("null");
        btnOpenFile.setEnabled(false);
        btnOpenFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Text Files", "txt", "dat"
                );
                chooser.setFileFilter(filter);
                int option = chooser.showOpenDialog(frmArdulinkAnalogMonitor);
                if (option == JFileChooser.APPROVE_OPTION) {
                    sf = chooser.getSelectedFile();
                    fileName = "nothing";
                    /*
                    if (sf.exists()) {
                        fileName = sf.getName();
                    }
                    */
                    fileName = sf.getName();
                    fileLabel.setText(fileName);
                } else {
                    fileLabel.setText("null");
                }
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        controlPanel.add(btnOpenFile, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        controlPanel.add(fileLabel, c);

        // Display Data in JText Field
        data0Name = new JLabel("Data0 = ");
        data0Value = new JLabel("NaN");
        dataPanel.add(data0Name);
        dataPanel.add(data0Value);
        data1Name = new JLabel("Data1 = ");
        data1Value = new JLabel("NaN");
        dataPanel.add(data1Name);
        dataPanel.add(data1Value);

        // Init Chart Panel JFREECHART
        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new DateAxis("Time"));
        datasets = new TimeSeriesCollection[SUBPLOT_COUNT];
        for (int i = 0; i < SUBPLOT_COUNT; i++) {
            TimeSeries series = new TimeSeries("DATA" + i);
            datasets[i] = new TimeSeriesCollection(series);
            NumberAxis rangeAxis = new NumberAxis("Data" + i);
            rangeAxis.setAutoRangeIncludesZero(false);
            rangeAxis.setAutoRange(true);
            XYPlot subplot = new XYPlot(
                    datasets[i], null, rangeAxis, new StandardXYItemRenderer()
            );
            subplot.setBackgroundPaint(Color.white);
            subplot.setDomainGridlinePaint(Color.lightGray);
            subplot.setRangeGridlinePaint(Color.lightGray);
            plot.add(subplot);
        }

        JFreeChart chart = new JFreeChart("Data Chart", plot);
        chart.setBorderPaint(Color.lightGray);
        chart.setBorderVisible(true);
        chart.setBackgroundPaint(Color.white);

        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        ValueAxis domainAxis = plot.getDomainAxis();
        domainAxis.setAutoRange(true);
        domainAxis.setFixedAutoRange(300000.0);  // 300 seconds

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        frmArdulinkAnalogMonitor.getContentPane().add(chartPanel, BorderLayout.CENTER);
        
        // button to clear graph
        btnClearDataSet = new JButton("Clear Graph");
        btnClearDataSet.setEnabled(false);
        btnClearDataSet.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                datasets[0].getSeries(0).clear();
                datasets[1].getSeries(0).clear();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 1;
        controlPanel.add(btnClearDataSet, c);
        
        // Register a RawDataListener to receive data from Arduino.
        link.addRawDataListener(this);

    }

    @Override
    public void parseInput(String id, int numBytes, int[] message) {
        StringBuilder build = new StringBuilder(numBytes + 1);
        for (int i = 0; i < numBytes; i++) {
            build.append((char) message[i]);
        }
        String messageString = build.toString();
        if (messageString.startsWith("RAWMONITOR")) {
            // print the value on the screen
            System.out.println(messageString);
            messageString = messageString.substring("RAWMONITOR".length());
            String[] dataSplits = messageString.split("_");
            int data0 = Integer.parseInt(dataSplits[0]);
            int data1 = Integer.parseInt(dataSplits[1]);

            // plot new data
            datasets[0].getSeries(0).add(new Millisecond(), data0);
            datasets[1].getSeries(0).add(new Millisecond(), data1);

            // print new data value on the screen
            data0Value.setText(dataSplits[0]);
            data1Value.setText(dataSplits[1]);

            // save data to file
            if (fileLabel.getText() != "null") {
                try {
                    FileWriter fw = new FileWriter(sf.getAbsoluteFile(), true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    Calendar cal = Calendar.getInstance();
                    cal.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    bw.write(
                            sdf.format(cal.getTime()) + "\t" + data0 + "\t" + data1 + "\r\n"
                    );
                    bw.close();
                } catch (Exception e) {
                    // do nothing... TODO
                }
            }
        }
    }

}
