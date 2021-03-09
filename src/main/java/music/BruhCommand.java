package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;

public class BruhCommand extends MusicCommand {
	public BruhCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "bruh";
		this.aliases = new String[] {"bruhmoment"};
		this.help = "When you have a bruh moment.";
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
			musicPlayer.loadAndPlay(event, musicManager, "https://www.youtube.com/watch?v=2ZIpFytCSVc", true);
		}
	}

}
