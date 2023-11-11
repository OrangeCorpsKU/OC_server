package OrangeCorps.LBridge.Controller;

import OrangeCorps.LBridge.DTO.UserDTO;
import OrangeCorps.LBridge.Entity.User;
import OrangeCorps.LBridge.Repository.UserRepository;
import OrangeCorps.LBridge.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import static OrangeCorps.LBridge.Config.*;
@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    public ResponseEntity<String> userRegist(@RequestBody UserDTO userDTO){
        try {
            User user = convertToUserEntity(userDTO);
            userRepository.save(user);
            log.info(USER_SAVE_SUCCESS, user.getUser_name());
            return ResponseEntity.ok(String.format(USER_SAVE_SUCCESS, user.getUser_name()));
        } catch (Exception e) {
            log.error(USER_SAVE_ERROR, e);
            return new ResponseEntity<>(USER_SAVE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/couple")
    public ResponseEntity<String> linkUserToCouple(@RequestParam String userId , @RequestParam String coupleId) {
        return userService.linkCouple(userId,coupleId);
    }

    private User convertToUserEntity(UserDTO userDTO) {
            return new User(userDTO);
    }

}