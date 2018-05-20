package app.assignment.controller;

import app.assignment.domain.Assignment;
import app.assignment.service.AssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AssignmentController {
    private static final Logger LOG = LoggerFactory.getLogger( AssignmentController.class );

    @Autowired
    private AssignmentService assignmentService;

    /**
     * Get all assignments.
     *
     * @return List of {@link Assignment} objects
     */
    @GetMapping( value = "/assignment" )
    public List<Assignment> getAssignments() throws Exception
    {
        LOG.info( "Obtaining assignments." );
        return assignmentService.getAssignments();
    }

}
