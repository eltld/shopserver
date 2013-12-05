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
import java.util.Map.Entry;

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
        if (msg instanceof User) {
            User user = (User) msg;
            // 用户上线,将个人信息广播给其他人
            if(user.getName()!=null){
                onlineUser.add(user);
                for (Entry<Integer, Channel> MapString : map.entrySet()) {
                    Channel value = MapString.getValue();
                    value.writeAndFlush(user);
                }
                map.put(user.getChannelId(), ctx.channel());
            }
            //用户下线,将个人信息广播给其他人
            else{
                map.remove(user.getChannelId());
                for (Entry<Integer, Channel> MapString : map.entrySet()) {
                    Channel value = MapString.getValue();
                    value.writeAndFlush(user);
                }
                for (int i = 0; i < onlineUser.size(); i++) {
                    if(onlineUser.get(i).getChannelId()==user.getChannelId()){
;                        onlineUser.remove(i);
                    }
                }
                
            }
        }
        if (msg instanceof Content) {
            Content content = (Content) msg;
            // 群发
            if (content.getReceiveId() == 0) {
                for (Channel c : channels) {
                    if (c != ctx.channel()) {
                        c.writeAndFlush(msg);
                    }
                }
            }
            // 私聊
            else {
                Channel c = map.get(content.getReceiveId());
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
        super.handlerRemoved(ctx);
    }

}
