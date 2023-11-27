package fr.uga.miashs.pablo;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.*;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Timer;

/**
 * La classe MoteurRoues représente un ensemble de moteurs pour contrôler les roues
 * d'un robot. Elle utilise la bibliothèque LeJOS pour la robotique.
 * Cette classe fournit des méthodes pour déplacer le robot en avant, en arrière,
 * et pour effectuer des rotations.
 * Elle permet également de régler la vitesse du robot en fonction de différents paramètres.
 * La classe utilise un châssis à deux roues de type différentiel.
 * 
 * @author pabloTeam
 * @version 1.0
 */
public class MoteurRoues {

	private EV3LargeRegulatedMotor portRoue1;
	private EV3LargeRegulatedMotor portRoue2;
	private Wheel roue1;
	private Wheel roue2;
	private Chassis chassis;
	private MovePilot pilot;

	/**
	 * Constructeur de la classe MoteurRoues.
	 * Initialise les moteurs, les roues, le châssis et le pilote.
	 * La vitesse initiale est définie en fonction du paramètre passé au constructeur.
	 *
	 * @param vitesse La vitesse initiale du robot, peut être "rapide", "moyen", "lent" ou "best".
	 */
	public MoteurRoues(String vitesse) {
		this.portRoue1 = new EV3LargeRegulatedMotor(MotorPort.D); // port roue droite
		this.portRoue2 = new EV3LargeRegulatedMotor(MotorPort.A); // port roue gauche
		this.roue1 = WheeledChassis.modelWheel(portRoue1, 56).offset(-70); // obtenir le diamètre des roues / la
		// distance au moteur des roues
		this.roue2 = WheeledChassis.modelWheel(portRoue2, 56).offset(70);
		this.chassis = new WheeledChassis(new Wheel[] { roue1, roue2 }, WheeledChassis.TYPE_DIFFERENTIAL); // type
		// différentiel
		// car elles
		// ont 2
		// roues
		this.pilot = new MovePilot(chassis);
		setVitesse(vitesse);
	}

	/**
	 * Définit la vitesse du robot en fonction du paramètre spécifié.
	 *
	 * @param v La chaîne représentant la vitesse souhaitée ("rapide", "moyen", "lent" ou "best").
	 */
	public void setVitesse(String v) {
		if (v.equals("rapide")) {
			pilot.setLinearAcceleration(300);
			pilot.setAngularAcceleration(2000);
			pilot.setAngularSpeed(1000);
		} else if (v.equals("moyen")) {
			pilot.setLinearAcceleration(150);
			pilot.setAngularAcceleration(1000);
		} else if (v.equals("lent")) {
			pilot.setLinearAcceleration(75);
			pilot.setAngularAcceleration(150);
		} else if (v.equals("best")) {
			pilot.setLinearAcceleration(120);
			pilot.setAngularAcceleration(400);
			pilot.setAngularSpeed(280);
		} else if (v.equals("tour")) {
			pilot.setAngularSpeed(35);
		}
	}

	/**
	 * Fait avancer le robot d'une certaine distance.
	 *
	 * @param distance La distance à parcourir en avant.
	 */
	public void avancerT(double distance) {
		pilot.travel(distance, true);
	}
	
	public void avancerF(double distance) {
		pilot.travel(distance, false);
	}

	/**
	 * Fait reculer le robot d'une certaine distance.
	 *
	 * @param distance La distance à parcourir en arrière.
	 */
	public void reculer(double distance) {
		pilot.travel(-distance, false);
	}

	/**
	 * Fait tourner le robot d'un certain angle.
	 *
	 * @param angle L'angle de rotation en degrés.
	 */
	public void tourner(double angle, boolean bool) {
		pilot.rotate(angle, bool);
	}

	public MovePilot getPilot() {
		return pilot;
	}

	public void stop() {
		pilot.stop();
	}
	
	public boolean isMoving() {
		return pilot.isMoving();
	}


}
