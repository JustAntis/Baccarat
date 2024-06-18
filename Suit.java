package Projekt;

    public class Suit {
    private final Card ace; // Туз
    private final Card two; // Двойка
    private final Card three; // Тройка
    private final Card four; // Четверка
    private final Card five; // Пятерка
    private final Card six; // Шестерка
    private final Card seven; // Семерка
    private final Card eight; // Восьмерка
    private final Card nine; // Девятка
    private final Card ten; // Десятка
    private final Card jack; // Валет
    private final Card queen; // Дама
    private final Card king; // Король
    private final Card[] cards; // Массив карт одной масти

    // Конструктор масти
    protected Suit(String suitName) {
        this.ace = new Card("A", 1, "JEDEN", suitName);
        this.two = new Card("2", 2, "DWA", suitName);
        this.three = new Card("3", 3, "TRZY", suitName);
        this.four = new Card("4", 4, "CZTERY", suitName);
        this.five = new Card("5", 5, "PIĘĆ", suitName);
        this.six = new Card("6", 6, "SZEŚĆ", suitName);
        this.seven = new Card("7", 7, "SIEDEM", suitName);
        this.eight = new Card("8", 8, "OSIEM", suitName);
        this.nine = new Card("9", 9, "DZIEWIĘĆ", suitName);
        this.ten = new Card("10", 10, "DZIESIĘĆ", suitName);
        this.jack = new Card("J", 10, "WALET", suitName);
        this.queen = new Card("Q", 10, "DAMA", suitName);
        this.king = new Card("K", 10, "KRÓL", suitName);
        this.cards = new Card[]{ace, two, three, four, five, six, seven, eight, nine, ten, jack, queen, king};
    }

    // Метод для получения карты по индексу
    protected Card getCard(int index) {
        return cards[index];
    }
}
