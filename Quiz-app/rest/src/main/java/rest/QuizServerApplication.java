// CHECKSTYLE:OFF
package rest;


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
        SpringApplication.run(QuizServerApplication.class, args);
    }
}
