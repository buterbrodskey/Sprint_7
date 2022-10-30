package model;

public enum OrderColor {
    BLACK("BLACK"),
    GREY("GREY");

    private String color;

    OrderColor() {
    }

    OrderColor(String color) {
        this.color = color;
    }
}
