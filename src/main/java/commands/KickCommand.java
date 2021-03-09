package commands;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.jagrosh.jdautilities.command.CommandEvent;

import constants.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

public class KickCommand extends GeneralCommand {
	public KickCommand() {
		super();
		this.name = "kick";
		this.aliases = new String[] {"votekick", "vote"};
		this.help = "Starts a vote to kick a user.";
		this.arguments = "@mention";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		String[] command = event.getMessage().getContentDisplay().split(" ", 2);
		
		if (command.length == 1) {
			EmbedBuilder embed = EmbedGenerator.makeError();
			embed.setDescription("Provide a user @mention to kick.");
			event.getChannel().sendMessage(embed.build()).queue();
		}
		else {
			EmbedBuilder embed = EmbedGenerator.makeEmbed();
			embed.setAuthor("Vote by " + event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl());
			embed.setTitle("Vote to kick " + command[1].substring(1) + " has begun.");
			// embed.setDescription("Vote :white_check_mark: to kick and :x: to stay.");
			embed.setFooter("Vote \u2705 to kick and \u274C to stay.", null);
			
			Random random = new Random();
			int roll = random.nextInt(3) + 1;
			
			if (roll == 1) {
				embed.setImage("https://vignette.wikia.nocookie.net/town-of-salem/images/8/8b/JudgementMenuSymbol.png/revision/latest?cb=20150802184046");
			}
			else if (roll == 2) {
				embed.setImage("https://vignette.wikia.nocookie.net/town-of-salem/images/8/8b/JudgementMenuSymbol.png/revision/latest?cb=20150802184046");
			}
			else {
				embed.setImage("https://vignette.wikia.nocookie.net/town-of-salem/images/8/8b/JudgementMenuSymbol.png/revision/latest?cb=20150802184046");
			}
			
			
			StringBuilder id = new StringBuilder();
			
			Message message = new MessageBuilder().setEmbed(embed.build()).build();
			embed.setTitle("Voting has ended.");
			
			/**
			String id2 = message.getId();
			event.getChannel().sendMessage(message).complete();
			event.getChannel().addReactionById(id2, "\u2705").complete();
			System.out.println(event.getChannel().getMessageById(id2).complete().getReactions().get(0).getCount());
			*/
			//event.getChannel().getLatestMessageId();
			
			event.getChannel().sendMessage(message).queue(m ->
					{
						m.addReaction("\u2705").queue();
						m.addReaction("\u274C").queue();
						id.append(m.getId());
						/**
						event.getChannel().getMessageById(id.toString()).queue(p -> 
						{
							p.editMessage(embed.build()).queue();
							System.out.println("edited");
						});
						*/
						try {
							TimeUnit.SECONDS.sleep(10);
							m.editMessage(embed.build()).queue();
							//TimeUnit.SECONDS.sleep(5);
							event.getChannel().retrieveMessageById(id.toString()).queue(n ->
									{
										int kick = n.getReactions().get(0).getCount();
										int stay = n.getReactions().get(1).getCount();
										
										//EmbedBuilder e = EmbedGenerator.makeEmbed();
										if (kick > stay) {
											//e.setDescription(command[1].substring(1) + " will be lynched.");
											event.reply(kickMessage(command[1].substring(1), roll).build());
										}
										else {
											// e.setDescription(command[1].substring(1) + " has been pardoned.");
											event.reply(stayMessage(command[1].substring(1), roll).build());
										}
									});
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
		}
	}
	
	private EmbedBuilder kickMessage(String user, int roll) {
		EmbedBuilder e = EmbedGenerator.makeEmbed();
		if (roll == 1) {
			e.setDescription(user + " will be lynched.");
			// https://vignette.wikia.nocookie.net/town-of-salem/images/1/19/DeathBurned.gif/revision/latest?cb=20140825175915
			// https://vignette.wikia.nocookie.net/town-of-salem/images/5/5c/DeathGuillotine2.gif/revision/latest?cb=20160425021233
			// https://s3.amazonaws.com/ksr/assets/002/579/246/bad86b5a4f7dad42caf56edbef07befd_large.gif?1410637144
			e.setImage("https://media.giphy.com/media/oznD6ErdPix44sd9dk/giphy.gif");
		}
		else if (roll == 2) {
			e.setDescription(user + " has been found guilty.");
			// e.setImage("https://3.bp.blogspot.com/-gZTZvr-6A1E/WGbKXOhO91I/AAAAAAAADDY/VdOAJX-SlEs792BtbCsdBQGOmZLXeVOxQCLcB/s400/culpable.gif");
			e.setImage("https://thumbs.gfycat.com/LavishUnitedAtlanticblackgoby-size_restricted.gif");
		}
		else {
			e.setDescription(user + " is the blackened. Time for punishment!");
			// https://66.media.tumblr.com/4631104cbabdc5051a49c15d796c2497/tumblr_mki95rYxOH1r98ylgo1_500.gif
			// https://randomc.net/image/Danganronpa%20The%20Animation/Danganronpa%20The%20Animation%20-%20OP2%20-%20Large%2002.jpg
			// https://i.imgur.com/UvKsw0Q.jpg
			e.setImage("https://i.ytimg.com/vi/TuAjqHg83d4/maxresdefault.jpg");
		}
		return e;
	}
	
	private EmbedBuilder stayMessage(String user, int roll) {
		EmbedBuilder e = EmbedGenerator.makeEmbed();
		if (roll == 1) {
			e.setDescription(user + " has been pardoned.");
			// https://vignette.wikia.nocookie.net/town-of-salem/images/7/78/Uriel-Death-Animation.gif/revision/latest/scale-to-width-down/135?cb=20160625184745
			// https://vignette.wikia.nocookie.net/vsbattles/images/b/b3/Guardian_angel.png/revision/latest?cb=20180825211229
			e.setImage("https://vignette.wikia.nocookie.net/town-of-salem/images/8/89/Survivor-Wins-2017.png/revision/latest?cb=20170908193845");
		}
		else if (roll == 2) {
			e.setDescription(user + " has been found not guilty.");
			// e.setImage("http://fanaru.com/ace-attorney/image/216671-ace-attorney-not-guilty-gif.gif");
			e.setImage("https://static.tvtropes.org/pmwiki/pub/images/479d1258f21df418925dacbd9a603a2ab56b90a4_hq.jpg");
		}
		else {
			e.setDescription(user + " is spotless.");
			// https://cdn0.tnwcdn.com/wp-content/blogs.dir/1/files/2016/02/tumblr_static_large.gif
			e.setImage("http://25.media.tumblr.com/0466a38fdf3b802f0ecf87aeb0b6ed67/tumblr_mi4a1rYrRb1r83ea6o1_500.gif");
		}
		return e;
	}
}
