package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.core.EmbedBuilder;

public class PauseCommand extends MusicCommand {
	public PauseCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "pause";
		this.help = "Pauses the current track.";
	}

	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		
		EmbedBuilder embed = EmbedGenerator.makeEmbed();
		if (musicManager.player.getPlayingTrack() == null) {
			embed.setDescription("Cannot pause when nothing is playing.");
	    	event.getTextChannel().sendMessage(embed.build()).queue();
	    }
	    else {
	    	musicManager.player.setPaused(true);
	    	embed.setDescription("Paused track.");
	    	event.getTextChannel().sendMessage(embed.build()).queue();
	    }
	}
}
