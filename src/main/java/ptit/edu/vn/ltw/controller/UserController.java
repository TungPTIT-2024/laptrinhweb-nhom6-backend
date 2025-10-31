package ptit.edu.vn.ltw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    @PostMapping("/api/v1/login")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/register")
    public ResponseEntity<?> register(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id){
        return ResponseEntity.ok().build();
    }
}
