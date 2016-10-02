import org.apache.commons.io.FileUtils;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 private void RegisterAFKCommand()
 {
 commands.CreateCommand("afk")
 .Description("If you are not yet set AFK the command will set you AFK so that every user gets a response from Rem when mentioning you. Triggering the command again will remove the AFK status.")
 .Do(async (e) =>
 {

 string nameofUser = createID(e.User.Mention, e.Server.Id);
 ulong UserID = 0;
 if (UInt64.TryParse(nameofUser, out UserID))
 {
 if (afkServer.IndexOf(UserID) < 0)
 {
 afkServer.Add(UserID);
 await e.Channel.SendMessage("You are set AFK");
 string save = "config/afklist.txt";
 StreamWriter file = new SystemRem.IO.StreamWriter(save);
 afkServer.ForEach(file.WriteLine);
 file.Close();
 Console.WriteLine("Succesfully Saved AKFList!");
 }
 else
 {
 afkServer.Remove(UserID);
 await e.Channel.SendMessage("You are no longer AFK");
 string save = "config/afklist.txt";
 StreamWriter file = new SystemRem.IO.StreamWriter(save);
 afkServer.ForEach(file.WriteLine);
 file.Close();
 Console.WriteLine("Succesfully Saved AFKList!");
 }
 for (int i = 0; i < afkServer.Count; i++)
 {
 Console.WriteLine("AFK " + i + ": " + afkServer[i]);
 }
 }
 else
 {
 await e.Channel.SendMessage("Failed to set AFK state");
 }

 });
 }
 */
public class AFKcommand {

    private static List<String> comID = new ArrayList<String>();
    static File afkList = new File("config/afklist");

    public static void afk(MessageReceivedEvent event){
        String clientID = event.getMessage().getAuthor().getID();
        String serverID = event.getMessage().getGuild().getID();
        String tempComp = clientID + ";" + serverID;
        if(!comID.contains(tempComp)){
            comID.add(clientID + ";" + serverID);
            RequestBuffer.request(() -> {
                try {
                    event.getMessage().getChannel().sendMessage("You are set AFK");
                    FileUtils.writeLines(afkList, comID);
                } catch (MissingPermissionsException |DiscordException|IOException e) {
                    e.printStackTrace();
                }
            });
        } else{
            comID.remove(tempComp);
            RequestBuffer.request(() -> {
                try {
                    event.getMessage().getChannel().sendMessage("You are no longer AFK");
                    FileUtils.writeLines(afkList, comID);
                } catch (MissingPermissionsException | DiscordException | IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static boolean checkAFK(String UserMention, String ServerID){
        String ID = createID(UserMention);
        boolean isTru;
        for(int i=0; i< comID.size(); i++){
            String temp = comID.get(i);
            String[] parts = temp.split(";");
            if(parts[0].equals(ID) && parts[1].equals(ServerID)){
                System.out.println("TRUE");
                return true;
            }

            }

            return false;
        }




    public List getComID(){
        return comID;
    }

    public static void loadList(){
        List<String> temp = new ArrayList<String>();
        try {
            temp = FileUtils.readLines(afkList, Charset.forName("UTF-8"));
            comID.addAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("AFK LIST LOADED");
    }

    public static String createID(String xMention){
        String mention2 = xMention;
        //int length = xMention.length()-2;
        System.out.println("STRING: " + mention2);
        //mention2 = xMention.substring(4,length);
        mention2=mention2.replaceAll("[^0-9]+", "");
        return mention2;
    }
}
