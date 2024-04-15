package org.w1;


import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataFetcher {
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/Educenter";
    private static final String DB_USER = "postgres";
    private static final String PASSWORD = "12345";


    public static JSONArray getWeatherDataAsJson() throws SQLException {
        JSONArray jsonArray = new JSONArray();
        Connection connectionDB = DriverManager.getConnection(DB_URL, DB_USER, PASSWORD);
        String sqlQuerySelection = "select * from weather";
                    PreparedStatement statement = connectionDB.prepareStatement(sqlQuerySelection);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        JSONObject record = new JSONObject();
                        record.put("city", resultSet.getString("city_name"));
                        record.put("temperature", resultSet.getString("temp"));
                        record.put("pressure", resultSet.getString("pressure"));
                        jsonArray.put(record);
                    }
                    connectionDB.close();
                    return jsonArray;
                    }
        public static void main(String[] args) throws SQLException{
            System.out.println(getWeatherDataAsJson().toString());
        }
        }

