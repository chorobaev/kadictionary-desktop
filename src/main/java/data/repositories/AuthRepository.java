package data.repositories;

import base.BaseRepository;
import data.model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthRepository extends BaseRepository {
    private static AuthRepository instance;

    public static AuthRepository getInstance() {
        if (instance == null) {
            instance = new AuthRepository();
        }
        return instance;
    }

    private AuthRepository() {
    }

    public User singIn(String email, String password) throws Exception {
        User user;
        try {
            openConnection();
            @SuppressWarnings("SqlResolve")
            String query = "CALL getUserByEmailAndPassword('" + email + "', '" + password + "');";
            System.out.println("Login query: " + query);
            resultSet = statement.executeQuery(query);
            user = parseUser();
        } finally {
            close();
        }

        return user;
    }

    private User parseUser() throws SQLException {
        User user = null;
        if (resultSet != null && resultSet.next()) {
            user = new User(
                resultSet.getInt("userID"),
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getString("email")
            );
        }
        System.out.println("User: " + user);
        return user;
    }

    public User signUp(String name, String surname, String email, String password) throws Exception {
        User user = null;
        openConnection();
        try {
            @SuppressWarnings("SqlResolve")
            PreparedStatement statement = connect.prepareStatement("CALL createUser(?, ?, ?, ?);");
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, name);
            statement.setString(4, surname);

            statement.execute();
            user = singIn(email, password);
        } finally {
            close();
        }
        return user;
    }
}
