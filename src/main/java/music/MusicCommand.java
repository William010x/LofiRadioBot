package music;

import com.jagrosh.jdautilities.command.Command;

public abstract class MusicCommand extends Command {
	protected MusicPlayer musicPlayer;
	
	public MusicCommand(MusicPlayer musicPlayer) {
		this.musicPlayer = musicPlayer;
		this.category = new Category("Music");
	}
}
