package commands;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

public class UserCommand extends GeneralCommand {
	public UserCommand() {
		super();
		this.name = "user";
		this.aliases = new String[] {"userinfo"};
		this.help = "Provides info on a user.";
	}

	@Override
	protected void execute(CommandEvent event) {
		String[] command = event.getMessage().getContentDisplay().split(" ", 2);
		
		if (command.length == 1) {
			EmbedBuilder embed = EmbedGenerator.makeError();
			embed.setDescription("Provide a user @mention to check.");
			event.getChannel().sendMessage(embed.build()).queue();
		}
		else {
			String userName = command[1].substring(1);
			Member member = event.getGuild().getMembersByName(userName, true).get(0);
			User user = member.getUser();
			String nickname;
			
			if (member.getNickname() != null) {
				nickname = member.getNickname();
			}
			else {
				nickname = "Not set";
			}
			
			EmbedBuilder embed = EmbedGenerator.makeEmbed();
			embed.setTitle(user.getName() + "'s info:");
			embed.addField("Name:", user.getAsMention(), true);
			embed.addField("Nickname:", nickname, true);
			embed.addField("Status:", event.getGuild().getMembersByName(userName, true).get(0).getOnlineStatus().toString(), true);
			embed.addField("Created on:", user.getCreationTime().toString(), true);
			embed.addField("Avatar:", "", true);
			embed.setImage(user.getAvatarUrl());
			
			
			event.getChannel().sendMessage(embed.build()).queue();
		}
	}
}
