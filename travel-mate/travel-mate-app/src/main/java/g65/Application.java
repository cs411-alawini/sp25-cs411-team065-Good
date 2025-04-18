package g65;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"g65"})
@Configurable
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }
}
