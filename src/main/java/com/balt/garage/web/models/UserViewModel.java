package com.balt.garage.web.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserViewModel {
    private String fullName;
    private String username;
    private String password;
    private String confirmPassword;
    private String phone;
    private String email;

}
