package com.serenity.rem.modules;


import net.dv8tion.d4j.player.MusicPlayer;
import net.dv8tion.jda.player.Playlist;
import net.dv8tion.jda.player.source.AudioInfo;
import net.dv8tion.jda.player.source.AudioSource;
import net.dv8tion.jda.player.source.AudioTimestamp;
import sx.blah.discord.handle.audio.IAudioManager;
import sx.blah.discord.handle.audio.impl.DefaultProvider;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Daniele on 15.10.2016.
 */
public class MusicV2 {
    public final float DEFAULT_VOLUME = 0.35f;
    IAudioManager manager;

    MusicPlayer player;

    public void init(MessageReceivedEvent e){
        String[] msg = e.getMessage().toString().split("\\s");
        manager = e.getMessage().getGuild().getAudioManager();
        if(manager.getAudioProvider() instanceof DefaultProvider){
            player = new MusicPlayer();
            player.setVolume(DEFAULT_VOLUME);
            manager.setAudioProvider(player);
        }else{
            player = (MusicPlayer) manager.getAudioProvider();
        }

        if(msg.length > 1){
            switch (msg[1]){
                case"vol":
                case"volume":
                    setVolume(e);
                    break;
                case"list":
                case"queue":
                    showList(e);
                    break;
                case"now":
                case"nowplaying":
                    nowPlaying(e);
                    break;
                case"join":
                case"summon":
                    joinVoice(e);
                    break;
                case"leave":
                    leaveVoice(e);
                    break;
                case"skip":
                    skipSong(e);
                    break;
                case"repeat":
                    setRepeat(e);
                    break;
                case"shuffle":
                    setShuffle(e);
                    break;
                case"reset":
                    reset(e);
                    break;
                case"play":
                case"add":
                    play(e);
                    break;
                case"pause":
                    setPause(e);
                    break;
                case"stop":
                    stop(e);
                    break;
                case"help":
                case"h":
                    Help.musicHelp(e);
                    break;
                case"clear":
                    resetQueue(e);
                    break;
                case"restart":
                    restart(e);
                    break;
                default:
                    MsgUtils.sendMsg(e, "Music Command unknown!");
                    break;
            }
        }else{
            MsgUtils.sendMsg(e,"Parameter!");
        }

    }

    public void updater(MessageReceivedEvent e){
        //for(int i=0; i<e.getClient().getConnectedVoiceChannels().size(); i++){
            //if(e.getClient().getConnectedVoiceChannels().get(i).getConnectedUsers().isEmpty()){
            /*event.getClient().getGuilds().forEach(guild -> {
                guild.getChannels().forEach(channel -> { channels.addAndGet(1); });
            });*/

            e.getClient().getConnectedVoiceChannels().forEach(voice ->{
                if(voice.getConnectedUsers().size() < 2) {
                    player.pause();
                    voice.leave();
                    MsgUtils.sendMsg(e, "Player paused and left Voice due to no one being there anymore");

                }
            });
            //AtomicInteger deafCounter = new AtomicInteger(0);
            e.getClient().getConnectedVoiceChannels().forEach(users->{
                int ppl = users.getConnectedUsers().size();
                users.getConnectedUsers().forEach(user->{
                    int deafCounter2 = 0;
                    if(user.isDeafLocally())
                        //deafCounter.addAndGet(1);
                    deafCounter2++;
                    if(ppl - 1== deafCounter2) {
                        player.pause();
                        MsgUtils.sendMsg(e,"Player Paused due to everyone being deaf");
                    }
                });
            });

            //}
        //}
    }

    private void resetQueue(MessageReceivedEvent e){
        player.getAudioQueue().clear();
        MsgUtils.sendMsg(e, "Cleared the Queue list!");
    }

    private void setPause(MessageReceivedEvent e){
        player.pause();
        MsgUtils.sendMsg(e, "Playback has been paused.");
    }

    private void stop(MessageReceivedEvent e){
        player.stop();
        MsgUtils.sendMsg(e, "Playback has been completely stopped.");
    }

