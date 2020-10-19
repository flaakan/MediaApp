package se.experis.academy.mediaapp.repository;

import se.experis.academy.mediaapp.model.dao.CustomerFavoriteGenreDAO;
import se.experis.academy.mediaapp.model.dao.CustomerSpentDAO;
import se.experis.academy.mediaapp.model.dao.CustomerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomerRepository {

    String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    Connection conn = null;

    public CustomerDAO getCustomerById(String trackId){
        CustomerDAO customer = null;
        try{
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep = conn.prepareStatement("Select customerId, firstName, lastName, country, postalCode,phone from customer where customerId = ?");
            prep.setString(1,trackId);
            ResultSet resultSet = prep.executeQuery();

            customer = new CustomerDAO(
                    resultSet.getString("customerId"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("country"),
                    resultSet.getString("postalCode"),
                    resultSet.getString("phone"));
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try{
                conn.close();
            }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
            }

        }
        return customer;
    }


    public ArrayList<CustomerSpentDAO> getAllTopSpenders(){
        ArrayList<CustomerSpentDAO> allCustomers = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep = conn.prepareStatement("select customer.firstName, customer.LastName, SUM(Total) as totalAmount from Invoice inner join Customer customer on Invoice.CustomerId = customer.CustomerId group by Invoice.CustomerId order by TotalAmount desc");
            ResultSet resultSet = prep.executeQuery();
            while(resultSet.next()) {
                allCustomers.add(new CustomerSpentDAO(
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("totalAmount"))
                );
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        finally {
            try{
                conn.close();
            }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
        return allCustomers;
    }

    public CustomerFavoriteGenreDAO getCustomerFavoriteGenres(int customerId){
        CustomerFavoriteGenreDAO customer = new CustomerFavoriteGenreDAO();
        HashMap<String,Integer> favoriteGenresMap = new HashMap<>();
        try{
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep = conn.prepareStatement("select customer.FirstName as firstName, customer.LastName as lastName, genre.Name as Genre ,count(*) as total from InvoiceLine inner join Invoice invoice on invoice.InvoiceId = InvoiceLine.InvoiceId inner join Customer customer on customer.CustomerId = invoice.CustomerId inner join Track track on InvoiceLine.TrackId = track.TrackId inner join Genre genre on track.GenreId = genre.GenreId where invoice.CustomerId = ? group by genre.Name order by total desc limit 2;");
            prep.setInt(1, customerId);
            ResultSet resultSet = prep.executeQuery();

            String firstName = resultSet.getString("firstName");
            String lastName  = resultSet.getString("lastName");
            int max = resultSet.getInt("total");
            while(resultSet.next()) {
                if(max == resultSet.getInt("total")){
                    favoriteGenresMap.put(resultSet.getString("Genre"),max);
                }
            }
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setFavoriteGenres(favoriteGenresMap);
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally {
            try{
                conn.close();
            }
            catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
        return customer;
    }



}
