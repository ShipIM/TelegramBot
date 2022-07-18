package BotPackage.CommandModule.Executors;

import BotPackage.CommandModule.Annotations.Core;
import BotPackage.Execution.Executable;
import BotPackage.CommandModule.Models.Command;
import BotPackage.Execution.Injector;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.*;

public class CommandExecutor implements Executable {
    private final Map<String, Command> commands;

    public CommandExecutor() {
        Map<String, Command> commands = new HashMap<>();
        Arrays.stream(Injector.getContext().getBeanNamesForAnnotation(Core.class)).forEach((name) -> {
            Command tmp = Injector.getContext().getBean(name, Command.class);

            commands.put(tmp.getName(), tmp);
        });

        this.commands = commands;
    }

    @Override
    public BotApiMethod<? extends Serializable> execute(Update update) {
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
