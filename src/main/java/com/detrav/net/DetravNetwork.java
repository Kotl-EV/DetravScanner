package com.detrav.net;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.MessageToMessageCodec;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.EnumMap;
import java.util.List;

/**
 * Created by wital_000 on 20.03.2016.
 */
@ChannelHandler.Sharable
public class DetravNetwork extends MessageToMessageCodec<FMLProxyPacket, DetravPacket> {
    static public DetravNetwork INSTANCE;
    private final EnumMap<Side, FMLEmbeddedChannel> mChannel;
    private DetravPacket[] mSubChannels = new DetravPacket[]{new ProspectingPacket()};

    public DetravNetwork() {
        INSTANCE = this;
        this.mChannel = NetworkRegistry.INSTANCE.newChannel("DetravScanner", this, new HandlerShared());
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, DetravPacket msg, List<Object> out) {
        out.add(new FMLProxyPacket(Unpooled.buffer().writeByte(msg.getPacketID()).writeBytes(msg.encode()).copy(), ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get()));
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) {
        ByteArrayDataInput aData = ByteStreams.newDataInput(msg.payload().array());
        out.add(mSubChannels[aData.readByte()].decode(aData));
    }

    public void sendToPlayer(DetravPacket aPacket, EntityPlayerMP aPlayer) {
        (this.mChannel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        (this.mChannel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(aPlayer);
        this.mChannel.get(Side.SERVER).writeAndFlush(aPacket);
    }

    public void sendToServer(DetravPacket aPacket) {
        this.mChannel.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        this.mChannel.get(Side.CLIENT).writeAndFlush(aPacket);
    }

    @ChannelHandler.Sharable
    static final class HandlerShared extends SimpleChannelInboundHandler<DetravPacket> {
        protected void channelRead0(ChannelHandlerContext ctx, DetravPacket aPacket) throws Exception {
            aPacket.process();
        }
    }
}
