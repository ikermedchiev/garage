package com.balt.garage.service.services;

import com.balt.garage.service.models.ReceiptServiceModel;
import javassist.NotFoundException;

import java.util.List;

public interface ReceiptService {
    List<ReceiptServiceModel> getReceipts(String username) throws NotFoundException;
}
