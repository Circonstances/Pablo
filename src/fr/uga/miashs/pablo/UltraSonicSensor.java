package fr.uga.miashs.pablo;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class UltraSonicSensor extends EV3UltrasonicSensor {

    private static SampleProvider sp, spEcoute;
    private static float[] trab, tEcoute;
    private float[] roboData;

    /**
     * Constructeur de la classe UltraSonicSensor.
     *
     * @param port Le port auquel le capteur ultrasonique est connecté.
     */
    public UltraSonicSensor(Port port) {
        super(port);
    }

    /**
     * Mesure et retourne la distance mesurée par le capteur ultrasonique.
     *
     * @return La distance mesurée en centimètres.
     */
    public float distance() {
        sp = this.getDistanceMode();
        trab = new float[sp.sampleSize()];
        sp.fetchSample(trab, 0);
        return trab[0] * 100;
    }

    /**
     * Vérifie si un robot est détecté à proximité en écoutant le capteur ultrasonique.
     *
     * @return true si un robot est détecté à moins de 25 centimètres, sinon false.
     */
    public boolean isRobot() {
        spEcoute = this.getListenMode();
        tEcoute = new float[spEcoute.sampleSize()];
        spEcoute.fetchSample(tEcoute, 0);
        if (tEcoute[0] == 1) {
            sp = this.getDistanceMode();
            trab = new float[sp.sampleSize()];
            sp.fetchSample(trab, 0);
            if (trab[0] * 100 <= 25) {
                return true;
            }
        }
        return false;
    }
}