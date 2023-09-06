package com.example.mediumclone.moduls.userservice.mapper;



import com.example.mediumclone.entity.models.User;
import com.example.mediumclone.moduls.userservice.dto.UsersDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UsersDto toDto(User users);
    User toEntity(UsersDto usersDto);
}
