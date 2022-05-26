package com.user.service.services;

import com.user.service.entity.User;
import com.user.service.feingClients.ICarroFeingClient;
import com.user.service.feingClients.IMotoFeignClient;
import com.user.service.models.Car;
import com.user.service.models.Moto;
import com.user.service.repository.IUserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    
    private final String urlCar = "http://localhost:8082/carro/usuario/";
    private final String urlMoto = "http://localhost:8083/moto/usuario/";

    @Autowired
    private RestTemplate RestTemplate;
    
    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private ICarroFeingClient carroFeignClient;
    
    @Autowired
    private IMotoFeignClient motoFeigtnClient;
    
    public List<User> getAll(){
        return this.userRepository.findAll();
    }
    
    public User getUsuarioById(int id){
        return this.userRepository.findById(id).orElse(null);
    }
    
    public User guardarUsuario(User usuario){
        User newUser = this.userRepository.save(usuario);
        return newUser;
    }
    
    //comunicacion servicios RestTemplate
    
    public List<Car> getCarros(int userId){
        //comunicamos el servicio de los carros con el de usuario
        List<Car> cars = this.RestTemplate.getForObject(this.urlCar + userId, List.class);
        return cars;
    }
    
    public List<Moto> getMotos(int userId){
        //comunicamos el servicio de los carros con el de usuario
        List<Moto> motos = this.RestTemplate.getForObject(this.urlMoto + userId, List.class);
        return motos;
    }
    
    //comunicacion servicios Feign client
    public Car saveCarro(int usuarioID, Car carro){
        carro.setUserId(usuarioID);
        Car newCar = this.carroFeignClient.save(carro);

        return newCar;
    }
    
    public Moto saveMoto(int usuarioID, Moto moto){
        moto.setUserId(usuarioID);
        Moto newMoto = this.motoFeigtnClient.save(moto);
        return newMoto;
    }
    
    public Map<String, Object> getUsuarioAndVehiculos(int usuarioID){
        Map<String, Object> result = new HashMap<>();
        User usuario = this.userRepository.findById(usuarioID).orElse(null);
        if(usuario == null){
            result.put("Error", "El usuario no existe");
            return result;
        }
        result.put("Usuario", usuario);
        List<Car> carros = this.carroFeignClient.getCarros(usuarioID);
        if(carros.isEmpty()){
            result.put("Mensaje", "El usuario no tiene carros");
        }else{
            result.put("Carros", carros);
        }
        
        List<Moto> motos = this.motoFeigtnClient.getMotos(usuarioID);
        if(motos.isEmpty()){
            result.put("Mensaje", "El usuario no tiene motos");
        }else{
            result.put("Motos", motos);
        }
        return result;
    }
    
}
