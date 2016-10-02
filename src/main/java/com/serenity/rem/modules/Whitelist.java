package com.serenity.rem.modules;

import org.apache.commons.io.FileUtils;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 int toDel = 0;
 String[] msg = event.getMessage().toString().split("\\s");
 if(msg[1].matches("\\d+")) {
 toDel = Integer.parseInt(msg[1]);
 */
public class Whitelist {

    private static List<String> whiteID = new ArrayList<String>();
    static File whiteList = new File("config/whitelist");

    public static void addWhite(MessageReceivedEvent event){
        String[] msg = event.getMessage().toString().split("\\s");//splits space
        if(msg[1].startsWith("<@")){
            System.out.println("WHITELIST MENTION FOUND");
            String serverID = event.getMessage().getGuild().getID();
            String userID = createID(msg[1]);
            System.out.println("USER ID: " + userID);
            String temp = userID+";"+serverID;
            if(checkWh(event)) {
                if (!whiteID.contains(temp)) {
                    whiteID.add(userID + ";" + serverID);
                    RequestBuffer.request(() -> {
                        try {
                            event.getMessage().getChannel().sendMessage("User " + msg[1] + " has been added to the com.serenity.rem.modules.Whitelist!");
                            FileUtils.writeLines(whiteList, whiteID);
                        } catch (MissingPermissionsException | DiscordException|IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    RequestBuffer.request(() -> {
                        try {
                            event.getMessage().getChannel().sendMessage("User is already whitelisted!");
                        } catch (MissingPermissionsException |DiscordException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }else{
                try {
                    event.getMessage().getChannel().sendMessage("You have to either be Whitelisted, or the Server Owner!");
                } catch (MissingPermissionsException |RateLimitException|DiscordException e) {
                    e.printStackTrace();
                }
            }

        }else{
            RequestBuffer.request(() ->{
            try {
                event.getMessage().getChannel().sendMessage("You have to Mention the user you wish to com.serenity.rem.modules.Whitelist!");

            } catch (MissingPermissionsException |DiscordException e) {
                e.printStackTrace();
            }
            });
        }

    }

    public static void rmWhite(MessageReceivedEvent event){
        String[] msg = event.getMessage().toString().split("\\s");//splits space
        if(msg[1].startsWith("<@")) {
            System.out.println("WHITELIST MENTION FOUND");
            String serverID = event.getMessage().getGuild().getID();
            String userID = createID(msg[1]);
            System.out.println("USER ID: " + userID);
            String temp = userID + ";" + serverID;
            if(checkWh(event)) {
                if (whiteID.contains(temp)) {
                    whiteID.remove(temp);
                    try {
                        event.getMessage().getChannel().sendMessage("User has been successfully removed!");
                        FileUtils.writeLines(whiteList, whiteID);
                    } catch (MissingPermissionsException |RateLimitException |DiscordException |IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    RequestBuffer.request(() -> {
                        try {
                            event.getMessage().getChannel().sendMessage("User is not whitelisted!");
                        } catch (MissingPermissionsException | DiscordException e) {
                            e.printStackTrace();
                        }

                    });
                }
            }else{
                try {
                    event.getMessage().getChannel().sendMessage("You have to either be Whitelisted, or the Server Owner!");
                } catch (MissingPermissionsException |RateLimitException |DiscordException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean checkWh(MessageReceivedEvent event){
        String userID = event.getMessage().getAuthor().getID();
        String serverID = event.getMessage().getGuild().getID();
        System.out.println("WH ID: " + userID);
        System.out.println("WH SERVER: " + serverID);
        String com = userID + ";" + serverID;
        if(whiteID.contains(com)){
            return true;
        } else if(userID.equals(event.getMessage().getGuild().getOwnerID())){
            return true;
        } else if(userID.equals("192750776005689344")){
            return true;
        } else{
            return false;
        }

    }

    public static void load(){
        List<String> temp = new ArrayList<String>();
        try {
            temp = FileUtils.readLines(whiteList, Charset.forName("UTF-8"));
            whiteID.addAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("WHITELIST LIST LOADED");
    }

    public static String createID(String xMention){
        String mention2 = xMention;
        //int length = xMention.length()-1;
        //com.serenity.rem.modules.SystemRem.out.println("STRING: " + mention2);
        //mention2 = xMention.substring(2,length);
        mention2=mention2.replaceAll("[^0-9]+", "");
        System.out.println("CREATED ID: " + mention2);
        return mention2;
    }

}
