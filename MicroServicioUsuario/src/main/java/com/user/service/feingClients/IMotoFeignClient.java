package com.user.service.feingClients;

import com.user.service.models.Moto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "moto-service")
public interface IMotoFeignClient {
    
    @PostMapping("/moto")
    public Moto save(@RequestBody Moto moto);
    
    @GetMapping("/moto/usuario/{usuarioID}")
    public List<Moto> getMotos(@PathVariable("usuarioID") int usuarioID);
}
