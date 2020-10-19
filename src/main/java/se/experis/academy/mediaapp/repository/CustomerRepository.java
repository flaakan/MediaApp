package se.experis.academy.mediaapp.repository;

import se.experis.academy.mediaapp.model.dao.CountryCustomersDAO;
import se.experis.academy.mediaapp.model.dao.CustomerDAO;
import se.experis.academy.mediaapp.model.dao.CustomerFavoriteGenreDAO;
import se.experis.academy.mediaapp.model.dao.CustomerSpentDAO;
import se.experis.academy.mediaapp.model.entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomerRepository {

    String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    Connection conn = null;


    public ArrayList<CustomerDAO> getAllCustomers() {
        ArrayList<CustomerDAO> customers = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep = conn.prepareStatement("SELECT CustomerId,FirstName, LastName,Country, PostalCode,Phone From Customer");
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                customers.add(new CustomerDAO(
                        set.getInt("customerId"),
                        set.getString("firstName"),
                        set.getString("lastName"),
                        set.getString("country"),
                        set.getString("postalCode"),
                        set.getString("phone")
                ));

            }
        } catch (Exception exception) {
            System.out.println(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        return customers;
    }


    public ArrayList<CountryCustomersDAO> getCustomerQuantityPerCountry() {
        ArrayList<CountryCustomersDAO> customerQuantity = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep = conn.prepareStatement("SELECT Customer.Country as Country, COUNT(*) as Quantity FROM Customer GROUP BY Customer.Country Order By COUNT(*) DESC");
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                customerQuantity.add(new CountryCustomersDAO(
                        set.getString("Country"),
                        set.getInt("Quantity")
                ));
            }
        } catch (Exception exception) {
            System.out.println(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        return customerQuantity;
    }

    public Boolean addCustomer(Customer customer) {
        Boolean success = false;

        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("INSERT INTO Customer(CustomerId,FirstName, LastName,Country, PostalCode,Phone,Email)" +
                            " VALUES(?,?,?,?,?,?,?)");

            prep.setInt(1, customer.getCustomerId());
            prep.setString(2, customer.getFirstName());
            prep.setString(3, customer.getLastName());
            prep.setString(4, customer.getCountry());
            prep.setString(5, customer.getPostalCode());
            prep.setString(6, customer.getPhone());
            prep.setString(7, customer.getEmail());

            int result = prep.executeUpdate();
            success = (result != 0);

            System.out.println("Add went well!");

        } catch (Exception exception) {
            System.out.println(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        return success;
    }

    public Boolean updateCustomer(Customer customer) {
        Boolean success = false;
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("UPDATE customer SET FirstName=?, LastName=?,Country=?, PostalCode=?, Phone=?,Email=?" +
                            " WHERE CustomerId=?");

            prep.setString(1, customer.getFirstName());
            prep.setString(2, customer.getLastName());
            prep.setString(3, customer.getCountry());
            prep.setString(4, customer.getPostalCode());
            prep.setString(5, customer.getPhone());
            prep.setString(6, customer.getEmail());
            prep.setInt(7, customer.getCustomerId());

            int result = prep.executeUpdate();
            success = (result != 0); // if res = 1; true

            System.out.println("Update went well!");

        } catch (Exception exception) {
            System.out.println(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        return success;
    }


    /**
     * Gets all the customers with the total amount spent, ordered descending by the total amount.
     *
     * @return A list with all customers first nam, last name and total amount spent.
     */
    public ArrayList<CustomerSpentDAO> getAllTopSpenders() {
        ArrayList<CustomerSpentDAO> allCustomers = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep = conn.prepareStatement("select customer.firstName, customer.LastName, SUM(Total) as totalAmount from Invoice inner join Customer customer on Invoice.CustomerId = customer.CustomerId group by Invoice.CustomerId order by TotalAmount desc");
            ResultSet resultSet = prep.executeQuery();
            while (resultSet.next()) {
                allCustomers.add(new CustomerSpentDAO(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("totalAmount"))
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return allCustomers;
    }

    /**
     * Gets the favorite genre of a customer based on the purchases the customer has made.
     * Returns an object with customer first name, last name and the favorite genre with the amount of purchases.
     * In the case of a tie of amount of purchases of a genre, returns both genres.
     *
     * @param customerId the id of the customer to check from
     * @return an object with the customers first name, last name and the favorite genre with the amount of purchases.
     */

    public CustomerFavoriteGenreDAO getCustomerFavoriteGenres(int customerId) {
        CustomerFavoriteGenreDAO customer = new CustomerFavoriteGenreDAO();
        HashMap<String, Integer> favoriteGenresMap = new HashMap<>();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep = conn.prepareStatement("select customer.FirstName as firstName, customer.LastName as lastName, genre.Name as Genre ,count(*) as total from InvoiceLine inner join Invoice invoice on invoice.InvoiceId = InvoiceLine.InvoiceId inner join Customer customer on customer.CustomerId = invoice.CustomerId inner join Track track on InvoiceLine.TrackId = track.TrackId inner join Genre genre on track.GenreId = genre.GenreId where invoice.CustomerId = ? group by genre.Name order by total desc limit 2;");
            prep.setInt(1, customerId);
            ResultSet resultSet = prep.executeQuery();

            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            int max = resultSet.getInt("total");
            while (resultSet.next()) {
                if (max == resultSet.getInt("total")) {
                    favoriteGenresMap.put(resultSet.getString("Genre"), max);
                }
            }
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setFavoriteGenres(favoriteGenresMap);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return customer;
    }
}
