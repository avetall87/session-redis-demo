package ru.spb.avetall.redisdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static ru.spb.avetall.redisdemo.controller.CommonConstant.SESSION_ATTRIBUTE_NAME;

@Slf4j
@Controller
public class SpringSessionController {

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute(SESSION_ATTRIBUTE_NAME);

        if (messages == null) {
            messages = new ArrayList<>();
        }
        model.addAttribute("sessionMessages", messages);
        model.addAttribute("sessionId", session.getId());

        return "index";
    }

    @PostMapping("/persistMessage")
    public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<String> msgs = (List<String>) request.getSession().getAttribute(SESSION_ATTRIBUTE_NAME);

        if (msgs == null) {
            msgs = new ArrayList<>();
            request.getSession().setAttribute(SESSION_ATTRIBUTE_NAME, msgs);
        }
        msgs.add(msg);
        request.getSession().setAttribute(SESSION_ATTRIBUTE_NAME, msgs);
        return "redirect:/";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
