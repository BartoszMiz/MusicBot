package com.bartoszmiz.jmusicbot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.commands.music.PlayCmd;
import com.jagrosh.jmusicbot.commands.music.SkipCmd;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static com.jagrosh.jmusicbot.JMusicBot.LOG;

public class Modification {
	private static final Logger log = LoggerFactory.getLogger(Modification.class);
	private final Bot bot;
	private final JDA jda;
	private final CommandClient commandClient;
	private final String settingsPath = "serversettings.txt";

	private Guild guild;
	private TextChannel textChannel;
	private User user;

	public Modification(Bot bot, CommandClient commandClient) {
		this.bot = bot;
		this.jda = bot.getJDA();
		this.commandClient = commandClient;
	}

	public void start() {
		try {
			jda.awaitReady();
		} catch (InterruptedException e) {
			return;
		}

		var settings = readSettings();
		if (settings == null) {
			return;
		}

		var guildId = settings.getGuildId();
		guild = jda.getGuildById(guildId);

		var channelId = settings.getTextChannelId();
		textChannel = guild.getTextChannelById(channelId);

		var userId = settings.getUserId();
		user = jda.retrieveUserById(userId).complete();

		var port = settings.getPort();

		try (var socket = new ServerSocket(port)) {
			log.info(String.format("TCP socket opened on port %s", port));
			while (true) {
				var connection = socket.accept();
				log.info(String.format("Incoming connection from %s", connection.getRemoteSocketAddress()));
				handleConnection(connection);
			}
		} catch (IOException ex) {
			log.error(String.format("Failed to initialize TCP server: %s", ex.getMessage()));
		}
	}

	/*
	Settings format (put this in serversettings.txt):
	guild = <id>
	user = <id>
	text_channel = <id>
	port = <id>
	 */
	private Settings readSettings() {
		try {
			var settingsFile = new File(settingsPath);
			var scanner = new Scanner(settingsFile);

			var settings = new Settings();
			while (scanner.hasNextLine()) {
				var line = scanner.nextLine();
				var segments = line.split(" ");
				if (segments.length != 3 || !segments[1].equals("=")) {
					return null;
				}

				if (segments[0].equalsIgnoreCase("guild")) {
					settings.setGuildId(Long.parseLong(segments[2]));
				} else if (segments[0].equalsIgnoreCase("user")) {
					settings.setUserId(Long.parseLong(segments[2]));
				} else if (segments[0].equalsIgnoreCase("text_channel")) {
					settings.setTextChannelId(Long.parseLong(segments[2]));
				} else if (segments[0].equalsIgnoreCase("port")) {
					settings.setPort(Integer.parseInt(segments[2]));
				}
			}

			return settings;
		} catch (FileNotFoundException | NumberFormatException ex) {
			log.error(String.format("Failed to read the TCP server settings: %s", ex.getMessage()));
			return null;
		}
	}

	private void handleConnection(Socket connection) {
		String command;
		try (var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			command = reader.readLine();
		} catch (IOException ex) {
			log.error(String.format("Failed to handle connection: %s", ex.getMessage()));
			return;
		}

		if (command.startsWith("play")) {
			var query = command.substring(command.indexOf(' ')).trim();
			sendPlayCommand(query);
		} else if (command.startsWith("skip")) {
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
