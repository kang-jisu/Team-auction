package com.project.auction.lol.web;

import com.project.auction.lol.controller.api.TeamController;
import com.project.auction.lol.service.TeamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TeamController.class)
@MockBean(JpaMetamodelMappingContext.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private TeamService teamsService;

    @Test
    public void 팀_포지션_정규식(){

        String reg = "TOP|JUG|MID|ADC|SUP";
        String position = "top";
        position = position.toUpperCase();
        assertTrue(Pattern.matches(reg,position));

    }

    @Test
    public void 팀_포지션_정규식_실패(){

        String reg = "TOP|JUG|MID|ADC|SUP";
        String position = "PJU";
        position = position.toUpperCase();
        assertFalse(Pattern.matches(reg,position));

    }

    @Test
    public void 팀생성_GET_잘못된인자() throws Exception {

        mvc.perform(get("/teams/leader/hi"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}