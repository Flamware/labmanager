package fr.utbm.ciad.wprest.conference;

import fr.utbm.ciad.labmanager.Constants;
import fr.utbm.ciad.labmanager.data.conference.Conference;
import fr.utbm.ciad.labmanager.data.conference.ConferenceQualityAnnualIndicators;
import fr.utbm.ciad.labmanager.services.conference.ConferenceService;
import fr.utbm.ciad.wprest.conference.data.ConferenceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST controller for managing project-related operations.
 *
 * <p>This controller provides endpoints for accessing and manipulating
 * project-related data, including retrieving information about organizations,
 * participants and links related to a project.</p>
 *
 * <p>Base URL: /api/v{majorVersion}/conferences</p>
 *
 * <p>This class is annotated with {@link RestController} and handles
 * HTTP requests mapped to the /api/v{majorVersion}/conferences endpoint.
 * The version of the API is determined by the constant
 * {@link Constants#MANAGER_MAJOR_VERSION}.</p>
 */
@Transactional
@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from this origin

@RequestMapping("/api/v" + Constants.MANAGER_MAJOR_VERSION + "/conferences")
public class ConferenceRestService {

    ConferenceService conferenceService;

    public ConferenceRestService(@Autowired ConferenceService conferenceService){
        this.conferenceService = conferenceService;

    }

    
   @GetMapping("/all")
   @Operation(summary = "Gets all conferences", description = "Gets all conferences", tags = {"Conference API"})
   @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "The conference",content = @Content(schema = @Schema(implementation = ConferenceDTO.class))),
    @ApiResponse(responseCode = "404", description = "Not Found if no conferences are found.")
})
   public ResponseEntity<List<ConferenceDTO>> getAllConferences(){
        List<Conference> conferenceList = conferenceService.getAllConferences(null);

        List<ConferenceDTO> conferences = this.conferenceToConferenceDTO(conferenceList);

        return ResponseEntity.ok(conferences);

    }

    @GetMapping("/conferenceWithYear")
   @Operation(summary = "Gets all conferences with years", description = "Gets all conferences with years", tags = {"Conference API"})
   @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "The conference",content = @Content(schema = @Schema(implementation = ConferenceDTO.class))),
    @ApiResponse(responseCode = "404", description = "Not Found if no conferences are found.")
})
    public ResponseEntity<List<ConferenceDTO>> getConferencesWithYear(){

        List<Conference> allConference = conferenceService.getAllConferences(null);
        List<Conference> conferenceList = new ArrayList<>();
        for(Conference conference : allConference){
           if(!conference.getQualityIndicators().isEmpty()  ){
            conferenceList.add(conference);
           }   
        }


        List<ConferenceDTO> conferences = this.conferenceToConferenceDTO(conferenceList);

        return ResponseEntity.ok(conferences);

    }





    private  List<ConferenceDTO> conferenceToConferenceDTO( Collection<Conference> conferencelist){
        List<ConferenceDTO> conferences = new ArrayList<>();

        for  (Conference conference : conferencelist){

            long id = conference.getId();
            String acronym = conference.getAcronym();
            String name = conference.getName();
            String publisher = conference.getPublisher();
            String conferenceUrl = conference.getConferenceURL();
            String coreId = conference.getCoreId();
            Boolean openAccess = conference.getOpenAccess();
            Boolean validated = conference.isValidated();
            List<Integer> year = getYearsforConferenceDTO(id);

            conferences.add( new ConferenceDTO(id, acronym,name,publisher,conferenceUrl,coreId,openAccess,validated,year));
            
        }

        return conferences;

    }



    private List<Integer> getYearsforConferenceDTO(long id){

        List<Integer> year = new ArrayList<Integer>();
        List<ConferenceQualityAnnualIndicators> QualityIndicators = conferenceService.getConferenceQualityIndicatorsByJournalId(id);
        if(!QualityIndicators.isEmpty()){
            for(ConferenceQualityAnnualIndicators QualityIndicator : QualityIndicators){
               year.add(QualityIndicator.getReferenceYear());
            }

        }

        return year;

    }
    
}
