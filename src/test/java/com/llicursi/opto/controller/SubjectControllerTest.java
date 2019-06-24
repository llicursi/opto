package com.llicursi.opto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llicursi.opto.datatransferobject.UserRegisterDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SubjectControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc ;

    @Autowired
    FilterChainProxy springSecurityFilterChain;

    final ObjectMapper objectMapper = new ObjectMapper();

    private final static String BASE_URL_API = "/api/v1";


    @Before
    public void setup() throws Exception {

        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .addFilter(springSecurityFilterChain)
            .build();

    }

    @Test
    public void givenSubjectApi_whenGET_thenReturnSingleElementInList() throws Exception {

        String tokenUser1 = obtainAccessToken("user@opto.com", "123");

        mvc.perform(get(BASE_URL_API + "/subject")
            .header("Authorization", "Bearer " + tokenUser1))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)))
        ;
    }


    @Test
    public void givenUserSubjectApi_whenGET_thenReturn2SubjectsRelatedToSessionUser() throws Exception {

        String tokenUser1 = obtainAccessToken("user@opto.com", "123");

        mvc.perform(get(BASE_URL_API + "/user/subject")
                .header("Authorization", "Bearer " + tokenUser1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
        ;
    }

/*
    @Test
    public void givenSubjectApi_whenPOSTValidJson_thenCreated() throws Exception {

        String tokenUser1 = obtainAccessToken("user@opto.com", "123");

        mvc.perform(get(BASE_URL_API + "/user/subject")
                .header("Authorization", "Bearer " + tokenUser1))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
        ;
    }
*/



    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "test");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
            = mvc.perform(post("/oauth/token")
            .params(params)
            .with(httpBasic("test","10c25665e49274c39b8e8f7ad6e2a3d0b0bc5052"))
            .accept("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

}
