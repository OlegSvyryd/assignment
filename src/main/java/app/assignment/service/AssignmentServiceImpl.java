package app.assignment.service;

import app.assignment.domain.Assignment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static app.assignment.util.JsonUtilities.unmarshallListFromJson;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private static final String FILE_NAME = "assignments_data";

    /**
     * Get all assignments with reversed 'title' and 'body'.
     *
     * @return List of {@link Assignment} objects
     * @throws IOException exception while reading from json file
     */
    @Override
    public List<Assignment> getAssignments() throws IOException {
        return unmarshallListFromJson(FILE_NAME, Assignment.class).stream()
                .map(AssignmentServiceImpl::doReverseTitleAndBody)
                .collect(Collectors.toList());

    }

    /**
     * Reverse 'title' and 'body' for assignment.
     *
     * @param actualAssignment original {@link Assignment} object
     * @return assignment with reversed 'title' and 'body' fields
     */
    private static Assignment doReverseTitleAndBody(Assignment actualAssignment) {
        return new Assignment(
                actualAssignment.getId(),
                actualAssignment.getUserId(),
                new StringBuilder(actualAssignment.getTitle()).reverse().toString(),
                new StringBuilder(actualAssignment.getBody()).reverse().toString()
        );
    }

}
