import javazoom.jl.player.Player;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniele on 01.10.2016.
 */
public class testAttk {

    private static List<String> userInfo = new ArrayList<String>();
    private static ArrayList<player> players = new ArrayList<player>();


    public static void start(MessageReceivedEvent e){
        //User Exists?
        if(exists(e)){
            System.out.println("USER EXISTS");
            printStats(e);
        } else{
            //ID;level;hp;attkDMG;def
            player p = new player(e.getMessage().getAuthor().getID(), 1, 100, 2, 0);
            players.add(p);
            userInfo.add(player.getID()+";"+player.getLvl()+";"+player.getHp()+";"+player.getAttackdmg()+";"+player.getDef());
            System.out.println("USER CREATED WITH STATS: " + userInfo);
            printStats(e);
        }

    }

    private static void printStats(MessageReceivedEvent e){
        RequestBuffer.request(()->{
            try {
                e.getMessage().getChannel().sendMessage(
                        ":information_source: Stats ```" +
                        "Level:         " + player.getLvl() + "\n" +
                        "HP:            " + player.getHp() + "\n" +
                        "Attack Damage: " + player.getAttackdmg() + "\n"+
                        "Defense:       " + player.getDef() + "\n"+
                        "```"

                );
            } catch (MissingPermissionsException | DiscordException e1) {
                e1.printStackTrace();
            }
        });
    }


    private static boolean exists(MessageReceivedEvent e){
        for (String temp: userInfo) {
            String[] tempL = temp.split(";");
            if(tempL[0].equals(e.getMessage().getAuthor().getID())){
                System.out.println(temp);
                return true;
            }
        }
        return false;
    }
}
