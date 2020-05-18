package main;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.MethodProvider;

import main.AbyssalDemons.State;

public class Banking extends MethodProvider {
	
	Entity bank;
	
	private boolean deposited = false;
	private final int[] withdrawItems;
	private final int FOOD;
	private final Position bankPosition;
	
	public Banking(final int[] withdrawItems, final int FOOD, final Position bankPosition) {
		this.withdrawItems = withdrawItems;
		this.FOOD = FOOD;
		this.bankPosition = bankPosition;
	}
	
	public boolean onLoop() throws InterruptedException {
		bank = objects.closest("Bank booth");
		
		if(getBank().isOpen()) {
			if(!deposited) {
				if(!inventory.isEmpty()) {
					getBank().depositAll();
					return true;
				}
				deposited = true;
				return true;
			}

			for(int i: withdrawItems) {
				if(!inventory.contains(i)) {
					getBank().withdraw(i, 1);
					return true;
				}
			}

			if(!inventory.isFull()) {
				getBank().withdraw(FOOD, 1000);
				return true;
			}
			if(inventory.isFull() && deposited) {
				deposited = false;
				return false;
			}
		}
		
		if(bank == null) {
			walking.webWalk(bankPosition);
			return true;
		}
		if(!bank.isVisible()) {
			camera.toEntity(bank);
			return true;
		}
		else {
			getBank().open();
			return true;
		}
	}

}
