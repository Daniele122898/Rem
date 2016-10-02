package com.serenity.rem.akkt;


import com.google.gson.Gson;
import com.serenity.rem.main.RemListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniele on 01.10.2016.
 */
public class Attk {


    private static List<Player> Players = new ArrayList<Player>();
    private static String[] msg;
    Gson gson = new Gson();
    AllPlayersPOJO allplayers = new AllPlayersPOJO();
    private long saveIntervall = 300000;
    private long saveTime = System.currentTimeMillis() + saveIntervall;


    public void start(MessageReceivedEvent e){
        msg = e.getMessage().toString().split("\\s");
        //User Exists?
        if(exists(e)){
            System.out.println("USER EXISTS");
            if(msg.length > 1){
                findComment(e);
            }
        } else{
            //ID;level;hp;attkDMG;def
            createAcc(e);

            //userInfo.add(com.serenity.rem.akkt.Player.getID()+";"+com.serenity.rem.akkt.Player.getLvl()+";"+com.serenity.rem.akkt.Player.getHp()+";"+com.serenity.rem.akkt.Player.getAttackdmg()+";"+com.serenity.rem.akkt.Player.getDef());

        }


    }

    private void findComment(MessageReceivedEvent e){
        if(msg.length > 1){
            if(checkAdv(e)){
                switch (msg[1].toLowerCase()){
                    case"1":
                        fight(e);
                        break;
                    case"2":
                        flee(e);
                        break;
                    case"stats":
                        printStats(e);
                        break;
                    default:
                        RequestBuffer.request(()->{
                            try {
                                e.getMessage().getChannel().sendMessage("Command not found! Try **" + RemListener.getPre()+"ak Help** for more information!");
                            } catch (MissingPermissionsException |DiscordException e1) {
                                e1.printStackTrace();
                            }
                        });
                        break;
                }
            } else {
                switch(msg[1].toLowerCase()){
                    case"stats":
                        printStats(e);
                        break;
                    case"adv":
                        adventure(e);
                        break;
                    case"shop":
                        System.out.println("ENTERING SHOP");
                        shop(e);
                        break;
                    case"inv":
                    case"inventory":
                        inve(e);
                        break;
                    case"use":
                        use(e);
                        break;
                    case"skill":
                        skillSystem(e);
                        break;
                    case"save":
                        saveToJason();
                        break;
                    default:
                        RequestBuffer.request(()->{
                            try {
                                e.getMessage().getChannel().sendMessage("Command not found! Try **" + RemListener.getPre()+"ak Help** for more information!");
                            } catch (MissingPermissionsException |DiscordException e1) {
                                e1.printStackTrace();
                            }
                        });
                        break;
                }
            }


        }
    }

    public void updateJson(){
        if(System.currentTimeMillis() > saveTime){
            saveToJason();
            saveTime = System.currentTimeMillis() + saveIntervall;
        } else{
            return;
        }

    }

    private void skillSystem(MessageReceivedEvent e){
        for(Player p : Players){
            if(p.getID().equals(e.getMessage().getAuthor().getID())) {
                p.skillSystem(e);
                break;
            }
        }
    }

    private void use(MessageReceivedEvent e){
        for(Player p : Players){
            if(p.getID().equals(e.getMessage().getAuthor().getID())) {
                p.use(e);
                break;
            }
        }
    }

    private void inve(MessageReceivedEvent e){
        for(Player p : Players){
            if(p.getID().equals(e.getMessage().getAuthor().getID())) {
                p.inve(e);
                break;
            }
        }
    }

    private void fight(MessageReceivedEvent e){
        for(Player p : Players){
            if(p.getID().equals(e.getMessage().getAuthor().getID())) {
                if(p.checkCooldown()) {
                    p.fight(e);
                } else{
                    p.notCool(e);
                }
                break;
            }
        }
    }
    private void flee(MessageReceivedEvent e){
        for(Player p : Players){
            if(p.getID().equals(e.getMessage().getAuthor().getID())) {
                if(p.checkCooldown()) {
                    p.flee(e);
                } else{
                    p.notCool(e);
                }
                break;
            }
        }
    }

    private boolean checkAdv(MessageReceivedEvent e){
        for(Player p : Players){
            if(p.getID().equals(e.getMessage().getAuthor().getID())){
                if(p.getAdv()){
                    return true;
                }
            }
        }
        return false;
    }

    private void adventure(MessageReceivedEvent e){
        for(Player p : Players){
            if(p.getID().equals(e.getMessage().getAuthor().getID())){
                if(p.checkCooldown()) {
                    p.adv(e);
                }else{
                    p.notCool(e);
                }
                break;
            }
        }
    }

    private void shop(MessageReceivedEvent e){
        for(Player p : Players){
            if(p.getID().equals(e.getMessage().getAuthor().getID())){
                p.shop(e);
                break;
            }
        }
    }

    public void loadJason(){
        try(Reader reader = new FileReader("config/players.json")){
            AllPlayersPOJO pojo = gson.fromJson(reader, AllPlayersPOJO.class);
            Players.clear();
            for(int i=0; i < pojo.Players.size(); i++){
                Player temp = new Player(pojo.Players.get(i));
                Players.add(temp);
            }

            System.out.println("JSON READ: "+ Players);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("JASON FILE LOADED");
    }

    public void saveToJason(){
        for(int i = 0; i < Players.size(); i++){
            allplayers.Players.add(Players.get(i).getAsPojo());
        }
        String json = gson.toJson(allplayers);
        System.out.println("JSON: " + json);
        try(FileWriter writer = new FileWriter("config/players.json")){
            gson.toJson(allplayers, writer);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("JASON FILE SAVED");
    }

    private void createAcc(MessageReceivedEvent e){
        Player p = new Player(e.getMessage().getAuthor().getID(), 1, 50, 10, 0);
        Players.add(p);
        saveToJason();

        RequestBuffer.request(()->{
        try {
            e.getMessage().getChannel().sendMessage("**Account has been created!**");
        } catch (MissingPermissionsException | DiscordException e1) {
            e1.printStackTrace();
        }
        });
    }

    private void printStats(MessageReceivedEvent e){

        for (Player p : Players) {
            if(p.getID().equals(e.getMessage().getAuthor().getID())){
                RequestBuffer.request(()->{
                    try {
                        e.getMessage().getChannel().sendMessage(
                                        ":beginner:    " + p.getLvl() + "\n" +
                                        ":hearts:    " + p.getHp() + "\n" +
                                        ":crossed_swords:    " + p.getAttackdmg() + "\n"+
                                        ":shield:    " + p.getDef() + "\n"+
                                        ":book:    " + p.getEXP() + "\n"+
                                        //"ID:            " + p.getID() + "\n" +
                                        ""

                        );
                    } catch (MissingPermissionsException | DiscordException e1) {
                        e1.printStackTrace();
                    }
                });

            }

        }


    }


    private boolean exists(MessageReceivedEvent e){
        for (Player p: Players) {
            String tempID = p.getID();
            if(tempID.equals(e.getMessage().getAuthor().getID())){
                System.out.println("TEMP ID: "+tempID);
                return true;
            }
        }
        return false;
    }
}
