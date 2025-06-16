package fr.utbm.ciad.wprest.person;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import fr.utbm.ciad.labmanager.data.member.*;

public class PersonSpecifications {

    /**
     * Specification to filter persons who have at least one permanent membership.
     *
     * @return a Specification for permanent memberships.
     */
    public static Specification<Person> hasPermanentMembership() {
    return (root, query, cb) -> {
            // Add this to avoid duplicates in the result list
            query.distinct(true);

            Join<Person, Membership> memberships = root.join("memberships", JoinType.INNER);
            return cb.isTrue(memberships.get("permanentPosition"));
        };
    }
}
