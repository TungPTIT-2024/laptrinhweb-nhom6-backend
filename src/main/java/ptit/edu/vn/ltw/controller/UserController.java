package ptit.edu.vn.ltw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ptit.edu.vn.ltw.dto.common.GenericResponse;
import ptit.edu.vn.ltw.dto.user.UpdateProfileImageRequest;
import ptit.edu.vn.ltw.dto.user.UserInfoResponse;
import ptit.edu.vn.ltw.dto.user.LoginRequest;
import ptit.edu.vn.ltw.dto.user.LoginResponse;
import ptit.edu.vn.ltw.dto.user.RegisterRequest;
import ptit.edu.vn.ltw.dto.user.RegisterResponse;
import ptit.edu.vn.ltw.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/v1/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/v1/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){
        RegisterResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/user/{id}")
    public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable String id){
        UserInfoResponse response = userService.getUserInfo(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/api/v1/user/profile-image")
    public ResponseEntity<GenericResponse> updateProfileImage(@Valid @RequestBody UpdateProfileImageRequest request){
        GenericResponse response = userService.updateProfileImage(request);
        return ResponseEntity.ok(response);
    }
}
