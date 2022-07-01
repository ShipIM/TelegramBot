package BotPackage.Models;

import BotPackage.Execution.Injector;

public abstract class Entity {
    private String name;
    private String description;

    public Entity(String name, String description) {
        this.name = name;
        this.description = description;

        Injector.getInjector().inject(this);
    }

    public Entity() {

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.getName() + ": " + this.getDescription() + "\n";
    }
}
