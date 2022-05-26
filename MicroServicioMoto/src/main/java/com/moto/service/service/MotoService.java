package com.moto.service.service;

import com.moto.service.entity.Moto;
import com.moto.service.repository.IMotoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotoService {
    @Autowired
    private IMotoRepository mtoRepository;
    
    public List<Moto> getAll(){
        return this.mtoRepository.findAll();
    }
    
    public Moto getCarById(int id){
        return this.mtoRepository.findById(id).orElse(null);
    }
    
    public Moto guardarMoto(Moto moto){
        Moto newCar= this.mtoRepository.save(moto);
        return newCar;
    }
    
    public List<Moto> getByUserId(int userId){
        return this.mtoRepository.findByUserId(userId);
    }
}
