package fr.uga.miashs.pablo;

import lejos.utility.Timer;

import lejos.hardware.*;
import lejos.utility.Delay;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;


public class Main {

	private static int boussolle;

	public enum Etat {
		Debut,
		RecherchePalet,
		PrendrePalet,
		RamenerPalet,
		Pause,
		Fin
	}

		public static void run(MoteurRoues roues, MoteurPinces pinces, TouchSensor touchS, UltraSonicSensor ultrasonic) {

			Etat etat = Etat.Debut;

		while(true) {
			Button.ENTER.waitForPressAndRelease();
			switch(etat) {
			case Debut :
					roues.setVitesse("rapide");
				roues.avancerF(500);
				pinces.fermeture();
				roues.tourner(80.5, false);
				roues.setVitesse("moyen");
				roues.avancerF(300);
				roues.tourner(-75, false);
				roues.setVitesse("rapide");
				roues.avancerT(10000);
				while (ultrasonic.distance()>20);
				roues.stop();
				pinces.ouverture();
				roues.reculer(200);
				roues.tourner(161, false);
				etat = Etat.RecherchePalet;

			case RecherchePalet:
					
				// tourne sur lui même pour chercher un palet, quand détecte passe à l'état d'après
			case RamenerPalet:
				// trouve quelle bande blanche il veut dépasser (besoin de savoir sa position)
				// lacher le pallet , reculer et se tourner vers le mur le plus loin, avance jusq'uà dépasser la ligne blanche
			case PrendrePalet:
				// tourne de l'angle, avance vers le palet de d, s'arrête quand touch true & ferme pince
			case Pause:

			case Fin:
			}
		}

		}



	public static void RecherchePalet(MoteurRoues roues) {
		//	roues.tourSurSoitM();
	}


	public static int[] tourSurSoitM(MoteurRoues r, UltraSonicSensor s) {
		r.setVitesse("tour");
		r.tourner(-360 ,true); //tour sur soit même 
		int tab [] = new int [200];
		Delay.msDelay(65);
		for (int i= 0 ; i < tab.length ; i++) { 
			tab[i] = (int)s.distance(); 
			Delay.msDelay(7);
		}
		return tab;
	}




	/*
	 * Permet de faire un tour sur sot même en renvoyant un set de données.
	 */
	public static void tour(MoteurRoues roues, UltraSonicSensor ultrasonic) {
		int [] t = tourSurSoitM(roues, ultrasonic);
		for (int i = 0 ; i < t.length ; i++) {
			System.out.print(t[i]+" ,");
		}

		System.out.println("NB DE VALER" + t.length); 

		System.out.println(ultrasonic.distance());
	}


	/*public int premierPalet(int [] tab) {
		for (int i = 1 ; i < tab.length-1 ; i++) {
			if (tab[i]-tab[i-1] < -15 && tab[i]-tab[i-1] > -70) {

			}
		}
		return -1;
	}*/
	/*
	 * public enum Etat { Debut, RecherchePalet, RamenerPalet, PrendrePalet, Fin,
	 * Pause }
	 */
	public static void main(String[] args) {
		UltraSonicSensor ultrasonic = new UltraSonicSensor(SensorPort.S2);
		ColorSensor color = new ColorSensor(SensorPort.S4);
		TouchSensor touch = new TouchSensor(SensorPort.S1);
		MoteurPinces pinces = new MoteurPinces();
		MoteurRoues roues = new MoteurRoues("tour");
		roues.avancerF(200);
		boussolle = 0;
		System.out.println("PRESS ENTER");
		Button.ENTER.waitForPressAndRelease();

		tour(roues,ultrasonic);


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







