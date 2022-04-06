package com.ex.security.persistence.DTO;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String matricula;
    private String nombre;
    private String password;
}
