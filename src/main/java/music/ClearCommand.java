package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;

public class ClearCommand extends MusicCommand {
	public ClearCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "clear";
		this.aliases = new String[] {"reset", "empty"};
		this.help = "Clears all tracks in the queue.";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		
		musicManager.scheduler.clear();
		EmbedBuilder embed = EmbedGenerator.makeEmbed();
		embed.setDescription("Queue has been cleared.");
		event.getTextChannel().sendMessage(embed.build()).queue();
	}
	
}
