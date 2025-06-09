package fr.utbm.ciad.wprest.person.data.dto;
import fr.utbm.ciad.labmanager.data.organization.ResearchOrganization;

import java.time.LocalDate;

public record PersonTeachingDTO(
    String code,
    String title,
    String degree,
    ResearchOrganization university,
    LocalDate startDate,
    String language
) {}