    private void restart(MessageReceivedEvent e){
        if(player.isStopped()){
            if(player.getPreviousAudioSource() != null){
                player.reload(true);
                MsgUtils.sendMsg(e, "The previous song has been restarted");
            }else{
                MsgUtils.sendMsg(e, "No song was previously played.");
            }
        }else{
            player.reload(true);
            MsgUtils.sendMsg(e,"The currently playing song has been restarted!");
        }
    }

    private void play(MessageReceivedEvent e){
        String[] msg = e.getMessage().toString().split("\\s");
        if(msg.length == 2){
            if(player.isPlaying()){
                MsgUtils.sendMsg(e,"Player is already playing!");
                return;
            }else if(player.isPaused()){
                player.play();
                MsgUtils.sendMsg(e,"Playback has been resumed!");
            }else{
                if(player.getAudioQueue().isEmpty())
                    MsgUtils.sendMsg(e, "The current audio queue is empty! Add something to the queue first!");
                else{
                    player.play();
                    MsgUtils.sendMsg(e, "Player has started playing!");
                }
            } //look what we need here w8
        }else if(msg.length == 3){
            String infoMsg = ""; //btw
            String url = msg[2];
            //leave it as it is xd yeah :D
            //you gonna move it later to linux? yes ubutnu server 16.04 i think i had some issues with it but i fixed them lel no XD fuck.
            //you just need to install differently ffmpeg and ffprobe

            Playlist playlist = Playlist.getPlaylist(url);
            List<AudioSource> sources = new LinkedList<>(playlist.getSources());

            //AudioSource source = new RemoteSource(url);
//          AudioSource source = new LocalSource(new File(url));
//          AudioInfo info = source.getInfo();   //Preload the audio info.
            if(sources.size() > 1){
                MsgUtils.sendMsg(e, "Found a playlist with **" + sources.size()+"** entries \n" +
                        "Proceeding to gather information and queue sources. This may take some time...");
                final MusicPlayer fPlayer = player; //TODO UNDERSTAND THIS SHIT!
                Thread thread = new Thread(){
                    int count=0;
                    @Override
                    public void run(){
                        for(Iterator<AudioSource> it = sources.iterator(); it.hasNext();){
                            AudioSource source = it.next();
                            AudioInfo info = source.getInfo();
                            List<AudioSource> queue = fPlayer.getAudioQueue();
                            if(queue.size() > 50){
                                MsgUtils.sendMsg(e,"The max of 50 was reached. The queue can only hold up to 100 songs for better performance across all guilds.");
                                if(info.getError() == null) {
                                    queue.add(source);
                                    if (!fPlayer.isPlaying())
                                        fPlayer.play();
                                }
                                break;
                            }else if(info.getError() == null){
                                queue.add(source);
                                if(!fPlayer.isPlaying())
                                    fPlayer.play();
                            }

                        }
                        MsgUtils.sendMsg(e,"Finished queing provided playlist. Successfully queued ** " + sources.size()+"** sources");
                    }
                };
                thread.start();
            }else{
                AudioSource source = sources.get(0);
                AudioInfo info = source.getInfo();
                if(info.getError() == null){
                    player.getAudioQueue().add(source);
                    infoMsg += "The provided URL has been added to the queue";
                    if(!player.isPlaying()){
                        player.play();
                        infoMsg += " and the player has started playing";
                    }
                    MsgUtils.sendMsg(e, infoMsg + ".");
                }
                else{
                    MsgUtils.sendMsg(e,"There was an error while loading the provided URL. \n" +
                            "Error: "+info.getError());
                }
            }
        }
    }

    private void reset(MessageReceivedEvent e){
        player.stop();
        player = new MusicPlayer();
        player.setVolume(DEFAULT_VOLUME);
        manager.setAudioProvider(player);
        MsgUtils.sendMsg(e, "Music player has been completely reset!");
    }

    private void setShuffle(MessageReceivedEvent e){
        if(player.isShuffle()){
            player.setShuffle(false);
            MsgUtils.sendMsg(e,"The player has been set to **not** shuffle.");
        }else{
            player.setShuffle(true);
            MsgUtils.sendMsg(e,"The player has been set to shuffle.");
        }
    }

