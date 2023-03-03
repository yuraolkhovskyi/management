package api.test;

import api.config.ApiPropertyModel;
import api.config.DBConfig;
import api.config.TestProperty;
import com.sombra.management.exception.SystemException;
import com.sombra.management.exception.code.ServiceErrorCode;
import com.sombra.management.security.dto.AuthenticationRequest;
import com.sombra.management.security.dto.RegisterRequest;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.sql.*;
import java.util.Objects;

import static api.util.RestUtil.makePostCall;
import static org.hamcrest.Matchers.notNullValue;

@Tag("api")
public class UserControllerAPITest {

    private static final ApiPropertyModel API_PROPERTY_MODEL = Objects.requireNonNull(TestProperty.PROPERTIES).getRest().getApi();

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = TestProperty.PROPERTIES.getRest().getBaseUrl();
    }

    @Test
    void registerNewUserAPITest() {
        //create new user
        final RegisterRequest createUserRequestDTO = getCreateUserRequestDTO();
        final ValidatableResponse validatableResponse = makePostCall(createUserRequestDTO, HttpStatus.OK.value(), API_PROPERTY_MODEL.getRegisterUri());

        //make sure all it's valid
        validatableResponse
                .body("token", notNullValue());

        //delete created user to keep db cleaned
        deleteUser(createUserRequestDTO.getEmail());
    }

    @Test
    void registerNewUserAPITest_userAlreadyExists() {
        //register new user
        makePostCall(getCreateUserRequestDTO(), HttpStatus.OK.value(), API_PROPERTY_MODEL.getRegisterUri());
        //try to register a new user with same email as previous
        makePostCall(getCreateUserRequestDTO(), HttpStatus.BAD_REQUEST.value(), API_PROPERTY_MODEL.getRegisterUri());
        //delete created user
        deleteUser(getCreateUserRequestDTO().getEmail());
    }

    @Test
    void authenticateNewUserAPITest() {
        //register new user
        final RegisterRequest createUserRequestDTO = getCreateUserRequestDTO();
        makePostCall(createUserRequestDTO, HttpStatus.OK.value(), API_PROPERTY_MODEL.getRegisterUri());

        //authenticate newly created user
        final AuthenticationRequest requestDTO = new AuthenticationRequest(createUserRequestDTO.getEmail(), createUserRequestDTO.getPassword());
        makePostCall(requestDTO, HttpStatus.OK.value(), API_PROPERTY_MODEL.getAuthenticateApiUri());

        //delete created user
        deleteUser(requestDTO.getEmail());
    }

    private static void deleteUser(final String email) {
        try (Connection connection = DBConfig.getDBConnection()) {
            final String query = "DELETE FROM management.users WHERE email =  ?";
            final PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.execute();
        } catch (Exception e) {
            throw new SystemException("Error", ServiceErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private static RegisterRequest getCreateUserRequestDTO() {
        return new RegisterRequest("test", "test", "test@gmail.com", "somePass");
    }

}
