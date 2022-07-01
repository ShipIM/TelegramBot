package BotPackage.Models;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents all specified classes with unique purposes.
 */
public abstract class Command extends Entity {
    private List<Argument> arguments;

    /**
     * Constructor, gets all necessary thins.
     *
     * @param name        command name
     * @param description command description
     */
    public Command(String name, ArrayList<Argument> arguments, String description) {
        super(name, description);
        this.arguments = arguments;
    }

    public Command() {

    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public abstract BotApiMethod<? extends Serializable> doOption(Update update);

    /**
     * Represents all kinds of arguments.
     */
    public enum Argument {
        NUMBER("number"),
        STRING("string"),
        NONE("none"),
        OBJECT("object"),
        USER("user");

        private final String name;

        Argument(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Represents additional data for transfer.
     */
    public static class CommandData {
        private final String name;
        private List<Argument> args;
        private String description;
        private List<String> userArgs;

        public CommandData(String name, List<Argument> args, String description, List<String> userArgs) {
            this.name = name;
            this.args = args;
            this.description = description;
            this.userArgs = userArgs;
        }

        public CommandData(String name, List<Argument> args, String description) {
            this.name = name;
            this.args = args;
            this.description = description;
        }

        public CommandData(String name, List<String> userArgs) {
            this.name = name;
            this.userArgs = userArgs;
        }

        public String getName() {
            return name;
        }

        public List<Argument> getArgs() {
            return args;
        }

        public String getDescription() {
            return description;
        }

        public List<String> getUserArgs() {
            return userArgs;
        }

        @Override
        public String toString() {
            return this.getName() + ": " + this.getDescription();
        }
    }
}
