import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

/**
 * Created by Daniele on 01.10.2016.
 */
public class ReZero {
    private static int level = 0;
    private static int points;
    private static IMessage Mess;
    public static void initialize(MessageReceivedEvent e){
        if(level == 0){
            start(e);
        }
    }

    private static void start(MessageReceivedEvent e){
        String[] msg = e.getMessage().toString().toLowerCase().split("\\s");
        if(msg.length ==1) {
            RequestBuffer.request(() -> {
                try {
                    Mess = e.getMessage().getChannel().sendMessage(
                            ":book: __**Re:Zero Chapter 1**__\n\n" +
                                    "***In a gas station;***\n" +
                                    "You are reading some mangas as you decide to buy some popcorn. \n\n" +
                                    "*\"363 Yen please\"* says the shopkeeper\n" +
                                    "You give him the money and walk out of the gas station. Things start to feel weird\n" +
                                    "Your vision starts to turn blurry, you rub your eyes and as you reopen them you see\n" +
                                    "lizards, walking as if they were human. You stand in a square in medieval times. \n\n" +
                                    "**This is the Start of Re:Zero\n **" +
                                    "To continue type ;re con!"
                    );
                } catch (MissingPermissionsException | DiscordException ex) {
                    ex.printStackTrace();
                }
            });
            points += 10;
        }else if(msg[1].equals("con")){
            try {
                Mess.delete();
            } catch (MissingPermissionsException |RateLimitException | DiscordException e1) {
                e1.printStackTrace();
            }
            chap1_1(e);
        }


    }

    private static void chap1_1(MessageReceivedEvent e){
        RequestBuffer.request(()->{
            try {
                Mess = e.getMessage().getChannel().sendMessage(
                        ":book: __**Re:Zero Chapter 1_1**__\n\n" +
                                "Work in progress..."
                );
            } catch (MissingPermissionsException |DiscordException ex) {
                ex.printStackTrace();
            }
        });
    }
}
