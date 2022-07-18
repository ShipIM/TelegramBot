package BotPackage.CommandModule.Custom.Commands;

import BotPackage.CommandModule.Annotations.Core;
import BotPackage.CommandModule.Custom.Models.QueueButtonCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Core
@Component
public class PreviousButtonCommand extends QueueButtonCommand {
    public PreviousButtonCommand() {
        super("previous", new ArrayList<>(),
                "nothing but pressing keyboard button");
    }

    @Override
    public BotApiMethod<? extends Serializable> doOption(Update update) {
        List<String> queue = parseQueueFrom(update.getCallbackQuery().getMessage().getText());

        if (queue.size() != 0) {
            EditMessageText editMessageText = new EditMessageText();
            boolean moved = false;
            int position = queue.indexOf(update.getCallbackQuery().getFrom().getUserName());
            queue.set(position, " ");
            for (int i = position + 1; i < queue.size() && !moved; i++) {
                if (queue.get(i).equals(" ")) {
                    queue.set(i, update.getCallbackQuery().getFrom().getUserName());
                    moved = true;
                }
            }

            if (!moved) {
                queue.add(update.getCallbackQuery().getFrom().getUserName());
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
