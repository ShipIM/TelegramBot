package BotPackage.Execution;

import BotPackage.Execution.Executable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.Optional;

@Component("moduleBot")
@PropertySource("PropertyFile")
public class ModuleBot extends TelegramLongPollingBot {
    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private Executable executable;

    public ModuleBot(Executable executable) {
        this.executable = executable;
    }

    public ModuleBot() {

    }

    @Override
    public void onUpdateReceived(Update update) {
        Optional<BotApiMethod<? extends Serializable>> method = Optional.ofNullable(executable.execute(update));

        method.ifPresent(action -> {
            try {
                execute(action);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void setExecutable(Executable executable) {
        this.executable = executable;
    }
}