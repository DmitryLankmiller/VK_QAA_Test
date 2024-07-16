package com.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupsGetCountersTest {
    private static final String BASE_URL = "https://api.ok.ru/fb.do";
    private static final String API_METHOD = "group.getCounters";
    private static final String GROUP_ID = "70000007356748";
    private static final String APPLICATION_KEY = "CBKKNNLGDIHBABABA";
    private static final String SESSION_KEY = "-n-LLLOPZM844I5fF2PoFXkMMJUhfiR06u4s52Ebt9ghpFOyOuuFJYQjfHsHEsrK3RrtiNwUo19T0QWpZs65";
    private static final String SESSION_SECRET_KEY = "e47d37e41dd023d2d829549ce3476592";

    private static final String COUNTERS_PARAM = "counterTypes";
    private static final String SIG_PARAM = "sig";

    private static final int INVALID_PARAMETER_ERROR_CODE = 100;
    private static final Pattern INVALID_PARAMETER_MSG_PATTERN = Pattern.compile("PARAM");

    private static final int NOT_FOUND_ERROR_CODE = 300;
    private static final Pattern NOT_FOUND_MSG_PATTERN = Pattern.compile("NOT_FOUND");

    private static final int INVALID_GROUP_ID_ERROR_CODE = 160;
    private static final Pattern INVALID_GROUP_ID_MSG_PATTERN = Pattern.compile("PARAM_GROUP_ID ");

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

    @BeforeEach
    public void initDefaultQueryParams() {
        queryParams.clear();
        queryParams.put("application_key", APPLICATION_KEY);
        queryParams.put("format", "json");
        queryParams.put("method", API_METHOD);
        queryParams.put("session_key", SESSION_KEY);
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

    private RequestSpecification defaultRequest(String... counters) {
        addCounterParam(counters);
        addSigParam();
        queryParams.put("group_id", GROUP_ID);
        var request = given()
                .spec(spec);
        setQueryParamsToRequest(request);
        return request;
    }

    private RequestSpecification requestToSpecifiedGroup(String groupId, String... counters) {
        addCounterParam(counters);
        addSigParam();
        queryParams.put("group_id", groupId);
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

    private static Stream<Arguments> getDifferentCounterTypes() {
        return Stream.of(
                Arguments.of(List.of("members")),
                Arguments.of(List.of("members", "moderators")),
                Arguments.of(List.of("ads_topics", "black_list", "catalogs", "delayed_topics", "friends", "join_requests")),
                Arguments.of(List.of("new_paid_topics", "own_products", "paid_members", "paid_topics", "photo_albums", "photos", "pinned_topics", "presents", "products")),
                Arguments.of(List.of("suggested_products", "suggested_topics", "themes", "unpublished_topics")),
                Arguments.of(List.of("promo_on_moderation")), // Test failes on it
                Arguments.of(List.of("ads_topics", "black_list", "catalogs", "delayed_topics", "friends", "join_requests", "links", "maybe", "members", "moderators", "music_tracks", "new_paid_topics", "own_products", "paid_members", "paid_topics", "photo_albums", "photos", "pinned_topics", "presents", "products", "suggested_products", "suggested_topics", "themes", "unpublished_topics", "videos"))
        );
    }

    @ParameterizedTest
    @MethodSource("getDifferentCounterTypes")
    public void shouldHaveResponseStatus200(List<String> counterTypes) {
        defaultRequest(counterTypes.toArray(new String[0]))
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @ParameterizedTest
    @MethodSource("getDifferentCounterTypes")
    public void checkResponseBody(List<String> counterTypes) {
        defaultRequest(counterTypes.toArray(new String[0]))
                .when()
                .get()
                .then()
                .extract().as(GroupsGetCountersDTO.class);
    }


    private static Stream<Arguments> getNotExistCounterTypes() {
        return Stream.of(
                Arguments.of(List.of("mems")),
                Arguments.of(List.of("news")),
                Arguments.of(List.of("suggestedPproducts")),
                Arguments.of(List.of("rs", "mods")),
                Arguments.of(List.of("ads", "list", "logs"))
        );
    }

    @ParameterizedTest
    @MethodSource("getNotExistCounterTypes")
    public void tryGetNotExistCounters(List<String> counterTypes) {
        var error = defaultRequest(counterTypes.toArray(new String[0]))
                .when()
                .get()
                .then()
                .extract().as(ErrorDTO.class);
        assertEquals(INVALID_PARAMETER_ERROR_CODE, error.getCode());
        assertTrue(INVALID_PARAMETER_MSG_PATTERN.matcher(error.getMessage()).find());
    }


    @ParameterizedTest
    @ValueSource(strings = {"7000000735674","123456789","000"})
    public void tryGetNotExistGroupCounters(String groupId) {
        var error = requestToSpecifiedGroup(groupId, "members")
                .when()
                .get()
                .then()
                .extract().as(ErrorDTO.class);
        assertEquals(NOT_FOUND_ERROR_CODE, error.getCode());
        assertTrue(NOT_FOUND_MSG_PATTERN.matcher(error.getMessage()).find());
    }


    @ParameterizedTest
    @ValueSource(strings = {"kitty","sdDF_@1fg7634f","    "})
    public void tryGetInvalidGroupCounters(String groupId) {
        var error = requestToSpecifiedGroup(groupId, "members")
                .when()
                .get()
                .then()
                .extract().as(ErrorDTO.class);
        assertEquals(INVALID_GROUP_ID_ERROR_CODE, error.getCode());
        assertTrue(INVALID_GROUP_ID_MSG_PATTERN.matcher(error.getMessage()).find());
    }

}
