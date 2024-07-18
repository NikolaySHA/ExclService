package com.ExclusiveService.service.impl;

import com.ExclusiveService.model.dto.AddCarDataDTO;
import com.ExclusiveService.model.entity.Car;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.repo.CarRepository;
import com.ExclusiveService.service.CarService;
import com.ExclusiveService.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class CarServiceImpl implements CarService {
    
    private final UserService userService;
    private final CarRepository carRepository;
    
    
    @Override
    public boolean addCar(AddCarDataDTO data) {
        Optional<Car> optionalCar = carRepository.findByLicensePlate(data.getLicensePlate());
        if (optionalCar.isPresent()){
            return false;
        }
        Car car = new Car();
        car.setMake(data.getMake());
        car.setModel(data.getModel());
        car.setLicensePlate(data.getLicensePlate());
        car.setColor(data.getColor());
        String vin = data.getVin();
        if (vin.equals("")) {
            vin = null;
        }
        car.setVin(vin);
        car.setOwner(userService.findLoggedUser());
        carRepository.save(car);
        return true;
    }
    
    @Override
    public List<Car> findCarsByUser() {
        User loggedUser = userService.findLoggedUser();
        return this.carRepository.findByOwner_Email(loggedUser.getEmail());
    }
    @Override
    public Optional<Car> findById(Long id) {
        return this.carRepository.findById(id);
    }
    
    
    @Override
    public void delete(Car car) {
        carRepository.delete(car);
    }
    
    @Override
    public List<Car> findAllCars() {
        return carRepository.findAll();
    }
    
    @Override
    public List<Car> searchCars(String licensePlate, String make, String customer) {
        return carRepository.searchCarsByFilter(licensePlate, make, customer);
    }
}
