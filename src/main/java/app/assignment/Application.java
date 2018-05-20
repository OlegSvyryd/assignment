package app.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.UnknownHostException;

@SpringBootApplication
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger( Application.class );

    // Number of port on which appliaction is running
    public static int port;

    /**
     * Main entry point.
     *
     * @param args parameters
     * @throws UnknownHostException on host getting error
     */
    public static void main(String[] args) throws UnknownHostException
    {
        // Run spring app
        ConfigurableApplicationContext run = SpringApplication.run( Application.class, args );
        Environment env = run.getEnvironment();

        // Get port on which app is running
        port = env.getProperty( "local.server.port", Integer.class );

        // Logging info of url of application
        LOG.info( "\n----------------------------------------------------------\n\t" +
                        "app.assignment.Application '{}' is running! Access URL:\n\t" +
                        "\thttp://localhost:{}\n\t" +
                        "----------------------------------------------------------",
                env.getProperty( "spring.application.name" ),
                port);
    }

}
