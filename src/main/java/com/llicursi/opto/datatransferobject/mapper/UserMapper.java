package com.llicursi.opto.datatransferobject.mapper;

import com.llicursi.opto.datatransferobject.UserDTO;
import com.llicursi.opto.datatransferobject.UserRegisterDTO;
import com.llicursi.opto.domainobject.UserDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private static final String DEFAULT_ROLE = "USER";
    public static final String HIDDEN_PASSWORD = "******";

    public static UserDTO convertToUserDTO(UserDO userDO)
    {
        UserDTO.UserDTOBuilder userDTOBuilder = UserDTO.newBuilder()
                .setId(userDO.getUserId())
                .setName(userDO.getName())
                .setSurname(userDO.getSurname())
                .setEmail(userDO.getEmail())
                .setPassword(HIDDEN_PASSWORD)
                .setRole(userDO.getRole());

        return userDTOBuilder.createUserDTO();
    }

    public static UserDO convertToUserDO(UserDTO userDTO)
    {
        return new UserDO(userDTO.getName(),userDTO.getSurname(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getRole());
    }

    public static UserDO convertToUserDO(UserRegisterDTO userRegisterDTO)
    {
        return new UserDO(userRegisterDTO.getName(), userRegisterDTO.getSurname(), userRegisterDTO.getEmail(), userRegisterDTO.getPassword(), DEFAULT_ROLE);
    }

    public static List<UserDTO> makeUserDTOList(Collection<UserDO> users)
    {
        return users.stream()
                .map(UserMapper::convertToUserDTO)
                .collect(Collectors.toList());
    }
}
