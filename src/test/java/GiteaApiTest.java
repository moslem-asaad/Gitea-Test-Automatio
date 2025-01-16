import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.EnvLoader;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiteaApiTest {

    private final String owner = "moslem";
    private static String apiToken;

    //echo "GITEA_TOKEN=...." > .env
    //echo "GITEA_TOKEN=${{ secrets.GITEA_TOKEN }}" > .env

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:3000/api/v1";
        RestAssured.port = 3000;
        EnvLoader.loadEnv(".env");
        apiToken =  EnvLoader.getEnv("GITEA_API_TOKEN");
        if (apiToken == null || apiToken.isEmpty())
            apiToken = System.getenv("GITEA_API_TOKEN");

        if (apiToken == null || apiToken.isEmpty()) {
            System.err.println("GITEA_API_TOKEN is not set in the environment variables.");
            return;
        }

    }



    @Test
    @Order(1)
    public void testCreateRepository() {
        given().
                header("Authorization", "token " + apiToken).
                contentType(ContentType.JSON).
                body("{ \"name\": \"newRepo\", \"private\": false }").
                when().
                post("/user/repos").
                then().
                statusCode(201).
                body("name", equalTo("newRepo"),
                        "private", equalTo(false));
    }

    @Test
    @Order(2)
    public void testGetRepositoryDetails() {
        String repo = "testRepo";
        given().
                header("Authorization", "token " + apiToken).
                contentType(ContentType.JSON).
                pathParam("owner", owner).
                pathParam("repo", repo).
                when().
                get("/repos/{owner}/{repo}").
                then().
                statusCode(200).
                body("name", equalTo(repo),
                        "owner.login", equalTo(owner));
    }

    @Test
    @Order(3)
    public void testCreateRepositoryExistingName() {
        given().
                header("Authorization", "token " + apiToken).
                contentType(ContentType.JSON).
                body("{ \"name\": \"newRepo\", \"private\": false }").
                when().
                post("/user/repos").
                then().
                statusCode(409);
    }


    @Test
    @Order(4)
    public void testDeleteRepository() {
        String repo = "newRepo";

        given().
                header("Authorization", "token " + apiToken).
                pathParam("owner", owner).
                pathParam("repo", repo).
                when().
                delete("/repos/{owner}/{repo}").
                then().
                statusCode(204);
    }
}
