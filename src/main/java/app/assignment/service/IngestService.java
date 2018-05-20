package app.assignment.service;

import app.assignment.domain.ImageDetails;

import java.io.File;
import java.io.IOException;

public interface IngestService {
    String getUrlForTheImage(ImageDetails text) throws IOException;
    File getImage(String imageUUID) throws IOException;
}
