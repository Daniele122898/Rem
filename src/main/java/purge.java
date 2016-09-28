import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 Message[] messagesToDelete;
 int toDel = 0;
 if (Int32.TryParse(e.GetArg("amount"), out toDel))
 {
 if (toDel <= 101)
 {
 messagesToDelete = await e.Channel.DownloadMessages(toDel);

 await e.Channel.DeleteMessages(messagesToDelete);
 }
 else
 {
 messagesToDelete = await e.Channel.DownloadMessages(101);

 await e.Channel.DeleteMessages(messagesToDelete);
 }
 */
public class purge {

    public static void purge(MessageReceivedEvent event){
        IMessage[] messagesToDelete;
        MessageList msgDel;
        List<IMessage> msgDel2 = new ArrayList<IMessage>();
        int toDel = 0;
        String[] msg = event.getMessage().toString().split("\\s");
        if(msg[1].matches("\\d+")) {
            toDel = Integer.parseInt(msg[1]);
            if(toDel > 100){
                toDel = 100;
            }
            msgDel2 = event.getMessage().getChannel().getMessages().stream().sequential().sorted((a, b) -> b.getCreationDate().compareTo(a.getCreationDate())).limit(toDel).collect(Collectors.toList());
            /*
            * Loop IChannel.getMessages().get(index)
            * */
            try {
                event.getMessage().getChannel().getMessages().bulkDelete(msgDel2);
            } catch (DiscordException e) {
                e.printStackTrace();
            } catch (RateLimitException e) {
                e.printStackTrace();
            } catch (MissingPermissionsException e) {
                e.printStackTrace();
            }

        } else {
            try {
                event.getMessage().getChannel().sendMessage("Incorrect Parameter! Parameter have to consists of only Numbers!");
            } catch (MissingPermissionsException e) {
                e.printStackTrace();
            } catch (RateLimitException e) {
                e.printStackTrace();
            } catch (DiscordException e) {
                e.printStackTrace();
            }
        }


        /*try{
            toDel = Ineger.parseInt(msg[1]);
        } catch (NumberFormatExeption ex){
        }
        * */


    }
}
