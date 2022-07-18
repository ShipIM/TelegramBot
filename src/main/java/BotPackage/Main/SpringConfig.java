package BotPackage.Main;

import BotPackage.Realisation.Custom.DataBaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource("PropertyFile")
@ComponentScan("BotPackage")
public class SpringConfig {

    @Bean("connection")
    public Connection connection(@Value("${bd.path}") String path, @Value("${bd.login}") String login,
                                 @Value("${bd.password}") String password) {
        try {
            return DriverManager.getConnection(path, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Bean
    public DataBaseController dataBaseController(Connection connection) {
        return new DataBaseController(connection);
    }
}
