package org.w1;
import org.json.JSONArray;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

import static spark.Spark.*;

public class WeatherAPI {
    private static String getWeatherDataAsJson(Request req, Response res) throws SQLException {
        JSONArray jsonArray;
            jsonArray = DataFetcher.getWeatherDataAsJson();

        res.type("application/json");
        return jsonArray.toString();
    }

    public static void main(String[] args){
        port(8081);

        before((req, res) ->{
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept");
            res.header("Access-Control-Allow-Credentials", "true");
        });
        get("/weather", (req,res) -> getWeatherDataAsJson(req, res));
    }
}

