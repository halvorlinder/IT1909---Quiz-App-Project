// CHECKSTYLE:OFF
package rest;


import io.SavePaths;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class QuizServerApplication {

    /**
     * Starts the server
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args[0].equals("test"))
            SavePaths.enableTestMode();
        SpringApplication.run(QuizServerApplication.class, args);
    }
}
