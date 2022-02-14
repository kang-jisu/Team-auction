package com.project.auction.lol.domain;

import lombok.Builder;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Builder
class Member {

    @NotEmpty(message = "닉네임을 입력해주세요")
    private String summonerName;

    @NotNull(message = "주 포지션을 입력해주세요.")
    @Pattern(regexp= "TOP|JUG|MID|ADC|SUP", message = "올바른 형식의 주포지션을 입력해주세요(TOP,JUG,MID,ADC,SUP)")
    private String mainPosition;

    @Pattern(regexp = "(TOP|JUG|MID|ADC|SUP)?(,(TOP|JUG|MID|ADC|SUP))*",message = "올바른 형식의 부포지션을 입력해주세요(TOP,JUG,MID,ADC,SUP)")
    private String subPositions;

    @NotNull(message = "현재 티어값을 입력해주세요")
    private String currentTier;
    @NotNull(message = "최고 티어값을 입력해주세요")
    private String highestTier;
}


public class ValidationTest {

    @Test
    void participantsValidtaionTest(){
        Member participants = Member.builder()
                .summonerName("하하")
                .mainPosition("JUG")
                .subPositions("SUP,JUG")
                .currentTier("silver2")
                .highestTier("silver2")
                .build();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Member>> constraintViolations = validator.validate(participants);

        assertThat(constraintViolations)
                .extracting(ConstraintViolation::getMessage);
    }
}
