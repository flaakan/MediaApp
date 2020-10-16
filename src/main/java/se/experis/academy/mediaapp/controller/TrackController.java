package se.experis.academy.mediaapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.experis.academy.mediaapp.model.Track;
import se.experis.academy.mediaapp.model.web.TrackWeb;
import se.experis.academy.mediaapp.repository.TrackRepository;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/track")
public class TrackController {

    TrackRepository trackRepository = new TrackRepository();


    @GetMapping("/all")
    public ResponseEntity<ArrayList<TrackWeb>> getAllTracks(){
        ArrayList<TrackWeb> tracks  = trackRepository.getAllTracks();
        return ResponseEntity.ok(tracks);
    }

    @GetMapping("/{trackId}")
    public ResponseEntity<?> getTrackByTrackId(@PathVariable String trackId){
        TrackWeb track;
        try{
             track =  trackRepository.getTrackByName(trackId);

        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(track);
    }
}
