import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

import java.io.IOException;


public class RemListener {

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) { // This method is called when the ReadyEvent is dispatched

    }
    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) { // This method is NOT called because it doesn't have the @EventSubscriber annotation

    }

    @EventSubscriber
    public void switchMessage(MessageReceivedEvent event){
        if(event.getMessage().getAuthor().isBot()) {
            return;
        }
        String msg = event.getMessage().getContent();
        String pre = "+";
        if(msg.startsWith(pre)) {
            switch (msg.substring(pre.length())) {
                case "ping":
                    ping(event);
                    break;
                case"rem":
                    randomRem(event);
                    break;
                case"morning":
                    remMorning(event);
                    break;
                default:
                    wrongCommand(event);
                    break;

            }
        }
    }

    public void remMorning(MessageReceivedEvent event){
        RequestBuffer.request(() ->{
            try {
                event.getMessage().getChannel().sendFile(fileStorage.morningRem);
                event.getMessage().reply("Good Morning");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MissingPermissionsException e) {
                e.printStackTrace();
            } catch (DiscordException e) {
                e.printStackTrace();
            }
        });

    }

    public void randomRem(MessageReceivedEvent event){
        int picToSend = RandomNumberGen.getRandint(fileStorage.remPics.length);
        RequestBuffer.request(() -> {
            try {
                event.getMessage().getChannel().sendFile(fileStorage.remFiles.get(picToSend));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MissingPermissionsException e) {
                e.printStackTrace();
            } catch (RateLimitException e) {
                e.printStackTrace();
            } catch (DiscordException e) {
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
                } catch (MissingPermissionsException e) {
                    e.printStackTrace();
                } catch (RateLimitException e) {
                    e.printStackTrace();
                } catch (DiscordException e) {
                    e.printStackTrace();
                }
            });

    }

    public void wrongCommand(MessageReceivedEvent event){
        RequestBuffer.request(()->{
            try {
                event.getMessage().getChannel().sendMessage("Error; Command Unknown.");
            } catch (MissingPermissionsException e) {
                e.printStackTrace();
            } catch (DiscordException e) {
                e.printStackTrace();
            }
        });
    }

}
