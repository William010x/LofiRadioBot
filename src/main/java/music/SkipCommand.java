package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;

public class SkipCommand extends MusicCommand {
	public SkipCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "skip";
		this.aliases = new String[] {"next", "forceskip"};
		this.help = "Skips the current track.";
	}

	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		
	    if (musicManager.player.getPlayingTrack() == null) {
	    	EmbedBuilder embed = EmbedGenerator.makeError();
	    	embed.setDescription("Nothing is playing right now.");
	    	event.getTextChannel().sendMessage(embed.build()).queue();
	    }
	    else {
	    	musicManager.scheduler.nextTrack();
	    	EmbedBuilder embed = EmbedGenerator.makeEmbed();
	    	embed.setDescription("Skipped to next track.");
	    	event.getTextChannel().sendMessage(embed.build()).queue();
	    }
	}

}
