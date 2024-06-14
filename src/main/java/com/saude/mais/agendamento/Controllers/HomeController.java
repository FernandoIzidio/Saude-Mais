package com.saude.mais.agendamento.Controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ResponseEntity HomeController() {
        return ResponseEntity.ok().body("Hello World2");
    }
}
