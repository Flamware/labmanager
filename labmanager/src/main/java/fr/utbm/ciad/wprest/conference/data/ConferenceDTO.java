package fr.utbm.ciad.wprest.conference.data;

import java.util.List;

/**
 * Describes information about a conference
 *
 * @param id                  - the id of the conference
 * @param acronym             - the acronym of the conference
 * @param name                - the name of the conference
 * @param publisher           - the publisher of the conference
 * @param conferenceUrl       - the url of the conference if it is independent of the annual occurrence
 * @param coreId              - the coreId of the conference for the Core ranking website
 * @param openAccess          - Indicates if the journal is an open-access journal
 * @param validated           - Indicates if the journal was validated by an authority
 * @param year                - History of the quality indicators for this conference
 */
public record ConferenceDTO(long id,
        String acronym,
        String name,
        String publisher,
        String conferenceUrl,
        String coreId,
        Boolean openAccess,
        Boolean validated,
        List<Integer> year) {

}
