package com.car.service.services;

import com.car.service.entity.Car;
import com.car.service.repository.ICarRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @Autowired
    private ICarRepository carRepository;
   
    
    public List<Car> getAll(){
        return this.carRepository.findAll();
    }
    
    public Car getCarById(int id){
        return this.carRepository.findById(id).orElse(null);
    }
    
    public Car guardarCarro(Car car){
        Car newCar= this.carRepository.save(car);
        return newCar;
    }
    
    public List<Car> getByUserId(int userId){
        return this.carRepository.findByUserId(userId);
    }
}
