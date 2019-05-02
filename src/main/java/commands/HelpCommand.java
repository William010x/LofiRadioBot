package commands;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.core.EmbedBuilder;

public class HelpCommand extends GeneralCommand {
	public HelpCommand() {
		this.name = "help";
		this.aliases = new String[] {"commands", "command"};
		this.help = "Provides a list of all available commands.";
	}

	@Override
	protected void execute(CommandEvent event) {
		EmbedBuilder embed = EmbedGenerator.makeEmbed();
		embed.addField("__Commands:__", "\n>help\n>commands\n>ping\n>cat\n>dog\n>kick\n>user\n", true);
		embed.addField("__Music commands:__", "\n>play\n>add\n>skip\n>pause\n>resume\n>stop\n>join\n>leave\n>clear\n>volume\n>mute\n>unmute\n>clear\n>loop\n>queue\n>airhorn\n>bruh\n>bruhminute\n>sans\n", true);
		embed.setFooter("Check direct messages for detailed information on each command.", null);
		event.getChannel().sendMessage(embed.build()).queue();
	}
}
