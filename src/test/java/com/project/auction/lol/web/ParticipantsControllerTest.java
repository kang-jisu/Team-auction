package com.project.auction.lol.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.project.auction.lol.dto.ParticipantsSaveRequestDto;
import com.project.auction.lol.errors.ErrorCode;
import com.project.auction.lol.errors.MayoException;
import com.project.auction.lol.service.ParticipantsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ParticipantsController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ParticipantsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ParticipantsService participantsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void mvcnull(){
        assertThat(mvc).isNotNull();
    }
    @Test
    public void DuplicateSummonerNameErrorTest() throws Exception {

        final ParticipantsSaveRequestDto dto = ParticipantsSaveRequestDto.builder()
                .summonerName("감귤or가씨")
                .mainPosition("SUP")
                .subPositions("MID")
                .currentTier("silver2")
                .highestTier("silver2")
                .build();

        given(participantsService.save(any(ParticipantsSaveRequestDto.class))).willThrow(new MayoException(ErrorCode.DUPLICATE_SUMMONER_NAME));

        mvc.perform(post("/participants")
                .header(HttpHeaders.CONTENT_TYPE,"application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

}
