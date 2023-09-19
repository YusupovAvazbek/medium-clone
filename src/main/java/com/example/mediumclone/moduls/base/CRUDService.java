package com.example.mediumclone.moduls.base;


import com.example.mediumclone.baseDto.BaseDto;
import com.example.mediumclone.baseDto.ResponseDto;
import com.example.mediumclone.moduls.userservice.dto.UsersDto;

import java.io.Serializable;

public interface CRUDService <
        D extends BaseDto,
        K extends Serializable>{
    ResponseDto<D> create(UsersDto currentUser,D dto);
    ResponseDto<Void> delete(UsersDto currentUser,K id);
    ResponseDto<D> update(UsersDto currentUser,D dto);
    ResponseDto<D> get(UsersDto currenUser,K id);


}
