package BotPackage.CommandModule.Custom.Commands;

import BotPackage.CommandModule.Annotations.Information;
import BotPackage.CommandModule.Models.Command;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", new ArrayList<>(), "Shows all available commands.");
    }

    @Override
    public BotApiMethod<? extends Serializable> doOption(Update update) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId().toString());
        StringBuilder stringBuilder = new StringBuilder();

        new Reflections(new ConfigurationBuilder().forPackages("BotPackage")).getTypesAnnotatedWith(Information.class)
                .forEach(type -> {
                    try {
                        Command tmp = (Command) type.getDeclaredConstructor().newInstance();
                        stringBuilder.append(tmp);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                            | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                });
        sendMessage.setText(stringBuilder.toString());

        return sendMessage;
    }
}
