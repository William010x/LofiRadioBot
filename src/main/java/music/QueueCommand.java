package music;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import constants.EmbedGenerator;
import net.dv8tion.jda.api.EmbedBuilder;

public class QueueCommand extends MusicCommand {
	public QueueCommand(MusicPlayer musicPlayer) {
		super(musicPlayer);
		this.name = "queue";
		this.aliases = new String[] {"list", "songs", "upcoming"};
		this.help = "Lists all tracks currently in the queue.";
	}

	@Override
	protected void execute(CommandEvent event) {
		GuildMusicManager musicManager = musicPlayer.getMusicManager(event.getGuild());
		ArrayList<String> tracks = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();
		BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();
		String url = "";
		String thumbnail = "";
		
		if (musicManager.player.getPlayingTrack() != null) {
			tracks.add(musicManager.player.getPlayingTrack().getInfo().title);
			names.add(musicManager.player.getPlayingTrack().getUserData().toString());
			url = musicManager.player.getPlayingTrack().getInfo().uri;
			thumbnail = "https://img.youtube.com/vi/" + musicManager.player.getPlayingTrack().getInfo().identifier + "/maxresdefault.jpg";
		}
		
		for (AudioTrack track : queue) {
			tracks.add(track.getInfo().title);
			names.add(musicManager.player.getPlayingTrack().getUserData().toString());
		}
		
		if (tracks.size() == 0) {
			EmbedBuilder embed = EmbedGenerator.makeError();
			embed.setDescription("Nothing currently in the queue.");
			event.reply(embed.build());
		}
		else {
			String list = "";
			String current = tracks.get(0);
			
			for (int i = 1; i < tracks.size(); i++) {
				list += "\n\n`" + i + ".` ";
				list += "**" + tracks.get(i) + "**";
				list += "\n`Added by: " + names.get(i) + "`";
			}
			
			EmbedBuilder embed = EmbedGenerator.makeEmbed();
			embed.setAuthor("Now playing:", null, "http://icons.iconarchive.com/icons/dtafalonso/yosemite-flat/512/Music-icon.png");
			embed.setTitle(current, url);
			embed.setDescription("`Added by: " + names.get(0) + "`\n");
			embed.setThumbnail(thumbnail);
			
			embed.addBlankField(false);
			if (!list.equals("")) {
				embed.addField("__Next:__", list, false);
			}
			
			event.reply(embed.build());
		}
	}

}
