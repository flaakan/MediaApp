package se.experis.academy.mediaapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.experis.academy.mediaapp.model.Track;
import se.experis.academy.mediaapp.repository.TrackRepository;


@Controller
public class HomeController {

    TrackRepository trackRepository = new TrackRepository();

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("track", new Track());
        model.addAttribute("genres", trackRepository.getFiveRandom("Genre"));
        model.addAttribute("artists", trackRepository.getFiveRandom("Artist"));
        model.addAttribute("tracks", trackRepository.getFiveRandom("Track"));
        return "home";
    }

    @GetMapping("/search")
    public String findTrack(@RequestParam( value="trackName", required = true)String trackName, Model model){
        if(trackName == null || trackName == ""){
            return "redirect:/";
        }
        model.addAttribute("track", trackRepository.getTrackByName(trackName));
        return "searchPage";
    }
}
