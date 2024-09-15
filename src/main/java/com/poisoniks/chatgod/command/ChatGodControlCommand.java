package com.poisoniks.chatgod.command;

import com.poisoniks.chatgod.service.impl.ChatGodThreadController;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;

import javax.annotation.Nonnull;
import java.util.List;

public class ChatGodControlCommand implements ICommand {
    private final ChatGodThreadController chatGodThreadController;

    public ChatGodControlCommand(ChatGodThreadController chatGodThreadController) {
        this.chatGodThreadController = chatGodThreadController;
    }

    @Override
    public String getCommandName() {
        return "chatgod";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/chatgod <on|off|clear>";
    }

    @Override
    public List<String> getCommandAliases() {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            throw new WrongUsageException(getCommandUsage(sender));
        }
        switch (args[0]) {
            case "on":
                chatGodThreadController.start();
                sender.addChatMessage(new ChatComponentText("ChatGod is now enabled."));
                break;
            case "off":
                chatGodThreadController.stop();
                sender.addChatMessage(new ChatComponentText("ChatGod is now disabled."));
                break;
            case "clear":
                chatGodThreadController.clear();
                sender.addChatMessage(new ChatComponentText("Memory of ChatGod was cleared."));
                break;
            default:
                throw new WrongUsageException(getCommandUsage(sender));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull Object o) {
        return 0;
    }
}
