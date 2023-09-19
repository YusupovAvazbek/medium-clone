package com.example.mediumclone.moduls.userservice.dto;

import com.example.mediumclone.baseDto.BaseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"authorities",
        "role", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"},allowSetters = true)
public class UsersDto extends BaseDto implements UserDetails {
    private String username;
    private String password;
    private String email;
    private String fullname;
    private String bio;
    public UsersDto(Long id, String username, String email, String fullname){
        super(id);
        this.email = email;
        this.fullname = fullname;
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
