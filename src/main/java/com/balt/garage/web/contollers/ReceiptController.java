package com.balt.garage.web.contollers;

import com.balt.garage.service.services.ReceiptService;
import com.balt.garage.web.models.ReceiptViewModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReceiptController {

    private final ReceiptService receiptService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReceiptController(ReceiptService receiptService, ModelMapper modelMapper) {
        this.receiptService = receiptService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/generated-receipts")
    public ModelAndView getReceipts(ModelAndView modelAndView, Authentication principal) throws NotFoundException {
        String username = principal.getName();
        modelAndView.addObject("user", username);

        List<ReceiptViewModel> receipts = this.receiptService.getReceipts(username)
                .stream().map(r -> this.modelMapper.map(r, ReceiptViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("receipts", receipts);
        modelAndView.setViewName("/receipts/generated-receipts");


        return modelAndView;
    }

}
