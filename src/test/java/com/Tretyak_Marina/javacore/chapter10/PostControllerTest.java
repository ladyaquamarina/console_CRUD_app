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

import static java.lang.Long.valueOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PostControllerTest {
    private final PostRepository postRepository = mock();
    private final PostController postController = new PostController(postRepository);
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

        Post post1 = new Post();
        post1.setId(1L);
        post1.setContent("post 1");
        post1.setCreated(now);
        post1.setUpdated(now);
        post1.setLabels(new ArrayList<>());
        post1.setStatus(PostStatus.ACTIVE);
        Post post2 = new Post();
        post2.setId(2L);
        post2.setContent("post 2");
        post2.setCreated(now);
        post2.setUpdated(now);
        post2.setLabels(new ArrayList<>());
        post2.setStatus(PostStatus.ACTIVE);

        posts.add(post1);
        posts.add(post2);
    }

    private void setPostRepositoryCreate() {
        Post postReturn = new Post();
        postReturn.setId(1L);
        postReturn.setContent("post 1");
        postReturn.setCreated(now);
        postReturn.setUpdated(now);
        postReturn.setLabels(new ArrayList<>());
        postReturn.setStatus(PostStatus.ACTIVE);
        when(postRepository.add(any(Post.class))).thenReturn(postReturn);
    }

    private void setPostRepositoryGetPost() {
        Post post = new Post();
        post.setId(1L);
        post.setContent("post 1");
        post.setCreated(now);
        post.setLabels(new ArrayList<>());
        post.setStatus(PostStatus.ACTIVE);
        post.setUpdated(now);
        when(postRepository.getById(1L)).thenReturn(post);
        when(postRepository.getById(500L)).thenReturn(null);
    }

    private void setPostRepositoryGetAllPosts() {
        when(postRepository.getAll()).thenReturn(posts);
    }

    private void setPostRepositoryUpdate() {
        Post post = new Post();
        post.setId(1L);
        post.setContent("new post 1");
        post.setCreated(now);
        post.setUpdated(now);
        post.setLabels(new ArrayList<>());
        post.setStatus(PostStatus.ACTIVE);
        when(postRepository.update(any(Post.class))).thenReturn(post);
    }

    @Test
    public void shouldCreatePostTest() {
        Post post = postController.createPost("post 1");
        post.setUpdated(now);
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
        post.setUpdated(now);
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
        Post post = new Post();
        post.setId(1L);
        post.setContent("post 1");
        post.setCreated(now);
        post.setLabels(new ArrayList<>());
        post.setStatus(PostStatus.ACTIVE);
        Label label = new Label(1L, "label 1", post);
        post.addLabel(label);
        post.setUpdated(now);

        when(postRepository.update(any(Post.class))).thenReturn(post);
        Post result = postController.addLabelToPost(1L, label);
        assertEquals(post, result);
    }

    @Test
    public void shouldNotAddLabelToPostTest() {
        Label label = new Label();
        label.setId(1L);
        label.setName("label 1");
        assertNull(postController.addLabelToPost(-1, label));
    }

    @Test
    public void shouldDeleteLabelFromPostTest() {
        Post result = new Post();
        result.setId(1L);
        result.setContent("post 1");
        result.setCreated(now);
        result.setLabels(new ArrayList<>());
        result.setStatus(PostStatus.ACTIVE);
        result.setUpdated(now);
        when(postRepository.update(any(Post.class))).thenReturn(result);

        Post post = new Post();
        post.setId(1L);
        post.setContent("post 1");
        post.setCreated(now);
        post.setLabels(new ArrayList<>());
        post.setStatus(PostStatus.ACTIVE);
        Label label = new Label(1L, "label 1", post);
        post.addLabel(label);
        post.setUpdated(now);

        post = postController.deleteLabelFromPost(1L, 1L);
        assertEquals(result.getLabels(), post.getLabels());
    }

    @Test
    public void shouldNotDeleteLabelFromPostTest() {
        assertNull(postController.deleteLabelFromPost(-1, 1));
        assertNull(postController.deleteLabelFromPost(1, -1));
    }

    @Test
    public void shouldDeleteAllLabelsFromPostTest() {
        Post result = new Post();
        result.setId(1L);
        result.setContent("post 1");
        result.setCreated(now);
        result.setLabels(new ArrayList<>());
        result.setStatus(PostStatus.ACTIVE);
        result.setUpdated(now);
        when(postRepository.update(any(Post.class))).thenReturn(result);

        Post post = new Post();
        post.setId(1L);
        post.setContent("post 1");
        post.setCreated(now);
        post.setLabels(new ArrayList<>());
        post.setStatus(PostStatus.ACTIVE);
        Label label1 = new Label(1L, "label 1", post);
        Label label2 = new Label(2L, "label 2", post);
        post.addLabel(label1);
        post.addLabel(label2);
        post.setUpdated(now);

        post = postController.deleteAllLabelFromPost(1);
        post.setUpdated(now);
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
        Post result = new Post();
        result.setId(1L);
        result.setContent("post 1");
        result.setCreated(now);
        result.setLabels(new ArrayList<>());
        result.setStatus(PostStatus.DELETED);
        result.setUpdated(now);

        postController.deletePost(1L);
        when(postRepository.getById(1L)).thenReturn(result);

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
