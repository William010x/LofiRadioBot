package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.core.EmbedBuilder;

public class StopCommand extends MusicCommand {
	public StopCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "stop";
		this.help = "Stops the current track and clears the queue.";
	}

	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		
		musicManager.scheduler.clear();
		musicManager.player.stopTrack();
		musicManager.player.setPaused(false);
		
		EmbedBuilder embed = EmbedGenerator.makeEmbed();
		embed.setDescription("Stopped player and cleared queue.");
		event.getTextChannel().sendMessage(embed.build()).queue();
		
		event.getGuild().getAudioManager().setSendingHandler(null);
		event.getGuild().getAudioManager().closeAudioConnection();
	}

}
