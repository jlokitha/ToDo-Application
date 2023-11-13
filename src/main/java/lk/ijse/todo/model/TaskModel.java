package lk.ijse.todo.model;

import lk.ijse.todo.db.DbConnection;
import lk.ijse.todo.dto.TasksDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskModel {

    public static boolean saveTask(final TasksDto tasksDto) {

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO tasks VALUES(?, ?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, tasksDto.getTask_id());
            ps.setString(2, tasksDto.getEmail());
            ps.setString(3, tasksDto.getDescription());
            ps.setString(4, tasksDto.getDueDate());
            ps.setInt(5, 0);

            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateNewId() {

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT task_id FROM tasks ORDER BY task_id DESC LIMIT 1";

            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            String currentTaskId = null;

            if (resultSet.next()) {

                currentTaskId = resultSet.getString(1);
                return splitOrderId(currentTaskId);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return splitOrderId(null);
    }

    private static String splitOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            int id = Integer.parseInt(currentOrderId);
            id++;
            return String.valueOf(id);
        }

        return "1";
    }

    public static List<TasksDto> getAllDueTask(final String email) {

        List<TasksDto> dtoList;

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM tasks WHERE isCompleted = 0 AND email = ?";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, email);

            ResultSet resultSet = ps.executeQuery();

            dtoList = new ArrayList<>();

            while (resultSet.next()) {
                var dto = new TasksDto(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getShort(5)
                );

                dtoList.add(dto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dtoList;
    }

    public static List<TasksDto> getAllCompletedTask(final String email) {

        List<TasksDto> dtoList;

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM tasks WHERE isCompleted = 1 AND email = ?";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, email);

            ResultSet resultSet = ps.executeQuery();

            dtoList = new ArrayList<>();

            while (resultSet.next()) {
                var dto = new TasksDto(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getShort(5)
                );

                dtoList.add(dto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dtoList;
    }

    public static boolean MarkAsCompleted(final String desc) {

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "UPDATE tasks SET isCompleted = 1 WHERE description = ?";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, desc);

            int affectedRows = ps.executeUpdate();
            System.out.println(affectedRows);
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteTask(final String desc) {

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "DELETE FROM tasks WHERE description = ?";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, desc);

            int affectedRows = ps.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
