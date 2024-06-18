package Projekt;

public class Card {
    private final String symbol; // Символ карты
    private final int value; // Значение карты
    private final String name; // Имя карты
    private final String suit; // Масть карты

    // Конструктор карты
    protected Card(String symbol, int value, String name, String suit) {
        this.symbol = symbol;
        this.value = value;
        this.name = name;
        this.suit = suit;
    }

    // Метод для отображения карты
    protected void showCard(Card t) {
        System.out.println(" WYPADŁA " + name + " Z " + suit);
    }

    // Метод для получения значения карты
    protected int getVal(Card x) {
        return x.value;
    }

    @Override
    public String toString() {
        return name + " z " + suit;
    }
}
