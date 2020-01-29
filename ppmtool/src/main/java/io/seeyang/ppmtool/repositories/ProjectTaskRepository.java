package io.seeyang.ppmtool.repositories;

import io.seeyang.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// takes a project task object and a long
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);

    // find projects by its project sequence number
    ProjectTask findByProjectSequence(String sequence);
}
