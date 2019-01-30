package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.core.EmbedBuilder;

public class VolumeCommand extends MusicCommand {
	public VolumeCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "volume";
		this.help = "Changes or displays the current volume.";
		this.arguments = "[0-100]";
	}

	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		String[] command = event.getMessage().getContentDisplay().split(" ", 2);
		
		if (command.length == 2) {
			try {
				int volume = Math.max(0, Math.min(100, Integer.parseInt(command[1])));
				musicManager.player.setVolume(volume);
				EmbedBuilder embed = EmbedGenerator.makeEmbed();
				embed.setDescription("Volume set to " + musicManager.player.getVolume() + "%");
				event.getTextChannel().sendMessage(embed.build()).queue();
			} catch (NumberFormatException e) {
				EmbedBuilder embed = EmbedGenerator.makeError();
				embed.setDescription("Enter an integer value from 0-100");
				event.getTextChannel().sendMessage(embed.build()).queue();
			}
		}
		else {
			EmbedBuilder embed = EmbedGenerator.makeEmbed();
			embed.setDescription("Current volume: " + musicManager.player.getVolume());
			event.getTextChannel().sendMessage(embed.build()).queue();
		}
	}

}
