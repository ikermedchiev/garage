package com.balt.garage.service.services.impl;

import com.balt.garage.data.models.*;
import com.balt.garage.data.repositories.*;
import com.balt.garage.error.GarageNotFoundException;
import com.balt.garage.service.models.DateServiceModel;
import com.balt.garage.service.models.GarageServiceModel;
import com.balt.garage.service.services.GarageService;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GarageServiceImpl implements GarageService {

    private final DateRepository dateRepository;
    private final GarageRepository garageRepository;
    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;
    private final ReceiptRepository receiptRepository;
    private final LoggerRepository loggerRepository;


    @Autowired
    public GarageServiceImpl(DateRepository dateRepository, GarageRepository garageRepository, UserProfileRepository userProfileRepository, ModelMapper modelMapper, ReceiptRepository receiptRepository, LoggerRepository loggerRepository) {
        this.dateRepository = dateRepository;
        this.garageRepository = garageRepository;
        this.userProfileRepository = userProfileRepository;
        this.modelMapper = modelMapper;
        this.receiptRepository = receiptRepository;
        this.loggerRepository = loggerRepository;
    }

    @Override
    public void add(GarageServiceModel garage, String username, LocalDateTime time) throws InterruptedException, ApiException, IOException, NotFoundException {
        Garage gar = this.modelMapper.map(garage, Garage.class);
        UserProfile userProfile = this.userProfileRepository.findByUserUsername(username).orElseThrow(() -> new NotFoundException("No user found"));
        String adress = garage.getTown() + ", " + garage.getAddress();

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCWQPIEh_U08lCIxb13itKNobDIy5wrYv0")
                .build();
        GeocodingResult[] results = GeocodingApi.geocode(context,
                adress).await();

        String[] coordinates = ((results[0].geometry).toString().split(" "))[1].split(",");

        gar.setLaititude(coordinates[0]);
        gar.setLongtitude(coordinates[1]);

        gar.setFree(false);
        gar.setUser(userProfile);
        this.garageRepository.save(gar);

        Logger logger = new Logger();
        logger.setTime(time);
        logger.setAction("garage-add");
        logger.setUser(username);
        this.loggerRepository.save(logger);

    }

    @Override
    public List<GarageServiceModel> getByUser(String username) throws NotFoundException {
        UserProfile userProfile = this.userProfileRepository.findByUserUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found"));
        return this.garageRepository
                .findAllByUser(userProfile)
                .stream()
                .map(g -> this.modelMapper.map(g, GarageServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GarageServiceModel> getAll() {
        return this.garageRepository
                .findAll()
                .stream()
                .map(g -> this.modelMapper.map(g, GarageServiceModel.class)).filter(GarageServiceModel::isFree)
                .collect(Collectors.toList());
    }

    @Override
    public GarageServiceModel getById(String id) {

        return this.modelMapper.map(this.garageRepository.getOne(id), GarageServiceModel.class);
    }

    @Override
    public void lendGarage(String id, DateServiceModel dateServiceModel) throws NotFoundException {
        Garage garage = this.garageRepository
                .findById(id).orElseThrow(() -> new GarageNotFoundException("No garage found"));
        Date date = this.modelMapper.map(dateServiceModel, Date.class);

        date.setGarage(garage);
        date.setGiven(false);
        this.dateRepository.save(date);
        garage.setFree(true);
        this.garageRepository.save(garage);

    }

    @Override
    public void borrowGarage(String id, String freeFrom, String username, String carNumber) throws NotFoundException {
        Garage garage = this.garageRepository.getOne(id);
        UserProfile userProfile = this.userProfileRepository.findByUserUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found"));
        Date date = this.dateRepository.getByFreeFromAndGarageId(freeFrom, id);
        Receipt inReceipt = new Receipt();
        inReceipt.setUser(garage.getUser());
        inReceipt.setValue(date.getPrice());
        inReceipt.setDate(date.getFreeFrom() + " - " + date.getFreeUntil());
        inReceipt.setType("income");
        inReceipt.setAddress(garage.getAddress());
        inReceipt.setCarNumber(carNumber);

        Receipt outReceipt = new Receipt();
        outReceipt.setUser(userProfile);
        outReceipt.setAddress(garage.getAddress());
        outReceipt.setValue(date.getPrice());
        outReceipt.setType("outcome");
        outReceipt.setDate(date.getFreeFrom() + " - " + date.getFreeUntil());
        outReceipt.setCarNumber(carNumber);

        this.dateRepository.delete(date);

        if (garage.getFreeDates().size() == 0) {
            garage.setFree(false);
        }
        this.receiptRepository.save(outReceipt);
        this.receiptRepository.save(inReceipt);
    }
}
