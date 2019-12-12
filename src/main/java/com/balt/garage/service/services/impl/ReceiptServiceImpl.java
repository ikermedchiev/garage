package com.balt.garage.service.services.impl;

import com.balt.garage.data.models.UserProfile;
import com.balt.garage.data.repositories.ReceiptRepository;
import com.balt.garage.data.repositories.UserProfileRepository;
import com.balt.garage.service.models.ReceiptServiceModel;
import com.balt.garage.service.services.ReceiptService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public ReceiptServiceImpl(ReceiptRepository receiptRepository, UserProfileRepository userProfileRepository, ModelMapper modelMapper) {
        this.receiptRepository = receiptRepository;
        this.userProfileRepository = userProfileRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<ReceiptServiceModel> getReceipts(String username) throws NotFoundException {
        UserProfile userProfile = this.userProfileRepository.findByUserUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found"));
        return this.receiptRepository
                .findAllByUser(userProfile)
                .stream()
                .map(g -> this.modelMapper.map(g, ReceiptServiceModel.class))
                .collect(Collectors.toList());
    }
}
