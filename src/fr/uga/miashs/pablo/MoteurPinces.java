package fr.uga.miashs.pablo;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

/**
 * La classe MoteurPinces représente un moteur régulé EV3Medium utilisé pour contrôler
 * une paire de pinces. Elle permet d'ouvrir et de fermer les pinces pour effectuer
 * des actions spécifiques.
 *
 * @author PabloTeam
 * @version 1.0
 */
public class MoteurPinces {

    private EV3MediumRegulatedMotor pinces;
    private boolean estOuvert;

    /**
     * Constructeur par défaut de la classe MoteurPinces.
     * Initialise le moteur des pinces sur le port C, définit la vitesse maximale du moteur
     * et initialise l'état des pinces comme étant ouvertes.
     */
    public MoteurPinces() {
        pinces = new EV3MediumRegulatedMotor(MotorPort.C);
        pinces.setSpeed(pinces.getMaxSpeed());
        estOuvert = true;
    }

    /**
     * Initialise la position des pinces en les rapprochant ou en les éloignant.
     *
     * @param i La valeur de rotation, si i < 0 rapproche les pinces, si i > 0 éloigne les pinces.
     */
    public void initialisation(int i) {
        pinces.rotate(i, false);
    }

    /**
     * Ferme les pinces.
     * 
     * @param t Si false, effectue uniquement l'action de fermeture. Si true, effectue
     *          autre chose en parallèle. Change l'état des pinces (estOuvert) utilisé par d'autres méthodes.
     */
    public void fermeture() {
        pinces.rotate(-700, false);
        estOuvert = false;
    }

    /**
     * Ouvre les pinces.
     * 
     * @param t Si false, effectue uniquement l'action d'ouverture. Si true, effectue
     *          autre chose en parallèle. Change l'état des pinces (estOuvert) utilisé par d'autres méthodes.
     */
    public void ouverture() {
        pinces.rotate(700, false);
        estOuvert = true;
    }
    
    public boolean getEstOuvert() {
    	return estOuvert;
    }
    
}
