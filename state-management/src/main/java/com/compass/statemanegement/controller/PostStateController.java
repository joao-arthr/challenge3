package com.compass.statemanegement.controller;
import com.compass.statemanegement.dto.PostStateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostStateController {

    @PostMapping
    public ResponseEntity<String> transitionState(@RequestBody PostStateDTO transitionDTO) {
        if (isValidTransition(transitionDTO)) {

            return ResponseEntity.ok("State transition successful.");
        } else {
            return ResponseEntity.badRequest().body("Invalid state transition.");
        }
    }

    private boolean isValidTransition(PostStateDTO transitionDTO) {

        return true;
    }


}
