package com.bartoszmiz.jmusicbot;

public class Settings {
	private long guildId;
	private long userId;
	private long textChannelId;
	private int port;

	public long getGuildId() {
		return guildId;
	}

	public void setGuildId(long guildId) {
		this.guildId = guildId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getTextChannelId() {
		return textChannelId;
	}

	public void setTextChannelId(long textChannelId) {
		this.textChannelId = textChannelId;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
