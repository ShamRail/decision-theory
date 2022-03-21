package decision.theory;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class DecisionTheoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DecisionTheoryApplication.class, args);
    }

    @PostConstruct
    public void setDefaultLocale() {
        Locale.setDefault(Locale.ENGLISH);
    }
    
}
