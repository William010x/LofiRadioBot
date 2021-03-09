package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;

public class SansCommand extends MusicCommand {
	public SansCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "sans";
		this.aliases = new String[] {"undertale", "megalovania"};
		this.help = "You feel like you're going to have a bad time.";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		
		if (musicManager.player.getPlayingTrack() != null) {
			EmbedBuilder embed = EmbedGenerator.makeError();
			embed.setDescription("Cannot Sans when tracks are queued.");
			event.getTextChannel().sendMessage(embed.build()).queue();
		}
		else {
			musicPlayer.loadAndPlay(event, musicManager, "https://www.youtube.com/watch?v=cFNcpicZ6vo", true);
		}
	}

}
