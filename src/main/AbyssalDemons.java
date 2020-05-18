package main;
import java.util.ArrayList;
import java.util.List;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import antiban.account.LoginTimer;
import antiban.camera.Pitch;
import antiban.camera.Yaw;
import antiban.mouse.MouseManagement;
import antiban.ui.TabManagement;
import worlds.WorldHopping;

@ScriptManifest(name = "PACKAGE AbyssalDemons", author = "N8", version = 1.0, info = "", logo = "") 

public class AbyssalDemons extends Script {
	
	int[] itemList = {	1113, 1147, 2361, 2363, 207, 442, 1149, 2366, 1249, 561, 563, 562, 565, 560, 562,
		13265, 4151, 379, 987, 985, 1615, 1617, 886, 892, 1247, 1373, 1319, 1185, 1201, 995, 556, 13507				
	};
	
	String[] otherItems = { "Grimy avantoe", "Grimy kwuarm", "Grimy cadantine", "Grimy lantadyme", "Uncut sapphire", "Uncut emerald", "Uncut ruby" };
	
	String[] dropItems = { "Black sword", "Steel battleaxe", "Black axe", "Mithril kiteshield", "Cosmic talisman", "Chaos talisman", "Defence potion(3)", "Nature talisman",
		"Rune javelin", "Adamant javelin", "Grimy guam leaf", "Grimy marrentill", "Grimy tarromin", "Grimy harralander", "Grimy irit leaf", "Grimy dwarf weed", "Ashes", "Vial"
	};

	int[] stackables = { 995, 560, 561, 562, 563, 565, 556 };
	int[] potions = { 2436, 2440, 2442 };
	int[] superAttack = { 149, 147, 145, 2436 };
	int[] superStrength = { 161, 159, 157, 2440 };
	int[] superDefense = { 167, 165, 163, 2442 };
//	int[] superCombat = {  };
	
	int[] withdrawItems = { 3751, 2436, 2440, 2442 };
	private final int FOOD = 385;
	
	private Yaw moveYaw = new Yaw();
	private Pitch movePitch = new Pitch();
	private final TabManagement tabManagement = new TabManagement();
	private MouseManagement mouseManagement = new MouseManagement();
	private final LoginTimer loginTimer = new LoginTimer();
	
	enum State {
		BANKING,
		FIGHTING,
		WALKING
	}
	
	private State state;
	Entity bank;
	private boolean deposited;
	NPC monster;
	GroundItem item;
	boolean eating = false;
	int randomNumber = 0;
	List<Player> p;
	private boolean hopping = false;
	
	WorldHopping w;
	
	public AbyssalDemons() {
		super();
		w = new WorldHopping(1600, "p2p");
		state = State.FIGHTING;
		deposited = false;
	}
	
	
	@Override
    public void onStart() {
		movePitch.exchangeContext(getBot());
		moveYaw.exchangeContext(getBot());
		tabManagement.exchangeContext(getBot());
		mouseManagement.exchangeContext(getBot());
		loginTimer.exchangeContext(getBot());
		new Thread(movePitch).start();
	  	new Thread(moveYaw).start();
    }
	
	@Override
	public void onExit() {
		moveYaw.stop();
		movePitch.stop();
	}
	
	@Override
	public void pause() {
		moveYaw.stop();
		movePitch.stop();
	}
	
	@Override
	public void resume() {
		moveYaw.start();
		movePitch.start();
	}
	
