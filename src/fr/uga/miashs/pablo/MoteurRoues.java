package fr.uga.miashs.pablo;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.*;
import lejos.robotics.navigation.MovePilot;

/**
 * La classe MoteurRoues repr�sente un ensemble de moteurs pour contr�ler les roues
 * d'un robot. Elle utilise la biblioth�que LeJOS pour la robotique.
 * Cette classe fournit des m�thodes pour d�placer le robot en avant, en arri�re,
 * et pour effectuer des rotations.
 * Elle permet �galement de r�gler la vitesse du robot en fonction de diff�rents param�tres.
 * La classe utilise un ch�ssis � deux roues de type diff�rentiel.
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
     * Initialise les moteurs, les roues, le ch�ssis et le pilote.
     * La vitesse initiale est d�finie en fonction du param�tre pass� au constructeur.
     *
     * @param vitesse La vitesse initiale du robot, peut �tre "rapide", "moyen", "lent" ou "best".
     */
    public MoteurRoues(String vitesse) {
        this.portRoue1 = new EV3LargeRegulatedMotor(MotorPort.D); // port roue droite
        this.portRoue2 = new EV3LargeRegulatedMotor(MotorPort.A); // port roue gauche
        this.roue1 = WheeledChassis.modelWheel(portRoue1, 56).offset(-70); // obtenir le diam�tre des roues / la
        // distance au moteur des roues
        this.roue2 = WheeledChassis.modelWheel(portRoue2, 56).offset(70);
        this.chassis = new WheeledChassis(new Wheel[] { roue1, roue2 }, WheeledChassis.TYPE_DIFFERENTIAL);
        this.pilot = new MovePilot(chassis);
        setVitesse(vitesse);
    }

    /**
     * D�finit la vitesse du robot en fonction du param�tre sp�cifi�.
     * 
     * Certaines vitesses ne sont pas utilis�es dans le code du main, elles sont pr�sentes en tant que potentielle vitesse � utiliser.
     *
     * @param v La cha�ne repr�sentant la vitesse souhait�e ("rapide", "moyen", "lent" ou "best").
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
     * @param distance La distance � parcourir en avant.
     */
    public void avancerT(double distance) {
        pilot.travel(distance, true);
    }

    /**
     * Fait avancer le robot d'une certaine distance sans attendre la fin du d�placement.
     *
     * @param distance La distance � parcourir en avant.
     */
    public void avancerF(double distance) {
        pilot.travel(distance, false);
    }

    /**
     * Fait reculer le robot d'une certaine distance.
     *
     * @param distance La distance � parcourir en arri�re.
     */
    public void reculer(double distance) {
        pilot.travel(-distance, false);
    }

    /**
     * Fait tourner le robot d'un certain angle.
     *
     * @param angle L'angle de rotation en degr�s.
     * @param bool  Bool�en pour indiquer si la rotation doit �tre continue.
     */
    public void tourner(double angle, boolean bool) {
        pilot.rotate(angle, bool);
    }

    /**
     * Obtient l'instance du pilote de d�placement du robot.
     *
     * @return L'instance du pilote de d�placement.
     */
    public MovePilot getPilot() {
        return pilot;
    }

    /**
     * Arr�te le mouvement du robot.
     */
    public void stop() {
        pilot.stop();
    }

    /**
     * V�rifie si le robot est en mouvement.
     *
     * @return true si le robot est en mouvement, sinon false.
     */
    public boolean isMoving() {
        return pilot.isMoving();
    }
}