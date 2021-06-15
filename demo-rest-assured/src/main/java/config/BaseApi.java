package config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.config.HeaderConfig;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseApi {
    private static ThreadLocal<Response> response = new ThreadLocal<>();

    public Response getResponse(){
        return response.get();
    }

    public static void setBaseUrl(String url){
        reqSpec.get().given().baseUri(url);
    }

    public static void initReqSpec(){
        RestAssuredConfig configRest = RestAssuredConfig.newConfig()
                .headerConfig(HeaderConfig.headerConfig()
                        .overwriteHeadersWithName("access-token","Accept","Content-Type")).httpClient(HttpClientConfig.httpClientConfig())
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 60000)
                        .setParam("http.socket.timeout", 60000));
        reqSpec.set((FilterableRequestSpecification) given().config(configRest).
                log().uri().filter(new AllureRestAssured()));
    }
    public static void setCookie(String name, String value){
        reqSpec.get().cookie(name,value);
    }

    private static ThreadLocal<FilterableRequestSpecification> reqSpec = new ThreadLocal<>();

    public Response sendGet(String url){
        Response res =
                given().
                        spec(reqSpec.get()).
                        contentType(ContentType.JSON).
                        when().
                        get(url).
                        then().
                        log().all().
                        extract().response();
        response.set(res);
        return res;
    }

    public Response sendPost(String url, Object body){
        Response res =
                given().
                        spec(reqSpec.get()).
                        contentType(ContentType.JSON).
                        body(body).
                        when().
                        post(url).
                        then().
                        log().all().
                        extract().response();
        response.set(res);
        return res;
    }

    public String getJsonAsString(){
        return response.get().body().asString();
    }

    public JsonPath getJsonPath(Response r){
        String json = r.asString();
        return new JsonPath(json);
    }
    
    public String getJsonValue(String jsonLocator){
        String jsonValue = null;
        try{
            jsonValue = getJsonPath(response.get()).get(jsonLocator).toString();
        } catch (NullPointerException npe){
            System.out.println("Json Not Found");
        }
        return jsonValue;
    }
}
