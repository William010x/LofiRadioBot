package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.core.EmbedBuilder;

public class PlayCommand extends MusicCommand {
	public PlayCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "play";
		this.aliases = new String[] {"start"};
		this.help = "Plays a track with a given name or URL. Also can be used to resume player.";
		this.arguments = "[song|URL]";
	}

	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		String[] command = event.getMessage().getContentDisplay().split(" ", 2);
		
		if (command.length == 1) {
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
        else {
        	musicPlayer.loadAndPlay(event, musicManager, command[1], false);
        }
	}
}