	@Override
	public int onLoop() throws InterruptedException {
		log(state);
		List<RS2Object> entities = new ArrayList<RS2Object>();
		entities = objects.getAll();
		
		randomNumber = random(1,1000);
		if(state == State.FIGHTING && randomNumber <= 50 ) {
			return tabManagement.onLoop();
		}
		else if(state == State.FIGHTING && randomNumber <= 100) {
			return mouseManagement.onLoop();
		}
		
		if(state == State.FIGHTING) {
			if(settings.getRunEnergy() > 10 && !settings.isRunning()) {
				settings.setRunning(true);
				return random(672, 879);
			}
			log("where are we");
			
			log("Short way: ");
			
			if(!containsFood() && (skills.getDynamic(Skill.HITPOINTS) < 55 || inventory.isFull())) {
				
			}
			log("long way: ");
			log(!inventory.contains(FOOD) && !inventory.contains(379) && skills.getDynamic(Skill.HITPOINTS) < 75 ||
					!inventory.contains(FOOD) && !inventory.contains(379) && inventory.isFull());
			if(!inventory.contains(FOOD) && !inventory.contains(379) && skills.getDynamic(Skill.HITPOINTS) < 75 ||
					!inventory.contains(FOOD) && !inventory.contains(379) && inventory.isFull()) {
				log("trying to bank");
				if(loot()) return random(872, 1421);
				if(inventory.contains(11865)) {
					inventory.interact("Wear", 11865);
					return random(1500, 2000);
				}
				log("banking");
				state = State.BANKING;
				return 100;
			}
			if(myPlayer().getZ() == 2) { //Top floor of slayer tower
				if(inventory.contains(3751)) {
					inventory.interact("Wear", 3751);
					return random(1500, 2000);
				}
			}	
			p = players.getAll();	
			if(p != null) {
				p = players.getAll();
				for(int i = 0; i < p.size(); i++) {
					if(p.size() > 1 && p.get(1).getX() <= 3430 && p.get(1).getY() >= 3555) {
						hopping = true;
						log("hopping");
					}
				}
			}
			log("where are we 1");
			// create crystal key
			if(inventory.contains(985) && inventory.contains(987)) {
				if(inventory.isItemSelected()) {
					inventory.getItem(987).interact();
					return random(987, 1473);
				}
				inventory.interact("Use", 985);
				return random(987, 1473);
			}
			// Equip rune arrows
			if(inventory.contains(892)) {
				inventory.interact("Wield", 892);
				return random(572, 983);
			}
			
			// Drop junk items
			for(String dropItem: dropItems) {
				if(inventory.contains(dropItem)) {
					inventory.interact("Drop", dropItem);
					return random(1174, 1689);
				}
			}
			if(hopping) {
				if(loot()) return random(872, 1421);
				worlds.hop(w.hopWorld());
				hopping = false;
				return 10000;
			}
			log("where are we 2");
			monster = npcs.closest(415);
			if(monster != null) {
				if(eating) {
					if(skills.getDynamic(Skill.HITPOINTS) < 75) {
						if(inventory.contains(379)) {
							inventory.interact("Eat", 379);
							return random(800, 1200);
						}
						log("here 4");
						inventory.interact("Eat", FOOD);
						return random(800, 1200);
					}
					else {
						eating = false;
						return random(600, 800);
					}
				}
				log("here 5");
				if(skills.getDynamic(Skill.HITPOINTS) < 25) {
					eating = true;
					return random(400, 600);
				}
				
				if(skills.getDynamic(Skill.HITPOINTS) < 50 && !myPlayer().isUnderAttack()) {
					eating = true;
					return random(600, 800);
				}
				// Super combat potion code
//				if(skills.getDynamic(Skill.ATTACK) == skills.getStatic(Skill.ATTACK)) {
//					for(int i: superCombat) {
//						if(inventory.contains(i)) {
//							inventory.interact("Drink", i);
//							return random(745, 1221);
//						}
//					}
//				}
				
				// Shorten super att/str/def potion code
//				Skill[] skillz = { Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE };
//				int[][] potss = { superAttack, superStrength, superDefense };
//				
//				for(int i = 0; i < skillz.length; i++) {
//					if(skills.getDynamic(skillz[i]) == skills.getStatic(skillz[i])) {
//						for(int j = 0; j < potss[i].length; j++) {
//							if(inventory.contains(potss[i][j])) {
//								inventory.interact("Drink", potss[i][j]);
//								return random(745, 1221);
//							}
//						}
//					}
//				}
				log("where are we 3");
				if(skills.getDynamic(Skill.DEFENCE) == skills.getStatic(Skill.DEFENCE)) {
					for(int i: superDefense) {
						if(inventory.contains(i)) {
							inventory.interact("Drink", i);
							return random(745, 1221);
						}
					}
				}
				
				if(skills.getDynamic(Skill.ATTACK) == skills.getStatic(Skill.ATTACK)) {
					for(int i: superAttack) {
						if(inventory.contains(i)) {
							inventory.interact("Drink", i);
							return random(745, 1221);
						}
					}
				}
				if(skills.getDynamic(Skill.STRENGTH) == skills.getStatic(Skill.STRENGTH)) {
					for(int i: superStrength) {
						if(inventory.contains(i)) {
							inventory.interact("Drink", i);
							return random(745, 1221);
						}
					}
				}
				
				if(loot()) return random(872, 1421);
				
				if(!myPlayer().isUnderAttack() && !myPlayer().isMoving()) {
					monster = npcs.closest(415);
					if(!monster.isUnderAttack() && !myPlayer().isUnderAttack() && !myPlayer().isAnimating() && !monster.isAnimating()) {
						monster.interact("Attack");
						return random(3800, 4500);
					}
				}
			}
			
			if(monster == null || Math.abs(myPlayer().getX() - monster.getX()) > 20 || Math.abs(myPlayer().getY() - monster.getY()) > 20) {
				
				walking.webWalk(new Position(3412, 3560, 2));
				return random(176, 875);
			}
		}
		
		if(state == State.BANKING) {
			
			// @Test banking external class code
//			Banking bankScript = new Banking(
//				withdrawItems,
//				FOOD,
//				new Position(3510, 3480, 0)
//			);
//			bankScript.exchangeContext(getBot());
//			if(bankScript.onLoop()) {
//				return random(844, 1276);
//			}
//			else {
//				state = State.FIGHTING;
//			}
			bank = objects.closest("Bank booth");
			
			if(getBank().isOpen()) {
				if(!deposited) {
					if(!inventory.isEmpty()) {
						getBank().depositAll();
						return random(1275, 2256);
					}
					deposited = true;
					return random(1275, 2256);
				}
				// 3751
//				if(!inventory.contains(3751)) {
//					getBank().withdraw(3751, 1);
//					return random(834, 1231);
//				}
//				if(!inventory.contains(1111)) {
//					getBank().withdraw(1111, 2);
//					return random(834, 1231);
//				}
//				if(inventory.getAmount(1111) > 2) {
//					getBank().deposit(1111, (int) inventory.getAmount(1111) - 2);
//				}
				for(int i: withdrawItems) {
					if(!inventory.contains(i)) {
						getBank().withdraw(i, 1);
						return random(834, 1231);
					}
				}

				if(!inventory.isFull()) {
					getBank().withdraw(FOOD, 1000);
					return random(800, 1200);
				}
				if(inventory.isFull() && deposited) {
					state = State.FIGHTING;
					deposited = false;
				}
			}
			
			if(bank == null) {
				walking.webWalk(new Position(3510, 3480, 0));
				return random(1275, 2256);
			}
			if(!bank.isVisible()) {
				camera.toEntity(bank);
				return random(1275, 2256);
			}
			else {
				getBank().open();
				return random(659, 942);
			}
		}
		return 1000;
	}
	
