package com.vukkumsp.notes_service.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginDTO {
    private String username;
    private String password;
}
