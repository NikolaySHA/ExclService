package com.ExclusiveService.service;

import com.ExclusiveService.model.entity.Car;
import com.ExclusiveService.model.dto.AddCarDataDTO;

import java.util.List;
import java.util.Optional;

public interface CarService {
    
    boolean addCar(AddCarDataDTO data);
    List<Car> findCarsByUser();
    
    Optional<Car> findById(Long id);
    
    void delete(Car car);
    
    List<Car> findAllCars();
    
    List<Car> searchCars(String licensePlate, String make, String customer);
}
