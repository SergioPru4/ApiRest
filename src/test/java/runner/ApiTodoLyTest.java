package runner;

import config.Configuration;
import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class ApiTodoLyTest {
    Response response;
    RequestInfo requestInfo = new RequestInfo();
    Map<String, String> variables = new HashMap<>();

    @Given("send {} request {string} with data")
    public void createdAUserInTodoLyWith(String method,String url) {
        // Create JSON
        JSONObject body = new JSONObject();
        body.put("Email", Configuration.email);
        body.put("FullName", Configuration.username);
        body.put("Password", Configuration.password);
        // Send POST
        sendPOSTRequestWithBody(method,url, body.toString());
    }

    @When("get token")
    public void sendGETRequestToGetToken(){
        // Request
        String credentials = Base64.getEncoder()
                .encodeToString((Configuration.email+":"+Configuration.password).getBytes());

        requestInfo.setUrl(Configuration.host+"/api/authentication/token.json")
                .setHeader("Authorization","Basic "+credentials);
        response = FactoryRequest.make("get").send(requestInfo);
        // Get Token
        String token = response.then().extract().path("TokenString");
        requestInfo = new RequestInfo();
        requestInfo.setHeader("Token",token);
    }

    @When("send {} request {string} with body")
    public void sendPOSTRequestWithBody(String method, String url, String body) {
        requestInfo.setUrl(Configuration.host + this.replaceValues(url)).setBody(body);
        response = FactoryRequest.make(method).send(requestInfo);
    }

    @Then("response code should be {int}")
    public void responseCodeShouldBe(int expectedCode) {
        response.then().statusCode(expectedCode);
    }

    @And("the attribute {string} should be {string}")
    public void theAttributeShouldBe(String attribute, String expectedValue) {
        response.then().body(attribute, equalTo(expectedValue));
    }

    @And("save {string} in the variable {string}")
    public void saveInTheVariable(String attribute, String nameVariable) {
        variables.put(nameVariable, response.then().extract().path(attribute) + "");
    }

    // Replace Values
    private String replaceValues(String value) {
        for (String key : variables.keySet()) {
            value = value.replace(key, variables.get(key));
        }
        return value;
    }
}
