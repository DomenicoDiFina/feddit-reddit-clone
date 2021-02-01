package feddit.logger;

import feddit.model.Post;
import feddit.model.User;
import feddit.model.Vote;
import feddit.security.FedditUserDetails;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Aspect
public class Logger {

    private static final Path path = Path.of("src/main/resources/log.txt");
    private final Set<Long> times = new HashSet<>();

    public Logger() {
    }

    @Pointcut("execution(public * feddit.services.AuthService.loadUserByUsername*(..))")
    private void loginPointcut() {}

    @Pointcut("execution(public * feddit.controllers.UserController.processSignUp*(..))")
    private void addUserPointcut() {}

    @Pointcut("execution(public * feddit.controllers.PostController.addPost*(..))")
    private void addPostPointcut() {}

    @Pointcut("execution(public * feddit.controllers.PostController.deletePost*(..))")
    private void deletePostPointcut() {}

    @Pointcut("execution(public * feddit.controllers.PostController.addComment*(..))")
    private void addCommentPointcut() {}

    @Pointcut("execution(public * feddit.controllers.PostController.deleteComment*(..))")
    private void deleteCommentPointcut() {}

    @Pointcut("execution(public * feddit.controllers.VoteController.processPostVote*(..))")
    private void addOrDeletePostVotePointcut() {}

    @Pointcut("execution(public * feddit.controllers.VoteController.processCommentVote*(..))")
    private void addOrDeleteCommentVotePointcut() {}

    @Pointcut("execution(public * org.springframework.data.repository.CrudRepository.save*(..))")
    private void databasePointcut() {}

    @Before("loginPointcut()")
    private void beforeLogin(JoinPoint joinPoint) {
        this.writeToFile("User with username '" + joinPoint.getArgs()[0] + "' is trying to login");
    }

    @AfterReturning("loginPointcut()")
    private void afterLogin(JoinPoint joinPoint) {
        this.writeToFile("User with username '" + joinPoint.getArgs()[0] + "' is logged in");
    }

    @AfterThrowing("loginPointcut()")
    private void afterLoginError(JoinPoint joinPoint) {
        this.writeToFile("User with username '" + joinPoint.getArgs()[0] + "' doesnt't exist");
    }

    @After("addUserPointcut()")
    private void afterAddUser(JoinPoint joinPoint) {
        this.writeToFile("User with username '" + ((User) joinPoint.getArgs()[0]).getUsername() + "' joined Feddit");
    }

    @After("addPostPointcut()")
    private void afterAddPost(JoinPoint joinPoint) {
        this.writeToFile("User with username '" + ((FedditUserDetails) joinPoint.getArgs()[0]).getUsername() +
                "' creates post with title '" + ((Post) joinPoint.getArgs()[3]).getTitle() + "'");
    }

    @After("deletePostPointcut()")
    private void afterDeletePost(JoinPoint joinPoint) {
        this.writeToFile("Delete post with id " + joinPoint.getArgs()[2] +
                ". Removed by admin");
    }

    @After("addCommentPointcut()")
    private void afterAddComment(JoinPoint joinPoint) {
        this.writeToFile("User with username '" + ((FedditUserDetails) joinPoint.getArgs()[5]).getUsername() +
                "' creates comment with content '" + joinPoint.getArgs()[1] +
                "' related to " + joinPoint.getArgs()[2].toString().toLowerCase() +
                " with id " + joinPoint.getArgs()[3] +
                " in post with id " + joinPoint.getArgs()[4]);
    }

    @After("deleteCommentPointcut()")
    private void afterDeleteComment(JoinPoint joinPoint) {
        this.writeToFile("Delete comment with id " + joinPoint.getArgs()[1] +
                " and all related comments, if any, in Post with id " + joinPoint.getArgs()[2] +
                ". Removed by admin or user who created the comment");
    }

    @After("addOrDeletePostVotePointcut()")
    private void afterPostVote(JoinPoint joinPoint) {
        this.writeToFile("User with username '" + ((FedditUserDetails) joinPoint.getArgs()[0]).getUsername() +
                "' " + ((Vote) joinPoint.getArgs()[6]).getType().toLowerCase() +
                " post with id " + joinPoint.getArgs()[2]);
    }

    @After("addOrDeleteCommentVotePointcut()")
    private void afterCommentVote(JoinPoint joinPoint) {
        this.writeToFile("User with username '" + ((FedditUserDetails) joinPoint.getArgs()[0]).getUsername() +
                "' " + ((Vote) joinPoint.getArgs()[4]).getType().toLowerCase() +
                " comment with id " + joinPoint.getArgs()[2] +
                " related to post with id " + joinPoint.getArgs()[3]);
    }

    @Around("databasePointcut()")
    private void saveExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        proceedingJoinPoint.proceed();
        this.times.add(System.currentTimeMillis() - startTime);
        printTimeReport();
    }

    private void printTimeReport() {
        this.writeToFile("Average milliseconds required for database operations: " +
                (int)this.times.stream().mapToInt(x -> x.intValue()).average().getAsDouble());
    }

    private void writeToFile(String string) {
        try {
            Files.writeString(Logger.path, new Date() + "\t" + string + "\n",
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            new IOException("Error: can't write to " + this.path);
        }
    }

}
