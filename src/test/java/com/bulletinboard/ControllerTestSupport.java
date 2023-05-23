package com.bulletinboard;

import com.bulletinboard.member.controller.MemberController;
import com.bulletinboard.member.service.MemberService;
import com.bulletinboard.post.controller.PostController;
import com.bulletinboard.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        PostController.class,
        MemberController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected PostService postService;

    @MockBean
    protected MemberService memberService;
}
