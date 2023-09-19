package com.example.mediumclone.baseDto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class LoginDto {
    private String username;
    private String password;

}
