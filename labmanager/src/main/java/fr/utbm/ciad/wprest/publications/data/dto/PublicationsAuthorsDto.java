package fr.utbm.ciad.wprest.publications.data.dto;

/**
 * Data Transfer Object representing an author of a publication.
 *
 * @param name The name of the author.
 * @param id   The unique identifier of the author.
 */
public record PublicationsAuthorsDto(String name, long id) {
    
}
