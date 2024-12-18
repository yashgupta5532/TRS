package com.trs.TRS.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trs.TRS.constants.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // generates getters,setters,toString()
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users") //
public class User {

    @Id
    private String id; // this id references the mongodb's _id

    @NotBlank(message = "Username is Required")
    private String username;

    @Email
    @NotBlank(message = "Email is Required")
    private String email;

    @NotBlank(message = "userType is Required")
    private UserRole userType;

    @NotBlank(message = "Password is Required")
    private String password;

    private String avatar;
    private String coverImage;
}
