package fr.thomas.engine;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer extends Thread {

	private int currentTick = 0;

	private long nanoCurrentExecutionTime;
	private long millisSleepTime;

	private boolean stopSignal = false;
	private Timer timer;
	private GameEngine game;

	public GameTimer(GameEngine game) {
		this.game = game;
		this.setName("tickThread");
		timer = new Timer();
		this.start();
	}

	@Override
	public void run() {
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("Processed ticks : " + currentTick);
				currentTick = 0;
			}
		}, 1000, 1000);
		
		while(!stopSignal) {
			currentTick++;
			nanoCurrentExecutionTime = 0;
			long b = System.nanoTime();
			game.tickExecTime();
			long e = System.nanoTime();

			nanoCurrentExecutionTime += e - b;
			millisSleepTime = (50000000 - nanoCurrentExecutionTime) / 1000000;
			if(millisSleepTime < 0) millisSleepTime = 0;

			//System.out.println((double) (nanoCurrentExecutionTime / 1000000000d));
			//System.out.println(millisSleepTime);

			try {
				Thread.sleep(millisSleepTime);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
				break;
			}
			super.run();
		}
	}
	
	public void stopTimer() {
		System.out.println("Stopping timer !");
		timer.cancel();
		this.stopSignal = true;
	}
}
