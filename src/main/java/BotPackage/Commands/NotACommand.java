package BotPackage.Commands;

import BotPackage.Models.Command;
import BotPackage.Annotations.Core;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayList;

@Core
public class NotACommand extends Command {
    public NotACommand() {
        super("not_a_command", new ArrayList<>(), "everything illegal");
    }

    @Override
    public BotApiMethod<? extends Serializable> doOption(Update update) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Такой команды нет / что-то не так с введенными аргументами.");

        return sendMessage;
    }
}
