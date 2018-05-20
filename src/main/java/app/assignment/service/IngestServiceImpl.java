package app.assignment.service;

import static app.assignment.Application.port;

import app.assignment.domain.ImageDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class IngestServiceImpl implements IngestService {
    private static final Logger LOG = LoggerFactory.getLogger( IngestServiceImpl.class );

    private static final String PNG_FORMAT = "png";
    private static final String FONT_TYPE = "TimesNewRoman";
    private static final int FONT_SIZE = 24;
    private static final Color FONT_COLOR = Color.BLACK;

    private static final Map<String, String> IMAGE_DETAILS_MAP = new HashMap<>();
    private static final String HOST = "http://localhost";
    private static final String IMAGES_ENDPOINT = "/images/";

    /**
     * Create image and get url of newly created image.
     *
     * @param imageDetails image info
     * @return url of the image
     * @throws IOException exception while working with file
     */
    @Override
    @Cacheable( value = "image-text", key = "#imageDetails.text")
    public String getUrlForTheImage(ImageDetails imageDetails) throws IOException {
        // Go through saved user-input text
        Iterator<String> iterator = IMAGE_DETAILS_MAP.keySet().iterator();
        while (iterator.hasNext()) {
            String text = iterator.next();
            // If text already inputted in the past
            if (imageDetails.getText().equals(text)) {

                // Return url for the image with the same text
                return IMAGE_DETAILS_MAP.get(text);
            }
        }

        // Create image with new text
        String url = createImage(imageDetails);

        // Save data in map
        IMAGE_DETAILS_MAP.put(imageDetails.getText(), url);

        // Return url for the new image
        return url;
    }

    /**
     * Return image as file.
     *
     * @param imageUUID the UUID of the image
     * @return image as a file
     * @throws IOException exception while reading file
     */
    @Override
    public File getImage(String imageUUID) throws IOException {
        return new File(imageUUID + "." + PNG_FORMAT);
    }

    /**
     * Create new image on server and return url for this image.
     *
     * @param text text that will be showed on the image
     * @return url of the image on the server
     */
    private String createImage(ImageDetails text) {
        LOG.debug("Creating image.");
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);// Represents an image with 8-bit RGBA color components packed into integer pixels.
        Graphics2D graphics2d = image.createGraphics();
        Font font = new Font(FONT_TYPE, Font.PLAIN, FONT_SIZE);
        graphics2d.setFont(font);
        FontMetrics fontmetrics = graphics2d.getFontMetrics();
        int width = fontmetrics.stringWidth(text.getText());
        int height = fontmetrics.getHeight();
        graphics2d.dispose();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2d = image.createGraphics();
        graphics2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics2d.setFont(font);
        fontmetrics = graphics2d.getFontMetrics();
        graphics2d.setColor(FONT_COLOR);
        graphics2d.drawString(text.getText(), 0, fontmetrics.getAscent());
        graphics2d.dispose();
        try {
            // Save image on the server
            ImageIO.write(image, PNG_FORMAT, new File(text.getImageId() + "." + PNG_FORMAT));

            // Generate image url
            return getImageUrl(text.getImageId());
        } catch (IOException ex) {
            // Throw an exception if there were problems while saving image on the server
            throw new RuntimeException("Encountered an issue while saving your image: " + ex);
        }
    }

    /**
     * Generate image url.
     *
     * @param imageId the UUID of the image
     * @return url of the image on the server
     */
    private String getImageUrl(String imageId) {
        return HOST + ":" + port + IMAGES_ENDPOINT + imageId;
    }
}
