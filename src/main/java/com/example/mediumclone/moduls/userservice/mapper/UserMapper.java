package com.example.mediumclone.moduls.userservice.mapper;



import com.example.mediumclone.entity.models.User;
import com.example.mediumclone.moduls.userservice.dto.UsersDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    PasswordEncoder passwordEncoder;
    public abstract UsersDto toDto(User users);
    @Mapping(target = "password",expression = "java(passwordEncoder.encode(usersDto.getPassword()))")
    public abstract User toEntity(UsersDto usersDto);

    public abstract User toEntityPassword(UsersDto usersDto);

}
