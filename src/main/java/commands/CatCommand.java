package commands;

import java.io.File;
import java.util.Random;

import com.jagrosh.jdautilities.command.CommandEvent;

public class CatCommand extends GeneralCommand {
	public CatCommand() {
		super();
		this.name = "cat";
		this.aliases = new String[] {"cats", "kitten"};
		this.help = "Posts an image or gif of a cat.";
	}

	@Override
	protected void execute(CommandEvent event) {
		Random random = new Random();
		int roll = random.nextInt(3) + 1;
		
		if (roll == 1) {
			event.getChannel().sendFile(new File("images/cat/cat.jpg")).queue();
		}
		else if (roll == 2) {
			event.getChannel().sendFile(new File("images/cat/bite.gif")).queue();
		}
		else if (roll == 3) {
			event.getChannel().sendFile(new File("images/cat/nap.gif")).queue();
		}
	}
}
