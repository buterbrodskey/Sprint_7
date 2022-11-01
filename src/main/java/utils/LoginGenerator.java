package utils;

import model.Courier;
import model.Login;

public class LoginGenerator {
    public static Login from(Courier courier) {
        return new Login(courier.getLogin(), courier.getPassword());
    }
}
