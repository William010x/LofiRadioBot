package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;

public class MuteCommand extends MusicCommand {
	public MuteCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "mute";
		this.aliases = new String[] {"deafen"};
		this.help = "Mutes the player.";
	}

	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		
		EmbedBuilder embed = EmbedGenerator.makeEmbed();
		if (musicManager.player.getVolume() == 0 && musicPlayer.getOldVolume() != 0) {
			musicManager.player.setVolume(musicPlayer.getOldVolume());
			embed.setDescription("Player unmuted.");
			event.getTextChannel().sendMessage(embed.build()).queue();
		}
		else {
			musicPlayer.setOldVolume(musicManager.player.getVolume());
			musicManager.player.setVolume(0);
			embed.setDescription("Player muted.");
			event.getTextChannel().sendMessage(embed.build()).queue();
		}
	}

}
