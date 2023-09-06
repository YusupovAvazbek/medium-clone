package com.example.mediumclone.moduls.userservice.service;

import com.example.mediumclone.baseDto.ResponseDto;
import com.example.mediumclone.moduls.base.CRUDService;
import com.example.mediumclone.moduls.userservice.dto.UsersDto;
import org.springframework.stereotype.Service;

@Service
public interface UsersService extends CRUDService<UsersDto, Long> {

    @Override
    ResponseDto<UsersDto> create(UsersDto dto);

    @Override
    ResponseDto<Void> delete(Long id);

    @Override
    ResponseDto<UsersDto> update(UsersDto dto);

    @Override
    ResponseDto<UsersDto> get(Long id);
}
