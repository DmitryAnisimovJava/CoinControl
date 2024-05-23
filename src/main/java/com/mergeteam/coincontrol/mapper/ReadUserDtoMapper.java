package com.mergeteam.coincontrol.mapper;

import com.mergeteam.coincontrol.dto.user.ReadUserDto;
import com.mergeteam.coincontrol.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public abstract class ReadUserDtoMapper {
    public static final ReadUserDtoMapper INSTANCE = Mappers.getMapper(ReadUserDtoMapper.class);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "avatarPath", source = "avatarPath")
    @Mapping(target = "role", source = "role")
    public abstract ReadUserDto entityToDto(User user);

}
