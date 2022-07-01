package BotPackage.Execution;

import BotPackage.Annotations.Core;
import BotPackage.Models.Command;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Executioner {
    private final Map<String, Command> commands = new HashMap<>();

    public Executioner() {
        new Reflections(new ConfigurationBuilder().forPackages("BotPackage")).getTypesAnnotatedWith(Core.class)
                .forEach(type -> {
                    try {
                        Command tmp = (Command) type.getDeclaredConstructor().newInstance();
                        commands.put(tmp.getName(), tmp);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                            | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                });
    }

    public BotApiMethod<? extends Serializable> executeCommand(Update update) {
        String command = Optional.ofNullable(update.getMessage()).map(Message::getText)
                .orElseGet(() -> update.getCallbackQuery().getData());
        command = command.trim().replace("/", "").split(" ")[0];

        if (commands.containsKey(command)) {
            Optional<BotApiMethod<? extends Serializable>> method =
                    Optional.ofNullable(commands.get(command).doOption(update));

            return method.orElseGet(() -> commands.get("not_a_command").doOption(update));
        } else {
            return commands.get("not_a_command").doOption(update);
        }
    }
}
