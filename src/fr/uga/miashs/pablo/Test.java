package fr.uga.miashs.pablo;

import lejos.hardware.*;
import lejos.utility.Delay;
import lejos.hardware.port.SensorPort;

/**
 * Cette classe représente un test pour le robot LEGO EV3 avec le framework LEJOS.
 * Elle initialise différents capteurs et moteurs, puis effectue des tests pour
 * vérifier leur bon fonctionnement.
 */
public class Test {

	/**
	 * Méthode principale pour exécuter le test.
	 */
	public static void run() {
		// Initialisation des capteurs et des moteurs
		UltraSonicSensor ultrasonic = new UltraSonicSensor(SensorPort.S2);
		ColorSensor color = new ColorSensor(SensorPort.S4);
		TouchSensor touch = new TouchSensor(SensorPort.S1);
		MoteurPinces pinces = new MoteurPinces();
		MoteurRoues roues = new MoteurRoues("best");

		// Message de base | Mettre les méthodes à tester juste en dessous
		System.out.println("Hello");
	}

	/**
	 * Méthode de test pour les pinces du robot.
	 *
	 * @param moteurPinces Instance du moteur de pinces à tester.
	 */
	public void testPinces(MoteurPinces moteurPinces) {
		// Test de la fermeture des pinces
		System.out.println("PRESS ENTER pour fermeture des pinces");
		Button.ENTER.waitForPress();
		System.out.println("Fermeture des pinces...");
		moteurPinces.fermeture();

		// Test de l'ouverture des pinces
		System.out.println("PRESS ENTER pour ouverture des pinces");
		Button.ENTER.waitForPress();
		System.out.println("Ouverture des pinces...");
		moteurPinces.ouverture();

		// Affichage de l'état des pinces après ouverture
		System.out.println("Les pinces sont-elles ouvertes ? " + moteurPinces.getEstOuvert());      
	}

	/**
	 * Méthode de test pour la vitesse des roues du robot.
	 *
	 * @param moteurRoues Instance du moteur de roues à tester.
	 */
	public void testVitesse(MoteurRoues moteurRoues) {
		// Tests d'avance à différentes vitesses
		System.out.println("PRESS ENTER pour tests de vitesse");
		Button.ENTER.waitForPress();
		moteurRoues.setVitesse("rapide");
		moteurRoues.avancerT(100);

		moteurRoues.setVitesse("moyen");
		moteurRoues.avancerT(100);

		moteurRoues.setVitesse("lent");
		moteurRoues.avancerT(100);

		moteurRoues.setVitesse("best");
		moteurRoues.avancerT(100);
	}

	/**
	 * Méthode de test pour la rotation des roues du robot.
	 *
	 * @param moteurRoues Instance du moteur de roues à tester.
	 */
	public void tourner(MoteurRoues moteurRoues){
		// Tests de rotation à droite et à gauche
		System.out.println("PRESS ENTER pour tour à droite de 90°");
		Button.ENTER.waitForPress();
		moteurRoues.tourner(80.5, false);


		System.out.println("PRESS ENTER pour tour à gauche de 90°");
		Button.ENTER.waitForPress();
		moteurRoues.tourner(-80.5, false);
	}

	/**
	 * Méthode de test pour la mesure de distance avec le capteur ultrasonique.
	 *
	 * @param uSS Instance du capteur ultrasonique à tester.
	 */
	public void distancePalet(UltraSonicSensor uSS) {
		// Attend que le bouton ENTER soit pressé avant de mesurer la distance
		System.out.println("PRESS ENTER quand le test ultrasonic est prêt");
		Button.ENTER.waitForPress();
		System.out.println("La distance est de :" + uSS.distance());
	}

	/**
	 * Méthode de test pour le capteur tactile.
	 *
	 * @param touchS Instance du capteur tactile à tester.
	 */
	public void touchSensor(TouchSensor touchS) {
		// Attend que le bouton ENTER soit pressé avant de tester le capteur tactile
		System.out.println("PRESS ENTER quand le test doit commencer");
		System.out.println("Quand cela sera pressé, vous pourrez appuyer sur le touchSensor");
		Button.ENTER.waitForPress();

		// Attend que le capteur tactile soit pressé
		while (!touchS.isPressed()) {
			// Attend 100ms avant de vérifier à nouveau
			Delay.msDelay(100);
			System.out.println("Toujours pas pressé");
		}

		System.out.println("TouchSensor est bien pressé");
	}

	/**
	 * Méthode principale pour exécuter le test.
	 *
	 * @param args Les arguments de la ligne de commande (non utilisés ici).
	 */
	public static void main(String[] args) {
		run();
	}
}
