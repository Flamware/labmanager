package fr.utbm.ciad.wprest.conference;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.utbm.ciad.labmanager.services.conference.ConferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;


@Transactional
@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from this origin
@RequestMapping("/api/v4/conferences")
public class ConferenceRestService {

    private final ConferenceService conferenceService;

    public ConferenceRestService(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    
    @GetMapping("/count")
    @Operation(summary = "Get number of conferences", description = "Count of distinct conferences in the last 5 years", tags = {"Conference API"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Count retrieved successfully")
    })
    public ResponseEntity<Long> getConferenceCount() {
        int currentYear = LocalDate.now().getYear();
        int startYear = currentYear - 5;  // last 5 years, e.g. 2020-2024

        long count = conferenceService.countConferencesBetweenYears(startYear, currentYear - 1);
        return ResponseEntity.ok(count);
    }

}
