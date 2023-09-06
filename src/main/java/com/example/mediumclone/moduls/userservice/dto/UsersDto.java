package com.example.mediumclone.moduls.userservice.dto;

import com.example.mediumclone.baseDto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto extends BaseDto {
    private String username;
    private String email;
    private String fullname;
    private String bio;
    public UsersDto(Long id, String username, String email, String fullname){
        super(id);
        this.email = email;
        this.fullname = fullname;
        this.username = username;
    }
}
