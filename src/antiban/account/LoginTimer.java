package antiban.account;

import main.MainScriptManager;

public class LoginTimer extends MainScriptManager {
	
	private long beginningTime;
	private long currentTime;
	private long switchTime;
	private int switchInteger;
	
	private int MORNING = 21_600_000;
	private int MORNING_PLAY;
	private int MORNING_SLEEP;
	private int AFTERNOON = 14_400_000;
	private int AFTERNOON_PLAY;
	private int AFTERNOON_SLEEP;
	private int NIGHT = 50_400_000;
	private int NIGHT_PLAY;
	private int NIGHT_SLEEP;
	
	private int[] PLAY = new int[6];
	
	private int randomNumber;

	
	private enum State  {
		LoggedIN,
		LoggedOUT
	};
	private State state;
	
	public LoginTimer() {
		MORNING_PLAY = random(10_800_000, 14_400_000);
		MORNING_SLEEP = MORNING - MORNING_PLAY;
		AFTERNOON_PLAY = random(7_200_000, 10_800_000);
		AFTERNOON_SLEEP = AFTERNOON - AFTERNOON_PLAY;
		NIGHT_PLAY = random(18_000_000, 21_600_000);
		NIGHT_SLEEP = NIGHT - NIGHT_PLAY;
		
		PLAY[0] = MORNING_PLAY;
		PLAY[1] = MORNING_SLEEP;
		PLAY[2] = AFTERNOON_PLAY;
		PLAY[3] = AFTERNOON_SLEEP;
		PLAY[4] = NIGHT_PLAY;
		PLAY[5] = NIGHT_SLEEP;
		
		switchTime = MORNING_PLAY;
		switchInteger = 0;
		beginningTime = System.currentTimeMillis();
		state = State.LoggedIN;
	}
	
	public boolean Loop() {
		currentTime = System.currentTimeMillis() - beginningTime;
		
		if(currentTime >= switchTime) {
			if(state == State.LoggedIN) {
				super.pause();
				state = State.LoggedOUT;
			}
			else {
				super.resume();
				state = State.LoggedIN;
			}
			
			beginningTime = System.currentTimeMillis();
			currentTime = beginningTime;
			if(switchInteger <= 4) {
				switchInteger += 1;
			}
			else {
				switchInteger = 0;
			}
			switchTime = PLAY[switchInteger];
		}
		return false;
	}

	@Override
	public int onLoop() throws InterruptedException {
		return 0;
	}
}
