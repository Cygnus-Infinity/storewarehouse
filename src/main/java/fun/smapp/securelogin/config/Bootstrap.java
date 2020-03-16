package fun.smapp.securelogin.config;

import fun.smapp.securelogin.model.auth.SystemUser;
import fun.smapp.securelogin.repository.auth.UserRepository;
import fun.smapp.securelogin.service.utility.UserUtilityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class Bootstrap implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
       System.out.println("Store Warehouse Application Started.");
    }
}
