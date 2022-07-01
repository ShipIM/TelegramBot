package BotPackage.Execution;

import BotPackage.Annotations.Injection;
import BotPackage.Realisation.Dependency;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class Injector {
    private final Map<String, Dependency<?>> injections;

    private Injector(Map<String, Dependency<?>> injections) {
        this.injections = injections;
    }

    private static Injector injector;

    public static Injector getInjector() {
        Optional<Injector> optional = Optional.ofNullable(injector);

        return optional.orElseGet(() -> injector = new Injector(new HashMap<>()));
    }

    public <T> void addInjection(String name, Dependency<T> dependency) {
        this.injections.put(name, dependency);
    }

    public void eraseInjection(String name) {
        this.injections.remove(name);
    }

    public <T> void inject(T object) {
        Class<?> type = object.getClass();
        List<Field> toInject = new ArrayList<>();

        do {
            toInject.addAll(Arrays.stream(type.getDeclaredFields()).filter(field ->
                    field.isAnnotationPresent(Injection.class)).collect(Collectors.toList()));
            type = type.getSuperclass();
        } while (type != null);

        toInject.forEach(field -> {
            try {
                field.setAccessible(true);
                field.set(object, injections.get(field.getName()).inject(field.getType()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
