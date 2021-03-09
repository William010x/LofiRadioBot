package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;

public class AirhornCommand extends MusicCommand {
	public AirhornCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "airhorn";
		this.aliases = new String[] {"horn"};
		this.help = "Plays airhorn sound effect.";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		
		if (musicManager.player.getPlayingTrack() != null) {
			EmbedBuilder embed = EmbedGenerator.makeError();
			embed.setDescription("Cannot airhorn when tracks are queued.");
			event.getTextChannel().sendMessage(embed.build()).queue();
		}
		else {
			musicPlayer.loadAndPlay(event, musicManager, "https://www.youtube.com/watch?v=2Tt04ZSlbZ0", true);
		}
	}

}
