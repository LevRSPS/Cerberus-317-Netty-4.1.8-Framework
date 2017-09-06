package com.cerberus.net.channel;

import com.cerberus.net.codec.login.LoginDecoder;
import com.cerberus.player.io.Session;
import com.cerberus.util.Constants;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * This implementation of the ChannelInitializer is used to initialize the pipeline
 * @author Lev Gazarov
 *
 */
public class PipelineInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		final ChannelPipeline channelPipeline = channel.pipeline();
		
		channel.attr(Constants.SESSION_KEY).set(new Session(channel));
		channelPipeline.addLast("decoder", new LoginDecoder());
		channelPipeline.addLast("channel-handler", new ChannelHandler());
	}

}
