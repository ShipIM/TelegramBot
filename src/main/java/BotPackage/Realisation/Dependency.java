package BotPackage.Realisation;

public class Dependency<T> {
    private final T object;

    public Dependency(T object) {
        this.object = object;
    }

    public T inject(Class<?> clazz) {
        return object.getClass().equals(clazz) ? object : null;
    }
}
