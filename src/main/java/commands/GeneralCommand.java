package commands;

import com.jagrosh.jdautilities.command.Command;

public abstract class GeneralCommand extends Command {
	public GeneralCommand() {
		this.category = new Category("General");
	}
}
