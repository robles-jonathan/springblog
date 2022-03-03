package com.codeup.springblog;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringblogApplication.class)
@AutoConfigureMockMvc
public class PostsIntegrationTests {

    private User testUser;
    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userDao;

    @Autowired
    PostRepository postsDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception {
        testUser = userDao.findByUsername("testUser");

        //Create the test user if not exists
        if (testUser == null) {
            User newUser = new User();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("password"));
            newUser.setEmail("testUser@codeup.com");
            testUser = userDao.save(newUser);
        }

        //Throws a Post request to /login and expect a redirection to the Posts index page after being logged in
        httpSession = this.mvc.perform(post("/login").with(csrf())
                        .param("username", "testUser")
                        .param("password", "password"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/posts"))
                .andReturn()
                .getRequest()
                .getSession();
    }

    @Test
    public void contextLoads() {
        // Sanity Test, just to make sure the MVC bean is working
        assertNotNull(mvc);
    }

    @Test
    public void testIfUserSessionIsActive() throws Exception {
        //It makes sure the returned session is not null
        assertNotNull(httpSession);
    }

    @Test
    public void testCreatePost() throws Exception {
        // Makes a Post request to /posts/create and expect a redirection to the Post index page
        this.mvc.perform(post("/posts/create").with(csrf())
                        .session((MockHttpSession) httpSession)
                        // Add all the required parameters to your request like this
                        .param("title", "test title")
                        .param("body", "test body"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testShowPost() throws Exception {
        Post existingPost = postsDao.findAll().get(0);
        //Makes a Get request to /posts/{id} and expects a redirection to the show Post page
        this.mvc.perform(get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk())
                //Test the dynamic content of the page
                .andExpect(content().string(containsString(existingPost.getBody())));
    }

    @Test
    public void testPostsIndex() throws Exception {
        Post existingPost = postsDao.findAll().get(0);
        this.mvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Posts")))
                .andExpect(content().string(containsString(existingPost.getTitle())));
    }

    @Test
    public void testEditPost() throws Exception {
        List<Post> usersPosts = postsDao.findPostsByUser(testUser);
        Post existingPost = usersPosts.get(0);
        this.mvc.perform(post("/posts/" + existingPost.getId() + "/edit").with(csrf())
                        .session((MockHttpSession) httpSession)
                        .param("title", "Automated Test Edited Title")
                        .param("body", "New Automated Test Edited Body"))
                .andExpect(status().is3xxRedirection());

        this.mvc.perform(get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Automated Test Edited Title")))
                .andExpect(content().string(containsString("New Automated Test Edited Body")));
    }

    @Test
    public void testDeletePost() throws Exception {

        this.mvc.perform(
                        post("/posts/create").with(csrf())
                                .session((MockHttpSession) httpSession)
                                .param("title", "Post to Be DELETED")
                                .param("body", "Test DELETE POST BODY"))
                .andExpect(status().is3xxRedirection());

        Post existingPost = postsDao.findPostByTitle("Post to Be DELETED");

        this.mvc.perform(
                        post("/posts/" + existingPost.getId() + "/delete").with(csrf())
                                .session((MockHttpSession) httpSession)
                                .param("id", String.valueOf(existingPost.getId())))
                .andExpect(status().is3xxRedirection());
    }


}
