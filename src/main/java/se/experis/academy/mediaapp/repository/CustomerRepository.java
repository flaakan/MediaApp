package se.experis.academy.mediaapp.repository;

import se.experis.academy.mediaapp.model.web.CustomerWeb;

import java.sql.*;

public class CustomerRepository {


    String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    Connection conn = null;

    public CustomerWeb getCustomerById(String trackId){
        CustomerWeb customer = null;
        try{
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
            PreparedStatement prep = conn.prepareStatement("Select customerId, firstName, lastName, country, postalCode,phone from customer where customerId = ?");
            prep.setString(1,trackId);

            ResultSet resultSet = prep.executeQuery();

            customer = new CustomerWeb(
                    resultSet.getString("customerId"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("country"),
                    resultSet.getString("postalCode"),
                    resultSet.getString("phone"));
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try{
                conn.close();
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }

        }

        return customer;
    }
}
