package commands;

import java.io.File;
import java.util.Random;

import com.jagrosh.jdautilities.command.CommandEvent;

public class DogCommand extends GeneralCommand {
	public DogCommand() {
		super();
		this.name = "dog";
		this.aliases = new String[] {"dogs", "pup", "puppy"};
		this.help = "Posts an image or gif of a dog.";
	}

	@Override
	protected void execute(CommandEvent event) {
		Random random = new Random();
		int roll = random.nextInt(3) + 1;
		
		if (roll == 1) {
			event.getChannel().sendFile(new File("images/dog/husky.jpg")).queue();
		}
		else if (roll == 2) {
			event.getChannel().sendFile(new File("images/dog/puppy.gif")).queue();
		}
		else if (roll == 3) {
			event.getChannel().sendFile(new File("images/cat/bear.gif")).queue();
		}
	}
}
