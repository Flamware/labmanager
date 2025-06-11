package fr.utbm.ciad.wprest.publications.data.dto;

import fr.utbm.ciad.labmanager.data.publication.PublicationLanguage;
import fr.utbm.ciad.labmanager.data.publication.PublicationType;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object representing the front-end view of a publication.
 *
 * @param publicationType      The type of the publication (e.g., journal, conference).
 * @param title                The title of the publication.
 * @param doi                  The Digital Object Identifier (DOI) of the publication.
 * @param issn                 The International Standard Serial Number (ISSN) of the publication.
 * @param publicationDate      The date when the publication was released.
 * @param publicatationYear    The year of publication.
 * @param persons              The list of persons associated with the publication.
 * @param abstractText         The abstract or summary of the publication.
 * @param extraUrl             An additional URL related to the publication.
 * @param dblpUrl              The DBLP entry URL for the publication.
 * @param pdfUrl               The URL to the PDF version of the publication.
 * @param awardCertificateUrl  The URL to the award certificate, if any.
 * @param language             The language in which the publication is written.
 * @param keywords             The list of keywords associated with the publication.
 */
public record PublicationsFrontDto (
            PublicationType publicationType,
            String title,
            String doi, 
            String issn,
            LocalDate publicationDate,
            List<PublicationsAuthorsDto> persons,
            String abstractText,
            String extraUrl,
            String dblpUrl,
            String pdfUrl,
            String awardCertificate,
            PublicationLanguage language,
            List<String> keywords
){
}
