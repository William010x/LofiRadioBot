package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.core.EmbedBuilder;

public class ResumeCommand extends MusicCommand {
	public ResumeCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "resume";
		this.aliases = new String[] {"unpause"};
		this.help = "Resumes the current track.";
	}

	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		
		if (musicManager.player.getPlayingTrack() == null) {
			EmbedBuilder embed = EmbedGenerator.makeError();
			embed.setDescription("Cannot resume when nothing is playing.");
			event.getTextChannel().sendMessage(embed.build()).queue();
	    }
	    else {
	    	musicManager.player.setPaused(false);
	    	EmbedBuilder embed = EmbedGenerator.makeEmbed();
	    	embed.setDescription("Resumed track.");
	    	event.getTextChannel().sendMessage(embed.build()).queue();
	    }
	}

}
