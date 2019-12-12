package com.balt.garage.web.contollers;

import com.balt.garage.error.GarageNotFoundException;
import com.balt.garage.service.models.CarServiceModel;
import com.balt.garage.service.models.GarageServiceModel;
import com.balt.garage.service.services.CarService;
import com.balt.garage.service.services.GarageService;
import com.balt.garage.web.annotation.PageTitle;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    private final GarageService garageService;
    private final CarService carService;

    @Autowired
    public HomeController(GarageService garageService, CarService carService) {
        this.garageService = garageService;
        this.carService = carService;
    }

    @GetMapping("/user-home")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Home")
    public ModelAndView home(HttpSession session, Authentication principal, ModelAndView modelAndView) throws NotFoundException {
        modelAndView.addObject("user", principal.getName());
        modelAndView.setViewName("user-home");

        List<GarageServiceModel> garages = this.garageService.getByUser(principal.getName());
        modelAndView.addObject("garages", garages);


        List<CarServiceModel> cars = this.carService.getByUser(principal.getName());
        modelAndView.addObject("cars", cars);

        return modelAndView;
    }

}
