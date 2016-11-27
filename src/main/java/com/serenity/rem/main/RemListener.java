package com.serenity.rem.main;

import com.serenity.rem.akkt.Attk;
import com.serenity.rem.modules.*;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


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

    /*@EventSubscriber //TODO FUCKING WELCOM MESSAGE
    public void userEvent(UserJoinEvent j, UserLeaveEvent l){
        String joiner = j.getUser().getName();
        j.getGuild().getChannelsByName()

    }*/

    @EventSubscriber
    public void switchMessage(MessageReceivedEvent event){
        m2.updater(event);
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
                    cctv(event);
                    break;
                case"rem":
                    randomRem(event);
                    cctv(event);
                    break;
                case"morning":
                    remMorning(event);
                    cctv(event);
                    break;
                case"afk":
                    AFKcommand.afk(event);
                    cctv(event);
                    break;
                case"flip":
                    coinFlip(event);
                    cctv(event);
                    break;
                case"purge":
                    Purge.purge(event);
                    cctv(event);
                    break;
                case"wh":
                    Whitelist.addWhite(event);
                    cctv(event);
                    break;
                case"rmwh":
                    Whitelist.rmWhite(event);
                    cctv(event);
                    break;
                case"help":
                case"h":
                    Help.help(event);
                    cctv(event);
                    break;
                case"system":
                case"sys":
                    SystemRem.sysInfo(event);
                    cctv(event);
                    break;
                case"git":
                case"github":
                    gitHub(event);
                    cctv(event);
                    break;
                case"invite":
                case"inv":
                    invBot(event);
                    cctv(event);
                    break;
                case"re":
                    reZero.initialize(event);
                    cctv(event);
                    break;
                case"ak":
                    AttkM.start(event);
                    cctv(event);
                    break;
                case"shutdown":
                    shutdown(event);
                    cctv(event);
                    break;
                case"m":
                    m2.init(event);
                    cctv(event);
                    break;
                /*case"gc":
                    garbage(event);
                    cctv(event);
                    break;
                case"admin":
                    admininfo(event);
                    cctv(event);
                    break;*/
                case"swag":
                    RequestBuffer.request(()->{
                    try {
                        Timer timer = new Timer();
                        IMessage Message = event.getMessage().getChannel().sendMessage("( ͡° ͜ʖ ͡°)>⌐■-■ ");
                        timer.schedule(new TimerTask() {//TODO TIMED TASK
                            @Override
                            public void run() {
                                RequestBuffer.request(()-> {
                                    try {
                                        Message.edit("( ͡⌐■ ͜ʖ ͡-■)");
                                    } catch (MissingPermissionsException | DiscordException e) {
                                        e.printStackTrace();
                                    }
                                });
                            }
                        }, 1000);
                    } catch (MissingPermissionsException | DiscordException e) {
                        e.printStackTrace();
                    }
                    });
                    cctv(event);
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


    private void admininfo(MessageReceivedEvent e){
        if(e.getMessage().getAuthor().getID().equals("192750776005689344")) {
            MessageBuilder msgB = new MessageBuilder(e.getClient()).withChannel(e.getMessage().getChannel());
            RequestBuffer.request(()->{
                msgB.appendContent("Server Name: "+e.getMessage().getGuild().getName()+"\n" +
                        "Server ID: "+ e.getMessage().getGuild().getID()+"\n" +
                        "Channel ID: `" + e.getMessage().getChannel().toString()+"`");
                for (int i = 0; i<main.getClient().getGuilds().size();i++){
                    msgB.appendContent("\nGuild Name: " + main.getClient().getGuilds().get(i).getName()+"\n");
                }
                try {
                    msgB.send();
                } catch (DiscordException | MissingPermissionsException e1) {
                    e1.printStackTrace();
                }
            });
        }
    }

    private void cctv(MessageReceivedEvent e){
        IChannel channel = main.getClient().getGuildByID("229310654517870592").getChannelByID("249862331805204480");
        MessageBuilder msgB = new MessageBuilder(main.getClient()).withChannel(channel);
        msgB.appendContent(":information_source: CCTV ``` \n" +
                "Guild Name: "+ e.getMessage().getGuild().getName()+"\n"+
                "Guild ID: "+ e.getMessage().getGuild().getID() +"\n"+
                "User Name: "+ e.getMessage().getAuthor().getName() +"\n"+
                "User ID: "+ e.getMessage().getAuthor().getID() +"\n"+
                "Date: "+ e.getMessage().getTimestamp().toString()+"\n"+
                "Message: "+e.getMessage().toString()+"\n"+
                "```");
        RequestBuffer.request(()->{
            try {
                msgB.send();
            } catch (DiscordException | MissingPermissionsException e1) {
                e1.printStackTrace();
            }
        });

    }
/*
    /*private void garbage(MessageReceivedEvent e){
        if(e.getMessage().getAuthor().getID().equals("192750776005689344")) {
            System.gc();
            MsgUtils.sendMsg(e, "GC executed");
        }else{
            MsgUtils.sendMsg(e, "Only the Bot owner has permissions to use this command!");
        }
    }*/

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
