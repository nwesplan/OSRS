package main;

import org.osbot.rs07.script.Script;

import antiban.camera.Pitch;
import antiban.camera.Yaw;
import antiban.mouse.MouseManagement;
import antiban.ui.TabManagement;


public abstract class MainScriptManager extends Script {
	
	private Yaw moveYaw = new Yaw();
	private Pitch movePitch = new Pitch();
	private final TabManagement tabManagement = new TabManagement();
	private MouseManagement mouseManagement = new MouseManagement();
	
	@Override
    public void onStart() {
		movePitch.exchangeContext(getBot());
		moveYaw.exchangeContext(getBot());
		tabManagement.exchangeContext(getBot());
		mouseManagement.exchangeContext(getBot());
		new Thread(movePitch).start();
	  	new Thread(moveYaw).start();
    }
	
	@Override
	public void pause() {
		super.pause();;
		moveYaw.stop();
		movePitch.stop();
	}
	
	@Override
	public void resume() {
		super.resume();
		moveYaw.start();
		movePitch.start();
	}
	
	@Override
	public void onExit() {
		moveYaw.stop();
		movePitch.stop();
	}
}
