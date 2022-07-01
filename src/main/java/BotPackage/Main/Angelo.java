package BotPackage.Main;

import BotPackage.Execution.Injector;
import BotPackage.Realisation.Dependency;
import BotPackage.Execution.Executioner;
import BotPackage.Realisation.DataBaseController;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

class Angelo {
    public static void main(String[] args) throws TelegramApiException {
        try {
            Connection connection = DriverManager.getConnection("",
                    "", new Scanner(System.in).nextLine());
            DataBaseController dataBaseController = new DataBaseController(connection);

            Injector.getInjector().addInjection("angeloController", new Dependency<>(dataBaseController));

            Executioner executioner = new Executioner();

            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            BotItself botItself = new BotItself(executioner);
            telegramBotsApi.registerBot(botItself);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}