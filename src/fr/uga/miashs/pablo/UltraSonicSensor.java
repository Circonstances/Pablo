package fr.uga.miashs.pablo;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class UltraSonicSensor extends EV3UltrasonicSensor {

	private static SampleProvider sp, spEcoute;
	private static float[] trab, tEcoute;
	private float[] roboData;

	public UltraSonicSensor(Port port) {
		super(port);
	}

	public float distance() {
		sp = this.getDistanceMode();
		trab = new float[sp.sampleSize()];
		sp.fetchSample(trab, 0);
		return trab[0] * 100;
	}
	
	public boolean isRobot() {
        spEcoute = this.getListenMode();
        tEcoute = new float[spEcoute.sampleSize()];
        spEcoute.fetchSample(tEcoute, 0);
        if(tEcoute[0] == 1) {
            sp = this.getDistanceMode();
            trab = new float[sp.sampleSize()];
            sp.fetchSample(trab, 0);
            if(trab[0]*100 <= 25) {
                return true;
            }
        }
        return false;
    }

}
