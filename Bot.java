package Projekt;

import java.util.Random;

public class Bot {
    private int bankroll; // Банкролл бота
    private char lastChoice; // Последний выбор бота
    private static final Random RANDOM = new Random();

    // Конструктор бота
    public Bot(int initialBankroll) {
        this.bankroll = initialBankroll;
    }

    // Метод для получения банкролла
    public int getBankroll() {
        return bankroll;
    }

    // Метод для ставки бота
    public int placeBet() {
        return Math.min(5, bankroll); // Ставка 5 или оставшийся банкролл, если меньше
    }

    // Метод для выбора ставки бота
    public char chooseBet() {
        char[] bets = {'P', 'B', 'T'};
        lastChoice = bets[RANDOM.nextInt(bets.length)];
        return lastChoice;
    }

    // Метод для получения последнего выбора бота
    public char getLastChoice() {
        return lastChoice;
    }

    // Метод для обновления банкролла
    public void updateBankroll(int amount) {
        this.bankroll += amount;
    }
}
