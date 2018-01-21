import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"controller"})
public class ClientApplication {

    public static void main(String[] args){

        SpringApplication.run(ClientApplication.class, args);

    }
}
