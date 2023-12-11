package fr.uga.miashs.pablo;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.*;
import lejos.robotics.navigation.MovePilot;

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
        this.chassis = new WheeledChassis(new Wheel[] { roue1, roue2 }, WheeledChassis.TYPE_DIFFERENTIAL);
        this.pilot = new MovePilot(chassis);
        setVitesse(vitesse);
    }

    /**
     * Définit la vitesse du robot en fonction du paramètre spécifié.
     * 
     * Certaines vitesses ne sont pas utilisées dans le code du main, elles sont présentes en tant que potentielle vitesse à utiliser.
     *
     * @param v La chaîne représentant la vitesse souhaitée ("rapide", "moyen", "lent" ou "best").
     */
    public void setVitesse(String v) {
        switch (v) {
            case "rapide":
                pilot.setLinearAcceleration(300);
                pilot.setAngularAcceleration(2000);
                pilot.setAngularSpeed(1000);
                break;
            case "moyen":
                pilot.setLinearAcceleration(150);
                pilot.setAngularAcceleration(1000);
                break;
            case "lent":
                pilot.setLinearAcceleration(75);
                pilot.setAngularAcceleration(150);
                break;
            case "best":
                pilot.setLinearAcceleration(120);
                pilot.setAngularAcceleration(400);
                pilot.setAngularSpeed(280);
                break;
            case "tour":
                pilot.setAngularSpeed(35);
                break;
            default:
                break;
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

    /**
     * Fait avancer le robot d'une certaine distance sans attendre la fin du déplacement.
     *
     * @param distance La distance à parcourir en avant.
     */
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
     * @param bool  Booléen pour indiquer si la rotation doit être continue.
     */
    public void tourner(double angle, boolean bool) {
        pilot.rotate(angle, bool);
    }

    /**
     * Obtient l'instance du pilote de déplacement du robot.
     *
     * @return L'instance du pilote de déplacement.
     */
    public MovePilot getPilot() {
        return pilot;
    }

    /**
     * Arrête le mouvement du robot.
     */
    public void stop() {
        pilot.stop();
    }

    /**
     * Vérifie si le robot est en mouvement.
     *
     * @return true si le robot est en mouvement, sinon false.
     */
    public boolean isMoving() {
        return pilot.isMoving();
    }
}