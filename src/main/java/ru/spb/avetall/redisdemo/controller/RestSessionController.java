package ru.spb.avetall.redisdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static ru.spb.avetall.redisdemo.controller.CommonConstant.SESSION_ATTRIBUTE_NAME;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RestSessionController {

    private final SessionRepository sessionRepository;

    @GetMapping("/session")
    public String getSessionId(HttpSession session) {
        return session.getId();
    }

    @GetMapping("/session/values")
    public List<String> getSessionValues(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> values = (List<String>) session.getAttribute(SESSION_ATTRIBUTE_NAME);

        return values;
    }

    @PostMapping("/session/save")
    public void persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3600);

        @SuppressWarnings("unchecked")
        List<String> msgs = (List<String>) session.getAttribute(SESSION_ATTRIBUTE_NAME);

        if (msgs == null) {
            msgs = new ArrayList<>();
            session.setAttribute(SESSION_ATTRIBUTE_NAME, msgs);
        }
        msgs.add(msg);
        session.setAttribute(SESSION_ATTRIBUTE_NAME, msgs);
    }


}
