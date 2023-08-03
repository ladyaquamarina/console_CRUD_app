package com.Tretyak_Marina.javacore.chapter10.repository.jdbc;

import com.Tretyak_Marina.javacore.chapter10.model.*;
import com.Tretyak_Marina.javacore.chapter10.repository.PostRepository;
import com.Tretyak_Marina.javacore.chapter10.utils.RepositoryUtils;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

public class JdbcPostRepositoryImpl implements PostRepository {
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

    private void setLabelsOfPost(Post post) {
        Set<Integer> oldLabelsSet = getLabelsOfPost(post.getId()).stream()
                .map(Label::getId).collect(Collectors.toSet());
        Set<Integer> setOfChangeablePartLabels = new HashSet<>();
        String sql = null;

        if (post.getLabels().size() > oldLabelsSet.size()) {
            for (Label label : post.getLabels()) {
                if (!oldLabelsSet.contains(label.getId())) {
                    setOfChangeablePartLabels.add(label.getId());
                }
            }
            sql = "insert into post_label (post_id, label_id) values (?, ?)";
        } else if (post.getLabels().size() < oldLabelsSet.size()) {
            Set<Integer> newLabelsSet = post.getLabels().stream()
                    .map(Label::getId).collect(Collectors.toSet());
            for (int labelId : oldLabelsSet) {
                if (!newLabelsSet.contains(labelId)) {
                    setOfChangeablePartLabels.add(labelId);
                }
            }
            sql = "delete from post_label where post_id = ? and label_id = ?";
        }

        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            for (int l : setOfChangeablePartLabels) {
                statement.setInt(1, post.getId());
                statement.setInt(2, l);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
    }

    private Post getPostFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String content = resultSet.getString("content");
        Date created = resultSet.getTimestamp("created");
        Date updated = resultSet.getTimestamp("updated");
        String strStatus = resultSet.getString("state");
        PostStatus Status;
        if (strStatus.equalsIgnoreCase("Active")) {
            Status = PostStatus.ACTIVE;
        } else if (strStatus.equalsIgnoreCase("Deleted")) {
            Status = PostStatus.DELETED;
        }  else {
            Status = PostStatus.UNDER_REVIEW;
        }
        Integer label_id = resultSet.getInt("label_id");
        String label_name = resultSet.getString("name");
        List<Label> labelList = new ArrayList<>();
        if (label_id != null) {
            Label label = new Label(label_id, label_name);
            labelList.add(label);
        }
        return new Post(id, content, created, updated, labelList, Status);
    }

    @Override
    public Post add(Post post) {
        String sql = "insert into Post (content) values (?);";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getContent());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                post.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        setLabelsOfPost(post);
        String sql = "update Post set content = ?, state = ? where id = ?;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            statement.setString(1, post.getContent());
            statement.setString(2, post.getStatus().toString());
            statement.setInt(3, post.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
        return post;
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = null;
        Set<Integer> ids = new HashSet<>();
        String sql = """
                select p.id, p.content, p.created, p.updated, p.state, l.id as "label_id", l.name
                from post p
                left outer join post_label pl
                on p.id = pl.post_id
                left outer join label l
                on pl.label_id = l.id;""";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (posts == null) {
                    posts = new ArrayList<>();
                }
                Post post = getPostFromResultSet(resultSet);
                if (ids.add(post.getId())) {
                    posts.add(post);
                } else for (Post p : posts) {
                    if (Objects.equals(p.getId(), post.getId())) {
                        p.addLabel(post.getLabels().get(0));
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
        return posts;
    }

    @Override
    public Post getById(Integer integer) {
        Post post = null;
        String sql = """
                select p.id, p.content, p.created, p.updated, p.state, l.id as "label_id", l.name
                from post p
                left outer join post_label pl
                on p.id = pl.post_id
                left outer join label l
                on pl.label_id = l.id
                where p.id = ?;""";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            statement.setInt(1, integer);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (post == null) {
                    post = getPostFromResultSet(resultSet);
                } else {
                    post.addLabel(getPostFromResultSet(resultSet).getLabels().get(0));
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
        return post;
    }

    @Override
    public void deleteById(Integer integer) {
        String sql = "update Post set state = \"deleted\" where id = ?;";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            statement.setInt(1, integer);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "update Post set state = \"deleted\";";
        try (PreparedStatement statement = RepositoryUtils.getPreparedStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        }
    }
}
