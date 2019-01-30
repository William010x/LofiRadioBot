package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.core.EmbedBuilder;

public class AddCommand extends MusicCommand {
	public AddCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "add";
		this.aliases = new String[] {"search", "find"};
		this.help = "Searches for a track on YouTube with a given name or URL to play.";
		this.arguments = "<song|URL>";
	}

	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		String[] command = event.getMessage().getContentDisplay().split(" ", 2);
		
		if (command.length == 1) {
			EmbedBuilder embed = EmbedGenerator.makeError();
			embed.setDescription("Provide an argument of a track to search for.");
			event.getTextChannel().sendMessage(embed.build()).queue();
        }
        else {
        	musicPlayer.loadAndPlay(event, musicManager, command[1], false);
        }
	}
}
