package data.model;

public class User {
    private final int userId;
    private final String name;
    private final String surname;
    private final String email;

    public User(int userId, String name, String surname, String email) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    @Override public String toString() {
        return "User{" +
            "userId=" + userId +
            ", name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
