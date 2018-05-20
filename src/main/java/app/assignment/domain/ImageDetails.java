package app.assignment.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * BO for ingest enpdoint.
 */
public class ImageDetails implements Serializable {
    private final String imageId;
    private String text;

    public ImageDetails(String imageId, String text) {
        this.imageId = imageId;
        this.text = text;
    }

    public String getImageId() {
        return imageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageDetails that = (ImageDetails) o;
        return Objects.equals(imageId, that.imageId) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId, text);
    }

    @Override
    public String toString() {
        return "ImageDetails{" +
                "imageId='" + imageId + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
