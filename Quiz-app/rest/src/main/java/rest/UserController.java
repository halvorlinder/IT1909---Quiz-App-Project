package rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.UserData;
import core.UserRecord;
import io.UserPersistence;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api")

public class UserController {

    private final UserPersistence userPersistence;
    private final ObjectMapper objectMapper;

    public UserController() throws IOException {
        objectMapper = UserPersistence.createObjectMapper();
        userPersistence = new UserPersistence();
    }


    @PostMapping("/users/login")
    public void loginUser(HttpServletResponse response, @RequestBody String userDataJson){
        try {
            UserData userData = userPersistence.loadUserData();
            userData.attemptLogIn(objectMapper.readValue(userDataJson, UserRecord.class));
            response.setStatus(200);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(403);
        }
    }

    @PostMapping("/users/register")
    public void registerUser(HttpServletResponse response, @RequestBody String userDataJson){
        try {
            UserData userData = userPersistence.loadUserData();
            UserRecord userRecord = objectMapper.readValue(userDataJson, UserRecord.class);
            userData.attemptRegister(userRecord);
            objectMapper.writeValueAsString(userData);
            userPersistence.saveUserData(userData);
            response.setStatus(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(403);
    }

}
