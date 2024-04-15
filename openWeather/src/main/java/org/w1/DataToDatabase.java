package org.w1;


import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataToDatabase {
    private static final String API_KEY="5232456521e9b4d69b85e2e0db55c674";
    private static final String DB_URL="jdbc:postgresql://localhost:5433/Educenter";
    private static final String DB_USER="postgres";
    private static final String PASSWORD="12345";
//taking json object from OpenWeather
    private static JSONObject getWeatherDataObject(String city) throws IOException {
        String urlAPI= "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY;
        URL url= new URL(urlAPI);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String fieldValues= reader.readLine();//only 1st field will be read
        while (fieldValues!= null){
            response.append(fieldValues);
            fieldValues= reader.readLine();//reading the next line
        }
        reader.close();
        return new JSONObject(response.toString());
    }

    private static void inputDataToDB(JSONObject object) throws SQLException {
        Connection connectionDB=DriverManager.getConnection(DB_URL,DB_USER,PASSWORD);
        String sqlQuery="insert into weather(city_name, temp, pressure) values(?, ?, ?)";
        PreparedStatement preparedStatement=connectionDB.prepareStatement(sqlQuery);
        preparedStatement.setString(1, object.getString("name"));
        preparedStatement.setDouble(2,object.getJSONObject("main").getDouble("temp"));
        preparedStatement.setDouble(3, object.getJSONObject("main").getDouble("pressure"));
        preparedStatement.execute();
        connectionDB.close();
    }
    public static void main( String[] args ) throws IOException, SQLException {
        JSONObject forecast=getWeatherDataObject("Slough");
        System.out.println(forecast.toString());
        inputDataToDB(forecast);
    }
}
