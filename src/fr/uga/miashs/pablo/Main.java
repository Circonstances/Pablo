package fr.uga.miashs.pablo;

import lejos.utility.Timer;

import lejos.hardware.*;
import lejos.utility.Delay;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;


public class Main {

	public enum Etat {
		Debut,
		RecherchePalet,
		PrendrePalet,
		RamenerPalet,
		Pause,
		Fin
	}

	public static void run(MoteurRoues moteurR, MoteurPinces moteurP, TouchSensor touchS) {

/*		Etat etat = etat.Debut;

		while(true) {
			Button.ENTER.waitForPressAndRelease();
			switch(etat) {
			case Debut :
				moteurR.setVitesse("rapide");
				moteurR.avancer(556);
				moteurR.setVitesse("lent");
				moteurR.avancer(51);
				while(touchS.isPressed()) {
					moteurR.avancer(500);
				}
				moteurP.fermeture();
				moteurR.tourner(80.5);
				moteurR.setVitesse("moyen");
				moteurR.avancer(300);
				moteurR.tourner(-80.5);
				moteurR.setVitesse("rapide");
				while () { //tant que il n'a pas dépassé la ligne blanche
					moteurR.avancer();
				}
				moteurR.avancer(150);
				moteurP.ouverture();
				while() { // recule jusqu'a qu'il capte ligne blanche
					moteurR.reculer();
				}
				moteurR.tourner(161);
				// tourne sur lui même pour chercher un palet, quand détecte passe à l'état d'après
			case RamenerPalet:
				// trouve quelle bande blanche il veut dépasser (besoin de savoir sa position)
				// lacher le pallet , reculer et se tourner vers le mur le plus loin, avance jusq'uà dépasser la ligne blanche
			case PrendrePalet:
				// tourne de l'angle, avance vers le palet de d, s'arrête quand touch true & ferme pince
			case Pause:

			case Fin:
			}
		}*/

	}



	public static void RecherchePalet(MoteurRoues moteurR) {
	//	moteurR.tourSurSoitM();
	}
	
	public static float[] tourSurSoitM(MoteurRoues r, UltraSonicSensor s) {
		r.tourner(322); //tour sur soit même 
		float tab [] = new float [50];
		for (int i= 0 ; i < tab.length ; i++) { 
			tab[i] = s.distance(); 
			Delay.msDelay(10);
		}
		return tab;
	}

	/*
	 * public enum Etat { Debut, RecherchePalet, RamenerPalet, PrendrePalet, Fin,
	 * Pause }
	 */
	public static void main(String[] args) {
		UltraSonicSensor ultrasonic = new UltraSonicSensor(SensorPort.S2);
		ColorSensor color = new ColorSensor(SensorPort.S4);
		TouchSensor touch = new TouchSensor(SensorPort.S1);
		MoteurPinces pinces = new MoteurPinces();
		MoteurRoues roues = new MoteurRoues("best");
		Button.ENTER.waitForPress();
		float [] t = tourSurSoitM(roues, ultrasonic);
		for (int i = 0 ; i < t.length ; i++) {
			System.out.print(t[i]+" ,");
		}
		/*
		 * while(true) { Button.ENTER.waitForPressAndRelease(); switch(etat) { case
		 * Debut:
		 * 
		 * case RecherchePalet:
		 * 
		 * case RamenerPalet:
		 * 
		 * case PrendrePalet:
		 * 
		 * case Pause:
		 * 
		 * case Fin: }
		 * 
		 */
	}
}







