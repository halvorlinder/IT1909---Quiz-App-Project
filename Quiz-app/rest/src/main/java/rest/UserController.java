package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.UserData;
import core.UserRecord;
import io.UserPersistence;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api")

public class UserController {

    private final UserPersistence userPersistence;
    private final ObjectMapper objectMapper;

    /**
     *
     * @throws IOException
     */
    public UserController() throws IOException {
        objectMapper = UserPersistence.createObjectMapper();
        userPersistence = new UserPersistence();
    }

    /**
     * attempts to login in a user
     * @param response
     * @param userDataJson the login information
     */
    @PostMapping("/users/login")
    public void loginUser(HttpServletResponse response, @RequestBody String userDataJson) {
        try {
            UserData userData = userPersistence.loadUserData();
            System.out.println(userData);
            if (userData.attemptLogIn(objectMapper.readValue(userDataJson, UserRecord.class)))
                response.setStatus(200);
            else
                response.setStatus(403);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

    /**
     * attempts to register a user
     * @param response
     * @param userDataJson the registration information
     */
    @PostMapping("/users/register")
    public void registerUser(HttpServletResponse response, @RequestBody String userDataJson) {
        try {
            UserData userData = userPersistence.loadUserData();
            System.out.println(userData);
            UserRecord userRecord = objectMapper.readValue(userDataJson, UserRecord.class);
            if (userData.attemptRegister(userRecord)) {
                userPersistence.saveUserData(userData);
                response.setStatus(200);
            } else
                response.setStatus(403);
        } catch (IOException e) {
            response.setStatus(500);
            e.printStackTrace();
        }
    }

}
