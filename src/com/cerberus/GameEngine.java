package com.cerberus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cerberus.world.World;

/**
 * Very Basic Game Engine
 * @author Lev Gazarov
 *
 */
public class GameEngine implements Runnable {

	private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	
	public void start() {
		service.scheduleAtFixedRate(this, 0, 600, TimeUnit.MILLISECONDS);
	}

	@Override
	public void run() {
		try {
			World.tick();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
