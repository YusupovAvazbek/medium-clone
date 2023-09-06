package com.example.mediumclone.moduls.userservice.controller;

import com.example.mediumclone.baseDto.ResponseDto;
import com.example.mediumclone.moduls.userservice.dto.UsersDto;
import com.example.mediumclone.moduls.userservice.service.impl.UsersServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UsersServiceImpl usersService;
    @GetMapping("/just")
    public ResponseDto<String> just(){
        return ResponseDto.<String>builder()
                .message("ishladi")
                .build();
    }
    @PostMapping("/sign-up")
    @Operation(
            summary = "Adds a new user",
            method = "POST",
            description = "Need to send UserDto to this endpoint to create new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "User info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    public ResponseDto<UsersDto> addUser(@RequestBody UsersDto usersDto) {
        return usersService.create(usersDto);
    }
    @PatchMapping("/update")
    @Operation(
            summary = "Update user",
            method = "PATCH",
            description = "Need to send UserDto to this endpoint to user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "User info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.update(usersDto);
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Gets a user by user ID",
            method = "GET",
            description = "Need to send the user ID to get this user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "User info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public ResponseDto<UsersDto> getUserById(@PathVariable Long id){
        return usersService.get(id);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a user by user ID",
            method = "DELETE",
            description = "Need to send the user ID to delete the user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "User info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    public ResponseDto<Void> deleteUserById(@PathVariable Long id){
        return usersService.delete(id);
    }

}
