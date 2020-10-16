package se.experis.academy.mediaapp.repository;

import se.experis.academy.mediaapp.model.Track;
import se.experis.academy.mediaapp.model.web.TrackWeb;

import java.sql.*;
import java.util.ArrayList;

public class TrackRepository {

    String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    Connection conn = null;

    public TrackWeb getTrackByName(String trackName){
        trackName = trackName.toLowerCase();
        TrackWeb track = new TrackWeb();
        try{
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
            PreparedStatement prep = conn.prepareStatement("select Track.Name, G.Name as genreName, Album.Title as albumTitle, Artist.Name as artistName from Track inner join Genre G on Track.GenreId = G.GenreId inner join Album Album on Track.AlbumId = Album.AlbumId inner join Artist Artist on  Album.ArtistId = Artist.ArtistId where lower(Track.Name) = ?;");
            prep.setString(1,trackName);

            ResultSet resultSet = prep.executeQuery();
            if(resultSet.next()) {
                track = new TrackWeb(
                        resultSet.getString("name"),
                        resultSet.getString("genreName"),
                        resultSet.getString("albumTitle"),
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




    public ArrayList<TrackWeb> getAllTracks(){
        ArrayList<TrackWeb> tracks = new ArrayList<>();
        System.out.println("in repo");
        try {
            // Open Connection
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");

            // Prepare Statement
            PreparedStatement preparedStatement =
                    conn.prepareStatement("select  track.name, Genre.name as genreName, album.Title as albumTitle, artist.name as artistName from track " +
                            "inner join Genre genre on genre.GenreId =Track.GenreId = genre.GenreId" +
                            " inner join Album album on Track.AlbumId = album.AlbumId" +
                            " inner join Artist artist on album.ArtistId = artist.ArtistId;");

            ResultSet resultSet = preparedStatement.executeQuery();



            // Process Results
            while (resultSet.next()) {
                tracks.add(
                        new TrackWeb(
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


    public ArrayList<String> getFiveRandomGenres(){
        ArrayList<String> randomGenres = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
            PreparedStatement prep = conn.prepareStatement("select genre.Name as genreName from Genre order by  random() limit 5;");

            ResultSet resultSet = prep.executeQuery();
            while(resultSet.next()){
                randomGenres.add(resultSet.getString("genreName"));
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
        return randomGenres;
    }


    public ArrayList<String> getFiveRandom(String type) {
        ArrayList<String> randomArtists = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
            PreparedStatement prep = conn.prepareStatement("select " + type +".name as name from "+ type +" order by  random() limit 5;");

            ResultSet resultSet = prep.executeQuery();
            while(resultSet.next()){
                randomArtists.add(resultSet.getString("name"));
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
        return randomArtists;
    }
}
