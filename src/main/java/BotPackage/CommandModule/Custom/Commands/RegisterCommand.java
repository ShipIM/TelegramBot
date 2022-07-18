package BotPackage.CommandModule.Custom.Commands;

import BotPackage.CommandModule.Annotations.Core;
import BotPackage.CommandModule.Annotations.Information;
import BotPackage.CommandModule.Models.Command;
import BotPackage.Realisation.Custom.DataBaseController;
import BotPackage.Realisation.Custom.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

@Core
@Component
@Information
public class RegisterCommand extends Command {
    private final DataBaseController dataBaseController;

    public RegisterCommand(@Autowired DataBaseController dataBaseController) {
        super("register", new ArrayList<>(Arrays.asList(Argument.STRING, Argument.STRING)),
                "format: /register user_name user_group_number");
        this.dataBaseController = dataBaseController;
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
