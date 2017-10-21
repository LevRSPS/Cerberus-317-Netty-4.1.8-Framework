package com.cerberus;

import java.util.logging.Logger;

import com.cerberus.net.channel.PipelineInitializer;
import com.cerberus.util.Constants;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * This class is used to start the Cerberus server
 * @author Lev Gazarov
 *
 */
public class Server {
	private final static ServerBootstrap bootstrap = new ServerBootstrap();
	
	private final static EventLoopGroup loopgroup = new NioEventLoopGroup();
	
	private final static Logger logger = Logger.getLogger("Cerberus");
	
	public static boolean serverUpdating = false;
	
	public final static int MAX_PLAYERS = 2000;
	
	public static void main(String args[]) {
		try {
			bootstrap.group(loopgroup).channel(NioServerSocketChannel.class).childHandler(new PipelineInitializer()).bind(Constants.PORT);
			new GameEngine().start();
			logger.info("Cerberus has been started on port 43594!");
		}  catch (Exception e) {
			logger.info("Error launching Cerberus!");
			e.printStackTrace();
		} 
	}
}
