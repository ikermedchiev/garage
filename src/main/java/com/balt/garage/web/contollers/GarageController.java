package com.balt.garage.web.contollers;

import com.balt.garage.service.models.DateServiceModel;
import com.balt.garage.service.models.GarageServiceModel;
import com.balt.garage.service.services.AuthService;
import com.balt.garage.service.services.GarageService;
import com.balt.garage.web.annotation.PageTitle;
import com.balt.garage.web.models.CarViewModel;
import com.balt.garage.web.models.DateViewModel;
import com.balt.garage.web.models.GarageViewModel;
import com.google.maps.errors.ApiException;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GarageController {

    private final GarageService garageService;
    private final ModelMapper modelMapper;
    private final AuthService authService;

    @Autowired
    public GarageController(GarageService garageService, ModelMapper modelMapper, AuthService authService) {
        this.garageService = garageService;
        this.modelMapper = modelMapper;

        this.authService = authService;
    }

    @GetMapping("/add-garage")
    @PageTitle("Add")
    public ModelAndView addGarageForm() {
        return new ModelAndView("/garages/add-garage");
    }

    @GetMapping("/find-garage")
    @PageTitle("Find")
    public ModelAndView findGarageForm(ModelAndView modelAndView) throws IOException, InterruptedException, ApiException {
        List<String> laititude = this.garageService
                .getAll().stream().map(GarageServiceModel::getLaititude)
                .collect(Collectors.toList());

        List<String> longtitude = this.garageService
                .getAll().stream().map(GarageServiceModel::getLongtitude)
                .collect(Collectors.toList());

        List<GarageViewModel> garages = this.garageService
                .getAll()
                .stream()
                .map(g -> this.modelMapper.map(g, GarageViewModel.class))
                .collect(Collectors.toList());


        modelAndView.addObject("garagesFind", garages);
        modelAndView.addObject("laititude", laititude);
        modelAndView.addObject("longtitude", longtitude);
        modelAndView.setViewName("/garages/find-garage");

        return modelAndView;
    }

    @GetMapping("/lend-garage/{id}")
    @PageTitle("Lend")
    public ModelAndView lendGarage(@PathVariable String id, ModelAndView modelAndView) {
        GarageViewModel garage = this.modelMapper.map(this.garageService.getById(id), GarageViewModel.class);


        modelAndView.addObject("garageId", id);
        modelAndView.setViewName("/garages/lend-garage");
        return modelAndView;
    }

    @PostMapping("/lend-garage/{id}")
    public ModelAndView lendGaragePost(@PathVariable String id, @ModelAttribute DateViewModel date, GarageViewModel garage) throws NotFoundException {
        this.garageService.lendGarage(id, this.modelMapper.map(date, DateServiceModel.class));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user-home");
        return modelAndView;
    }

    @GetMapping("/borrow-garage/{id}")
    @PageTitle("Borrow")
    public ModelAndView borrowGarage(@PathVariable String id, ModelAndView modelAndView, Authentication principal) throws NotFoundException {
        GarageViewModel garage = this.modelMapper.map(this.garageService.getById(id), GarageViewModel.class);


        String username = principal.getName();

        List<CarViewModel> cars = this.authService
                .getUser(username)
                .getCars()
                .stream()
                .map(c -> this.modelMapper.map(c, CarViewModel.class)).collect(Collectors.toList());


        modelAndView.addObject("cars", cars);
        modelAndView.addObject("dates", garage.getFreeDates());
        modelAndView.addObject("garage", garage);
        modelAndView.setViewName("/garages/borrow-garage");
        return modelAndView;
    }

    @PostMapping("/borrow-garage/{id}")
    public ModelAndView borrowGaragePost(@PathVariable String id, @ModelAttribute DateViewModel date, @ModelAttribute CarViewModel car, ModelAndView modelAndView, Authentication principal) throws NotFoundException {
        String username = principal.getName();
        this.garageService.borrowGarage(id, date.getFreeFrom(), username, car.getCarNumber());
        modelAndView.setViewName("redirect:/user-home");
        return modelAndView;
    }


    @PostMapping("/add-garage")
    public ModelAndView addGaragePost(@ModelAttribute GarageViewModel model, Authentication principal, HttpSession session) throws InterruptedException, ApiException, IOException, NotFoundException {

        LocalDateTime time = (LocalDateTime) session.getAttribute("time");
        ModelAndView modelAndView = new ModelAndView();
        String username = principal.getName();
        this.garageService.add(this.modelMapper.map(model, GarageServiceModel.class), username, time);

        modelAndView.setViewName("redirect:/user-home");
        return modelAndView;
    }
}
