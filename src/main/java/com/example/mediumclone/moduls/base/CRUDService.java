package com.example.mediumclone.moduls.base;


import com.example.mediumclone.baseDto.BaseDto;
import com.example.mediumclone.baseDto.ResponseDto;

import java.io.Serializable;

public interface CRUDService <
        D extends BaseDto,
        K extends Serializable>{
    ResponseDto<D> create(D dto);
    ResponseDto<Void> delete(K id);
    ResponseDto<D> update(D dto);
    ResponseDto<D> get(K id);


}
