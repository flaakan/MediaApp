package se.experis.academy.mediaapp.controller.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.experis.academy.mediaapp.model.dao.TrackDAO;
import se.experis.academy.mediaapp.repository.TrackRepository;


@Controller
public class HomeController {

    TrackRepository trackRepository = new TrackRepository();

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("track", new TrackDAO());
        model.addAttribute("genres", trackRepository.getFiveRandom("Genre"));
        model.addAttribute("artists", trackRepository.getFiveRandom("Artist"));
        model.addAttribute("tracks", trackRepository.getFiveRandom("Track"));
        return "home";
    }

    @GetMapping("/search")
    public String findTrack(@RequestParam( value="trackName", required = false)String trackName, Model model){
        if(trackName == null || trackName == ""){
            return "redirect:/";
        }
        model.addAttribute("track", trackRepository.getTrackByName(trackName));
        return "searchPage";
    }
}
