package BotPackage.Execution;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

public interface Executable {
    BotApiMethod<? extends Serializable> execute(Update update);
}
