package antiban.camera;

import org.osbot.rs07.script.MethodProvider;

public class Pitch extends MethodProvider implements Runnable {
	
	int pitch;
	private int randomNumber;
	private volatile boolean run = true;
	
	private void movePitch() {
		pitch = camera.getPitchAngle();
		if(pitch <= 22) {
			camera.movePitch(camera.getPitchAngle() + random(1, 45));
		}
		else if(pitch >= 67) {
			camera.movePitch(camera.getPitchAngle() - random(1, 45));
		}
		else {
			randomNumber = random(1, 2);
			if(randomNumber == 1) {
				camera.movePitch(camera.getPitchAngle() - random(1, 45));
			}
			else {
				camera.movePitch(camera.getPitchAngle() + random(1, 45));
			}
		}
	}

	@Override
	public void run() {
		while(run) {
			if(!bank.isOpen()) {
				log("Moving Pitch");
				movePitch();
				try {
					Thread.sleep(random(176, 57654));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void stop() {
		run = false;
	}
	
	public void start() {
		run = true;
	}
	
	public void sleepTime(long millis) {
		try {
			Thread.sleep(millis);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
