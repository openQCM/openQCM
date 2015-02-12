#openQCM Software Java#

![openQCM-design](https://dl.dropboxusercontent.com/u/51833595/openQCM-splashscreen.png)

[openQCM](http://openqcm.com/) is the unique opensource quartz crystal microbalance

##Intro##
This is the latest release of openQCM Java software version 1.0 stable version. The repository is designed  for the developers and contributors of the open source project.
The openQCM software is developed using Java 8 programming language and NetBeans development environment (IDE). You need to configure your Java Runtime Environment JRE to run the project correctly.

openQCM sw uses the RXTX Java library which provides serial communication for the Java Development Kit JDK. Refer to [Arduino Playground](http://playground.arduino.cc/Interfacing/Java) for more info about Arduino and Java interfacing. 

The communication protocol of openQCM sw is based on [Ardulink](http://www.ardulink.org/), the open source Java solution for the control and coordination of Arduino boards. 

The users of openQCM software can directly download the executable program on the openQCM website. 

## openQCM v0.2 ##
![openQCM-GUI](https://dl.dropboxusercontent.com/u/51833595/openQCM_main_GUI_signal.PNG)

openQCM Java software v0.2 application user interface GUI.

The **openQCM GUI** is designed to accomplish the following tasks:

1. **Connect Arduino** find the COM port and set the serial connection between the Arduino board and your PC.
2. **Read Data** read the QCM frequency and temperature data available at the connection COM port.
3. **Plot Data** plot the QCM frequency and temperature data using a real time chart.
4. **Save Data** save the frequency and temperature data in a text file. 

For detailed description of openQCM software features go to the openQCM website.

##License##
openQCM ia free software licensed under [GNU GPL v3.0](http://www.gnu.org/licenses/gpl-3.0.txt) General Public License 

##Info##
Follow the [wiki](https://github.com/marcomauro/openQCM/wiki/openQCM-Wiki) for more info and latest update on openQCM software.

##Acknowledgements##
- [Novaetech S.r.l.](http://www.novaetech.it/en/) for supporting and powering the openQCM project. 
- Luciano Zu for developing [Ardulink](http://www.ardulink.org/) and contributing to the this release of openQCM application.
- Glenda Torres Guizado for the design of the software user interface
