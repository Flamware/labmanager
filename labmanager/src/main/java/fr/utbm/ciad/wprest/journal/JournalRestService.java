package fr.utbm.ciad.wprest.journal;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aspose.slides.internal.ax.ar;
import com.vaadin.flow.component.html.Article;

import fr.utbm.ciad.labmanager.data.journal.Journal;
import fr.utbm.ciad.labmanager.data.journal.JournalQualityAnnualIndicators;
import fr.utbm.ciad.labmanager.data.publication.Publication;
import fr.utbm.ciad.labmanager.data.publication.PublicationType;
import fr.utbm.ciad.labmanager.services.journal.JournalService;
import fr.utbm.ciad.labmanager.services.publication.PublicationService;
import fr.utbm.ciad.wprest.journal.data.dto.JournalDTO;
import fr.utbm.ciad.wprest.journal.data.dto.JournalDTO.QualityIndicator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;

@Transactional
@RestController
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from this origin
@RequestMapping("/api/v4/journals")
public class JournalRestService {

    private final JournalService journalService;
    private PublicationService publicationService;



    @Autowired
    public JournalRestService(JournalService journalService, PublicationService publicationService) {
        this.journalService = journalService;
        this.publicationService = publicationService;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all journals", description = "Retrieve all journals", tags = {"Journal API"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of journals retrieved successfully")
    })
    public ResponseEntity<List<JournalDTO>> getAllJournals() {
        List<Journal> journals = journalService.getAllJournals();

        List<JournalDTO> dtos = journals.stream()
            .map(this::toJournalDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    private JournalDTO toJournalDTO(Journal journal) {
        return new JournalDTO(
            journal.getJournalName(),
            journal.getScimagoCategory(),
            journal.isValidated(),
            String.valueOf(journal.getId()),
            journal.getScimagoId(),
            journal.getPublisher(),
            journal.getJournalURL(),
            journal.getWosCategory(),
            convertIndicators(journal.getQualityIndicators())
        );
    }

    private Map<String, JournalDTO.QualityIndicator> convertIndicators(Map<Integer, JournalQualityAnnualIndicators> original) {
        if (original == null) return Map.of(); // safe fallback for null
        return original.entrySet().stream()
            .collect(Collectors.toMap(
                e -> String.valueOf(e.getKey()),
                e -> new JournalDTO.QualityIndicator(
                    e.getValue().getWosQIndex() != null ? e.getValue().getWosQIndex().toString() : null,
                    e.getValue().getScimagoQIndex() != null ? e.getValue().getScimagoQIndex().toString() : null,
                    Double.valueOf(e.getValue().getImpactFactor())
                )
            )
        );
    }

    @GetMapping("/scimago-ranked-publications/count")
    public ResponseEntity<Long> getScimagoRankedPublicationCount() {
        int currentYear = LocalDate.now().getYear();
        int startYear = currentYear - 5; // e.g., 2020 if currentYear = 2025

        //List<Publication> allPublications = publicationService.findByReferenceYearBetween(startYear, currentYear - 1);

        /*long count = allPublications.stream()
            .filter(pub -> pub.getJournal() != null)
            .filter(pub -> {
                Journal journal = pub.getJournal();
                Map<Integer, QualityIndicator> indicators = journal.getQualityIndicators();
                if (indicators == null) return false;

                QualityIndicator qi = indicators.get(pub.getPublicationYear());
                return qi != null && qi.getScimagoQIndex() != null;
            })
            .count();*/
            long count = 3;

        return ResponseEntity.ok(count);
    }

    @GetMapping("/journals-per-year")
    public Map<Integer, Long> getJournalPublicationCountBetweenYears(
        @RequestParam int startYear,
        @RequestParam int endYear) {

        List<Publication> publications = publicationService.getAllPublications();

        return publications.stream()
            .filter(p -> {
                int year = p.getPublicationYear();
                return year >= startYear && year <= endYear;
            })
            .filter(p -> p.getISSN() != null && !p.getISSN().isBlank())
            .map(p -> {
                Journal journal = journalService.findByIssn(p.getISSN());
                return (journal != null) ? p.getPublicationYear() : null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }


    @GetMapping("/scimago-journals/average-per-fte")
    public ResponseEntity<Double> getAverageScimagoRankedJournalsPerFTE() {
        int currentYear = LocalDate.now().getYear(); // e.g., 2025
        int startYear = currentYear - 5;             // 2020
        int endYear = currentYear - 1;               // 2024

        List<JournalDTO> dtos = journalService.getAllJournals().stream()
            .map(this::toJournalDTO) // Use your conversion method
            .collect(Collectors.toList());

        int totalRankedJournals = 0;

        for (int year = startYear; year <= endYear - 1; year++) {
            final String yearStr = String.valueOf(year);

            long countForYear = dtos.stream()
                .map(JournalDTO::getQualityIndicatorsHistory)
                .filter(Objects::nonNull)
                .map(qi -> qi.get(yearStr))
                .filter(Objects::nonNull)
                .filter(indicator -> indicator.getScimagoQIndex() != null)
                .count();

            totalRankedJournals += countForYear;
        }

        // Let's say you hardcode the FTE to 25 for now
        int fte = 25;

        double averagePerYear = (double) totalRankedJournals / (currentYear - startYear);
        double averagePerFTEPerYear = averagePerYear / fte;

        return ResponseEntity.ok(averagePerFTEPerYear);
    }
}
