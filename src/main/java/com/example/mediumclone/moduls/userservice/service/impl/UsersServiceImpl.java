package com.example.mediumclone.moduls.userservice.service.impl;

import com.example.mediumclone.baseDto.ResponseDto;
import com.example.mediumclone.entity.models.User;
import com.example.mediumclone.message.AppStatusCodes;
import com.example.mediumclone.message.AppStatusMessages;
import com.example.mediumclone.moduls.userservice.dto.UsersDto;
import com.example.mediumclone.moduls.userservice.mapper.UserMapper;
import com.example.mediumclone.moduls.userservice.repository.UsersRepository;
import com.example.mediumclone.moduls.userservice.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.mediumclone.message.AppStatusCodes.DATABASE_ERROR_CODE;
import static com.example.mediumclone.message.AppStatusCodes.NOT_FOUND_ERROR_CODE;
import static com.example.mediumclone.message.AppStatusMessages.*;


@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {
    private final UserMapper usersMapper;
    private final UsersRepository usersRepository;
    @Override
    public ResponseDto<UsersDto> create(UsersDto usersDto) {
        try {
            Optional<User> byEmail = usersRepository.findByEmail(usersDto.getEmail());
            Optional<User> byUsername = usersRepository.findByUsername(usersDto.getUsername());

            if (byEmail.isPresent())
                return ResponseDto.<UsersDto>builder()
                        .code(AppStatusCodes.ALREADY_EXISTS_ERROR_CODE)
                        .message(AppStatusMessages.ALREADY_EXISTS+" this email:"+usersDto.getEmail())
                        .build();

            if (byUsername.isPresent())
                return ResponseDto.<UsersDto>builder()
                        .code(AppStatusCodes.ALREADY_EXISTS_ERROR_CODE)
                        .message(AppStatusMessages.ALREADY_EXISTS + " this username : "+usersDto.getUsername())
                        .build();

            User users = usersMapper.toEntity(usersDto);
            users.setRole("USER");
            usersRepository.save(users);
            return ResponseDto.<UsersDto>builder()
                    .success(true)
                    .code(AppStatusCodes.OK_CODE)
                    .data(usersMapper.toDto(users))
                    .message(OK)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .data(usersDto)
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR+" :Error while saving user: " + e.getMessage())
                    .build();
        }
    }


    @Override
    public ResponseDto<Void> delete(Long id) {
        try {
            Optional<User> byId = usersRepository.findById(id);
            if (byId.isPresent()) {
                usersRepository.deleteById(id);
                return ResponseDto.<Void>builder()
                        .success(true)
                        .message(OK)
                        .build();
            }
            return ResponseDto.<Void>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<Void>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseDto<UsersDto> update(UsersDto usersDto) {
        if (usersDto.getId() == null) {
            return ResponseDto.<UsersDto>builder()
                    .message(AppStatusMessages.NULL_VALUE)
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .build();
        }
        try {
            Optional<User> userOptional = usersRepository.findById(usersDto.getId());
            if (userOptional.isEmpty()) {
                return ResponseDto.<UsersDto>builder()
                        .message(NOT_FOUND)
                        .code(NOT_FOUND_ERROR_CODE)
                        .build();
            }
            if (usersDto.getEmail() != null) {
                Optional<User> byEmail = usersRepository.findByEmail(usersDto.getEmail());
                if (byEmail.isPresent() && !userOptional.get().getEmail().equals(usersDto.getEmail()))
                    return ResponseDto.<UsersDto>builder()
                            .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                            .message(usersDto.getEmail() + AppStatusMessages.ALREADY_EXISTS)
                            .build();
            }
            if (usersDto.getUsername() != null) {
                Optional<User> byUsername = usersRepository.findByUsername(usersDto.getUsername());
                if (byUsername.isPresent() && !userOptional.get().getUsername().equals(usersDto.getUsername()))
                    return ResponseDto.<UsersDto>builder()
                            .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                            .message(usersDto.getUsername() + AppStatusMessages.ALREADY_EXISTS)
                            .build();
            }

            User updatedUser = editUserInfo(userOptional.get(), usersDto);
            updatedUser.setUpdatedAt(LocalDateTime.now());
            usersRepository.save(updatedUser);

            return ResponseDto.<UsersDto>builder()
                    .data(usersMapper.toDto(updatedUser))
                    .success(true)
                    .message(OK)
                    .code(AppStatusCodes.OK_CODE)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .code(AppStatusCodes.UNEXPECTED_ERROR_CODE)
                    .message(e.getMessage())
                    .build();
        }

    }


    @Override
    public ResponseDto<UsersDto> get(Long id) {
        try {
            Optional<User> byId = usersRepository.findById(id);
            if (byId.isEmpty()) {
                return ResponseDto.<UsersDto>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .build();
            }
            return ResponseDto.<UsersDto>builder()
                    .success(true)
                    .message(OK)
                    .data(usersMapper.toDto(byId.get()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .code(1)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }
    public User editUserInfo(User user, UsersDto usersDto) {
        user.setEmail(Optional.ofNullable(usersDto.getEmail()).orElse(user.getEmail()));
        user.setBio(Optional.ofNullable(usersDto.getBio()).orElse(user.getBio()));
        user.setUsername(Optional.ofNullable(usersDto.getUsername()).orElse(user.getUsername()));

        return user;
    }

}
