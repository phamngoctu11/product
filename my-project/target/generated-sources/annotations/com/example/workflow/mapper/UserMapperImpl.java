package com.example.workflow.mapper;

import com.example.workflow.dto.UserCreDTO;
import com.example.workflow.dto.UserResDTO;
import com.example.workflow.entity.User;
import com.example.workflow.nume.Role;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-28T14:22:58+0700",
    comments = "version: 1.6.0, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public User toEntity(UserCreDTO request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setRoles( stringHashSetToRoleSet( request.getRoles() ) );
        user.setId( request.getId() );
        user.setUsername( request.getUsername() );
        user.setPassword( request.getPassword() );

        return user;
    }

    @Override
    public UserResDTO toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResDTO userResDTO = new UserResDTO();

        userResDTO.setCart( cartMapper.toDTO( user.getCart() ) );
        userResDTO.setId( user.getId() );
        userResDTO.setUsername( user.getUsername() );
        userResDTO.setRoles( roleSetToStringList( user.getRoles() ) );

        return userResDTO;
    }

    @Override
    public void updateUser(User user, UserCreDTO request) {
        if ( request == null ) {
            return;
        }

        if ( user.getRoles() != null ) {
            Set<Role> set = stringHashSetToRoleSet( request.getRoles() );
            if ( set != null ) {
                user.getRoles().clear();
                user.getRoles().addAll( set );
            }
            else {
                user.setRoles( null );
            }
        }
        else {
            Set<Role> set = stringHashSetToRoleSet( request.getRoles() );
            if ( set != null ) {
                user.setRoles( set );
            }
        }
        user.setUsername( request.getUsername() );
    }

    protected Set<Role> stringHashSetToRoleSet(HashSet<String> hashSet) {
        if ( hashSet == null ) {
            return null;
        }

        Set<Role> set = new LinkedHashSet<Role>( Math.max( (int) ( hashSet.size() / .75f ) + 1, 16 ) );
        for ( String string : hashSet ) {
            set.add( Enum.valueOf( Role.class, string ) );
        }

        return set;
    }

    protected List<String> roleSetToStringList(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        List<String> list = new ArrayList<String>( set.size() );
        for ( Role role : set ) {
            list.add( role.name() );
        }

        return list;
    }
}
