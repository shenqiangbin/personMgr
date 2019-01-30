package com.sqber.personMgr.ui.controller;

import com.sqber.personMgr.base.BaseResponse;
import com.sqber.personMgr.base.SessionHelper;
import com.sqber.personMgr.base.StringUtil;
import com.sqber.personMgr.bll.ICodeService;
import com.sqber.personMgr.entity.ImageCaptchaDTO;
import com.sqber.personMgr.ui.security.MyCookieClearingLogoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ICodeService codeService;

    @GetMapping("/")
    public String index() {
        return "redirect:menu/first";
    }

    @ResponseBody
    @GetMapping("nothing")
    public String nothing(){return "没有权限";}

    @GetMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {

        boolean isExist = SessionHelper.IsUserInfoExsit();
        System.out.println(isExist);
        if(isExist){
            return   "redirect:menu/first";
        }

        String[] returnurl = request.getParameterValues("return");
        if (returnurl != null && returnurl.length > 0) {
            model.addAttribute("returnurl", returnurl[0]);
        } else {
            model.addAttribute("returnurl", "\\");
        }

        return "home/login";
    }

    @GetMapping("/logout")
    public String Logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            new MyCookieClearingLogoutHandler("JSESSIONID","javasampleapproach-remember-me").logout(request, response, auth);
        }
       return "redirect:/login?logout";
    }

    @ResponseBody
    @GetMapping("home/captcha")
    public BaseResponse<ImageCaptchaDTO> getCaptcha() {
        BaseResponse<ImageCaptchaDTO> response = new BaseResponse<ImageCaptchaDTO>();

        ImageCaptchaDTO imageCap = null;
        try {
            imageCap = codeService.getImageCaptcha();
        } catch (IOException e) {
            response.setCode(500);
            response.setMsg("内部错误");
            log.error(e.getStackTrace() + e.getMessage());
        }

        response.setCode(200);
        response.setData(imageCap);

        return response;
    }
}
