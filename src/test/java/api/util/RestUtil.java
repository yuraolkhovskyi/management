package api.util;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public final class RestUtil {

    private RestUtil() {
        throw new UnsupportedOperationException();
    }

    public static <T> ValidatableResponse makePostCall(final T requestDTO,
                                                       final int expectedStatusCode,
                                                       final String uri) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
                .when()
                .post(uri)
                .then()
                .statusCode(expectedStatusCode);
    }

}
