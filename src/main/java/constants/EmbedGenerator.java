package constants;

import net.dv8tion.jda.core.EmbedBuilder;

public class EmbedGenerator {
	public static EmbedBuilder makeEmbed() {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(ColorGenerator.randomColor());
		return embed;
	}
	
	public static EmbedBuilder makeError() {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(ColorGenerator.randomColor());
		embed.setAuthor("ERROR", null, "https://cdn4.iconfinder.com/data/icons/generic-interaction/143/close-x-cross-multiply-delete-cancel-modal-error-no-512.png");
		return embed;
	}
}
