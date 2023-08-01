package com.Tretyak_Marina.javacore.chapter10.repository.jdbc;

import com.Tretyak_Marina.javacore.chapter10.model.*;
import com.Tretyak_Marina.javacore.chapter10.repository.WriterRepository;
import com.Tretyak_Marina.javacore.chapter10.utils.RepositoryUtils;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

public class JdbcWriterRepositoryImpl implements WriterRepository {
    private List<Label> getLabelsOfPost(int postId) {
        List<Label> labels = new ArrayList<>();
        ArrayList<Integer> post_label = new ArrayList<>();

        String sql = "select * from post_label where post_id = ?;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            statement.setInt(1, postId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int label_id = resultSet.getInt("label_id");
                post_label.add(label_id);
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }

        sql = "select * from label";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                post_label.forEach(l -> {
                    if (l == id) labels.add(new Label(id, name));
                });
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }

        return labels;
    }

    private List<Post> getPostsOfWriter(int writerId) {
        List<Post> posts = new ArrayList<>();
        ArrayList<Integer> writer_post = new ArrayList<>();

        String sql = "select * from writer_post where writer_id = ?;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            statement.setInt(1, writerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int post_id = resultSet.getInt("post_id");
                writer_post.add(post_id);
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }

        sql = "select * from Post;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String content = resultSet.getString("content");
                Date created = resultSet.getDate("created");
                Date updated = resultSet.getDate("updated");
                String strStatus = resultSet.getString("state");
                PostStatus Status;
                if (strStatus.equalsIgnoreCase("Active")) {
                    Status = PostStatus.ACTIVE;
                } else if (strStatus.equalsIgnoreCase("Deleted")) {
                    Status = PostStatus.DELETED;
                }  else {
                    Status = PostStatus.UNDER_REVIEW;
                }
                writer_post.forEach(p -> {
                    if (p == id)
                        posts.add(new Post(id, content, created, updated, getLabelsOfPost(id), Status));
                });
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }

        return posts;
    }
    private void setPostsOfWriter(Writer writer) {
            Set<Integer> oldPostsSet = getPostsOfWriter(writer.getId()).stream()
                    .map(Post::getId).collect(Collectors.toSet());
            Set<Integer> setOfChangeblePartPosts = new HashSet<>();

            String sql = null;
            if (writer.getPosts().size() > oldPostsSet.size()) {
                for (Post post : writer.getPosts()) {
                    if (!oldPostsSet.contains(post.getId())) {
                        setOfChangeblePartPosts.add(post.getId());
                    }
                }
                sql = "insert into writer_post (writer_id, post_id) values (?, ?);";
            } else if (writer.getPosts().size() < oldPostsSet.size()) {
                Set<Integer> newPostsSet = writer.getPosts().stream()
                        .map(Post::getId).collect(Collectors.toSet());
                for (int post : oldPostsSet) {
                    if (!newPostsSet.contains(post)) {
                        setOfChangeblePartPosts.add(post);
                    }
                }
                sql = "delete from writer_post where writer_id = ? and post_id = ?;";
            }

        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            for (int p : setOfChangeblePartPosts) {
                statement.setInt(1, writer.getId());
                statement.setInt(2, p);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
    }

    private Writer getWriterFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("firstName");
        String secondName = resultSet.getString("secondName");
        return new Writer(id, firstName, secondName, new ArrayList<>());
    }

    @Override
    public Writer add(Writer writer) {
        String sql = "insert into Writer (firstName, secondName) values (?, ?);";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, writer.getFirstName());
            statement.setString(2, writer.getLastName());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                writer.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        setPostsOfWriter(writer);
        String sql = "update Writer set firstName = ?, secondName = ? where id = ?;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            statement.setString(1, writer.getFirstName());
            statement.setString(2, writer.getLastName());
            statement.setInt(3, writer.getId());
            statement.executeUpdate();
            writer = getById(writer.getId());
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
        return writer;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writers = null;
        String sql = "select * from Writer;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (writers == null) {
                    writers = new ArrayList<>();
                }
                writers.add(getWriterFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
        return writers;
    }

    @Override
    public Writer getById(Integer integer) {
        Writer writer = null;
        String sql = "select * from Writer where id = ?;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            statement.setInt(1, integer);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                writer = getWriterFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
        return writer;
    }

    @Override
    public void deleteById(Integer integer) {
        String sql = "delete from Writer where id = ?;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            statement.setInt(1, integer);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "truncate Writer;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
    }
}
