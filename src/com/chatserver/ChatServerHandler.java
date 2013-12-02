package com.chatserver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import com.wy.vo.Content;
import com.wy.vo.User;

public class ChatServerHandler extends SimpleChannelInboundHandler<Content> {

    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    int onlineSize = 0;
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        for (Channel c:channels) {
            //新好友上线将个人信息广播给其他用户
            User user = new User();
            user.setName("王毅"+onlineSize);
            user.setChannelId(ctx.channel().hashCode());
            c.writeAndFlush(user);
            
        }
        onlineSize++;
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
        Content content = (Content) msg;
        // 群发
        if (content.getHashCode() == 0) {
            for (Channel c : channels) {
                if (c != ctx.channel()) {
                    c.writeAndFlush(msg);
                }
            }
        }
        // 私聊
        else {
            for (Channel c : channels) {
                if (c.hashCode() == msg.hashCode()) {
                    c.writeAndFlush(msg);
                    return;
                }
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Content msg) throws Exception {
        System.out.println("新好友上线");
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
