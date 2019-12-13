package com.balt.garage.web.contollers;

import com.balt.garage.service.models.CarServiceModel;
import com.balt.garage.service.services.CarService;
import com.balt.garage.web.annotation.PageTitle;
import com.balt.garage.web.models.CarViewModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class CarController {

    private final CarService carService;
    private final ModelMapper modelMapper;

    @Autowired
    public CarController(CarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/add-car")
    @PageTitle("Add Car")
    public ModelAndView addCarForm() {
        return new ModelAndView("/cars/add-car");
    }


    @PostMapping("/add-car")
    public ModelAndView addCarFormPost(@ModelAttribute CarViewModel model, Authentication principal, HttpSession session) throws NotFoundException {

        ModelAndView modelAndView = new ModelAndView();
        String username = principal.getName();
        LocalDateTime time = (LocalDateTime) session.getAttribute("time");
        System.out.println();
        this.carService.add(this.modelMapper.map(model, CarServiceModel.class), username, time);

        modelAndView.setViewName("redirect:/user-home");
        return modelAndView;

    }
}
