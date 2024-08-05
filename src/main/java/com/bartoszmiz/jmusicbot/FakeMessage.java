package com.bartoszmiz.jmusicbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FakeMessage extends NullMessage {
	private final User user;
	private final Guild guild;
	private final TextChannel textChannel;
	private final JDA jda;

	public FakeMessage(User user, Guild guild, TextChannel textChannel, JDA jda) {

		this.user = user;
		this.guild = guild;
		this.textChannel = textChannel;
		this.jda = jda;
	}

	@NotNull
	@Override
	public User getAuthor() {
		return user;
	}

	@NotNull
	@Override
	public @Nullable Member getMember() {
		return guild.getMember(user);
	}

	@NotNull
	@Override
	public ChannelType getChannelType() {
		return ChannelType.TEXT;
	}

	@NotNull
	@Override
	public MessageChannel getChannel() {
		return textChannel;
	}

	@NotNull
	@Override
	public TextChannel getTextChannel() {
		return textChannel;
	}

	@NotNull
	@Override
	public Guild getGuild() {
		return guild;
	}

	@NotNull
	@Override
	public JDA getJDA() {
		return jda;
	}
}