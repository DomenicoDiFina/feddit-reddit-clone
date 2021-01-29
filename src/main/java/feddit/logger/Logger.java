package feddit.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Aspect
public class Logger {

    private final Path path = Path.of("src/main/resources/log.txt");

    public Logger() {}

    @Pointcut("execution(public * feddit.controllers.UserController.processSignUp*(..))")
    private void createUserPointcut() {}

    @After("createUserPointcut()")
    private void afterCreateUser(JoinPoint joinPoint) {
        this.writeToFile("Create user calling " + joinPoint.getSignature().getName());
    }

    private void writeToFile(String string) {
        try {
            Files.writeString(path, string);
        } catch (IOException e) {
            System.out.println("Error: can't write to file!");
        }
    }

}
