package com.cerberus.player.updating;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cerberus.model.Flags;
import com.cerberus.net.packet.ByteOrder;
import com.cerberus.net.packet.PacketBuilder;
import com.cerberus.net.packet.PacketBuilder.AccessType;
import com.cerberus.player.Player;
import com.cerberus.util.ByteBufUtils;
import com.cerberus.world.World;

/**
 * 317 Player Updating (Not finished)
 * @author Lev Gazarov
 *
 */
public class PlayerUpdating {
	private final static ExecutorService updateService = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public static void updatePlayers() {
		updateService.execute(new Runnable() {

			@Override
			public void run() {
				for (Player player : World.players) {
					update(player);
				}
			}

		});
	}

	public static void update(Player player) {
		PacketBuilder updatePacket = new PacketBuilder();
		PacketBuilder otherPacket = new PacketBuilder(81);
		
		otherPacket.initializeAccess(AccessType.BIT);
		
		if (player.getUpdateFlags().size() > 0) {
			otherPacket.putBits(1, 1);
			otherPacket.putBits(2, 0);
		} else {
			otherPacket.putBits(1, 0);
		}
		
		updatePlayer(player, updatePacket);
		
		otherPacket.putBits(8, 0);
		
		if (updatePacket.buffer().writerIndex() > 0) {
			otherPacket.putBits(11, 2047);
			otherPacket.initializeAccess(AccessType.BYTE);
			otherPacket.putBytes(updatePacket.buffer());
		} else {
			otherPacket.initializeAccess(AccessType.BYTE);
		}
		
		player.getSession().getChannel().writeAndFlush(otherPacket.toPacket());
		System.out.println("end of updating");
	}

	public static void updatePlayer(Player player, PacketBuilder builder) {
		int mask = 0;
		
		if (player.getUpdateFlags().contains(Flags.APPEARANCE)) {
			mask |= 0x10;
		}
		
		if (mask >= 0x100) {
			mask |= 0x40;
			builder.putShort(mask, ByteOrder.LITTLE);
		} else {
			builder.put(mask);
		}
		
		if (player.getUpdateFlags().contains(Flags.APPEARANCE)) {
			updateAppearance(player, builder);
		}

		player.getUpdateFlags().clear();
	}

	private static void updateAppearance(Player player, PacketBuilder builder) {
		Appearance appearance = new Appearance(player);
		
		builder.put(0); //gender
		builder.put(0); //headicon
		
		for (int i = 0; i < 12; i++) {
			builder.put(0);			
		}
				
		builder.putShort(0x100 + appearance.getLook()[Appearance.CHEST]);
		builder.putShort(0x100 + appearance.getLook()[Appearance.ARMS]);
		builder.putShort(0x100 + appearance.getLook()[Appearance.LEGS]);
		builder.putShort(0x100 + appearance.getLook()[Appearance.HEAD]);
		builder.putShort(0x100 + appearance.getLook()[Appearance.HANDS]);
		builder.putShort(0x100 + appearance.getLook()[Appearance.FEET]);
		builder.putShort(0x100 + appearance.getLook()[Appearance.BEARD]);
		
		builder.put(appearance.getLook()[Appearance.HAIR_COLOUR]);
		builder.put(appearance.getLook()[Appearance.TORSO_COLOUR]);
		builder.put(appearance.getLook()[Appearance.LEG_COLOUR]);
		builder.put(appearance.getLook()[Appearance.FEET_COLOUR]);
		builder.put(appearance.getLook()[Appearance.SKIN_COLOUR]);
		
		for(int i = 0; i < 7; i++) {
			builder.putShort(65535);
		}
		
		builder.putLong(ByteBufUtils.stringToLong(player.getUsername()));
		builder.put(126);
		builder.putShort(1000);
		System.out.println("end of appearance updating");
	}
	
	
}
