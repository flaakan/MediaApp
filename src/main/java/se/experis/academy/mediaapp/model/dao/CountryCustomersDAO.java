package se.experis.academy.mediaapp.model.dao;


public class CountryCustomersDAO {
    private int numberOfCustomers;
    private String Country;

    public CountryCustomersDAO() {

    }

    public CountryCustomersDAO(String country, int numberOfCustomers) {
        Country = country;
        this.numberOfCustomers = numberOfCustomers;

    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }
}

