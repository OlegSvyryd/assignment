package app.assignment.service;

import app.assignment.domain.Assignment;

import java.io.IOException;
import java.util.List;

public interface AssignmentService {
    List<Assignment> getAssignments() throws IOException;
}
