package BotPackage.Commands;

import BotPackage.Annotations.Core;
import BotPackage.Annotations.Information;
import BotPackage.Annotations.Injection;
import BotPackage.Models.Command;
import BotPackage.Realisation.DataBaseController;
import BotPackage.Realisation.User;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

@Core
@Information
public class RegisterCommand extends Command {
    @Injection
    private DataBaseController dataBaseController;

    public RegisterCommand() {
        super("register", new ArrayList<>(Arrays.asList(Argument.STRING, Argument.STRING)),
                "format: /register user_name user_group_number");
    }

    @Override
    public BotApiMethod<? extends Serializable> doOption(Update update) {
        String message = update.getMessage().getText();
        String[] fragments = message.split(" ");

        if (fragments.length == 3) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());

            User user = new User(fragments[1], Integer.parseInt(fragments[2]),
                    update.getMessage().getFrom().getId(), User.Status.UNVERIFIED);

            if (dataBaseController.addUser(user)) {
                sendMessage.setText("Вставить_сюда_текст.");
                return sendMessage;
            }
        }

        return null;
    }
}
