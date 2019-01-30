package music;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import net.dv8tion.jda.core.entities.Guild;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
	private final AudioPlayer player;
	private final Guild guild;
	private final BlockingQueue<AudioTrack> queue;
	private AudioTrack lastTrack;
	private boolean loop = false;

	/**
	 * @param player The audio player this scheduler uses
	 */
	public TrackScheduler(AudioPlayer player, Guild guild) {
		this.player = player;
		this.guild = guild;
		this.queue = new LinkedBlockingQueue<>();
	}

	/**
	 * Add the next track to queue or play right away if nothing is in the queue.
	 *
	 * @param track The track to play or add to queue.
	 */
	public void queue(AudioTrack track) {
		// Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
		// something is playing, it returns false and does nothing. In that case the player was already playing so this
		// track goes to the queue instead.
		if (!player.startTrack(track, true)) {
			queue.offer(track);
		}
	}

	/**
	 * Start the next track, stopping the current one if it is playing.
	 */
	public void nextTrack() {
		// Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
		// giving null to startTrack, which is a valid argument and will simply stop the player.
		AudioTrack next = queue.poll(); 
		player.startTrack(next, false);
		if (next == null) {
			player.stopTrack();
			guild.getAudioManager().setSendingHandler(null);
			guild.getAudioManager().closeAudioConnection();
		}
	}
	
	/**
	 * Clear all current tracks.
	 */
	public void clear() {
		this.queue.clear();
	}
	
	@Override
	public void onPlayerPause(AudioPlayer player) {
	    // Player was paused
	}

	@Override
	public void onPlayerResume(AudioPlayer player) {
		// Player was resumed
	}

	@Override
	public void onTrackStart(AudioPlayer player, AudioTrack track) {
		this.lastTrack = track;
	}

	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		// Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
		this.lastTrack = track;
		if (endReason.mayStartNext) {
			if (this.loop) {
				player.startTrack(lastTrack.makeClone(), false);
			}
			else {
				nextTrack();
			}
		}
		
		// endReason == FINISHED: A track finished or died by an exception (mayStartNext = true).
	    // endReason == LOAD_FAILED: Loading of a track failed (mayStartNext = true).
	    // endReason == STOPPED: The player was stopped.
	    // endReason == REPLACED: Another track started playing while this had not finished
	    // endReason == CLEANUP: Player hasn't been queried for a while, if you want you can put a
	    //                       clone of this back to your queue
	}
	
	public void setLoop() {
		if (this.loop) {
			this.loop = false;
		}
		else {
			this.loop = true;
		}
	}
	
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	public boolean getLoop() {
		return this.loop;
	}
	
	public BlockingQueue<AudioTrack> getQueue() {
		return this.queue;
	}
}