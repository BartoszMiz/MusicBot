package com.bartoszmiz.jmusicbot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.commands.music.PlayCmd;
import com.jagrosh.jmusicbot.commands.music.SkipCmd;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static com.jagrosh.jmusicbot.JMusicBot.LOG;

public class Modification {
	private final Bot bot;
	private final JDA jda;
	private final CommandClient commandClient;
	private final int port;

	private Guild guild;
	private TextChannel textChannel;
	private User user;
	private VoiceChannel voiceChannel;

	public Modification(Bot bot, CommandClient commandClient) {
		this.bot = bot;
		this.jda = bot.getJDA();
		this.commandClient = commandClient;
		this.port = 8080;
	}

	public void start() {
		try {
			jda.awaitReady();
		} catch (InterruptedException e) {
			return;
		}

		var guildId = 0L; // TODO
		guild = jda.getGuildById(guildId);

		var channelId = 0L; // TODO
		textChannel = guild.getTextChannelById(channelId);

		var userId = 0L; // TODO
		user = jda.retrieveUserById(userId).complete();

		var voiceChannelId = 0L; // TODO
		voiceChannel = guild.getVoiceChannelById(voiceChannelId);

		try (var socket = new ServerSocket(port)) {
			LOG.info(String.format("TCP socket opened on port %s", port));
			while (true) {
				var connection = socket.accept();
				LOG.info(String.format("Incoming connection from %s", connection.getRemoteSocketAddress()));
				handleConnection(connection);
			}
		} catch (IOException ex) {
			LOG.error(String.format("Failed to initialize TCP server: %s", ex.getMessage()));
			return;
		}
	}

	private void handleConnection(Socket connection) {
		String command;
		try (var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			var writer = new OutputStreamWriter(connection.getOutputStream());
			command = reader.readLine();
			writer.write("Message received!\n");
			writer.flush();
		} catch (IOException ex) {
			LOG.error(String.format("Failed to handle connection: %s", ex.getMessage()));
			return;
		}

		if (command.startsWith("play") || command.startsWith("p")) {
			var query = command.substring(command.indexOf(' ')).trim();
			sendPlayCommand(query);
		} else if (command.startsWith("skip") || command.startsWith("s")) {
			sendSkipCommand();
		}
	}

	private void sendPlayCommand(String song) {
		var message = new FakeMessage(user, guild, textChannel, jda);
		var responseNumber = 69L; // IDK

		var playCommand = (PlayCmd)commandClient.getCommands().stream().filter((command -> command.getName().equals("play"))).findFirst().get();

		var commandEvent = new CommandEvent(
			new MessageReceivedEvent(
				jda,
				responseNumber,
				message
			),
			song,
			commandClient
		);

		playCommand.run(commandEvent);
	}

	private void sendSkipCommand() {
		var message = new FakeMessage(user, guild, textChannel, jda);
		var responseNumber = 69L; // IDK

		var skipCommand = (SkipCmd)commandClient.getCommands().stream().filter((command -> command.getName().equals("skip"))).findFirst().get();

		var commandEvent = new CommandEvent(
				new MessageReceivedEvent(
						jda,
						responseNumber,
						message
				),
				"",
				commandClient
		);

		skipCommand.run(commandEvent);
	}
}
