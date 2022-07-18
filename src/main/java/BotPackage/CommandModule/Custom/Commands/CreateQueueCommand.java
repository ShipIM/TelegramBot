package BotPackage.CommandModule.Custom.Commands;

import BotPackage.CommandModule.Annotations.Information;
import BotPackage.CommandModule.Models.Command;
import BotPackage.CommandModule.Annotations.Core;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Core
@Component
@Information
public class CreateQueueCommand extends Command {
    public CreateQueueCommand() {
        super("create_queue", new ArrayList<>(Collections.singletonList(Argument.STRING)),
                "format: create_queue queueName");
    }

    @Override
    public BotApiMethod<? extends Serializable> doOption(Update update) throws NullPointerException {
        String message = update.getMessage().getText();
        String[] fragments = message.split(" ");

        if (fragments.length == 2) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText(fragments[fragments.length - 1] + ": {\n}");

            InlineKeyboardButton push = new InlineKeyboardButton("push");
            push.setCallbackData("push");

            InlineKeyboardButton pop = new InlineKeyboardButton("pop");
            pop.setCallbackData("pop");

            InlineKeyboardButton next = new InlineKeyboardButton("next");
            next.setCallbackData("next");

            InlineKeyboardButton previous = new InlineKeyboardButton("previous");
            previous.setCallbackData("previous");

            sendMessage.setReplyMarkup(new InlineKeyboardMarkup(new ArrayList<>(Arrays.asList(new
                    ArrayList<>(Arrays.asList(pop, push)), new ArrayList<>(Arrays.asList(previous, next))))));

            return sendMessage;
        }

        return null;
    }
}
