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
    private void addUserPointcut() {}

    @Pointcut("execution(public * feddit.controllers.PostController.addComment*(..))")
    private void addCommentPointcut() {}

    @Pointcut("execution(public * feddit.controllers.PostController.deleteComment*(..))")
    private void deleteCommentPointcut() {}

    @Pointcut("execution(public * feddit.controllers.PostController.addPost*(..))")
    private void addPostPointcut() {}

    @Pointcut("execution(public * feddit.controllers.PostController.deletePost*(..))")
    private void deletePostPointcut() {}

    @After("addUserPointcut()")
    private void afterAddUser(JoinPoint joinPoint) {
        this.writeToFile("Create user calling " + joinPoint.getSignature().getName());
    }

    @After("addCommentPointcut()")
    private void afterAddComment(JoinPoint joinPoint) {
        this.writeToFile("Create comment calling " + joinPoint.getSignature().getName() +
                ", Post id: " + joinPoint.getArgs()[4] +
                ", Parent: " + joinPoint.getArgs()[2] + " with id " + joinPoint.getArgs()[3] +
                ", Content: " + joinPoint.getArgs()[1]);
    }

    @After("deleteCommentPointcut()")
    private void afterDeleteComment(JoinPoint joinPoint) {
        this.writeToFile("Delete comment calling " + joinPoint.getSignature().getName() +
                ", Id: " + joinPoint.getArgs()[1] +
                ", Post id: " + joinPoint.getArgs()[2]);
    }

    @After("addPostPointcut()")
    private void afterAddPost(JoinPoint joinPoint) {
        //TODO
    }

    @After("deleteCommentPointcut()")
    private void afterDeletePost(JoinPoint joinPoint) {
        //TODO
    }

    private void writeToFile(String string) {
        try {
            Files.writeString(path, string);
        } catch (IOException e) {
            System.out.println("Error: can't write to file!");
        }
    }

}
