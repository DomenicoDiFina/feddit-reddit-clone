package feddit.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Logger {

    @Pointcut("execution(public * feddit.controllers.UserController.processSignUp*(..))")
    private void createUserPointcut() {}

    @After("createUserPointcut()")
    private void afterCreateUser(JoinPoint joinPoint) {
        System.out.println("Create user calling " + joinPoint.getSignature().getName() + " " + joinPoint.getArgs()[0]);
    }

}
