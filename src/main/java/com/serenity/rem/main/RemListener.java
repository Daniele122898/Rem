package com.serenity.rem.main;

import com.serenity.rem.akkt.Attk;
import com.serenity.rem.modules.*;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

import java.io.IOException;


public class RemListener {

    private static String pre;
    //private static AtomicInteger channels = new AtomicInteger(0);
    //private static AtomicInteger users = new AtomicInteger(0);
    //private static int servers;
    private Attk AttkM = new Attk();
    private ReZero reZero = new ReZero();
    private RemBot main = new RemBot();
    private MusicV2 m2 = new MusicV2();

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) { // This method is called when the ReadyEvent is dispatched
        System.out.println("BOT READY!");
        AFKcommand.loadList();
        FileStorage.storage();
        Whitelist.load();
        AttkM.loadJason();
        main.getClient().changeStatus(Status.game(";h for help"));


        /*
        servers = event.getClient().getGuilds().size();
        //final int[] channels = {0};
        //final int[] users = {0};


        event.getClient().getGuilds().forEach(guild -> {
            guild.getChannels().forEach(channel -> { channels.addAndGet(1); });
        });


        event.getClient().getGuilds().forEach(guild -> {
            guild.getUsers().forEach(user -> { users.addAndGet(1); });
        });


        event.getClient().getGuilds().forEach(guild -> {
            guild.getChannels().forEach(channel -> { channels[0]++; });
        });


        event.getClient().getGuilds().forEach(guild ->{
           guild.getUsers().forEach(channel -> {users[0]++;});
        });


        System.out.println("Servers: " + servers);
        //com.serenity.rem.modules.SystemRem.out.println("Channels: " + channels[0]);
        //com.serenity.rem.modules.SystemRem.out.println("Users: " + users[0]);
        System.out.println("Channles: " + channels.get());
        System.out.println("Users: " + users.get());*/

    }
    //@EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) { // This method is NOT called because it doesn't have the @EventSubscriber annotation

    }

    @EventSubscriber
    public void switchMessage(MessageReceivedEvent event){
        AttkM.updateJson();
        if(event.getMessage().getAuthor().isBot()) {
            return;
        }
        /*if(event.getMessage().getChannel().isPrivate()){
            RequestBuffer.request(()-> {
                try {
                    event.getMessage().getChannel().sendMessage("Doesn't work in private channels!");
                } catch (MissingPermissionsException | DiscordException e) {
                    e.printStackTrace();
                }
            });
            return;
            }*/

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
                    Purge.purge(event);
                    break;
                case"wh":
                    Whitelist.addWhite(event);
                    break;
                case"rmwh":
                    Whitelist.rmWhite(event);
                    break;
                case"help":
                case"h":
                    Help.help(event);
                    break;
                case"system":
                case"sys":
                    SystemRem.sysInfo(event);
                    break;
                case"git":
                case"github":
                    gitHub(event);
                    break;
                case"invite":
                case"inv":
                    invBot(event);
                    break;
                case"re":
                    reZero.initialize(event);
                    break;
                case"ak":
                    AttkM.start(event);
                    break;
                case"shutdown":
                    shutdown(event);
                    break;
                case"m":
                    m2.init(event);
                    break;
                default:
                    //wrongCommand(event);
                    break;

            }
        }
        /*if(com.serenity.rem.modules.AFKcommand.checkAFK(event.getMessage().getMentions().toString(), event.getMessage().getGuild().getID())){
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

    private void shutdown(MessageReceivedEvent e){
        if(e.getMessage().getAuthor().getID().equals("192750776005689344")) {
            RequestBuffer.request(() -> {
                try {
                    e.getMessage().getChannel().sendMessage("I wanted to leave anyway :information_desk_person: ");
                } catch (MissingPermissionsException | DiscordException e1) {
                    e1.printStackTrace();
                }
            });
            AttkM.saveToJason();
            System.exit(1);
        } else{
            RequestBuffer.request(() -> {
                try {
                    e.getMessage().getChannel().sendMessage("Only Daddy can turn me off");
                } catch (MissingPermissionsException | DiscordException e1) {
                    e1.printStackTrace();
                }
            });
        }
    }

    public void coinFlip(MessageReceivedEvent event){
        int picToSend = RandomNumberGen.getRandint((FileStorage.coinPics.length));
        RequestBuffer.request(() ->{
            try {
                event.getMessage().getChannel().sendFile(FileStorage.coinFiles.get(picToSend));
            } catch (MissingPermissionsException |DiscordException|IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void remMorning(MessageReceivedEvent event){
        RequestBuffer.request(() ->{
            try {
                event.getMessage().getChannel().sendFile(FileStorage.morningRem);
                event.getMessage().reply("Good Morning");
            } catch (MissingPermissionsException |DiscordException| IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void randomRem(MessageReceivedEvent event){
        int picToSend = RandomNumberGen.getRandint(FileStorage.remPics.length);
        RequestBuffer.request(() -> {
            try {
                event.getMessage().getChannel().sendFile(FileStorage.remFiles.get(picToSend));
            } catch (MissingPermissionsException |DiscordException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void gitHub(MessageReceivedEvent event){
        RequestBuffer.request(()->{
            try {
                event.getMessage().getChannel().sendMessage(
                        "Link to my github:\n"+
                        "https://github.com/Daniele122898/Rem"
                );
            } catch (MissingPermissionsException |DiscordException e) {
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

    public void invBot(MessageReceivedEvent event){
        RequestBuffer.request(()->{
            try {
                event.getMessage().getChannel().sendMessage(
                        "To invite Me just open this link and choose the Server: \n"+
                        "https://discordapp.com/oauth2/authorize?client_id=229287955003473920&scope=bot&permissions=30720");
            } catch (MissingPermissionsException | DiscordException e) {
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
    /*
    public static AtomicInteger getChannels(){
        return channels;
    }

    public static AtomicInteger getUsers(){
        return users;
    }

    public static int getServers(){
        return servers;
    }*/

}
