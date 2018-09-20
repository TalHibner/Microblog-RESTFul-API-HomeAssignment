package microblog.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

public class ExceptionController {

    @RequestMapping("/404")
    public String notFoundErrorHandler(HttpServletRequest request, Exception e) {
        return "404";
    }
    @RequestMapping("/error")
    public String defaultErrorHandler(HttpServletRequest request, Exception e) {
        return "error";
    }
}