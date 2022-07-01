package BotPackage.Main;

import BotPackage.Execution.Executioner;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.Optional;

public class BotItself extends TelegramLongPollingBot {
    private final Executioner executioner;

    public BotItself(Executioner executioner) {
        this.executioner = executioner;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Optional<BotApiMethod<? extends Serializable>> method = Optional.ofNullable(executioner.executeCommand(update));

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
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }
}