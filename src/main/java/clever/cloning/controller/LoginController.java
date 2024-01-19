package clever.cloning.controller;

import clever.cloning.model.User;
import clever.cloning.repository.UserRepository;
import clever.cloning.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    @GetMapping("/")
    public String home(HttpServletRequest request,HttpServletResponse response, Model model, HttpSession session) {
        // Check if the user is already logged in (session)
        User loggedInUser = (User) session.getAttribute("user");

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("home cookie: {}", cookie);
                if ("userToken".equals(cookie.getName())) {
                    // Retrieve user by token
                    User user = userRepository.findByToken(cookie.getValue());
                    log.info("home user: {}", user);
                    if (user != null) {
                        // Check if the token needs refresh
                        if (tokenService.isTokenNeedRefresh(user.getToken())) {
                            // Generate and set a new token for the user
                            String newToken = tokenService.generateAndRefreshToken(user);
                            log.info("home newtoken: {}", newToken);
                            // Update the user in the repository
                            userRepository.save(user);

                            // Update the user's token in the cookie
                            cookie.setValue(newToken);
                            response.addCookie(cookie);
                        }

                        // Set the user in the session
                        session.setAttribute("user", user);
                        model.addAttribute("user", user);
                        log.info("home model: {}", user);
                        return "home";
                    }
                }
            }
        }

        model.addAttribute("user", new User());
        return "home";
    }


    @GetMapping("/signup")
    public String viewHome(Model model, HttpSession session) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user) {
        // Check if a user with the given username already exists
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser == null) {
            // Save user information to the database
            userRepository.save(user);

            // Always return to the home page
            return "redirect:/";
        }

        // User already exists, handle as needed (you can add error messages or redirect)
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();

        // 쿠키 삭제
        Cookie cookie = new Cookie("userToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String viewLogin(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, HttpServletResponse response) {
        // Check if a user with the given username and password exists
        User existingUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());

        if (existingUser != null) {
            // Generate and save token for the user
            String token = tokenService.generateAndRefreshToken(existingUser);
            existingUser.setToken(token);
            userRepository.save(existingUser);

            // Set the user in the session
            session.setAttribute("user", existingUser);

            Cookie cookie = new Cookie("userToken", token);
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days expiration (you can adjust this)
            cookie.setPath("/"); // Set the cookie path
            response.addCookie(cookie);

            // Redirect to the home page
            return "redirect:/";
        }

        // Handle invalid login (you can add error messages or redirect to a login page)
        return "redirect:/login";
    }

}
