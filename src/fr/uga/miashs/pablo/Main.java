/**
 * La classe Main représente la logique de contrôle principale pour un robot utilisant la
 * bibliothèque LeJOS sur la plateforme LEGO Mindstorms EV3. Le robot est conçu pour effectuer
 * des tâches telles que la recherche et la récupération de palets à l'aide de moteurs, de capteurs
 * et d'actionneurs.
 *
 * @author pabloTeam
 * @version finale
 */

package fr.uga.miashs.pablo;

import lejos.utility.Timer;

import lejos.hardware.*;
import lejos.utility.Delay;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;


/**
 * La classe principale qui définit le comportement du robot dans différents états.
 */

public class Main {

	private static int boussolle;
	private final static int MAX_DISTANCE = 2147483647;
	private static int nb_palets;

	/**
	 * Énumération représentant les différents états possibles du robot.
	 */

	public enum Etat {
		Debut,
		RecherchePalet,
		PrendrePalet,
		RamenerPalet,
		Pause,
		Fin
	}

	/**
	 * Méthode principale qui contrôle le comportement du robot en fonction de son état actuel.
	 *
	 * @param roues      MoteurRoues pour le contrôle des roues.
	 * @param pinces     MoteurPinces pour le contrôle des pinces.
	 * @param touchS     TouchSensor pour détecter l'appui.
	 * @param ultrasonic UltraSonicSensor pour mesurer la distance.
	 */

	public static void run(MoteurRoues roues, MoteurPinces pinces, TouchSensor touchS, UltraSonicSensor ultrasonic) {

		Etat etat = Etat.Debut;

		while(true) {
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
				roues.tourner(70, false);
				boussolle += 70;
				etat = Etat.RecherchePalet;

			case RecherchePalet:
				roues.setVitesse("tour");
				roues.tourner(170, true);
				boolean trouve = tourJusqua(roues, ultrasonic, boussolle);
				Delay.msDelay(20);
				if (trouve) {
					boussolle %= 340;
					System.out.print("trouvé, va prendre le palet");
					etat = Etat.PrendrePalet;
					break;
				}
				//Le cas du else n'a pas été fait, ce que nous aurions voulu faire est de remettre le robot en face de nos cages, de le faire avancer vers le centre puis de lui refaire faire un tour pour chercher un palet.

			case PrendrePalet:
				int dis=(int) ultrasonic.distance();
				while (touchS.isPressed()==false && dis<(int) ultrasonic.distance()) {
					roues.avancerT(10);
					dis =(int) ultrasonic.distance();
				}
				etat=Etat.PrendrePalet;

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
				else { //Dans ce cas nous aurions du réaliser un check en avançant un petit peu pour voir si le palet était presque en contact avec le touchSensor
					etat=Etat.RecherchePalet;
				}
				//non utilisé, la pause est la méthode escape();
			case Pause:
				// non fini, aurait été le cas où nb_palets == 0
			case Fin:
			}
		}

	}


	/**
	 * Méthode pour effectuer un tour continu jusqu'à détecter une anomalie (palet ou robot)
	 * dans les données recueillies, plus rapide que de faire un tour entier sur soi-même puis
	 * d'analyser les données.
	 *
	 * @param roues      MoteurRoues pour le contrôle des roues.
	 * @param ultrasonic UltraSonicSensor pour mesurer la distance.
	 * @param boussolle  La boussole pour suivre l'orientation.
	 * @return true si une anomalie est détectée, sinon false.
	 */
	public static boolean tourJusqua(MoteurRoues roues, UltraSonicSensor ultrasonic, int boussolle) {
		float valeurActuelle = ultrasonic.distance();
		Delay.msDelay(20);
		float valeurRecente = ultrasonic.distance();
		while ((valeurRecente > valeurActuelle || valeurRecente == valeurActuelle) && roues.isMoving()) {
			valeurActuelle = valeurRecente;
			valeurRecente = ultrasonic.distance();
			boussolle += 1;
			if (valeurActuelle - valeurRecente > 5) {
				roues.stop();
				if (valeurRecente < 65) roues.tourner(12, false);
				return true;
			}
			if (valeurRecente < valeurActuelle) {
				while ((valeurRecente < valeurActuelle || valeurRecente == valeurActuelle) && roues.isMoving()) {
					boussolle += 1;
					if (valeurActuelle - valeurRecente > 5) {
						roues.stop();
						if (valeurRecente < 65) roues.tourner(12, false);
						return true;
					}
					valeurActuelle = valeurRecente;
					valeurRecente = ultrasonic.distance();
				}
			}
			Delay.msDelay(20);
		}
		return false; //si échec de la méthode // cas supposé où les palets ne sont pas assez proche ou pas trouvé
	}


	/**
	 * Méthode pour gérer l'événement de pression du bouton d'échappement pour terminer le programme.
	 */

	public static void escape() {
		BrickFinder.getDefault().getKey(Button.ESCAPE.getName()).addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(Key k) {
				System.exit(0);
			}
			@Override
			public void keyPressed(Key k) {}
		});
	}

	/**
	 * Méthode principale qui initialise les capteurs, les moteurs, et lance l'exécution du robot.
	 *
	 * @param args Les arguments de la ligne de commande (non utilisés).
	 */

	public static void main(String[] args) {
		UltraSonicSensor ultrasonic = new UltraSonicSensor(SensorPort.S2);
		ColorSensor color = new ColorSensor(SensorPort.S4);
		TouchSensor touch = new TouchSensor(SensorPort.S1);
		MoteurPinces pinces = new MoteurPinces();
		MoteurRoues roues = new MoteurRoues("tour");
		Sound.beep(); //bip pour savoir quand le robot peut être lancé
		boussolle = 0;
		nb_palets = 9;
		escape();
		System.out.println("PRESS ENTER");
		Button.ENTER.waitForPressAndRelease();
		run(roues, pinces, touch, ultrasonic);

	}



	/*	Ancienne méthode dans le même registre que celle utilisée pour le tournois, elle tourne sur elle même jusqu'à trouver une anomalie, elle fonctionnait mais pas à chaque fois nous avons donc décider de changer de méthode
	 * 
	 * Permet de faire un tour sur sot même en renvoyant un set de données.

	public static void tour(MoteurRoues roues, UltraSonicSensor ultrasonic, TouchSensor touch) {
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

	}*/
}
