package com.ex.security.Mapper;

import com.ex.security.persistence.DTO.UserDTO;
import com.ex.security.persistence.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTOtoUser  implements IMapper<UserDTO, User>{
    @Override
    public User map(UserDTO userDTO) {
        User user = new User();

        user.setNombre(userDTO.getNombre());
        user.setMatricula(userDTO.getMatricula());

        user.setPassword(userDTO.getPassword());

        return user;
    }
}
