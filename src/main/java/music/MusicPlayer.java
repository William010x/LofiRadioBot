package music;
import java.util.HashMap;
import java.util.Map;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import constants.EmbedGenerator;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.AudioManager;

public class MusicPlayer {
	private static final int DEFAULT_VOLUME = 50; // Volume ranges from 0-100
	private int oldVolume = DEFAULT_VOLUME;
	
	private final AudioPlayerManager playerManager;
	private final Map<Long, GuildMusicManager> musicManagers;
	
	
	public MusicPlayer() {
		this.musicManagers = new HashMap<>();
		this.playerManager = new DefaultAudioPlayerManager();
		// Registers YouTube, SoundCloud, Bandcamp, Vimeo, Twitch, Http
		AudioSourceManagers.registerRemoteSources(playerManager);
		// Registers local
	    AudioSourceManagers.registerLocalSource(playerManager);
	}
	
	/**
	 * Bot will join voice channel of the member who issued the command
	 * @param event Member's command to make bot to join channel that their voice channel 
	 * @param guild The discord server that the bot will join
	 * @return true if bot successfully joined a voice channel
	 */
	boolean join(CommandEvent event, Guild guild) {
		AudioManager audioManager = guild.getAudioManager();
		VoiceChannel voiceChannel = event.getMember().getVoiceState().getChannel();
		if (voiceChannel == null) {
			EmbedBuilder embed = EmbedGenerator.makeError();
			embed.setDescription("You must be in a voice channel.");
			event.getTextChannel().sendMessage(embed.build()).queue();
			return false;
		}
		else {
			audioManager.openAudioConnection(voiceChannel);
			return true;
		}
	}
	
	// aka getGuildAudioPlayer
	synchronized GuildMusicManager getMusicManager(Guild guild) {
		long guildId = Long.parseLong(guild.getId());
		GuildMusicManager musicManager = musicManagers.get(guildId);
		
		if (musicManager == null) {
			musicManager = new GuildMusicManager(playerManager, guild);
			musicManager.player.setVolume(DEFAULT_VOLUME);
			musicManagers.put(guildId, musicManager);
		}

		guild.getAudioManager().setSendingHandler(musicManager.sendHandler);
		return musicManager;
	}
	
	int getOldVolume() {
		return this.oldVolume;
	}
	
	void setOldVolume(int oldVolume) {
		this.oldVolume = oldVolume;
	}
	
	/**
	 * Add track to the music player's queue.
	 * @param event        The command that was issued to add audio to the queue
	 * @param guild        Server that the audio will be added to
	 * @param musicManager Holder for music player and scheduler
	 * @param track        The audio file to add to the queue
	 * @return true if track was successfully added to queue.
	 */
	private boolean play(CommandEvent event, Guild guild, GuildMusicManager musicManager, AudioTrack track) {
		AudioManager audioManager = guild.getAudioManager();
		track.setUserData(event.getAuthor().getName());
		if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
	    	if (join(event, audioManager.getGuild())) {
	    		musicManager.scheduler.queue(track);
	    	}
	    	else {
	    		return false;
	    	}
	    }
		else {
			musicManager.scheduler.queue(track);
		}
		return true;
	}

	/**
	 * Attempts to load and play a track.
	 * @param event        The command that was issued to add audio to the queue
	 * @param musicManager Holder for music player and scheduler
	 * @param url          The URL to an audio track OR a phrase to search on YouTube
	 * @param special      True if the audio is a special command (no message when added)
	 */
	void loadAndPlay(CommandEvent event, GuildMusicManager musicManager, final String url, boolean special) {
		playerManager.loadItemOrdered(musicManager, url, new ResultHandler(event, false, special));
	}
	
	private class ResultHandler implements AudioLoadResultHandler {
		private final boolean ytsearch;
		private final CommandEvent event;
        private final TextChannel channel;
        private final String url;
        private final GuildMusicManager musicManager;
        private final boolean special;
        
        private ResultHandler(CommandEvent event, boolean ytsearch, boolean special)
        {
        	this.ytsearch = ytsearch;
        	this.event = event;
            this.channel = event.getTextChannel();
            this.url = event.getArgs(); // Alternative to event.getMessage().getContentDisplay().split(" ", 2)[1];
            this.musicManager = getMusicManager(event.getGuild());
            this.special = special;
        }
		
		@Override
        public void trackLoaded(AudioTrack track) {
			if (special) {
				play(event, channel.getGuild(), musicManager, track);
			}
			else {
				if (play(event, channel.getGuild(), musicManager, track)) {
					EmbedBuilder embed = EmbedGenerator.makeEmbed();
					embed.setImage("https://img.youtube.com/vi/" + track.getInfo().identifier + "/maxresdefault.jpg");
					embed.setAuthor("Added to queue", null, "http://icons.iconarchive.com/icons/dtafalonso/yosemite-flat/512/Music-icon.png");
					embed.setTitle(track.getInfo().title, track.getInfo().uri);
					
					embed.addField("Uploaded by:", track.getInfo().author, true);
					embed.addField("Added by:", track.getUserData().toString(), true);
					
					channel.sendMessage(embed.build()).queue();
				}
    		}
        }

        @Override
        public void playlistLoaded(AudioPlaylist playlist) {
        	AudioTrack firstTrack = playlist.getSelectedTrack();

        	if (firstTrack == null) {
        		firstTrack = playlist.getTracks().get(0);
        	}

        	if (special) {
				play(event, channel.getGuild(), musicManager, firstTrack);
			}
			else {
				if (play(event, channel.getGuild(), musicManager, firstTrack)) {
					EmbedBuilder embed = EmbedGenerator.makeEmbed();
					embed.setImage("https://img.youtube.com/vi/" + firstTrack.getInfo().identifier + "/maxresdefault.jpg");
					embed.setAuthor("Added to queue", null, "http://icons.iconarchive.com/icons/dtafalonso/yosemite-flat/512/Music-icon.png");
					embed.setTitle(firstTrack.getInfo().title, firstTrack.getInfo().uri);
					
					embed.addField("Uploaded by:", firstTrack.getInfo().author, true);
					embed.addField("Added by:", firstTrack.getUserData().toString(), true);
					
					channel.sendMessage(embed.build()).queue();
				}
        	}
        }

        @Override
        public void noMatches() {
        	if (ytsearch) {
        		EmbedBuilder embed = EmbedGenerator.makeError();
        		embed.setDescription("Nothing found by " + url);
        		channel.sendMessage(embed.build()).queue();
        	}
        	else {
        		playerManager.loadItemOrdered(event.getTextChannel(), "ytsearch:" + url, new ResultHandler(event, true, special));
        	}
        }

        @Override
        public void loadFailed(FriendlyException exception) {
        	EmbedBuilder embed = EmbedGenerator.makeError();
        	embed.setDescription("Could not play: " + exception.getMessage());
        	channel.sendMessage(embed.build()).queue();
        }
	}
}