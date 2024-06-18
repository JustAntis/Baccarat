package Projekt;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Deck d1; // Колода карт
    private int bankroll; // Сумма денег игрока
    private List<Bot> bots; // Список ботов
    private GameUI gameUI; // Пользовательский интерфейс игры

    // Конструктор контроллера игры
    public GameController() {
        d1 = new Deck(); // Создание новой колоды карт
        bankroll = getInitialBankroll(); // Получение начального банкролла игрока
        bots = new ArrayList<>(); // Инициализация списка ботов
        int numberOfBots = getNumberOfBots(); // Получение количества ботов от пользователя
        for (int i = 0; i < numberOfBots; i++) {
            bots.add(new Bot(1000)); // Добавление ботов с начальным банкроллом 1000
        }
        gameUI = new GameUI(this, bankroll, bots); // Создание пользовательского интерфейса
    }

    // Метод для начала игры
    public void startGame() {
        gameUI.show(); // Отображение пользовательского интерфейса
    }

    // Метод для получения начального банкролла игрока
    private int getInitialBankroll() {
        while (true) {
            String input = JOptionPane.showInputDialog("Wprowadź środki:"); // Запрос ввода суммы денег от пользователя
            try {
                int initialBankroll = Integer.parseInt(input); // Преобразование введенного значения в целое число
                if (initialBankroll > 0) {
                    return initialBankroll; // Возврат суммы, если она положительная
                } else {
                    JOptionPane.showMessageDialog(null, "Proszę wprowadzić dodatnią kwotę."); // Сообщение об ошибке, если сумма не положительная
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Nieprawidłowy format. Proszę wprowadzić prawidłową liczbę."); // Обработка исключения, если введено не число
            }
        }
    }

    // Метод для получения количества ботов
    private int getNumberOfBots() {
        while (true) {
            String input = JOptionPane.showInputDialog("Wprowadź liczbę botów (maks 5):"); // Запрос ввода количества ботов от пользователя
            try {
                int numberOfBots = Integer.parseInt(input); // Преобразование введенного значения в целое число
                if (numberOfBots >= 0 && numberOfBots <= 5) {
                    return numberOfBots; // Возврат количества ботов, если оно в допустимом диапазоне
                } else {
                    JOptionPane.showMessageDialog(null, "Proszę wprowadzić liczbę od 0 do 5."); // Сообщение об ошибке, если количество ботов недопустимо
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Nieprawidłowy format. Proszę wprowadzić prawidłową liczbę."); // Обработка исключения, если введено не число
            }
        }
    }

    // Метод для игры одного раунда
    public void playRound(char userBetChoice, int bet) {
        // Проверка, что ставка больше нуля
        if (bet <= 0) {
            gameUI.showResult("Zakład musi być większy od zera.");
            return;
        }
        // Проверка, что ставка не превышает банкролл
        if (bet > bankroll) {
            gameUI.showResult("Niewystarczające środki. Zmniejsz zakład.");
            return;
        }

        bankroll -= bet; // Уменьшение банкролла на сумму ставки
        char result = baccarat(d1); // Получение результата игры

        // Проверка, совпадает ли результат с выбором пользователя
        if (result == userBetChoice) {
            bankroll += payout(bet, result); // Увеличение банкролла в случае выигрыша
            gameUI.showResult("Wygrałeś!");
        } else if (result == 'T' && userBetChoice != 'T') {
            bankroll += bet; // Возврат ставки в случае ничьи, если пользователь не выбрал ничью
            gameUI.showResult("Wygраłeś!");
        } else {
            gameUI.showResult("Przegrałeś!"); // Сообщение о проигрыше
        }
        gameUI.updateGameLog("Zakład gracza: " + userBetChoice + ", Wynik: " + result); // Обновление игрового лога

        // Обработка ставок ботов
        for (int i = 0; i < bots.size(); i++) {
            Bot bot = bots.get(i);
            int botBet = bot.placeBet(); // Получение ставки бота
            char botChoice = bot.chooseBet(); // Получение выбора бота
            if (result == botChoice) {
                bot.updateBankroll((int) payout(botBet, result)); // Обновление банкролла бота в случае выигрыша
            } else if (result == 'T' && botChoice != 'T') {
                bot.updateBankroll(botBet); // Возврат ставки в случае ничьи, если бот не выбрал ничью
            }
            gameUI.updateGameLog("Bot " + (i + 1) + " zakład: " + botChoice + ", Wynik: " + result); // Обновление игрового лога для ботов
            gameUI.updateBotBankrollLabel(i, bot.getBankroll()); // Обновление лога банкролла бота

            // Проверка банкролла бота и вывод сообщения, если он проиграл все средства
            if (bot.getBankroll() <= 0) {
                JOptionPane.showMessageDialog(gameUI.getFrame(), "Bot " + (i + 1) + " stracił wszystkie środki. Bot przegrał!");
            }
        }

        gameUI.updateBankrollLabel(bankroll); // Обновление лога банкролла пользователя
        gameUI.updateResultsLog(userBetChoice, result, bots); // Обновление лога результатов

        // Проверка банкролла пользователя и завершение игры, если он проиграл все средства
        if (bankroll <= 0) {
            JOptionPane.showMessageDialog(gameUI.getFrame(), "Straciłeś wszystkie środki. Koniec gry.");
            System.exit(0);
        }
    }

    // Метод для симуляции игры
    private char baccarat(Deck ofCards) {
        StringBuilder combinationLog = new StringBuilder();
        char result = ' ';
        int banker = 0;
        int player = 0;

        // Вытягивание двух карт игроком
        combinationLog.append("Gracz wylosował: ");
        for (int i = 0; i < 2; i++) {
            String drawnCard = ofCards.drawCard();
            player = (addCard(player, ofCards.cards[(ofCards.count) - 1])) % 10;
            combinationLog.append(drawnCard).append(" ");
        }
        // Вытягивание двух карт банкиром
        combinationLog.append("\nBankier wylosował: ");
        for (int i = 0; i < 2; i++) {
            String drawnCard = ofCards.drawCard();
            banker = (addCard(banker, ofCards.cards[(ofCards.count) - 1])) % 10;
            combinationLog.append(drawnCard).append(" ");
        }

        // Вытягивание карт ботами
        for (int j = 0; j < bots.size(); j++) {
            Bot bot = bots.get(j);
            combinationLog.append("\nBot ").append(j + 1).append(" wylosował: ");
            for (int i = 0; i < 2; i++) {
                String drawnCard = ofCards.drawCard();
                combinationLog.append(drawnCard).append(" ");
            }
        }

        // Определение результата игры
        if (banker > player) {
            result = 'B';
        }
        if (banker < player) {
            result = 'P';
        }
        if (banker == player) {
            result = 'T';
        }

        gameUI.updateCardCombinationLog(combinationLog.toString()); // Обновление лога комбинаций карт
        return result; // Возврат результата игры
    }

    // Метод для расчета выплаты
    private double payout(int bet, char result) {
        double payout = 0.0;
        switch (result) {
            case 'P':
                payout = bet * 2;
                break;
            case 'B':
                payout = bet * 1.95;
                break;
            case 'T':
                payout = bet * 8;
                break;
        }
        return payout; // Возврат рассчитанной выплаты
    }

    // Методы для сложения значений карт
    public static int addCard(Card x, Card y) {
        int sum = x.getVal(x) + y.getVal(y);
        return sum;
    }

    public static int addCard(int x, Card y) {
        int sum = x + y.getVal(y);
        return sum;
    }

    // Метод для получения значения карты
    public static int cardVal(Card x) {
        int y = x.getVal(x);
        return y;
    }
}
