import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

/**
 * Created by Daniele on 27.09.2016.
 */
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

            }
        }
    }


    public void ping(MessageReceivedEvent event){
            long startTime = System.nanoTime();

            RequestBuffer.request(() ->  {
                try {
                     IMessage message = event.getMessage().getChannel().sendMessage("Pong!");

                     message.edit(message.getContent() + " (" + ((System.nanoTime() - startTime) / 1000000) + "ms RTT)");
                } catch (MissingPermissionsException e) {
                    e.printStackTrace();
                } catch (RateLimitException e) {
                    e.printStackTrace();
                } catch (DiscordException e) {
                    e.printStackTrace();
                }
            });

    }

}
