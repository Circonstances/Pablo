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
	private final static int MAX_DISTANCE = 2147483647;
	private static int nb_palets;

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
				boussolle += 180;
				etat = Etat.RecherchePalet;

			case RecherchePalet:
				roues.setVitesse("tour");
				roues.tourner(342, true);
				int dist = (int) ultrasonic.distance();
				Delay.msDelay(20);
				while (dist-ultrasonic.distance()<dist*0.9  && dist!=MAX_DISTANCE) {
					boussolle += 2;
					System.out.print("tjr pas, ");
					Delay.msDelay(20);
					dist = (int) ultrasonic.distance();
				}
				roues.stop();
				boussolle = boussolle%360;
				etat = Etat.PrendrePalet;

			case PrendrePalet:

			case RamenerPalet: 
				if(touchS.isPressed()==true){
					pinces.fermeture();
					roues.tourner(-boussolle*0.94,false);
					roues.setVitesse("rapide");
					roues.avancerT(10000);
					while (ultrasonic.distance()>20);
					roues.stop();
					pinces.ouverture();
					roues.reculer(200);
					roues.tourner(161, false);
					etat = Etat.RecherchePalet;
				}
				else {
					etat=Etat.RecherchePalet;
				}



				// trouve quelle bande blanche il veut dépasser (besoin de savoir sa position)
				// lacher le pallet , reculer et se tourner vers le mur le plus loin, avance jusq'uà dépasser la ligne blanche

			case Pause:

			case Fin:
			}
		}

	}



	public static void RecherchePalet(MoteurRoues roues) {
		//	roues.tourSurSoitM();
	}


	public boolean tourJusqua(MoteurRoues roues, UltraSonicSensor ultrasonic, char sens) {
		    float valeurActuelle = ultrasonic.distance(); // valeurActuelle comme "valeur ancienne"
		    Delay.msDelay(20);
		    float valeurRecente = ultrasonic.distance(); // valeurRecente comme "valeur récente"
		    //    
		    while ((valeurRecente > valeurActuelle || valeurRecente == valeurActuelle) && roues.isMoving()) {
		        valeurActuelle = valeurRecente;
		        valeurRecente = ultrasonic.distance();
		        if (valeurActuelle - valeurRecente > 5) {
		            roues.stop();
		            if (valeurRecente < 65) {
		                if (sens == 'd')
		                    roues.tourner(12, false);
		                else
		                    roues.tourner(-12, false);
		            }
		            return true;
		        }
		        if (valeurRecente < valeurActuelle) {
		            while ((valeurRecente < valeurActuelle || valeurRecente == valeurActuelle) && roues.isMoving()) {
		                if (valeurActuelle - valeurRecente > 5) {
		                    roues.stop();
		                    if (valeurRecente < 65) {
		                        if (sens == 'd')
		                            roues.tourner(12, false);
		                        else
		                            roues.tourner(-12, false);
		                    }
		                    return true;
		                }
		                System.out.println("boucle2");
		                valeurActuelle = valeurRecente;
		                valeurRecente = ultrasonic.distance();
		            }
		        }
		        Delay.msDelay(20);
		    }
		    return false;
		}
	
	

	/*
	 * Permet de faire un tour sur sot même en renvoyant un set de données.
	 */
/*	public static void tour(MoteurRoues roues, UltraSonicSensor ultrasonic, TouchSensor touch) {
		roues.setVitesse("tour");
		roues.tourner(330, true);
		int dist = (int) ultrasonic.distance();
		Delay.msDelay(20);
		while (dist-ultrasonic.distance()<dist*0.9  && dist!=MAX_DISTANCE && roues.isMoving()) {
			boussolle += 1;
			System.out.print("tjr pas, ");
			Delay.msDelay(15);
			dist = (int) ultrasonic.distance();
		}
		roues.stop();
		System.out.println(boussolle);
		//	roues.tourner(9.5, false);
		roues.avancerT(3000);
		while (ultrasonic.distance()>20 && !(touch.isPressed())) {
			Delay.msDelay(20);
		}
		roues.stop();
	}
*/

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
		Sound.beep();



		boussolle = 0;
		nb_palets = 9;
		System.out.println("PRESS ENTER");
		Button.ENTER.waitForPressAndRelease();
		tour(roues,ultrasonic, touch);



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







