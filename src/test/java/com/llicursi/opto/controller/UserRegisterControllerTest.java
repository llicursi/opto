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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserRegisterControllerTest {

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
    public void givenRegisterUser_whenRoleUser_thenReturnOK() throws Exception {

        String tokenUser1 = obtainAccessToken("user@opto.com", "123");

        mvc.perform(post(BASE_URL_API + "/register")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(simulateUserRegisterDTO()))
                .header("Authorization", "Bearer " + tokenUser1))
            .andDo(print())
            .andExpect(status().isCreated());
    }


    @Test
    public void givenRegisterUser_whenAnonymous_thenReturnOK() throws Exception {

        mvc.perform(post(BASE_URL_API + "/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(simulateUserRegister2DTO())))
                .andDo(print())
                .andExpect(status().isCreated());
    }


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

    UserRegisterDTO simulateUserRegisterDTO(){
        return new UserRegisterDTO("Lisa", "lisa","lisa@lisa", "123" );
    }

    UserRegisterDTO simulateUserRegister2DTO(){
        return new UserRegisterDTO("Lisa", "lisa II","lisa2@lisa2", "123" );
    }
}
