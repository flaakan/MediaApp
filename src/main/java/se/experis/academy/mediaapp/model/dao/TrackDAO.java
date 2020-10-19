package se.experis.academy.mediaapp.model.dao;

public class TrackDAO {
    String name;
    String genre;
    String albumName;
    String artistName;


    public TrackDAO() {
    }

    public TrackDAO(String name, String albumName, String genre, String artistName ) {

        this.name = name;
        this.albumName = albumName;
        this.genre = genre;
        this.artistName = artistName;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
