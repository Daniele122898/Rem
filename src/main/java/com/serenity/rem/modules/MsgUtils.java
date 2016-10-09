package com.serenity.rem.modules;

import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

/**
 * Created by Daniele on 09.10.2016.
 */
public class MsgUtils {

    public static void sendMsg(MessageReceivedEvent e, String msg){
        RequestBuffer.request(()->{
            try {
                e.getMessage().getChannel().sendMessage(msg);
            } catch (MissingPermissionsException | DiscordException e1) {
                e1.printStackTrace();
            }
        });
    }
}
