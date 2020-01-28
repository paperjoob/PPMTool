package io.seeyang.ppmtool.repositories;

import io.seeyang.ppmtool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
// takes a backlog object and a Long as an ID
public interface BacklogRepository  extends CrudRepository<Backlog, Long> {
    // return method to find project identifier
    Backlog findByProjectIdentifier(String Identifier);
}
