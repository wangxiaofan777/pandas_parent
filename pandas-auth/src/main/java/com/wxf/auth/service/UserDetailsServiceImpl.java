package com.wxf.auth.service;

import com.wxf.auth.dto.UserDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wxf
 * @since 2024-03-19 16:49:46
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private List<UserDTO> userList;

    @PostConstruct
    public void initData() {
        userList.add(UserDTO.builder().id(1).username("admin").password("admin").roleList(Collections.singletonList("ADMIN")).build());
        userList.add(UserDTO.builder().id(2).username("wxf").password("wxf").roleList(Collections.singletonList("TEST")).build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDTO> dtoList = userList.stream().filter(x -> x.getUsername().equals(username)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(dtoList)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return null;
    }
}
