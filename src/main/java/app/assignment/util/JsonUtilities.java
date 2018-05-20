package app.assignment.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class JsonUtilities {

    private static final ObjectMapper JSON_MARSHALLER = new ObjectMapper();
    private static final String UTF_8 = "UTF-8";
    private static final String JSON_FORMAT = ".json";

    public static <T> List<T> unmarshallListFromJson(String name, Class<T> returnType) throws IOException {
        // Create a collection type
        JavaType type = JSON_MARSHALLER.getTypeFactory().constructCollectionType(
                List.class,
                returnType
        );

        // Get the JSON into our format
        List<T> groups = JSON_MARSHALLER.readValue(
                getResourceFileContents( name + JSON_FORMAT ),
                type
        );

        return groups;
    }

    /**
     * Read JSON file content.
     *
     * @param fileName the name of the file
     * @return file payload (content)
     * @throws IOException exception while working with file
     */
    private static String getResourceFileContents(String fileName) throws IOException {
        BufferedReader reader = null;

        try {
            // Convert to string from the input stream
            reader = new BufferedReader(
                    new InputStreamReader(
                            JsonUtilities.class.getClassLoader().getResourceAsStream( fileName ),
                            UTF_8
                    )
            );

            // Read lines from file
            return reader.lines().collect( Collectors.joining( "\n" ) );
        }
        finally {
            // Dispose resources
            if ( reader != null ) {
                reader.close();
            }
        }
    }

}
