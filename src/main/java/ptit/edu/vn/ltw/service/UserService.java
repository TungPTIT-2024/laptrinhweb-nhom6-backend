package ptit.edu.vn.ltw.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ptit.edu.vn.ltw.dto.common.GenericResponse;
import ptit.edu.vn.ltw.dto.user.LoginRequest;
import ptit.edu.vn.ltw.dto.user.LoginResponse;
import ptit.edu.vn.ltw.dto.user.RegisterRequest;
import ptit.edu.vn.ltw.dto.user.RegisterResponse;
import ptit.edu.vn.ltw.dto.user.UpdateProfileImageRequest;
import ptit.edu.vn.ltw.dto.user.UserInfoResponse;
import ptit.edu.vn.ltw.entity.UserInfo;
import ptit.edu.vn.ltw.exception.ErrorDetail;
import ptit.edu.vn.ltw.exception.HttpStatusException;
import ptit.edu.vn.ltw.repository.UserInfoRepository;
import ptit.edu.vn.ltw.security.encryption.UserEncryptionService;
import ptit.edu.vn.ltw.utility.ContextUtility;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserInfoRepository userInfoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserEncryptionService jwtService;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";


    public LoginResponse login(LoginRequest request) {
        UserInfo userInfo = userInfoRepository.findByUsername(request.getUsername()).orElseThrow(() -> {
            ErrorDetail userDetail = new ErrorDetail().setField(USERNAME).setIssue("Username not found");
            ErrorDetail passwordDetail = new ErrorDetail().setField(PASSWORD).setIssue("Invalid password");
            return HttpStatusException.badRequest("Invalid username or password", List.of(userDetail, passwordDetail));
        });

        String encodedPassword = userInfo.getPassword();
        if (!bCryptPasswordEncoder.matches(request.getPassword(), encodedPassword)) {
            ErrorDetail userDetail = new ErrorDetail().setField(USERNAME).setIssue("Username not found");
            ErrorDetail passwordDetail = new ErrorDetail().setField(PASSWORD).setIssue("Invalid password");
            throw HttpStatusException.badRequest("Invalid username or password", List.of(userDetail, passwordDetail));
        }

        String jwtToken = jwtService.encryptToken(userInfo);
        LoginResponse response = new LoginResponse().setJwtToken(jwtToken).setUserId(userInfo.getId());
        BeanUtils.copyProperties(userInfo, response);

        return response;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());

        Optional<UserInfo> checkExistingUser = userInfoRepository.findByUsername(request.getUsername());
        if (checkExistingUser.isPresent()) {
            ErrorDetail errorDetail = new ErrorDetail().setField(USERNAME).setIssue("Username already exists");
            throw HttpStatusException.badRequest("Username already exists", List.of(errorDetail));
        }

        UserInfo userInfo = new UserInfo()
                .setFullName(request.getFullName())
                .setUsername(request.getUsername())
                .setPassword(encodedPassword)
                .setProfileImage(request.getProfileImage());

        userInfo = userInfoRepository.save(userInfo);

        String jwtToken = jwtService.encryptToken(userInfo);

        return new RegisterResponse().setJwtToken(jwtToken).setUsername(userInfo.getUsername()).setUserId(userInfo.getId());
    }

    public UserInfoResponse getUserInfo(String userId) {
        UserInfo userInfo = userInfoRepository.findById(userId).orElseThrow(() -> {
            ErrorDetail errorDetail = new ErrorDetail().setField("id").setIssue(String.format("User %s not found", userId));
            return HttpStatusException.badRequest("User not found", List.of(errorDetail));
        });
        UserInfoResponse response = new UserInfoResponse();
        BeanUtils.copyProperties(userInfo, response);
        return response;
    }

    public GenericResponse updateProfileImage(@Valid UpdateProfileImageRequest request) {
        String userId = ContextUtility.getUserId();

        UserInfo userInfo = userInfoRepository.findById(userId)
                .orElseThrow(() -> HttpStatusException.internalServerError(String.format("User %s not found", userId)));

        userInfo.setProfileImage(request.getProfileImage());
        userInfoRepository.save(userInfo);

        return new GenericResponse().setHttpStatus(200).setMessage("Profile image updated successfully");

    }
}
