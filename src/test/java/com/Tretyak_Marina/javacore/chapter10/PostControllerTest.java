package com.Tretyak_Marina.javacore.chapter10;

import com.Tretyak_Marina.javacore.chapter10.controller.PostController;
import com.Tretyak_Marina.javacore.chapter10.model.Label;
import com.Tretyak_Marina.javacore.chapter10.model.Post;
import com.Tretyak_Marina.javacore.chapter10.model.PostStatus;
import com.Tretyak_Marina.javacore.chapter10.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PostControllerTest {
    private PostRepository postRepository = mock();
    private PostController postController = new PostController(postRepository);
    private List<Post> posts;
    private Date now;

    @Before
    public void before() {
        now = new Date();
        setPosts();
        setPostRepositoryCreate();
        setPostRepositoryGetPost();
        setPostRepositoryGetAllPosts();
        setPostRepositoryUpdate();
    }

    private void setPosts() {
        posts = new ArrayList<>();
        Post post1 = new Post(1, "post 1", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        Post post2 = new Post(2, "post 2", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        posts.add(post1);
        posts.add(post2);
    }

    private void setPostRepositoryCreate() {
        Post post = new Post("post 1");
        Post postReturn = new Post(1, "post 1", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        when(postRepository.add(any(Post.class))).thenReturn(postReturn);
    }

    private void setPostRepositoryGetPost() {
        Post post = new Post(1, "post 1", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        when(postRepository.getById(1)).thenReturn(post);
        when(postRepository.getById(500)).thenReturn(null);
    }

    private void setPostRepositoryGetAllPosts() {
        when(postRepository.getAll()).thenReturn(posts);
    }

    private void setPostRepositoryUpdate() {
        Post post = new Post(1, "new post 1", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        when(postRepository.update(any(Post.class))).thenReturn(post);
    }

    @Test
    public void shouldCreatePostTest() {
        Post post = postController.createPost("post 1");
        assertEquals(valueOf(1), post.getId());
        assertEquals("post 1", post.getContent());
        assertEquals(now, post.getCreated());
        assertEquals(now, post.getUpdated());
        assertEquals(0, post.getLabels().size());
        assertEquals(PostStatus.ACTIVE, post.getStatus());
    }

    @Test
    public void shouldNotCreateEmptyContentPostTest() {
        assertNull(postController.createPost(""));
    }

    @Test
    public void shouldGetPostTest() {
        Post post = postController.getPost(1);
        assertEquals(valueOf(1), post.getId());
        assertEquals("post 1", post.getContent());
        assertEquals(now, post.getCreated());
        assertEquals(now, post.getUpdated());
        assertEquals(0, post.getLabels().size());
        assertEquals(PostStatus.ACTIVE, post.getStatus());
    }

    @Test
    public void shouldNotGetWrongIdPostTest() {
        assertNull(postController.getPost(-1));
    }

    @Test
    public void shouldGetAllPostsTest() {
        List<Post> resultList = postController.getAllPosts();
        assertEquals(posts, resultList);
    }

    @Test
    public void shouldUpdatePostContentTest() {
        Post post = postController.updatePost(1, "new post 1");
        assertEquals(valueOf(1), post.getId());
        assertEquals("new post 1", post.getContent());
        assertEquals(now, post.getCreated());
        assertEquals(now, post.getUpdated());
        assertEquals(0, post.getLabels().size());
        assertEquals(PostStatus.ACTIVE, post.getStatus());
    }

    @Test
    public void shouldNotUpdatePostTest() {
        assertNull(postController.updatePost(-1, "new post 1"));
        assertNull(postController.updatePost(1, ""));
    }

    @Test
    public void shouldAddLabelToPostTest() {
        Post post = new Post(1, "post 1", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        Label label = new Label(1, "label 1");
        post.addLabel(label);
        when(postRepository.update(any(Post.class))).thenReturn(post);
        Post result = postController.addLabelToPost(1, label);
        assertEquals(post, result);
    }

    @Test
    public void shouldNotAddLabelToPostTest() {
        Label label = new Label(1, "label 1");
        assertNull(postController.addLabelToPost(-1, label));
    }

    @Test
    public void shouldDeleteLabelFromPostTest() {
        Post result = new Post(1, "post 1", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        Label label = new Label(1, "label 1");
        when(postRepository.update(any(Post.class))).thenReturn(result);
        Post post = new Post(1, "post 1", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        post.addLabel(label);
        post = postController.deleteLabelFromPost(1, 1);
        assertEquals(result.getLabels(), post.getLabels());
    }

    @Test
    public void shouldNotDeleteLabelFromPostTest() {
        assertNull(postController.deleteLabelFromPost(-1, 1));
        assertNull(postController.deleteLabelFromPost(1, -1));
    }

    @Test
    public void shouldDeleteAllLabelsFromPostTest() {
        Post result = new Post(1, "post 1", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        when(postRepository.update(any(Post.class))).thenReturn(result);

        Label label1 = new Label(1, "label 1");
        Label label2 = new Label(2, "label 2");
        Post post = new Post(1, "post 1", now, now, new ArrayList<>(), PostStatus.ACTIVE);
        post.addLabel(label1);
        post.addLabel(label2);

        post = postController.deleteAllLabelFromPost(1);
        assertEquals(valueOf(1), post.getId());
        assertEquals("post 1", post.getContent());
        assertEquals(now, post.getCreated());
        assertEquals(now, post.getUpdated());
        assertEquals(0, post.getLabels().size());
        assertEquals(PostStatus.ACTIVE, post.getStatus());
    }

    @Test
    public void shouldNotDeleteAllLabelsFromPostTest() {
        assertNull(postController.deleteAllLabelFromPost(-1));
    }

    @Test
    public void shouldDeletePostTest() {
        Post result = new Post(1, "post 1", now, now, new ArrayList<>(), PostStatus.DELETED);
        postRepository.deleteById(1);
        when(postRepository.getById(1)).thenReturn(result);

        Post post = postController.getPost(1);
        assertEquals(valueOf(1), post.getId());
        assertEquals("post 1", post.getContent());
        assertEquals(now, post.getCreated());
        assertEquals(now, post.getUpdated());
        assertEquals(0, post.getLabels().size());
        assertEquals(PostStatus.DELETED, post.getStatus());

        postController.deletePost(-1);
        assertNull(postController.getPost(-1));
    }
}
