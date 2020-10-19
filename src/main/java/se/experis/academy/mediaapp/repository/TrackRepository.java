package se.experis.academy.mediaapp.repository;

import se.experis.academy.mediaapp.model.dao.TrackDAO;
import java.sql.*;
import java.util.ArrayList;

public class TrackRepository {

    String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    Connection conn = null;

    /**
     * Gets information of a Track based on String trackName.
     *
     * @param trackName the nane of the track to search for.
     * @return TrackDAO an object with information of the Track.
     */
    public TrackDAO getTrackByName(String trackName) {
        trackName = trackName.toLowerCase();
        TrackDAO track = new TrackDAO();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep = conn.prepareStatement("select Track.Name, genre.Name as genreName, Album.Title as albumTitle, Artist.Name as artistName from Track inner join Genre genre on Track.GenreId = genre.GenreId inner join Album Album on Track.AlbumId = Album.AlbumId inner join Artist Artist on  Album.ArtistId = Artist.ArtistId where lower(Track.Name) = ?;");
            prep.setString(1, trackName);

            ResultSet resultSet = prep.executeQuery();
            if (resultSet.next()) {
                track = new TrackDAO(
                        resultSet.getString("name"),
                        resultSet.getString("albumTitle"),
                        resultSet.getString("genreName"),
                        resultSet.getString("artistName")
                );
            }
        } catch (SQLException throwables) {
            throw new RuntimeException();
        } finally {
            try {
                conn.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return track;
    }


    /**
     * Gets five random objects based on the tableName parameter.
     *
     * @param tableName the table to get random objects from.
     * @return A list of 5 random names fetched from tableName.
     */
    public ArrayList<String> getFiveRandom(String tableName) {
        ArrayList<String> fiveRandom = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep = conn.prepareStatement("select " + tableName + ".name as name from " + tableName + " order by  random() limit 5;");

            ResultSet resultSet = prep.executeQuery();
            while (resultSet.next()) {
                fiveRandom.add(resultSet.getString("name"));
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
        return fiveRandom;
    }
}
