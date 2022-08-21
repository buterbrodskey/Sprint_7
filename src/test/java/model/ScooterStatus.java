package model;

public enum ScooterStatus {

    CREATE_COURIER_WITHOUT_REQUIRED_FIELD(400, "Недостаточно данных для создания учетной записи"),
    CREATE_COURIER_WITH_DUPLICATE_LOGIN(409, "Этот логин уже используется"),
    DELETE_COURIER_WITHOUT_ID(400, "Недостаточно данных для удаления курьера"),
    DELETE_COURIER_WITH_NON_EXIST_ID(404, "Курьера с таким id нет"),
    GET_ORDER_COUNT_BY_COURIER_ID_WITHOUT_COURIER_ID(400, "Недостаточно данных для поиска"),
    GET_ORDER_COUNT_BY_COURIER_ID_WITH_NON_EXIST_COURIER_ID(404, "Курьер не найден"),
    LOGIN_COURIER_WITHOUT_REQUIRED_FIELD(400, "Недостаточно данных для входа"),
    LOGIN_COURIER_WITH_NON_EXIST_FIELD(404, "Учетная запись не найдена");

    private int httpCode;
    private String message;

    ScooterStatus(int httpCode, String message) {
        this.httpCode = httpCode;
        this.message = message;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getMessage() {
        return message;
    }
}