    private void setRepeat(MessageReceivedEvent e){
        if(player.isRepeat()){
            player.setRepeat(false);
            MsgUtils.sendMsg(e, "The player has been set to **not** repeat.");
        }else{
            player.setRepeat(true);
            MsgUtils.sendMsg(e, "The player has been set to repeat.");
        }
    }

    private void skipSong(MessageReceivedEvent e){
        player.skipToNext();
        MsgUtils.sendMsg(e, "Skipped the current song.");
    }

    private void leaveVoice(MessageReceivedEvent e){
        IVoiceChannel channel = e.getMessage().getAuthor().getConnectedVoiceChannels().get(0);
        if(channel.isConnected()){
            channel.leave();
        }else{
            MsgUtils.sendMsg(e, "Bot is not connected to a Voice Channel!");
        }
    }

    private void joinVoice(MessageReceivedEvent e){
        if(e.getMessage().getAuthor().getConnectedVoiceChannels().isEmpty()){
            MsgUtils.sendMsg(e,"You must be connected to a Voice channel!");
            return;
        }else {
            IVoiceChannel channel = e.getMessage().getAuthor().getConnectedVoiceChannels().get(0);
            try {
                channel.join();
            } catch (MissingPermissionsException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void nowPlaying(MessageReceivedEvent e){
        if(player.isPlaying()){
            AudioTimestamp currentTime = player.getCurrentTimestamp();
            AudioInfo info = player.getCurrentAudioSource().getInfo();
            if(info.getError() == null){
                MsgUtils.sendMsg(e,"**Playing:** "+info.getTitle()+"\n" +
                        "**Time:**    ["+currentTime.getTimestamp() + " / " + info.getDuration().getTimestamp() + "]");
            }else{
                MsgUtils.sendMsg(e, "" +
                        "**Playing** Info Error. Known source: " +  player.getCurrentAudioSource().getSource() + "\n" +
                        "**Time**     ["+currentTime.getTimestamp() + " / (N/A)]");
            }
        }else{
            MsgUtils.sendMsg(e,"The player is currently not playing anything!");
        }
    }

    private void showList(MessageReceivedEvent e){
        List<AudioSource> queue = player.getAudioQueue();
        if(queue.isEmpty()){
            MsgUtils.sendMsg(e, "The queue is currently empty!");
            return;
        }

        MessageBuilder builder = new MessageBuilder(e.getMessage().getClient());
        builder.appendContent("__Current Queue. Entries: " + queue.size() + " __\n");
        for(int i = 0; i<queue.size() && i < 10; i++){
            AudioInfo info = queue.get(i).getInfo();
            if(info == null)
                builder.appendContent("*Could not get info for this song.*");
            else{
                AudioTimestamp duration = info.getDuration();
                builder.appendContent("`[");
                if(duration == null)
                    builder.appendContent("N/A");
                else
                    builder.appendContent(duration.getTimestamp());
                builder.appendContent("] " + info.getTitle() + "`\n");
            }
        }

        boolean error = false;
        int totalSeconds = 0;
        for(AudioSource source : queue){
            AudioInfo info = source.getInfo();
            if(info == null || info.getDuration() == null){
                error = true;
                continue;
            }
            totalSeconds += info.getDuration().getTotalSeconds();
        }
        builder.appendContent("\nTotal Queue Time Length: " + AudioTimestamp.fromSeconds(totalSeconds).getTimestamp());
        if(error)
            builder.appendContent("`An error occured calculating total time. Might not be completely valid.");
        RequestBuffer.request(()->{
            try {
                builder.withChannel(e.getMessage().getChannel()).build();
            }  catch (DiscordException | MissingPermissionsException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void setVolume(MessageReceivedEvent e){
        String[] msg = e.getMessage().toString().toLowerCase().split("\\s");
        if(msg.length==3){
            if(msg[2].matches("\\d+")){
                float vol = Float.parseFloat(msg[2])/100;
                if(vol > 1)
                    vol = 1;
                if(vol < 0)
                    vol = 0;
                player.setVolume(vol);
                MsgUtils.sendMsg(e, "Volume was changed to `"+vol*100+"`");
            }else{
                MsgUtils.sendMsg(e, "Only Numbers allowed!");
            }
        }else{
            MsgUtils.sendMsg(e, "Enter a desired Volume level! (0-100)");
        }
    }

}
