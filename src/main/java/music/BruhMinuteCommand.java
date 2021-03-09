package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;

public class BruhMinuteCommand extends MusicCommand {
	public BruhMinuteCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "bruhminute";
		this.aliases = new String[] {"bruhmoments", "bruhs"};
		this.help = "When you have a bruh minute.";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		
		if (musicManager.player.getPlayingTrack() != null) {
			EmbedBuilder embed = EmbedGenerator.makeError();
			embed.setDescription("Cannot bruh when tracks are queued.");
			event.getTextChannel().sendMessage(embed.build()).queue();
		}
		else {
			for (int i = 0; i < 60; i++) {
				musicPlayer.loadAndPlay(event, musicManager, "https://www.youtube.com/watch?v=2ZIpFytCSVc", true);
			}
		}
	}

}
