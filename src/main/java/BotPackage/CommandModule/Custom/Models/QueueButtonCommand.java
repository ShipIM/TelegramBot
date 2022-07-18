package BotPackage.CommandModule.Custom.Models;

import BotPackage.CommandModule.Models.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class QueueButtonCommand extends Command {
    public QueueButtonCommand(String name, ArrayList<Argument> arguments, String description) {
        super(name, arguments, description);
    }

    public QueueButtonCommand() {

    }

    public List<String> parseQueueFrom(String message) {
        String[] contents = message.split("\n");

        return IntStream.range(1, contents.length - 1).mapToObj(i -> contents[i].split(".  ")[1])
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public String parseQueueTo(List<String> queue, String message) {
        String[] contents = message.split("\n");

        StringBuilder result = new StringBuilder(contents[0] + "\n");
        for (int i = 0; i < queue.size(); i++) {
            result.append(i).append(".  ").append(queue.get(i)).append("\n");
        }
        result.append(contents[contents.length - 1]);

        return result.toString();
    }
}
