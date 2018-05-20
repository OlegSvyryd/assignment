package app.assignment.controller;

import app.assignment.domain.ImageDetails;
import app.assignment.service.IngestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.activation.FileTypeMap;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@RestController
public class IngestController {
    private static final Logger LOG = LoggerFactory.getLogger( AssignmentController.class );

    @Autowired
    private IngestService ingestService;

    @PostMapping( value = "/ingest", consumes = MediaType.TEXT_PLAIN_VALUE )
    public String createImageAndGetUrl(
            @Valid
            @NotNull( message = "The text cannot be empty." )
            @RequestBody String text
    ) throws Exception
    {
        LOG.info( "Obtaining url for image." );

        ImageDetails imageDetails = new ImageDetails(UUID.randomUUID().toString(), text);
        return ingestService.getUrlForTheImage(imageDetails);
    }

    @GetMapping(value = "/images/{uuid}")
    public ResponseEntity<byte[]> getImage(@PathVariable("uuid") String imageUUID) throws IOException{
        File imageFile = ingestService.getImage(imageUUID);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + imageFile.getName())
                .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(imageFile)))
                .body(Files.readAllBytes(imageFile.toPath()));
    }

}
