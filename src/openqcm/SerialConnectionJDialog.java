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
import static java.awt.BorderLayout.SOUTH;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import org.zu.ardulink.gui.SerialConnectionPanel;

/**
 *
 * @author Marco
 */
public class SerialConnectionJDialog extends JDialog implements ActionListener {
    // Arddulink swing component for serial connection
    private final SerialConnectionPanel serialConnectionPanel;
    // Serial COM port connected to Arduino
    public String port = null;

    public SerialConnectionJDialog(JFrame parent, String title, String message) {
        super(parent, title, true);

        // message panel
        JPanel messagePane = new JPanel();
        messagePane.add(new JLabel(message));
        getContentPane().add(messagePane, BorderLayout.NORTH);
        JPanel buttonPane = new JPanel();
        
        // show the Ardulink swing component fo serial connection
        serialConnectionPanel = new SerialConnectionPanel();
        // hide the baud rate setting
        serialConnectionPanel.setBaudRateVisible(false);
        getContentPane().add(serialConnectionPanel, BorderLayout.CENTER);

        // add dialog button
        JButton button = new JButton("OK");
        button.addActionListener(this);
        buttonPane.add(button);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setSize(300, 150);
        // display at the centre of the screen
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // find the COM port 
        port = serialConnectionPanel.getConnectionPort();
        setVisible(false);
        dispose();
    }
}
