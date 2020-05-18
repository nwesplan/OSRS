package antiban.mouse;

import org.osbot.rs07.script.MethodProvider;

public class MouseManagement extends MethodProvider {
	
	private int randomNumber = 0;
	
	public int onLoop() throws InterruptedException {
		
		log("Inside MouseManagement");
		randomNumber = random(0, 1000);
		
		if(randomNumber <= 475) {
			getMouse().move(random(16, 496), random(10, 322));
			Thread.sleep(random(576, 984));
			getMouse().click(true);
		}
		else if(randomNumber <= 950){
			getMouse().move(random(550, 735), random(270, 470));
		}
		else {
			getMouse().moveOutsideScreen();
			return random(7567, 12476);
		}
		return random(412, 1287);
	}
}
