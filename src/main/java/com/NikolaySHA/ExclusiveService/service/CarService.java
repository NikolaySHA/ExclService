package com.NikolaySHA.ExclusiveService.service;

import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.dto.carDTO.CarDataDTO;

import java.util.List;
import java.util.Optional;

public interface CarService {
    
    boolean addCar(CarDataDTO data);
    List<Car> findCarsByUser(Long id);
    
    Optional<Car> findById(Long id);
    
    void delete(Car car);
    
    List<Car> findAllCars();
    
    List<Car> searchCars(String licensePlate, String make, String customer);
    
    void updateCar(Long id, CarDataDTO car);
}
