import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniele on 01.10.2016.
 */
public class Attk {


    private static List<Player> Players = new ArrayList<Player>();
    private static String[] msg;


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

            //userInfo.add(Player.getID()+";"+Player.getLvl()+";"+Player.getHp()+";"+Player.getAttackdmg()+";"+Player.getDef());

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
                    p.fight();
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
                    p.flee();
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

    private void createAcc(MessageReceivedEvent e){
        Player p = new Player(e.getMessage().getAuthor().getID(), 1, 50, 10, 0);
        Players.add(p);

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
