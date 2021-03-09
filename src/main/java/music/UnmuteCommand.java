package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;

public class UnmuteCommand extends MusicCommand {
	public UnmuteCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "unmute";
		this.aliases = new String[] {"undeafen"};
		this.help = "Unmutes the player, setting volume back to before it was muted.";
	}

	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		
		musicManager.player.setVolume(musicPlayer.getOldVolume());
		EmbedBuilder embed = EmbedGenerator.makeEmbed();
		embed.setDescription("Player unmuted.");
		event.getTextChannel().sendMessage(embed.build()).queue();
	}

}
