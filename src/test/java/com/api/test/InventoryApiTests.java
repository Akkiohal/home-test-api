package com.api.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InventoryApiTests {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:3100/api";
    }

    @Test
    @Order(1)
    public void testGetAllMenuItems() {
    		
        given()
        .when()
            .get("/inventory")
        .then()
            .statusCode(200)
            .body("data.size()", greaterThanOrEqualTo(9))
            .body("data.id", everyItem(notNullValue()))
            .body("data.name", everyItem(notNullValue()))
            .body("data.price", everyItem(notNullValue()))
            .body("data.image", everyItem(notNullValue()));
    }

    @Test
    @Order(2)
    public void testFilterById() {
        given()
            .queryParam("id", 3)
        .when()
            .get("/inventory/filter")
        .then()
            .statusCode(200)
            .body("name", equalTo("Baked Rolls x 8"))
            .body("price", notNullValue())
            .body("image", notNullValue());
    }

    @Test
    @Order(3)
    public void testAddNewItem() {
        String payload = """
            {
                "id": "10",
                "name": "Hawaiian",
                "image": "hawaiian.png",
                "price": "$14"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/inventory/add")
        .then()
            .statusCode(400);
    }

    @Test
    @Order(4)
    public void testAddItemWithExistingId() {
        String payload = """
            {
                "id": "10",
                "name": "Hawaiian",
                "image": "hawaiian.png",
                "price": "$14"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/inventory/add")
        .then()
            .statusCode(400);
    }

    @Test
    @Order(5)
    public void testAddItemWithMissingFields() {
        String payload = """
            {
                "name": "Hawaiian",
                "image": "hawaiian.png",
                "price": "$14"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/inventory/add")
        .then()
            .statusCode(400)
            .body(containsString("Not all requirements are met"));
    }

    @Test
    @Order(6)
    public void testItemPresentInInventory() {
        given()
        .when()
            .get("/inventory")
        .then()
            .statusCode(200)
            .body("data", hasItem(allOf(
                    hasEntry("id", "10"),
                    hasEntry("name", "Hawaiian"),
                    hasEntry("image", "hawaiian.png"),
                    hasEntry("price", "$14")
            )));
    }
}
