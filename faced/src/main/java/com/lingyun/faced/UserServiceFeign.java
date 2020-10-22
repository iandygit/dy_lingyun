package com.lingyun.faced;

import com.lingyun.beans.LoginUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserServiceFeign {
    @RequestMapping(value="/user/name", method= RequestMethod.GET)
    public LoginUser validateUser(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password);
}
