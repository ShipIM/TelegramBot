package BotPackage.Commands;

import BotPackage.Annotations.Core;
import BotPackage.Models.QueueButtonCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Core
public class PushButtonCommand extends QueueButtonCommand {
    public PushButtonCommand() {
        super("push", new ArrayList<>(),
                "nothing but pressing keyboard button");
    }

    @Override
    public BotApiMethod<? extends Serializable> doOption(Update update) {
        EditMessageText editMessageText = new EditMessageText();

        List<String> queue = parseQueueFrom(update.getCallbackQuery().getMessage().getText());
        boolean inserted = queue.contains(update.getCallbackQuery().getFrom().getUserName());

        for (int i = 0; i < queue.size() && !inserted; i++) {
            if (queue.get(i).equals(" ")) {
                queue.set(i, update.getCallbackQuery().getFrom().getUserName());
                inserted = true;
            }
        }
        if (!inserted) {
            queue.add(update.getCallbackQuery().getFrom().getUserName());
        }

        editMessageText.setText(parseQueueTo(queue, update.getCallbackQuery().getMessage().getText()));
        editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        editMessageText.setReplyMarkup(update.getCallbackQuery().getMessage().getReplyMarkup());
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());

        return editMessageText;
    }
}
