package commands;

import java.time.temporal.ChronoUnit;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;

public class PingCommand extends GeneralCommand {
	public PingCommand() {
		super();
		this.name = "ping";
		this.aliases = new String[] {"pong"};
		this.help = "Gives the bot's latency and an image.";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		EmbedBuilder embed = EmbedGenerator.makeEmbed();
		
		long ping = event.getMessage().getTimeCreated().until(event.getMessage().getTimeCreated(), ChronoUnit.MILLIS);
		embed.addField("Pong!", "**Ping: " + ping  + "ms**\n**Websocket: " + event.getJDA().getGatewayPing() + "ms**", false);
		event.reply(embed.build());
		
		/**
		//MessageReceivedEvent e = event.getEvent();
		//Message message = e.getMessage();
		String id = event.getEvent().getMessageId();
		event.getMessage().addReaction("\u2705").queue();
		try {
			TimeUnit.SECONDS.sleep(5);
			System.out.println("Waited");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println(event.getChannel().getMessageById(id).complete().getReactions().get(0).getCount());
		*/
	}
}
