package lk.ijse.todo.model;

import lk.ijse.todo.db.DbConnection;
import lk.ijse.todo.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    public static String getPassword(final String name) {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT password FROM users WHERE name = ?";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, name);

            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()) {

                return resultSet.getString(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static boolean saveUser(final UserDto userDto) {

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO users VALUES(?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, userDto.getEmail());
            ps.setString(2, userDto.getName());
            ps.setString(3, userDto.getPassword());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEmailOfUName(final String uName) {

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT email FROM users WHERE name = ?";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, uName);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
