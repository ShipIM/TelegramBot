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
public class PopButtonCommand extends QueueButtonCommand {
    public PopButtonCommand() {
        super("pop", new ArrayList<>(),
                "nothing but pressing keyboard button");
    }

    @Override
    public BotApiMethod<? extends Serializable> doOption(Update update) {
        List<String> queue = parseQueueFrom(update.getCallbackQuery().getMessage().getText());

        if (queue.size() != 0) {
            EditMessageText editMessageText = new EditMessageText();
            boolean deleted = false;
            for (int i = 0; i < queue.size() && !deleted; i++) {
                if (queue.get(i).equals(update.getCallbackQuery().getFrom().getUserName())) {
                    queue.set(i, " ");
                    deleted = true;
                }
            }

            editMessageText.setText(parseQueueTo(queue, update.getCallbackQuery().getMessage().getText()));
            editMessageText.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
            editMessageText.setReplyMarkup(update.getCallbackQuery().getMessage().getReplyMarkup());
            editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());

            return editMessageText;
        }

        return null;
    }
}
