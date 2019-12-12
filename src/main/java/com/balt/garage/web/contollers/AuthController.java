package com.balt.garage.web.contollers;

import com.balt.garage.error.LogNotFoundException;
import com.balt.garage.service.models.UserProfileServiceModel;
import com.balt.garage.service.models.UserServiceModel;
import com.balt.garage.service.services.AuthService;
import com.balt.garage.service.services.LoggerService;
import com.balt.garage.web.annotation.PageTitle;
import com.balt.garage.web.models.LoggerViewModel;
import com.balt.garage.web.models.UserViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthController {

    private final ModelMapper modelMapper;
    private final AuthService authService;
    private final LoggerService loggerService;

    @Autowired
    public AuthController(ModelMapper modelMapper, AuthService authService, LoggerService loggerService) {
        this.modelMapper = modelMapper;
        this.authService = authService;
        this.loggerService = loggerService;
    }


    @GetMapping("/register")
    @PageTitle("Register")
    public ModelAndView getRegisterForm() {
        return new ModelAndView("/auth/register");
    }

    @GetMapping("/about")
    @PageTitle("About")
    public ModelAndView getAboutForm() {
        return new ModelAndView("/about");
    }


    @GetMapping("/login")
    @PageTitle("Login")
    public ModelAndView getLoginPage(ModelAndView modelAndView) {
        modelAndView.setViewName("/auth/login");
        return modelAndView;
    }

    @GetMapping("/admin")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ModelAndView getAdminPage(ModelAndView modelAndView) {
        modelAndView.setViewName("/auth/admin");
        List<LoggerViewModel> logs = this.loggerService.getAllLogs()
                .stream()
                .map(l -> this.modelMapper.map(l, LoggerViewModel.class))
                .collect(Collectors.toList());


        modelAndView.addObject("logs", logs);

        return modelAndView;
    }


    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute UserViewModel model) {
        ModelAndView modelAndView = new ModelAndView();
        LocalDateTime time = LocalDateTime.now();

        if (!model.getPassword().equals(model.getConfirmPassword())) {
            modelAndView.setViewName("/auth/register");
            return modelAndView;
        }

        UserServiceModel userServiceModel = this.modelMapper.map(model, UserServiceModel.class);
        UserProfileServiceModel userProfileServiceModel = this.modelMapper.map(model, UserProfileServiceModel.class);

        this.authService.register(userServiceModel, userProfileServiceModel, time);

        modelAndView.setViewName("auth/login");
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ModelAndView reserveTicket(@PathVariable String id, ModelAndView modelAndView) throws Exception {
        this.loggerService.deleteLog(id);
        modelAndView.setViewName("redirect:/admin");
        return modelAndView;
    }

}
