package Projekt;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Deck {
    protected final Card[] cards; // Массив карт
    private final Suit[] suits; // Массив мастей
    private final Suit diamonds; // Масть бубны
    private final Suit clubs; // Масть трефы
    private final Suit hearts; // Масть червы
    private final Suit spades; // Масть пики
    protected int count = 0; // Количество оставшихся карт в колоде

    // Конструктор колоды
    protected Deck() {
        this.cards = new Card[52];
        this.diamonds = new Suit("DZWONEK");
        this.clubs = new Suit("ŻOŁĄDŹ");
        this.hearts = new Suit("RÓŻA");
        this.spades = new Suit("TARCZA");
        this.suits = new Suit[]{diamonds, clubs, hearts, spades};

        int v = 0;
        for (int i = 0; i < 4; i++) {
            for (int x = 0; x < 13; x++) {
                cards[v] = suits[i].getCard(x);
                v++;

            }
        }
        shuffle();
    }

    // Метод для вытягивания карты из колоды
    protected String drawCard() {
        if (count >= cards.length) {
            shuffle(); // Перетасовка, если карты закончились
        }
        Card drawnCard = cards[count];
        drawnCard.showCard(drawnCard);
        this.count += 1;
        return drawnCard.toString();
    }

    // Метод для перетасовки колоды
    public void shuffle() {
        System.out.println("MIESZANIE...");
        Random chance = new Random();
        count = 0;
        Card temp;
        for (int i = 0; i < 52; i++) {
            int index = chance.nextInt(52); // Поправленный диапазон индексов
            temp = cards[i];
            cards[i] = cards[index];
            cards[index] = temp;
        }
    }
}
