package data.repositories;

import base.BaseRepository;

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
}
