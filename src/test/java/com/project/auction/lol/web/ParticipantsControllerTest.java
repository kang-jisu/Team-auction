package com.project.auction.lol.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.project.auction.lol.domain.ParticipantsEntity;
import com.project.auction.lol.dto.ParticipantsSaveRequestDto;
import com.project.auction.lol.dto.ParticipantsSaveResponseDto;
import com.project.auction.lol.errors.ErrorCode;
import com.project.auction.lol.errors.MayoException;
import com.project.auction.lol.service.ParticipantsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
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
    @DisplayName("이미 등록된 사용자일 때 테스트")
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
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("validation으로 유효성 검증 에러핸들링 테스트")
    public void parameterValidationTest() throws Exception {

        final ParticipantsSaveRequestDto validErrorDto = ParticipantsSaveRequestDto.builder()
                .summonerName("감귤or가씨")
                .mainPosition("")
                .subPositions("MIDdd")
                .currentTier("silver2")
                .highestTier("")
                .build();

        given(participantsService.save(any(ParticipantsSaveRequestDto.class))).willThrow(new MayoException(ErrorCode.DUPLICATE_SUMMONER_NAME));

        mvc.perform(post("/participants")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(validErrorDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void successTest() throws Exception {
        final ParticipantsSaveRequestDto requestDto = ParticipantsSaveRequestDto.builder()
                .summonerName("새로운참가자")
                .mainPosition("TOP")
                .currentTier("platinum2")
                .highestTier("platinum2")
                .build();

        final ParticipantsSaveResponseDto responseDto = ParticipantsSaveResponseDto.builder()
                .id(1l)
                .summonerName("새로운참가자")
                .mainPosition("TOP")
                .currentTier("platinum2")
                .highestTier("platinum2")
                .build();

        given(participantsService.save(requestDto)).willReturn(responseDto);

        mvc.perform(post("/participants")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }
}
