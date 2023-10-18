package com.jobscout.domain.loginandregister;

import com.jobscout.domain.loginandregister.dto.RegistrationResultDto;
import com.jobscout.domain.loginandregister.dto.UserDto;
import com.jobscout.domain.loginandregister.dto.UserRegisterDto;
import lombok.AllArgsConstructor;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

class LoginAndRegisterFacadeTest {

    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(new InMemoryDatabase());


    @Test
    void should_throw_exception_when_user_not_found() {
        //given
        String username="usernameNonExist";

        // when

        Throwable thrown = catchThrowable(() -> loginAndRegisterFacade.findByUsername(username));

        //then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(RuntimeException.class);


    }

    @Test
    void should_find_user_by_user_name() {
        //given
        UserRegisterDto registerDto=new UserRegisterDto("username","password");
        RegistrationResultDto registrationResultDto= loginAndRegisterFacade.register(registerDto);

        //when
        UserDto findUser= loginAndRegisterFacade.findByUsername(registrationResultDto.username());

        //then
        assertEquals(findUser.username(), registerDto.username());
        assertThat(findUser).isEqualTo(new UserDto(registrationResultDto.id(), "username", "password"));

    }

    @Test
    void should_register_user() {
        // given
        UserRegisterDto userRegisterDto = new UserRegisterDto("username", "password");

        // when
        RegistrationResultDto register = loginAndRegisterFacade.register(userRegisterDto);

        // then
        assertAll(
                () -> assertThat(register.created()).isTrue(),
                () -> assertThat(register.username()).isEqualTo("username")
        );

    }

}