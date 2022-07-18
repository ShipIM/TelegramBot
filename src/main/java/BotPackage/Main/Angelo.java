package BotPackage.Main;

import BotPackage.CommandModule.Executors.CommandExecutor;
import BotPackage.Execution.Executable;
import BotPackage.Execution.ModuleBot;
import BotPackage.Execution.Injector;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

class Angelo {
    public static void main(String[] args) throws TelegramApiException {
        ModuleBot moduleBot = Injector.getContext().getBean("moduleBot", ModuleBot.class);
        Executable executable = new CommandExecutor();
        moduleBot.setExecutable(executable);

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(moduleBot);
    }
}