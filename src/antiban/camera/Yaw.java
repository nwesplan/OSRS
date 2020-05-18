package antiban.camera;

import org.osbot.rs07.script.MethodProvider;

public class Yaw extends MethodProvider implements Runnable {
	private int randomNumber;
	private volatile boolean run = true;
	
	private void moveYaw() {
		randomNumber = random(1, 2);
		if(randomNumber == 1) {
			camera.moveYaw(camera.getYawAngle() + random(1, 361));
		}
		else {
			camera.moveYaw(camera.getYawAngle() - random(1, 361));
		}
	}
	
	@Override
	public void run() {
		while(run) {
			if(!bank.isOpen()) {
				log("Moving Yaw");
				moveYaw();
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