	public boolean isStackable(int x) {
		for(int i: stackables) {
			if(x == i) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasStackableItem(int x) {
		return isStackable(x) && inventory.contains(x) ? true : false;
	}
	
	public boolean containsFood() {
		return !inventory.contains(FOOD) && !inventory.contains(379) ? false : true;
	}
	
	public boolean loot() {
		for(int i: itemList) {
			item = groundItems.closest(i);
			if(item != null && item.getX() <= 3430) {
				if(Math.abs(myPlayer().getX() - item.getX()) < 5 && Math.abs(myPlayer().getY() - item.getY()) < 5 || item.getId() == 4151 || item.getId() == 13265) {
					if(inventory.isFull() && !hasStackableItem(item.getId())) {
						if(inventory.contains(379)) {
							inventory.interact("Eat", 379);
							return true;
						}
						else {
							inventory.interact("Eat", FOOD);
							return true;
						}
					}
					item.interact("Take");
					return true;
				}
			}
		}
		for(String s: otherItems) {
			item = groundItems.closest(s);
			if(item != null && item.getX() <= 3430 && inventory.getEmptySlots() >= 3) {
				if(Math.abs(myPlayer().getX() - item.getX()) < 5 && Math.abs(myPlayer().getY() - item.getY()) < 5 || item.getId() == 4151 || item.getId() == 13265) {
					item.interact("Take");
					return true;
				}
			}
		}
		return false;
	}
	
	
	public boolean loot2(int[] itemList, int... food) {
		for(int i = 0; i < itemList.length; i++) {
			item = groundItems.closest(itemList[i]);
			if(item != null && item.getX() <= 3430 ) {
				if(Math.abs(myPlayer().getX() - item.getX()) < 5 && Math.abs(myPlayer().getY() - item.getY()) < 5 || item.getId() == 4151 || item.getId() == 13265) {
					if(inventory.isFull()  && !hasStackableItem(item.getId())) {
						for(int f: food) {
							if(inventory.contains(f)) {
								inventory.interact("Eat", f);
								return true;
							}
						}
					}
					item.interact("Take");
					return true;
				}
			}
		}
		return false;
	}
}