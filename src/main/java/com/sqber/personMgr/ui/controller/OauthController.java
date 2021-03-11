package com.sqber.personMgr.ui.controller;

import com.alibaba.fastjson.JSON;
import com.sqber.personMgr.entity.LoginUserInfo;
import com.xkcoding.justauth.AuthRequestFactory;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 第三方登录 Controller
 * </p>
 */
@Controller
@RequestMapping("/oauth")
public class OauthController {

    @Autowired
    private AuthRequestFactory factory;

    /**
     * 登录类型
     */
    @GetMapping
    @ResponseBody
    public Map<String, String> loginType() {
        List<String> oauthList = factory.oauthList();
        return oauthList.stream().collect(Collectors.toMap(oauth -> oauth.toLowerCase() + "登录", oauth -> "http://xxxx/demo/oauth/login/" + oauth.toLowerCase()));
    }

    /**
     * 登录
     *
     * @param oauthType 第三方登录类型
     * @param response  response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/login/{oauthType}")
    public void renderAuth(@PathVariable String oauthType, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(oauthType);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    /**
     * 登录成功后的回调
     *
     * @param oauthType 第三方登录类型
     * @param callback  携带返回的信息
     * @return 登录成功后的信息
     */
    @RequestMapping("/{oauthType}/callback")
    public String login(@PathVariable String oauthType, AuthCallback callback, HttpSession session) {
        AuthRequest authRequest = factory.get(oauthType);
        AuthResponse response = authRequest.login(callback);
        //log.info("【response】= {}", JSONUtil.toJsonStr(response));


        String str = JSON.toJSONString(response);
        System.out.println(str);

        if (response.ok()) {
            AuthUser authUser = (AuthUser) response.getData();
            toLogin(authUser);
            return "redirect:/menu/first";
        } else {
            return "redirect:/oauth/fail";
        }
    }

    @ResponseBody
    @GetMapping("/fail")
    public String fail() {
        return "登录失败";
    }

    private void toLogin(AuthUser authUser) {

        String uuid = authUser.getUuid();
        String usercode = authUser.getUsername();
        String displayName = authUser.getNickname();
        String avatar = authUser.getAvatar();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));

        LoginUserInfo user = new LoginUserInfo(usercode, "", authorities);
        user.setUserCode(usercode);
        user.setDisplayName(displayName);
        user.setRoleId(1);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, "", authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}