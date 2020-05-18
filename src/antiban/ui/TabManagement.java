package antiban.ui;

import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.MethodProvider;

public class TabManagement extends MethodProvider {
	
	private int randomNumber = 0;
	
	Tab[] mainTabs = {
			Tab.INVENTORY,
			Tab.SKILLS
	};
	
	Tab[] secondaryTabs = {
			Tab.EQUIPMENT,
			Tab.ATTACK,
			Tab.FRIENDS
	};
	
	Tab[] otherTabs = {
			Tab.CLANCHAT,
			Tab.ACCOUNT_MANAGEMENT,
			Tab.EMOTES,
			Tab.LOGOUT,
			Tab.MAGIC,
			Tab.MUSIC,
			Tab.QUEST,
			Tab.SETTINGS,
			Tab.PRAYER
	};
		
	public int onLoop() throws InterruptedException {
		log("Inside TabManagement");
		randomNumber = random(0, 1000);
		if(randomNumber <= 5) {
			getTabs().open(otherTabs[random(0, otherTabs.length - 1)]);
		}
		else if(randomNumber <= 50) {
			getTabs().open(secondaryTabs[random(0, secondaryTabs.length - 1)]);
		}
		else {
			getTabs().open(mainTabs[random(0, mainTabs.length - 1)]);
		}
		return random(379, 862);
	}
}