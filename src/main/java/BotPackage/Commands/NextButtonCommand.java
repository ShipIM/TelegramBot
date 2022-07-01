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
public class NextButtonCommand extends QueueButtonCommand {
    public NextButtonCommand() {
        super("next", new ArrayList<>(),
                "nothing but pressing keyboard button");
    }

    @Override
    public BotApiMethod<? extends Serializable> doOption(Update update) {
        List<String> queue = parseQueueFrom(update.getCallbackQuery().getMessage().getText());
        if (queue.size() != 0) {
            EditMessageText editMessageText = new EditMessageText();
            boolean moved = false;
            int position = queue.indexOf(update.getCallbackQuery().getFrom().getUserName()), placeHolder = -1;
            for (int i = position; i >= 0 && !moved; i--) {
                if (queue.get(i).equals(" ")) {
                    placeHolder = i;
                }

                if (placeHolder != -1) {
                    queue.set(position, " ");
                    queue.set(placeHolder, update.getCallbackQuery().getFrom().getUserName());
                    moved = true;
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
