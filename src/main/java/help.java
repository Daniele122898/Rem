import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

/**
 * Created by Daniele on 29.09.2016.
 */
public class help {


    public static void help(MessageReceivedEvent event){

        String[] msg = event.getMessage().toString().split("\\s");
        if (msg.length < 2 ) {
            RequestBuffer.request(()->{
            try {
                event.getMessage().getChannel().sendMessage(
                        "                           :information_source: Commands " + "```" +
                        //"---------------------------------------------------------------------- \n\n" +
                        "ping: Check's the RTT ping\n\n" +
                       // "----------------------------------------------------------------------\n" +
                        "rem: Posts random pictures of rem \n\n" +
                        //"----------------------------------------------------------------------\n" +
                        "morning: Rem will say good morning to you ;) \n\n" +
                        //"----------------------------------------------------------------------\n" +
                        "afk: Will set you afk so that when users mention you they will get " +
                        "a message from Rem, stating that you are afk\n\n" +
                        //"----------------------------------------------------------------------\n" +
                        "flip: Flips a coin for you\n\n" +
                        //"----------------------------------------------------------------------\n" +
                        "purge <n>: Will delete the last n messages for you up to 100 " +
                        "messages max (RateLimit) \n\n" +
                        //"----------------------------------------------------------------------\n" +
                        "wh <mention>: Will whitelist the mentioned user\n\n" +
                        //"----------------------------------------------------------------------\n" +
                        "rmwh <mention>: Will remove the mentioned user of the Whitelist\n\n" +
                        //"----------------------------------------------------------------------\n" +
                        "help: Displays this message\n\n" +
                        //"----------------------------------------------------------------------\n" +
                        "help <command>: gives further information about the command\n\n" +
                         "system: gives information about the Ram usage.\n\n" +
                         "about: Find out about everyone who contributed significantly\n\n" +
                        //"---------------------------------------------------------------------- " +
                        "```");
            } catch (MissingPermissionsException | DiscordException e) {
                e.printStackTrace();
            }
            });

        }else{
            //TODO
            switch(msg[1].toLowerCase()) {
                case"wh":
                    try {
                        event.getMessage().getChannel().sendMessage(
                                ":information_source: Commands > wh ```" +
                                        RemListener.getPre()+"wh <mention>\n\n"+
                                        "ParameterType: Required\n\n" +
                                        "Parameter: Mention of the user that should get Whitelisted\n\n" +
                                        "Premissions needed: ServerOwner status or on the Whitelist\n\n" +
                                        "The user added on the Whitelist can add or remove other people aswell as use administrative commands; purge etc\n" +
                                        "```"
                        );
                    } catch (MissingPermissionsException |RateLimitException |DiscordException e) {
                        e.printStackTrace();
                    }
                    break;
                case"ping":
                    try {
                        event.getMessage().getChannel().sendMessage(
                                ":information_source: Commands > ping ```" +
                                        RemListener.getPre()+"ping\n\n"+
                                        "ParameterType: None\n\n" +
                                        "Premissions needed: none\n\n" +
                                        "Checks the RTT ping of the bot\n" +
                                        "```"
                        );
                    } catch (MissingPermissionsException |RateLimitException |DiscordException e) {
                        e.printStackTrace();
                    }
                    break;
                case"rem":
                    try {
                        event.getMessage().getChannel().sendMessage(
                                ":information_source: Commands > rem ```" +
                                        RemListener.getPre()+"rem\n\n"+
                                        "ParameterType: None\n\n" +
                                        "Premissions needed: none\n\n" +
                                        "Will post random pictures of Rem that are stored in the Bot's database.\n\n" +
                                        "Current Databse: " + fileStorage.remPics.length +
                                        "```"
                        );
                    } catch (MissingPermissionsException |RateLimitException |DiscordException e) {
                        e.printStackTrace();
                    }
                    break;
                case"morning":
                    try {
                        event.getMessage().getChannel().sendMessage(
                                ":information_source: Commands > morning ```" +
                                        RemListener.getPre()+"morning \n\n"+
                                        "ParameterType: None\n\n" +
                                        "Premissions needed: none\n\n" +
                                        "The user gets greeted why a kawaii picture of Rem for a good start even in mondays\n" +
                                        "```"
                        );
                    } catch (MissingPermissionsException |RateLimitException |DiscordException e) {
                        e.printStackTrace();
                    }
                    break;
                case"afk":
                    try {
                        event.getMessage().getChannel().sendMessage(
                                ":information_source: Commands > afk ```" +
                                        RemListener.getPre()+"afk \n\n"+
                                        "ParameterType: None\n\n" +
                                        "Premissions needed: none\n\n" +
                                        "If you are not on the list yet you will get added so that every user that @mentions " +
                                        "you will get a Response from Rem stating that you are AFK.\n" +
                                        "If you are already on the list it will delete the entry and reset your AFK state.\n" +
                                        "```"
                        );
                    } catch (MissingPermissionsException |RateLimitException |DiscordException e) {
                        e.printStackTrace();
                    }
                    break;
                case"flip":
                    try {
                        event.getMessage().getChannel().sendMessage(
                                ":information_source: Commands > flip ```" +
                                        RemListener.getPre()+"flip \n\n"+
                                        "ParameterType: None\n\n" +
                                        "Premissions needed: none\n\n" +
                                        "Will flip a coin for you. \nThe 1 is tails, the harp is head\n" +
                                        "```"
                        );
                    } catch (MissingPermissionsException |RateLimitException |DiscordException e) {
                        e.printStackTrace();
                    }
                    break;
                case"purge":
                    try {
                        event.getMessage().getChannel().sendMessage(
                                ":information_source: Commands > purge ```" +
                                        RemListener.getPre()+"purge <n>\n\n"+
                                        "ParameterType: Required\n\n" +
                                        "Parameter: Number of Messages to delete\n\n" +
                                        "Premissions needed: ServerOwner status or on the Whitelist\n\n" +
                                        "Will delete n messages in the current Channel.\n" +
                                        "100 is the maximum at once otherwise the Bot would get RateLimited\n" +
                                        "```"
                        );
                    } catch (MissingPermissionsException |RateLimitException |DiscordException e) {
                        e.printStackTrace();
                    }
                    break;
                case"rmwh":
                    try {
                        event.getMessage().getChannel().sendMessage(
                                ":information_source: Commands > rmwh ```" +
                                        RemListener.getPre()+"rmwh <mention>\n\n"+
                                        "ParameterType: Required\n\n" +
                                        "Parameter: Mention of the user that should get removed\n\n" +
                                        "Premissions needed: ServerOwner status or on the Whitelist\n\n" +
                                        "The mentioned user will get deleted from the Whitelist, loosing his RemPermissions\n" +
                                        "```"
                        );
                    } catch (MissingPermissionsException |RateLimitException |DiscordException e) {
                        e.printStackTrace();
                    }
                    break;
                case"system":
                    try {
                        event.getMessage().getChannel().sendMessage(
                                ":information_source: Commands > system ```" +
                                        RemListener.getPre()+"system \n\n"+
                                        "ParameterType: None\n\n" +
                                        "Premissions needed: none\n\n" +
                                        "Total Allocated Ram = Total Heap size\n" +
                                        "Used allocated Ram = actual used Ram\n" +
                                        "Free allocated Ram = Free space on heap\n" +
                                        "Max. usable Ram = Max ram the JVM can use to increae heapsize\n" +
                                        "```" +
                                        "Love to 0xFADED who explained this :heart:"

                        );
                    } catch (MissingPermissionsException |RateLimitException |DiscordException e) {
                        e.printStackTrace();
                    }
                    break;
                case"about":
                    try {
                        event.getMessage().getChannel().sendMessage(
                                ":information_source: Commands > about ```" +
                                        RemListener.getPre()+"h about \n\n"+
                                        "A huge thanks to:\n\n" +
                                        "0xFADED\n" +
                                        "Igloo\n" +
                                        "dec\n" +
                                        "Arsen4J\n" +
                                        "chrislo27\n\n" +
                                        "Without these awesome people this bot would not be possible" +
                                        "```" +
                                        "Love you guys :heart:"

                        );
                    } catch (MissingPermissionsException |RateLimitException |DiscordException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                RequestBuffer.request(() -> {
                    try {
                        event.getMessage().getChannel().sendMessage("Not added yet :/");
                    } catch (MissingPermissionsException | DiscordException e) {
                        e.printStackTrace();
                    }

                });
                    break;
            }
        }

    }
}
