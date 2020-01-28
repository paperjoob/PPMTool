package io.seeyang.ppmtool.repositories;

import io.seeyang.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
// takes a project task object and a long
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
}
