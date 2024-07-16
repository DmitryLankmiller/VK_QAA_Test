package com.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class GroupsGetCountersTest {
    private static final String BASE_URL = "https://api.ok.ru/fb.do";
    private static final String API_METHOD = "group.getCounters";
    private static final String GROUP_ID = "70000007356748";
    private static final String APPLICATION_KEY = "CBKKNNLGDIHBABABA";
    private static final String SESSION_KEY = "-n-LLLOPZM844I5fF2PoFXkMMJUhfiR06u4s52Ebt9ghpFOyOuuFJYQjfHsHEsrK3RrtiNwUo19T0QWpZs65";
    private static final String SESSION_SECRET_KEY = "e47d37e41dd023d2d829549ce3476592";
    private static final String COUNTERS_PARAM = "counterTypes";
    private static final String SIG_PARAM = "sig";
    private static final Map<String, String> queryParams = new HashMap<>();
    private static RequestSpecification spec;

    @BeforeAll
    public static void initSpec() {
        spec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addFilter(new ResponseLoggingFilter())//log request and response for better debugging. You can also only log if a requests fails.
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @BeforeAll
    public static void initDefaultQueryParams() {
        queryParams.put("application_key", APPLICATION_KEY);
        queryParams.put("format", "json");
        queryParams.put("method", API_METHOD);
        queryParams.put("session_key", SESSION_KEY);
        queryParams.put("group_id", GROUP_ID);
    }

    private void addCounterParam(String... counters) {
        queryParams.put(
                COUNTERS_PARAM,
                Stream.of(counters)
                        .map(counter -> counter + ",")
                        .collect(Collectors.joining())
        );
    }

    private void addSigParam() {
        queryParams.put(SIG_PARAM, calculateSig());
    }

    private RequestSpecification defaultRequest(String ...counters) {
        addCounterParam("members");
        addSigParam();
        var request = given()
                .spec(spec);
        setQueryParamsToRequest(request);
        return request;
    }

    private void setQueryParamsToRequest(RequestSpecification request) {
        queryParams
                .keySet()
                .stream()
                .sorted()
                .forEach(
                        key -> request.param(key, queryParams.get(key))
                );
    }

    private String calculateSig() {
        return DigestUtils.md5Hex(
                queryParams
                        .keySet()
                        .stream()
                        .sorted()
                        .map(key -> key + "=" + queryParams.get(key))
                        .collect(Collectors.joining())
                        + SESSION_SECRET_KEY
        );
    }

    @Test
    public void shouldHaveResponseStatus200() {
        defaultRequest("members")
                .when()
                .get()
                .then()
                .statusCode(200);
    }
}
