package com.example.jeremiah_boothe_final.model.user;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jeremiah_boothe_final.model.AppDatabase;

public class UserRepository {
    private final UserDao userDao;
    private final MutableLiveData<User> loggedInUser = new MutableLiveData<>();
    /**
     * Constructor for UserRepository
     * @param application Application context
     */
    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
    }
    /**
     * Method to get the logged-in user
     * @return LiveData of the logged-in user
     */
    public LiveData<User> getLoggedInUser() {
        return loggedInUser;
    }
    /**
     * Method to login a user
     * @param username Username of the user to be logged in
     * @param password Password of the user to be logged in
     * @param callback LoginCallback instance
     */
    public void login(String username, String password, LoginCallback callback) {
        new LoginAsyncTask(userDao, callback).execute(username, password);
    }
    /**
     * Method to register a user
     * @param user User object to be registered
     */
    public void register(User user) {
        new RegisterAsyncTask(userDao).execute(user);
    }

    private static class LoginAsyncTask extends AsyncTask<String, Void, User> {
        private final UserDao userDao;
        private final LoginCallback callback;
        /**
         * Constructor for LoginAsyncTask
         * @param userDao User DAO instance
         * @param callback LoginCallback instance
         */
        LoginAsyncTask(UserDao userDao, LoginCallback callback) {
            this.userDao = userDao;
            this.callback = callback;
        }
        /**
         * Method to perform the login operation
         * @param params The parameters of the task.
         * @deprecated doInBackground is deprecated.
         * @return User object if successful, null otherwise
         */
        @Override
        protected User doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            return userDao.login(username, password);
        }
        /**
         * Method to be called when the login operation is complete.
         * @param user The result of the operation computed by {@link #doInBackground}.
         * @deprecated onPostExecute is deprecated.
         */
        @Override
        protected void onPostExecute(User user) {
            callback.onLoginResult(user);
        }
    }

    private static class RegisterAsyncTask extends AsyncTask<User, Void, Void> {
        private final UserDao userDao;
        /**
         * Constructor for RegisterAsyncTask
         * @param userDao User DAO instance
         */
        RegisterAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }
        /**
         * Method to perform the registration operation
         * @param users The parameters of the task.
         * @deprecated doInBackground is deprecated.
         * @return Void object
         */
        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }
    /**
     * Interface for login callbacks
     */
    public interface LoginCallback {
        void onLoginResult(User user);
    }
}