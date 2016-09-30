import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

import java.io.IOException;
/* TODO
            Purge Command
            Whitelist command
            List Command
            SL Command
            AFK Command
            OP Command
            CoinFlip Command
 */


public class RemListener {

    private static String pre;

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) { // This method is called when the ReadyEvent is dispatched
        System.out.println("BOT READY!");
        AFKcommand.loadList();
        fileStorage.storage();
        whitelist.load();


    }
    //@EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) { // This method is NOT called because it doesn't have the @EventSubscriber annotation

    }

    @EventSubscriber
    public void switchMessage(MessageReceivedEvent event){
        if(event.getMessage().getAuthor().isBot()) {
            return;
        }
        String msg = event.getMessage().getContent();
        pre = ";";
        String[] msgL = msg.split("\\s");
        if(msgL[0].startsWith(pre)) {
            switch (msgL[0].substring(pre.length()).toLowerCase()) {
                case "ping":
                    ping(event);
                    break;
                case"rem":
                    randomRem(event);
                    break;
                case"morning":
                    remMorning(event);
                    break;
                case"afk":
                    AFKcommand.afk(event);
                    break;
                //case event.getMessage().getMentions():
                  //  break;
                case"flip":
                    coinFlip(event);
                    break;
                case"purge":
                    purge.purge(event);
                    break;
                case"wh":
                    whitelist.addWhite(event);
                    break;
                case"rmwh":
                    whitelist.rmWhite(event);
                    break;
                case"help":
                case"h":
                    help.help(event);
                    break;
                case"system":
                    system.sysInfo(event);
                    break;
                default:
                    //wrongCommand(event);
                    break;

            }
        }
        /*if(AFKcommand.checkAFK(event.getMessage().getMentions().toString(), event.getMessage().getGuild().getID())){
            try {
                event.getMessage().getChannel().sendMessage("Mentioned User is AFK");
            } catch (MissingPermissionsException e) {
                e.printStackTrace();
            } catch (RateLimitException e) {
                e.printStackTrace();
            } catch (DiscordException e) {
                e.printStackTrace();
            }
        }*/
    }



    @EventSubscriber
    public void afkCheck(MessageReceivedEvent event){
        if(event.getMessage().getMentions().toString().length() > 2) {
            if (AFKcommand.checkAFK(event.getMessage().getMentions().toString(), event.getMessage().getGuild().getID())) {
                RequestBuffer.request(()->{
                try {
                    event.getMessage().getChannel().sendMessage("Mentioned User is AFK");
                } catch (MissingPermissionsException |DiscordException  e) {
                    e.printStackTrace();
                }
                });
            }
        } else {
            return;
        }
    }

    public void coinFlip(MessageReceivedEvent event){
        int picToSend = RandomNumberGen.getRandint((fileStorage.coinPics.length));
        RequestBuffer.request(() ->{
            try {
                event.getMessage().getChannel().sendFile(fileStorage.coinFiles.get(picToSend));
            } catch (MissingPermissionsException |DiscordException|IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void remMorning(MessageReceivedEvent event){
        RequestBuffer.request(() ->{
            try {
                event.getMessage().getChannel().sendFile(fileStorage.morningRem);
                event.getMessage().reply("Good Morning");
            } catch (MissingPermissionsException |DiscordException| IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void randomRem(MessageReceivedEvent event){
        int picToSend = RandomNumberGen.getRandint(fileStorage.remPics.length);
        RequestBuffer.request(() -> {
            try {
                event.getMessage().getChannel().sendFile(fileStorage.remFiles.get(picToSend));
            } catch (MissingPermissionsException |DiscordException | IOException e) {
                e.printStackTrace();
            }
        });
    }


    public void ping(MessageReceivedEvent event){
            long startTime = System.nanoTime();

            RequestBuffer.request(() ->  {
                try {
                     IMessage message = event.getMessage().getChannel().sendMessage("Pong!");

                     message.edit(message.getContent() + " (" + ((System.nanoTime() - startTime) / 1000000) + "ms RTT) :ping_pong: ");
                } catch (MissingPermissionsException |DiscordException e) {
                    e.printStackTrace();
                }
            });

    }


    public void wrongCommand(MessageReceivedEvent event){
        RequestBuffer.request(()->{
            try {
                event.getMessage().getChannel().sendMessage("Error; Command Unknown.");
            } catch (MissingPermissionsException |DiscordException e) {
                e.printStackTrace();
            }
        });
    }

    public static String getPre(){
        return pre;
    }

}
