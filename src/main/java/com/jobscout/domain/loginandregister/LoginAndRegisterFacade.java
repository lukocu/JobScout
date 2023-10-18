package com.jobscout.domain.loginandregister;

import com.jobscout.domain.loginandregister.dto.RegistrationResultDto;
import com.jobscout.domain.loginandregister.dto.UserDto;
import com.jobscout.domain.loginandregister.dto.UserRegisterDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginAndRegisterFacade {

    private final UserRepository userRepository;

    public UserDto findByUsername(String username){
       return  userRepository.findByUsername(username)
                .map(user -> new UserDto(user.id(),user.username(),user.password()))
                .orElseThrow(RuntimeException::new);
    }

    public RegistrationResultDto register(UserRegisterDto userRegister){
        final User user=User.builder()
                .username(userRegister.username())
                .password(userRegister.password())
                .build();
        User savedUser=userRepository.save(user);

        return new RegistrationResultDto(savedUser.id(),true, savedUser.username());
    }

}
