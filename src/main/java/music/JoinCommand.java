package music;

import com.jagrosh.jdautilities.command.CommandEvent;

public class JoinCommand extends MusicCommand {
	public JoinCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "join";
		this.aliases = new String[] {"summon", "connect"};
		this.help = "Makes the bot join the user's voice channel.";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		musicPlayer.join(event, event.getGuild());
	}
}
