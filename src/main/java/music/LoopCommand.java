package music;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;

public class LoopCommand extends MusicCommand {
	public LoopCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "loop";
		this.aliases = new String[] {"repeat"};
		this.help = "Sets whether or not current track will loop when it is done.";
		this.arguments = "[on|off]";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		String[] command = event.getMessage().getContentDisplay().split(" ", 2);
		
		EmbedBuilder embed = EmbedGenerator.makeEmbed();
		if (command.length == 1) {
			musicManager.scheduler.setLoop();
			if (musicManager.scheduler.getLoop()) {
				embed.setDescription("Player will now loop the current track.");
				event.getTextChannel().sendMessage(embed.build()).queue();
			}
			else {
				embed.setDescription("Player will no longer loop the current track.");
				event.getTextChannel().sendMessage(embed.build()).queue();
			}
		}
		else {
			if (command[1].equals("on")) {
				musicManager.scheduler.setLoop();
				embed.setDescription("Player will now loop the current track.");
				event.getTextChannel().sendMessage(embed.build()).queue();
			}
			else if (command[1].equals("off")) {
				musicManager.scheduler.setLoop();
				embed.setDescription("Player will no longer loop the current track.");
				event.getTextChannel().sendMessage(embed.build()).queue();
			}
			else {
				embed.setAuthor("ERROR", null, "https://cdn4.iconfinder.com/data/icons/generic-interaction/143/close-x-cross-multiply-delete-cancel-modal-error-no-512.png");
				embed.setDescription("Type on or off");
				event.getTextChannel().sendMessage(embed.build()).queue();
			}
		}
	}
	
}
