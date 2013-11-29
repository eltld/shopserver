package com.chatserver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import com.wy.vo.Content;

public class ChatServerHandler extends SimpleChannelInboundHandler<Content> {

    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
            channels.add(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.toString());
        channels.remove(ctx.channel());
        ctx.close();
    }

    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        return super.acceptInboundMessage(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        for (Channel c: channels) {
            if (c != ctx.channel()) {
                c.writeAndFlush(msg);
            } 
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Content msg) throws Exception {
        
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
       channels.remove(ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(channels.size());
        super.handlerRemoved(ctx);
    }
    
    
}
