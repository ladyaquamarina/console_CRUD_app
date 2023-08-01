package com.Tretyak_Marina.javacore.chapter10.repository.jdbc;

import com.Tretyak_Marina.javacore.chapter10.model.Label;
import com.Tretyak_Marina.javacore.chapter10.repository.LabelRepository;
import com.Tretyak_Marina.javacore.chapter10.utils.RepositoryUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcLabelRepositoryImpl implements LabelRepository {
    @Override
    public Label add(Label label) {
        String sql = "insert into Label (name) values (?);";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, label.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                label.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
        return label;
    }

    @Override
    public Label update(Label label) {
        String sql = "update Label set name = ? where id = ?;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            statement.setString(1, label.getName());
            statement.setInt(2, label.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
        return label;
    }

    @Override
    public List<Label> getAll() {
        List<Label> labels = null;
        String sql = "select * from Label;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (labels == null) {
                    labels = new ArrayList<>();
                }
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                labels.add(new Label(id, name));
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
        return labels;
    }

    @Override
    public Label getById(Integer integer) {
        Label label = null;
        String sql = "select * from Label where id = ?;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            statement.setInt(1, integer);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                label = new Label(id, name);
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
        return label;
    }

    @Override
    public void deleteById(Integer integer) {
        String sql = "delete from Label where id = ?;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            statement.setInt(1, integer);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "truncate Label;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
    }
}