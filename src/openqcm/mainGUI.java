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

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.zu.ardulink.Link;
import org.zu.ardulink.RawDataListener;

/**
 *
 * @author Marco
 */
public class mainGUI extends javax.swing.JFrame implements RawDataListener {

    // the COM port connected to Arduino
    private String portCOM;
    // Ardulink link class for communication and connection with Arduino
    private final Link link = Link.getDefaultInstance();
    // Store file for data recording
    File sf;
    // Write file for data recording
    private FileWriter fw;
    // size of circular buffer
    private final int bufferSize = 10;
    // frequency array circular buffer
    ArrayCircularBuffer bufferFrequency = new ArrayCircularBuffer(bufferSize);
    // temperauture circular buffer
    ArrayCircularBuffer bufferTemperature = new ArrayCircularBuffer(bufferSize);

    /**
     * Creates new form mainGUI
     */
    public mainGUI() {
        java.net.URL url = getClass().getResource("concept-logo-RGB-QUA.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        this.setIconImage(img);
        appInit();
        initComponents();
        // Register a RawDataListener to receive data from Arduino.
        link.addRawDataListener(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelBottom = new javax.swing.JPanel();
        saveFileBtn = new javax.swing.JToggleButton();
        startBtn = new javax.swing.JToggleButton();
        titleJTextField = new javax.swing.JTextField();
        jPanelChart = new javax.swing.JPanel();
        chartData = new openqcm.ChartDynamicData();
        logoImage = new javax.swing.JLabel();
        clearChartBtn = new javax.swing.JButton();
        showTemperatureBtn = new javax.swing.JToggleButton();
        frequencyCurrent = new javax.swing.JFormattedTextField();
        temperatureCurrent = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("openQCM 0.2");
        setIconImages(null);
        setMinimumSize(new java.awt.Dimension(720, 500));
        setName("applicationFrame"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanelBottom.setMinimumSize(new java.awt.Dimension(341, 52));

        saveFileBtn.setText("Save File");
        saveFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileBtnActionPerformed(evt);
            }
        });

        startBtn.setText("Connect");
        startBtn.setMaximumSize(new java.awt.Dimension(60, 32));
        startBtn.setMinimumSize(new java.awt.Dimension(60, 32));
        startBtn.setPreferredSize(new java.awt.Dimension(60, 32));
        startBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startBtnActionPerformed(evt);
            }
        });

        titleJTextField.setEditable(false);
        titleJTextField.setBackground(new java.awt.Color(220, 220, 220));
        titleJTextField.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        titleJTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        titleJTextField.setText("QCM Data Chart");
        titleJTextField.setBorder(null);

        javax.swing.GroupLayout jPanelBottomLayout = new javax.swing.GroupLayout(jPanelBottom);
        jPanelBottom.setLayout(jPanelBottomLayout);
        jPanelBottomLayout.setHorizontalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBottomLayout.createSequentialGroup()
                .addComponent(saveFileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(titleJTextField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelBottomLayout.setVerticalGroup(
            jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBottomLayout.createSequentialGroup()
                .addGroup(jPanelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(titleJTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(saveFileBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanelChart.setBackground(new java.awt.Color(0, 142, 192));

        logoImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/openqcm/openQCM-logo.png"))); // NOI18N
        logoImage.setText("jLabel1");

        clearChartBtn.setText("Clear");
        clearChartBtn.setBorder(null);
        clearChartBtn.setBorderPainted(false);
        clearChartBtn.setOpaque(false);
        clearChartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearChartBtnActionPerformed(evt);
            }
        });

        showTemperatureBtn.setText("Show");
        showTemperatureBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showTemperatureBtnActionPerformed(evt);
            }
        });

        frequencyCurrent.setEditable(false);
        frequencyCurrent.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        frequencyCurrent.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        frequencyCurrent.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        temperatureCurrent.setEditable(false);
        temperatureCurrent.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        temperatureCurrent.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        temperatureCurrent.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Clear Chart");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Temperature Chart");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Frequency (Hz)");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Temperature (°C)");

        javax.swing.GroupLayout jPanelChartLayout = new javax.swing.GroupLayout(jPanelChart);
        jPanelChart.setLayout(jPanelChartLayout);
        jPanelChartLayout.setHorizontalGroup(
            jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChartLayout.createSequentialGroup()
                .addGroup(jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelChartLayout.createSequentialGroup()
                        .addComponent(logoImage, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clearChartBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(showTemperatureBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(frequencyCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(temperatureCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelChartLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(chartData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelChartLayout.setVerticalGroup(
            jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelChartLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelChartLayout.createSequentialGroup()
                        .addGroup(jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(1, 1, 1)
                        .addGroup(jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clearChartBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(showTemperatureBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(frequencyCurrent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(temperatureCurrent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(logoImage, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chartData, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // add action for save data into file
    private void saveFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveFileBtnActionPerformed

        // if th button is pressed
        if (saveFileBtn.isSelected() == true) {
            saveFileBtn.setText("Stop Save");
            // open a file chooser
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Text Files", "txt", "dat"
            );
            chooser.setFileFilter(filter);
            int option = chooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                sf = chooser.getSelectedFile();
                saveFileBtn.setText("Stop Save");
                titleJTextField.setText(sf.getName() + " - QCM Data Chart");
                //saveFile = true;
                //jFormattedTextField3.setText(sf.getName());

            } else {
                JOptionPane.showMessageDialog(
                        null, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
                saveFileBtn.setText("Save File");
                saveFileBtn.setSelected(false);
                titleJTextField.setText("QCM Data Chart");
            }
        } // if the button is released
        else if (saveFileBtn.isSelected() == false) {
            saveFileBtn.setText("Save File");
            titleJTextField.setText("QCM Data Chart");
        }

    }//GEN-LAST:event_saveFileBtnActionPerformed

    private void clearChartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearChartBtnActionPerformed
        chartData.clearChart();
    }//GEN-LAST:event_clearChartBtnActionPerformed

    private void showTemperatureBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showTemperatureBtnActionPerformed
        if (showTemperatureBtn.isSelected() == true) {
            showTemperatureBtn.setText("Hide");
        } else if (showTemperatureBtn.isSelected() == false) {
            showTemperatureBtn.setText("Show");
        }
    }//GEN-LAST:event_showTemperatureBtnActionPerformed

    // add action for serial connection event
    private void startBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startBtnActionPerformed

        if (startBtn.isSelected() == true) {
            // open the popup frame for serial connection
            SerialConnectionJDialog dlg = new SerialConnectionJDialog(
                    new JFrame(), "Arduino Connection", "Select the COM port");
            portCOM = dlg.port;
            if (portCOM == null || "".equals(portCOM)) {
                JOptionPane.showMessageDialog(
                        null, "Invalid COM PORT setted.", "Error", JOptionPane.ERROR_MESSAGE);
                startBtn.setSelected(false);
            } else {
                try {
                    // Connect to the Arduino board
                    link.connect(portCOM);
                    startBtn.setText("Disconnect");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    String message = ex.getMessage();
                    if (message == null || message.trim().equals("")) {
                        message = "Generic Error on connection";
                    }
                    JOptionPane.showMessageDialog(
                            null, message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } else if (startBtn.isSelected() == false) {
            boolean disconnected = link.disconnect();
            chartData.clearChart();
            startBtn.setText("Connect");
        }

    }//GEN-LAST:event_startBtnActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        java.net.URL url = getClass().getResource("openQCM-icon-30x30.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        this.setIconImage(img);
    }//GEN-LAST:event_formWindowOpened

    // add a dumb delay to show splashscreen wait 5 second
    private static void appInit()
    {
        for(int i=1;i<=3;i++)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException ex)
            {
                // ignore it
            }
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private openqcm.ChartDynamicData chartData;
    private javax.swing.JButton clearChartBtn;
    private javax.swing.JFormattedTextField frequencyCurrent;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelChart;
    private javax.swing.JLabel logoImage;
    private javax.swing.JToggleButton saveFileBtn;
    private javax.swing.JToggleButton showTemperatureBtn;
    private javax.swing.JToggleButton startBtn;
    private javax.swing.JFormattedTextField temperatureCurrent;
    private javax.swing.JTextField titleJTextField;
    // End of variables declaration//GEN-END:variables

    /* implements RawDataListener 
     * for reading and storing frequency and temperaure data
     */
    @Override
    public void parseInput(String id, int numBytes, int[] message) {
        // read the message
        StringBuilder build = new StringBuilder(numBytes + 1);
        for (int i = 0; i < numBytes; i++) {
            build.append((char) message[i]);
        }
        String messageString = build.toString();

        // if the message starts with the string "RAWMONITOR" display and store data
        if (messageString.startsWith("RAWMONITOR")) {
            // print the value on the screen
            System.out.println(messageString);
            messageString = messageString.substring("RAWMONITOR".length());
            String[] dataSplits = messageString.split("_");
            int dataFrequency = (int) (Integer.parseInt(dataSplits[0]));
            int dataTemperature = Integer.parseInt(dataSplits[1]);

            // insert new frequency data in circuar buffer and calculate the mean value
            bufferFrequency.insert(dataFrequency);
            double sum = 0;
            for (int i = 0; i < bufferFrequency.size(); i++) {
                sum = sum + (int) bufferFrequency.data[i];
            }
            double meanFrequency = sum / bufferFrequency.size();

//            Median implemented TODO 
//            int count = buffer.size();
//            double values [] = new double [count];
//            for (int i = 0; i < count; i++) values[i] = (int) (buffer.data[i]);
//            Median median = new Median();
//            double average = median.evaluate(values);
            
            // insert new Temperature data in circuar buffer and calculate the mean value
            bufferTemperature.insert(dataTemperature);
            double sumT = 0;
            for (int i = 0; i < bufferTemperature.size(); i++) {
                sumT = sumT + (int) bufferTemperature.data[i];
            }
            double meanTemperature = sumT / bufferTemperature.size();
            
            // display data
            frequencyCurrent.setText(String.format("%.1f", meanFrequency));
            temperatureCurrent.setText(String.format("%.1f", meanTemperature));

            // chart new frequency data in dynamic chart
            chartData.addFrequencyData(meanFrequency);
            
            // chart new temperature data in dynamic chart
            if (showTemperatureBtn.isSelected() == true) {
                chartData.addTemperatureData(meanTemperature);
            }

            // store data 
            if (saveFileBtn.isSelected() == true) {
                try {
                    fw = new FileWriter(sf.getAbsoluteFile(), true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    Calendar cal = Calendar.getInstance();
                    cal.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YY" + "\t" + "HH:mm:ss");
                    bw.write(
                            sdf.format(cal.getTime()) + "\t" 
                            + String.format("%.1f", meanFrequency)  + "\t" 
                            + String.format("%.1f", meanTemperature) + "\r\n"
                    );
                    bw.close();
                } catch (Exception e) {
                    // do nothing... TODO
                }

            }

        }
    }
}
