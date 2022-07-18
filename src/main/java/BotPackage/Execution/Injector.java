package BotPackage.Execution;

import BotPackage.Main.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class Injector {
    private static AnnotationConfigApplicationContext context;

    private Injector() {

    }

    public static AnnotationConfigApplicationContext getContext() {
        context = Optional.ofNullable(context)
                .orElseGet(() -> new AnnotationConfigApplicationContext(SpringConfig.class));

        return context;
    }
}
