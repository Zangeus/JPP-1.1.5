package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main extends Util {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Bradley", "Brooks", (byte) 20);
        userService.saveUser("Timothy", "Davis", (byte) 25);
        userService.saveUser("Gregory", "Barton", (byte) 30);
        userService.saveUser("Albert", "Smith", (byte) 35);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
