package BotPackage.Realisation;

public class User {
    private final String name;
    private final int group;
    private final long id;
    private final Status status;

    public User(String name, int group, long id, Status status) {
        this.name = name;
        this.group = group;
        this.id = id;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getGroup() {
        return group;
    }

    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        ADMIN("ADMIN"),
        USER("USER"),
        UNVERIFIED("UNVERIFIED");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
