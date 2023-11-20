package fr.uga.miashs.pablo;

import lejos.utility.Timer;
import lejos.hardware.*;
import lejos.utility.Delay;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;


public class TestNoGIT {
	
	public static void run() {
		UltraSonicSensor ultrasonic = new UltraSonicSensor(SensorPort.S2);
		ColorSensor color = new ColorSensor(SensorPort.S4);
		TouchSensor touch = new TouchSensor(SensorPort.S1);
		MoteurPinces pinces = new MoteurPinces();
		MoteurRoues roues = new MoteurRoues("best");
		System.out.println("Hello");
	}

	public static void main(String[] args) {
		run();
	}

}
