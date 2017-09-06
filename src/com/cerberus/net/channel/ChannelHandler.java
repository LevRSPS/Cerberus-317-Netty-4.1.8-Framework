package com.cerberus.net.channel;

import com.cerberus.login.LoginInformation;
import com.cerberus.net.packet.Packet;
import com.cerberus.player.Player;
import com.cerberus.player.io.Session;
import com.cerberus.util.Constants;
import com.cerberus.world.World;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * Handling messages on the channel
 * @author Lev Gazarov
 *
 */
public class ChannelHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, Object arg1) throws Exception {
		 	Session session = arg0.channel().attr(Constants.SESSION_KEY).get();
		 	
		 	if (session == null) {
		 		return;
		 	}
		 	
		 	if (arg1 instanceof Packet) {
		 		Packet packet = (Packet) arg1;
		 		session.getQueuedPackets().add(packet);
		 	} else if (arg1 instanceof LoginInformation) {
		 		session.finish((LoginInformation) arg1);
		 	} 
		 	
		 	ReferenceCountUtil.release(arg1);
		 	
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Session session = ctx.channel().attr(Constants.SESSION_KEY).get();
		
		if (session == null) {
			return;
		}
		
		Player player = session.getPlayer();
		
		if (player == null) {
			return;
		}
		
		if (!World.toRemove.contains(player)) {
			World.toRemove.add(player);
		}
	}

}
