package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.ColorGenerator;
import net.dv8tion.jda.api.EmbedBuilder;

public class LeaveCommand extends MusicCommand {
	public LeaveCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "leave";
		this.aliases = new String[] {"exit", "disconnect"};
		this.help = "Makes the bot leave it's current voice channel and pauses tracks.";
	}

	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		
		if (musicManager.player.getPlayingTrack() == null) {
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(ColorGenerator.randomColor());
			embed.setDescription("Player paused.");
			event.getTextChannel().sendMessage(embed.build()).queue();
		}
		
		event.getGuild().getAudioManager().setSendingHandler(null);
		event.getGuild().getAudioManager().closeAudioConnection();
	}
}
