package com.chatserver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wy.vo.Content;
import com.wy.vo.User;

public class ChatServerHandler extends SimpleChannelInboundHandler<Content> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static List<User> onlineUser = new ArrayList<User>();
    public static Map<Integer, Channel> map = new HashMap<Integer, Channel>();

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
        // 用户上线,将个人信息广播给其他人
        if (msg instanceof User) {
            User user = (User) msg;
            onlineUser.add(user);
            map.put(user.getChannelId(), ctx.channel());

            for (Channel c : channels) {
                if (c.hashCode() != user.getChannelId()) {
                    c.writeAndFlush(user);
                }
            }
        }
        if (msg instanceof Content) {
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
                Channel c = map.get(content.getHashCode());
                c.writeAndFlush(content);
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
