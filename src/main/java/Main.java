import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.CommandEvent;

import commands.*;
import constants.ColorGenerator;
import music.*;

public class Main {
	public static void main(String[] args) throws Exception {
		try {
			Config config = new Config();
			
			JDA bot = new JDABuilder(AccountType.BOT)
					.setToken(config.getToken())
					.build();

			CommandClientBuilder builder = new CommandClientBuilder();
			builder.setOwnerId(config.getOwnerId());
			builder.setPrefix(config.getPrefix());
			builder.setHelpWord("helpme");
			builder.setHelpConsumer(event -> event.replyInDm(formatHelp(event)));
			
			builder.addCommand(new PingCommand());
			builder.addCommand(new HelpCommand());
			builder.addCommand(new CatCommand());
			builder.addCommand(new DogCommand());
			builder.addCommand(new KickCommand());
			builder.addCommand(new UserCommand());
			
			MusicPlayer player = new MusicPlayer();
			builder.addCommand(new PlayCommand(player));
			builder.addCommand(new AddCommand(player));
			builder.addCommand(new SkipCommand(player));
			builder.addCommand(new PauseCommand(player));
			builder.addCommand(new ResumeCommand(player));
			builder.addCommand(new StopCommand(player));
			builder.addCommand(new JoinCommand(player));
			builder.addCommand(new LeaveCommand(player));
			builder.addCommand(new ClearCommand(player));
			builder.addCommand(new LoopCommand(player));
			builder.addCommand(new VolumeCommand(player));
			builder.addCommand(new MuteCommand(player));
			builder.addCommand(new UnmuteCommand(player));
			builder.addCommand(new QueueCommand(player));
			
			builder.addCommand(new AirhornCommand(player));
			builder.addCommand(new BruhCommand(player));
			builder.addCommand(new BruhMinuteCommand(player));
			builder.addCommand(new SansCommand(player));
			
			
			CommandClient client = builder.build();
			bot.addEventListener(client);
			
			// optionally block until JDA is ready
	        bot.awaitReady();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Message formatHelp(CommandEvent event)
    {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(ColorGenerator.randomColor());
        
        List<Command> commandsInCategory;
        ArrayList<String> categories = new ArrayList<String>();
        String content = " **"+event.getSelfUser().getName()+"** commands:";
        
        for (Command command : event.getClient().getCommands()) {
        	if (!categories.contains(command.getCategory().getName())) {
        		categories.add(command.getCategory().getName());
        	}
        }
        for (String category : categories) {
        	StringBuilder commands = new StringBuilder();
        	commandsInCategory = event.getClient().getCommands().stream().filter(cmd -> 
            {
            	if(cmd.isHidden() || cmd.isOwnerCommand()) {
            		return false;
            	}
            	if(cmd.getCategory().getName().equals(category)) {
            		return true;
            	}
            	return false;
            }).collect(Collectors.toList());
        	
        	commandsInCategory.forEach(cmd -> 
        		{
        			commands.append("**!" + cmd.getName() + (cmd.getArguments()==null ? "" : " " + cmd.getArguments()) + "**: ");
        			commands.append(cmd.getHelp()+"\n");
        		});
        	embed.addField("__" + category + " commands:__", commands.toString(), false);
        }
        
        return new MessageBuilder().append(content).setEmbed(embed.build()).build();
    }
}
