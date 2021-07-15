package projects;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class GitHub_RestAssured_Project {
	
// Declare request specification
RequestSpecification requestSpec;

String sshID;
int id;

@BeforeClass
public void setup() {
    // Create request specification
    requestSpec = new RequestSpecBuilder()
        .setContentType(ContentType.JSON)
        .addHeader("Authorization", "token ")
        .setBaseUri("https://api.github.com/")
        .build();
  sshID= "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCEDpTezTqro0mR2bhnLf4yThDS0Do28W0XpK+RkSFX4Otpc1d9hnw1Sd9LO+w4u5vYCMIJ1VUuqxNIpfxexrd0yj68P6bskFMArZ4OBhzX/T/WCAfuJf+1Sv6V98SJZEZkYFUR38CwGidXebAsENNijVdHHemG+LCvM8L++Ym7xPa8yEwSN3XkUIecSw6Ct5UAzG2rORSbAERXs0VRcS5liP7v40D5ZMW/LKXhu8l2duW9RIVOeg7beoRL4AOW6xdwHM38mPbmHNZwXXSlyW7opMOCyLn51lB8Krk5l5W7O29VZH486NtWWS+EIrHVerzy36BffkWSyOWSqZAb";
       
}
@Test(priority=1)
public void addNewKey() {
    // Create JSON request
    String reqBody = "{\"title\": \"TestAPIKey\",\"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCEDpTezTqro0mR2bhnLf4yThDS0Do28W0XpK+RkSFX4Otpc1d9hnw1Sd9LO+w4u5vYCMIJ1VUuqxNIpfxexrd0yj68P6bskFMArZ4OBhzX/T/WCAfuJf+1Sv6V98SJZEZkYFUR38CwGidXebAsENNijVdHHemG+LCvM8L++Ym7xPa8yEwSN3XkUIecSw6Ct5UAzG2rORSbAERXs0VRcS5liP7v40D5ZMW/LKXhu8l2duW9RIVOeg7beoRL4AOW6xdwHM38mPbmHNZwXXSlyW7opMOCyLn51lB8Krk5l5W7O29VZH486NtWWS+EIrHVerzy36BffkWSyOWSqZAb\"}"; 
    		

    Response response = 
        given().spec(requestSpec) // Set headers
        .body(reqBody) // Add request body
        .when().post("/user/keys");//send post request
    
    System.out.println(response.asPrettyString());
    sshID = response.then().extract().path("id");
    
    //Assertions
    response.then().statusCode(201);
    //response.then().body("id", equalTo(sshID));
     
}

@Test(priority=2)
public void getinfo() {
    Response response = 
        given().spec(requestSpec) // Use requestSpec
        .when().get("/user/keys"); // Send GET request
    
   // Reporter.log(response.body().asPrettyString());
    System.out.println(response.asPrettyString());
 
    //Assertion
    //response.then().statusCode(200);
    //response.then().body("id", equalTo(sshID));
     
}

@Test(priority=3)
public void deleteKey() {
    Response response = given().spec(requestSpec) // Use requestSpec
            .when().pathParam("id", sshID) // Add path parameter
            .delete("/user/keys/{id}"); // Send GET request
    
    System.out.println(response.asPrettyString());

    // Assertions
    response.then().statusCode(204);
}
}
