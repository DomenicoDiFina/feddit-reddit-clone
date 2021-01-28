package feddit.logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class LoggerConfiguration {

    @Bean
    public Logger beanLogger() {
        return new Logger();
    }

}
