package com.bartoszmiz.jmusicbot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.commands.music.PlayCmd;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ComponentLayout;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.requests.restaction.pagination.ReactionPaginationAction;
import org.apache.commons.collections4.Bag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.OffsetDateTime;
import java.util.*;

public class Modification {
	private final Bot bot;
	private final JDA jda;
	private final CommandClient commandClient;

	private Guild guild;
	private TextChannel textChannel;
	private User user;
	private VoiceChannel voiceChannel;

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

		var guildId = 0L; // TODO
		guild = jda.getGuildById(guildId);

		var channelId = 0L; // TODO
		textChannel = guild.getTextChannelById(channelId);

		var userId = 0L; // TODO
		user = jda.retrieveUserById(userId).complete();

		var voiceChannelId = 0L; // TODO
		voiceChannel = guild.getVoiceChannelById(voiceChannelId);

		sendPlayCommand("example");
	}

	public void sendPlayCommand(String song) {

		class FakeMessage implements Message {

			@Nullable
			@Override
			public MessageReference getMessageReference() {
				return null;
			}

			@NotNull
			@Override
			public List<User> getMentionedUsers() {
				return List.of();
			}

			@NotNull
			@Override
			public Bag<User> getMentionedUsersBag() {
				return null;
			}

			@NotNull
			@Override
			public List<TextChannel> getMentionedChannels() {
				return List.of();
			}

			@NotNull
			@Override
			public Bag<TextChannel> getMentionedChannelsBag() {
				return null;
			}

			@NotNull
			@Override
			public List<Role> getMentionedRoles() {
				return List.of();
			}

			@NotNull
			@Override
			public Bag<Role> getMentionedRolesBag() {
				return null;
			}

			@NotNull
			@Override
			public List<Member> getMentionedMembers(@NotNull Guild guild) {
				return List.of();
			}

			@NotNull
			@Override
			public List<Member> getMentionedMembers() {
				return List.of();
			}

			@NotNull
			@Override
			public List<IMentionable> getMentions(@NotNull MentionType... mentionTypes) {
				return List.of();
			}

			@Override
			public boolean isMentioned(@NotNull IMentionable iMentionable, @NotNull MentionType... mentionTypes) {
				return false;
			}

			@Override
			public boolean mentionsEveryone() {
				return false;
			}

			@Override
			public boolean isEdited() {
				return false;
			}

			@Nullable
			@Override
			public OffsetDateTime getTimeEdited() {
				return null;
			}

			@NotNull
			@Override
			public User getAuthor() {
				return user;
			}

			@Nullable
			@Override
			public Member getMember() {
				return null;
			}

			@NotNull
			@Override
			public String getJumpUrl() {
				return "";
			}

			@NotNull
			@Override
			public String getContentDisplay() {
				return "";
			}

			@NotNull
			@Override
			public String getContentRaw() {
				return "";
			}

			@NotNull
			@Override
			public String getContentStripped() {
				return "";
			}

			@NotNull
			@Override
			public List<String> getInvites() {
				return List.of();
			}

			@Nullable
			@Override
			public String getNonce() {
				return "";
			}

			@Override
			public boolean isFromType(@NotNull ChannelType channelType) {
				return false;
			}

			@NotNull
			@Override
			public ChannelType getChannelType() {
				return ChannelType.TEXT;
			}

			@Override
			public boolean isWebhookMessage() {
				return false;
			}

			@NotNull
			@Override
			public MessageChannel getChannel() {
				return textChannel;
			}

			@NotNull
			@Override
			public PrivateChannel getPrivateChannel() {
				return null;
			}

			@NotNull
			@Override
			public TextChannel getTextChannel() {
				return textChannel;
			}

			@Nullable
			@Override
			public Category getCategory() {
				return null;
			}

			@NotNull
			@Override
			public Guild getGuild() {
				return guild;
			}

			@NotNull
			@Override
			public List<Attachment> getAttachments() {
				return List.of();
			}

			@NotNull
			@Override
			public List<MessageEmbed> getEmbeds() {
				return List.of();
			}

			@NotNull
			@Override
			public List<ActionRow> getActionRows() {
				return List.of();
			}

			@NotNull
			@Override
			public List<Emote> getEmotes() {
				return List.of();
			}

			@NotNull
			@Override
			public Bag<Emote> getEmotesBag() {
				return null;
			}

			@NotNull
			@Override
			public List<MessageReaction> getReactions() {
				return List.of();
			}

			@NotNull
			@Override
			public List<MessageSticker> getStickers() {
				return List.of();
			}

			@Override
			public boolean isTTS() {
				return false;
			}

			@Nullable
			@Override
			public MessageActivity getActivity() {
				return null;
			}

			@NotNull
			@Override
			public MessageAction editMessage(@NotNull CharSequence charSequence) {
				return null;
			}

			@NotNull
			@Override
			public MessageAction editMessageEmbeds(@NotNull Collection<? extends MessageEmbed> collection) {
				return null;
			}

			@NotNull
			@Override
			public MessageAction editMessageComponents(@NotNull Collection<? extends ComponentLayout> collection) {
				return null;
			}

			@NotNull
			@Override
			public MessageAction editMessageFormat(@NotNull String s, @NotNull Object... objects) {
				return null;
			}

			@NotNull
			@Override
			public MessageAction editMessage(@NotNull Message message) {
				return null;
			}

			@NotNull
			@Override
			public AuditableRestAction<Void> delete() {
				return null;
			}

			@NotNull
			@Override
			public JDA getJDA() {
				return jda;
			}

			@Override
			public boolean isPinned() {
				return false;
			}

			@NotNull
			@Override
			public RestAction<Void> pin() {
				return null;
			}

			@NotNull
			@Override
			public RestAction<Void> unpin() {
				return null;
			}

			@NotNull
			@Override
			public RestAction<Void> addReaction(@NotNull Emote emote) {
				return null;
			}

			@NotNull
			@Override
			public RestAction<Void> addReaction(@NotNull String s) {
				return null;
			}

			@NotNull
			@Override
			public RestAction<Void> clearReactions() {
				return null;
			}

			@NotNull
			@Override
			public RestAction<Void> clearReactions(@NotNull String s) {
				return null;
			}

			@NotNull
			@Override
			public RestAction<Void> clearReactions(@NotNull Emote emote) {
				return null;
			}

			@NotNull
			@Override
			public RestAction<Void> removeReaction(@NotNull Emote emote) {
				return null;
			}

			@NotNull
			@Override
			public RestAction<Void> removeReaction(@NotNull Emote emote, @NotNull User user) {
				return null;
			}

			@NotNull
			@Override
			public RestAction<Void> removeReaction(@NotNull String s) {
				return null;
			}

			@NotNull
			@Override
			public RestAction<Void> removeReaction(@NotNull String s, @NotNull User user) {
				return null;
			}

			@NotNull
			@Override
			public ReactionPaginationAction retrieveReactionUsers(@NotNull Emote emote) {
				return null;
			}

			@NotNull
			@Override
			public ReactionPaginationAction retrieveReactionUsers(@NotNull String s) {
				return null;
			}

			@Nullable
			@Override
			public MessageReaction.ReactionEmote getReactionByUnicode(@NotNull String s) {
				return null;
			}

			@Nullable
			@Override
			public MessageReaction.ReactionEmote getReactionById(@NotNull String s) {
				return null;
			}

			@Nullable
			@Override
			public MessageReaction.ReactionEmote getReactionById(long l) {
				return null;
			}

			@NotNull
			@Override
			public AuditableRestAction<Void> suppressEmbeds(boolean b) {
				return null;
			}

			@NotNull
			@Override
			public RestAction<Message> crosspost() {
				return null;
			}

			@Override
			public boolean isSuppressedEmbeds() {
				return false;
			}

			@NotNull
			@Override
			public EnumSet<MessageFlag> getFlags() {
				return null;
			}

			@Override
			public long getFlagsRaw() {
				return 0;
			}

			@Override
			public boolean isEphemeral() {
				return false;
			}

			@NotNull
			@Override
			public MessageType getType() {
				return MessageType.DEFAULT;
			}

			@Nullable
			@Override
			public Interaction getInteraction() {
				return null;
			}

			@Override
			public void formatTo(Formatter formatter, int flags, int width, int precision) {

			}

			@Override
			public long getIdLong() {
				return 0;
			}
		}

		var message = new FakeMessage();

		var responseNumber = 69L; // IDK

		bot.getPlayerManager().setUpHandler(guild);

		guild.getAudioManager().openAudioConnection(voiceChannel);

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
		playCommand.doCommand(commandEvent);
	}


}
