package BotPackage.Models;

import java.sql.Connection;

public abstract class Controller {
    private final Connection connection;

    public Controller(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
