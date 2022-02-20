import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.FakerResponse;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class deneme {

    public static final String URL_ADDRESSES = "https://fakerapi.it/api/v1/addresses?";
    public static final String URL_CHARACTERS =  "https://fakerapi.it/api/v1/texts?_quantity=1&";
    public static final String URL_FAKERRESPONSE =  "https://fakerapi.it/api/v1/custom?_quantity=1&name=name&lmd=dateTime&phoneNumber=phone&description=text";



    @Test
    public void quantityCheck() {
        RestAssured.baseURI = URL_ADDRESSES;

        int quantity = 5;
        Response res = getResponse("_quantity", String.valueOf(quantity));

        Assert.assertEquals(res.statusCode(), 200);

        int responseTotal = res.jsonPath().getInt("total");

        Assert.assertEquals(quantity, responseTotal);

    }

    @Test
    public void checkDataAreasforText(){
        RestAssured.baseURI = URL_CHARACTERS;

        int characters= 500;
        Response res2 = getResponse("_characters", String.valueOf(characters));

        Assert.assertNotNull(res2.jsonPath().get("data"));
        ArrayList<Map<String, String>> dataMap = res2.jsonPath().getJsonObject("data");
        System.out.println(dataMap.get(0).get("title"));
        Assert.assertNotNull(dataMap.get(0).get("title"));
        Assert.assertNotNull(dataMap.get(0).get("author"));
        Assert.assertNotNull(dataMap.get(0).get("genre"));
        Assert.assertNotNull(dataMap.get(0).get("content"));
    }

    @Test
    public void sendCharacterRequestforText(){
        RestAssured.baseURI = URL_CHARACTERS;
        List<Integer> list = new ArrayList<Integer>();
        list.add(0);
        list.add(200);
        list.add(500);

        for (int i=0; i<=list.size(); i++) {

            Response res3 = getResponse("_characters", String.valueOf(list.get(i)));
            System.out.println(res3.asString());
            ArrayList<Map<String, String>> dataMap = res3.jsonPath().getJsonObject("data");

//          System.out.println(dataMap.get(0).get("content"));
            String data = (dataMap.get(0).get("" +
                    "content"));
      //      System.out.println(list.get(i));
            System.out.println(data.length());
          Assert.assertEquals(data.length(), String.valueOf(list.get(i)));
        }
    }

    @Test
    public void createURLandCheckResponse() throws IOException {
        RestAssured.baseURI = URL_FAKERRESPONSE;
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, String> paramsMap= new HashMap<>();
        paramsMap.put("_quantity", "1");
        paramsMap.put("name", "name");
        paramsMap.put("lmd", "dateTime");
        paramsMap.put("phoneNumber", "phone");
        paramsMap.put("description", "text");

        //create endpoint and response
        Response res3 = given().params(paramsMap).get().then()
                .extract()
                .response();
        //get response from body
        String response = res3.body().asString();

        Assertions.assertDoesNotThrow(()->objectMapper.readValue(response, FakerResponse.class));
        //

    }

    public Response getResponse(String paramApi, String paramV) {
        return given()
                .param(paramApi, paramV)
                .get()
                .then()
                .extract()
                .response();
    }

}
