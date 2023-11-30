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
		
		public void testPinces(MoteurPinces moteurPinces) {
			// Test de la fermeture des pinces
			System.out.println("Fermeture des pinces...");
			moteurPinces.fermeture();


			// Test de l'ouverture des pinces
			System.out.println("Ouverture des pinces...");
			moteurPinces.ouverture();

			// Affichage de l'état des pinces après ouverture
			System.out.println("Les pinces sont-elles ouvertes ? " + moteurPinces.estOuvert());      
		}
		public void testVitesse(MoteurRoues moteurRoues) {
			System.out.println("Avance vite de 10 cm...");
			moteurRoues.setVitesse("rapide");
			moteurRoues.avancerT(100);
			
			System.out.println("Avance moyennement de 10 cm...");
			moteurRoues.setVitesse("moyen");
			moteurRoues.avancerT(100);

			
			System.out.println("Avance lentement de 10 cm...");
			moteurRoues.setVitesse("lent");
			moteurRoues.avancerT(100);

			
			System.out.println("Avance au mieux de 10 cm...");
			moteurRoues.setVitesse("best");
			moteurRoues.avancerT(100);
		}
		public void tourner(MoteurRoues moteurRoues){
			System.out.println("Tourne a droite de 90 degres...");
			moteurRoues.tourner(80.5, false);
			
			System.out.println("Tourne a gauche de 90 degres...");
			moteurRoues.tourner(-80.5, false);
		}
		public void distancePalet(TouchSensor touchS, UltraSonicSensor uSS) {
			System.out.println("Avance lentement de 10 cm...");
			
		}

	public static void main(String[] args) {
		run();
	}


}
