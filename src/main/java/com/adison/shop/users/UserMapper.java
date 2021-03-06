package com.adison.shop.users;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.PagedResultDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

//spring container will have a bean called UserMapper that we'll be able to inject into controllers
@Mapper(componentModel = "spring")
public interface UserMapper {

    //because we changed email to emailAddress on a field name, we need to provide tips for MapStruct
    @Mapping(source = "emailAddress", target = "email")
    User toUser(UserDTO userDTO);

    @Mapping(source = "email", target = "emailAddress")
    UserDTO toUserDTO(User user);

    //is this necessary because it'll be used in the toUserTransferObjectsPage method anyway? (this here is where the
    //iteration is)
    @IterableMapping(elementTargetType = UserDTO.class)
    List<UserDTO> toUserDTOs(List<User> users);

    PagedResultDTO<UserDTO> toUserDTOsPage(PagedResult<User> usersPage);
}
