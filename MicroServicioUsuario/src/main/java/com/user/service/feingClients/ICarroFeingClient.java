package com.user.service.feingClients;

import com.user.service.models.Car;
import com.user.service.models.Moto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "carro-service")
public interface ICarroFeingClient {
    
    @PostMapping("/carro")
    public Car save(@RequestBody Car carro);
    
     @GetMapping("/carro/usuario/{usuarioID}")
    public List<Car> getCarros(@PathVariable("usuarioID") int usuarioID);
}
