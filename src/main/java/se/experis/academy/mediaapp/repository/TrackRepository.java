package se.experis.academy.mediaapp.repository;

import se.experis.academy.mediaapp.model.dao.TrackDAO;

import java.sql.*;
import java.util.ArrayList;

public class TrackRepository {

    String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    Connection conn = null;

    public TrackDAO getTrackByName(String trackName){
        trackName = trackName.toLowerCase();
        TrackDAO track = new TrackDAO();
        try{
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
            PreparedStatement prep = conn.prepareStatement("select Track.Name, G.Name as genreName, Album.Title as albumTitle, Artist.Name as artistName from Track inner join Genre G on Track.GenreId = G.GenreId inner join Album Album on Track.AlbumId = Album.AlbumId inner join Artist Artist on  Album.ArtistId = Artist.ArtistId where lower(Track.Name) = ?;");
            prep.setString(1,trackName);

            ResultSet resultSet = prep.executeQuery();
            if(resultSet.next()) {
                track = new TrackDAO(
                        resultSet.getString("name"),
                        resultSet.getString("albumTitle"),
                        resultSet.getString("genreName"),
                        resultSet.getString("artistName")
                );
            }

        }
        catch (SQLException throwables) {
            throw new RuntimeException();
        }finally {
            try{
                conn.close();
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }

        }

        return track;
    }




    public ArrayList<TrackDAO> getAllTracks(){
        ArrayList<TrackDAO> tracks = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);

            PreparedStatement preparedStatement = conn.prepareStatement("select  track.name, Genre.name as genreName, album.Title as albumTitle, artist.name as artistName from track " +
                            "inner join Genre genre on genre.GenreId =Track.GenreId = genre.GenreId" +
                            " inner join Album album on Track.AlbumId = album.AlbumId" +
                            " inner join Artist artist on album.ArtistId = artist.ArtistId;");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tracks.add(
                        new TrackDAO(
                                resultSet.getString("name"),
                                resultSet.getString("albumTitle"),
                                resultSet.getString("genreName"),
                                resultSet.getString("artistName")));
            }
        }
        catch (Exception ex){
            System.out.println("Something went wrong...");
            System.out.println(ex.toString());
        }
        finally {
            try {
                // Close Connection
                conn.close();
            }
            catch (Exception ex){
                System.out.println("Something went wrong while closing connection.");
                System.out.println(ex.toString());
            }
            return tracks;
        }
    }

    public ArrayList<String> getFiveRandom(String type) {
        ArrayList<String> fiveRandom = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
            PreparedStatement prep = conn.prepareStatement("select " + type +".name as name from "+ type +" order by  random() limit 5;");

            ResultSet resultSet = prep.executeQuery();
            while(resultSet.next()){
                fiveRandom.add(resultSet.getString("name"));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try{
                conn.close();
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
        return fiveRandom;
    }
}
