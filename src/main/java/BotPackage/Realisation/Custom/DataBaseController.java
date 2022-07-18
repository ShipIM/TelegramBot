package BotPackage.Realisation.Custom;

import BotPackage.Models.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseController extends Controller {
    public DataBaseController(Connection connection) {
        super(connection);
    }

    public boolean addUser(User user) {
        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement("INSERT INTO " +
                    "users (user_name, user_id, user_group, user_status) VALUES (?, ?, ?, CAST (? AS status))");

            preparedStatement.setString(1, user.getName());
            preparedStatement.setLong(2, user.getId());
            preparedStatement.setInt(3, user.getGroup());
            preparedStatement.setString(4, user.getStatus().getName());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
