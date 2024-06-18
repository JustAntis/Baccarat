package Projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameUI {
    private JFrame frame; // Окно приложения
    private JTextField betField; // Поле для ввода ставки
    private JLabel bankrollLabel; // Метка для отображения банкролла пользователя
    private List<JLabel> botBankrollLabels; // Список меток для отображения банкроллов ботов
    private JLabel resultLabel; // Метка для отображения результата игры останнього раунда
    private JTextArea gameLog; // Область для отображения лога игры
    private JTextArea cardCombinationLog; // Область для отображения комбинаций карт
    private JTextArea resultsLog; // Область для отображения результатов
    private GameController gameController; // Контроллер игры

    // Конструктор пользовательского интерфейса
    public GameUI(GameController gameController, int bankroll, List<Bot> bots) {
        this.gameController = gameController; // Инициализация контроллера игры
        this.botBankrollLabels = new java.util.ArrayList<>(bots.size()); // Инициализация списка меток для ботов
        initializeGUI(bankroll, bots); // Инициализация GUI
    }

    // Метод для получения основного окна
    public JFrame getFrame() {
        return frame;
    }

    // Метод для отображения окна
    public void show() {
        frame.setVisible(true); // Установить видимость окна
    }

    // Метод для инициализации графического интерфейса
    private void initializeGUI(int bankroll, List<Bot> bots) {
        frame = new JFrame("Baccarat"); // Создание основного окна с заголовком "Baccarat"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Установить действие при закрытии окна
        frame.setSize(800, 800); // Увеличенная высота окна
        frame.setResizable(false); // Запрет на изменение размера окна


        ImageIcon backgroundIcon = new ImageIcon("C:\\Users\\Рома\\IdeaProjects\\untitled2\\src\\Projekt\\image\\fon.png");

        // Создание панели с фоновым изображением
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this); // Отрисовка изображения фона
            }
        };
        panel.setLayout(null); // Установка абсолютного позиционирования для элементов на панели
        frame.setContentPane(panel); // Установка панели как содержимого окна

        bankrollLabel = new JLabel("Bankroll: $" + bankroll); // Создание метки для отображения банкролла пользователя
        bankrollLabel.setForeground(Color.WHITE); // Установка белого цвета текста
        bankrollLabel.setBounds(10, 10, 200, 30); // Установка позиции и размера метки
        panel.add(bankrollLabel); // Добавление метки на панель

        // Добавление меток для каждого бота
        int botLabelY = 50; // Начальная позиция по Y для меток ботов
        for (Bot bot : bots) {
            JLabel botLabel = new JLabel("Bankroll bota: $" + bot.getBankroll()); // Создание метки для банкролла бота
            botLabel.setForeground(Color.WHITE); // Установка белого цвета текста
            botLabel.setBounds(10, botLabelY, 200, 30); // Установка позиции и размера метки
            botBankrollLabels.add(botLabel); // Добавление метки в список меток ботов
            panel.add(botLabel); // Добавление метки на панель
            botLabelY += 30; // Смещение позиции по Y для следующей метки
        }

        resultLabel = new JLabel(""); // Создание метки для отображения результата игры
        resultLabel.setForeground(Color.WHITE); // Установка белого цвета текста
        resultLabel.setBounds(10, botLabelY + 30, 200, 30); // Установка позиции и размера метки
        panel.add(resultLabel); // Добавление метки на панель

        JLabel betLabel = new JLabel("Wprowadź zakład: "); // Создание метки для ввода ставки
        betLabel.setForeground(Color.WHITE);
        betLabel.setBounds(320, 10, 100, 30);
        panel.add(betLabel);

        betField = new JTextField(10); // Создание текстового поля для ввода ставки
        betField.setBounds(420, 10, 100, 30);
        panel.add(betField); // Добавление текстового поля на панель

        JButton playerButton = new JButton("Zakład na gracza"); // Создание кнопки для ставки на игрока
        playerButton.setBounds(320, 50, 200, 30);
        panel.add(playerButton);

        JButton bankerButton = new JButton("Zakład na bankiera"); // Создание кнопки для ставки на банкира
        bankerButton.setBounds(320, 90, 200, 30);
        panel.add(bankerButton);

        JButton tieButton = new JButton("Zakład na remis"); // Создание кнопки для ставки на ничью
        tieButton.setBounds(320, 130, 200, 30);
        panel.add(tieButton);

        // Добавление обработчиков событий для кнопок
        playerButton.addActionListener(new BetActionListener('P'));
        bankerButton.addActionListener(new BetActionListener('B'));
        tieButton.addActionListener(new BetActionListener('T'));

        gameLog = new JTextArea(); // Создание области для отображения лога игры
        gameLog.setForeground(Color.WHITE);
        gameLog.setBackground(Color.BLACK);
        gameLog.setEditable(false); // Запрет на редактирование
        JScrollPane scrollPane = new JScrollPane(gameLog); // Создание прокручиваемой панели для лога игры
        scrollPane.setBounds(10, 450, 760, 100); // Установка позиции и размера прокручиваемой панели
        panel.add(scrollPane); // Добавление прокручиваемой панели на панель

        cardCombinationLog = new JTextArea(); // Создание области для отображения комбинаций карт
        cardCombinationLog.setForeground(Color.WHITE);
        cardCombinationLog.setBackground(Color.BLACK);
        cardCombinationLog.setEditable(false);
        JScrollPane combinationScrollPane = new JScrollPane(cardCombinationLog); // Создание прокручиваемой панели для комбинаций карт
        combinationScrollPane.setBounds(10, 560, 760, 100);
        panel.add(combinationScrollPane); // Добавление прокручиваемой панели на панель

        resultsLog = new JTextArea(); // Создание области для отображения результатов
        resultsLog.setForeground(Color.WHITE);
        resultsLog.setBackground(Color.BLACK);
        resultsLog.setEditable(false);
        JScrollPane resultsScrollPane = new JScrollPane(resultsLog);
        resultsScrollPane.setBounds(10, 670, 760, 100);
        panel.add(resultsScrollPane);
    }

    // Метод для отображения результата игры
    public void showResult(String message) {
        resultLabel.setText(message); // Установка текста результата игры
    }

    // Метод для обновления игрового лога
    public void updateGameLog(String message) {
        gameLog.append(message + "\n"); // Добавление сообщения в лог игры
    }

    // Метод для обновления лога комбинаций карт
    public void updateCardCombinationLog(String message) {
        cardCombinationLog.append(message + "\n"); // Добавление сообщения в лог комбинаций карт
    }

    // Метод для обновления лога результатов
    public void updateResultsLog(char userBetChoice, char result, List<Bot> bots) {
        resultsLog.append("Gracz wybrał: " + userBetChoice + ", Wynik: " + result + "\n"); // Добавление результата выбора пользователя в лог
        for (int i = 0; i < bots.size(); i++) {
            Bot bot = bots.get(i);
            resultsLog.append("Bot " + (i + 1) + " wybrał: " + bot.getLastChoice() + ", Wynik: " + result + "\n"); // Добавление результата выбора ботов в лог
        }
    }

    // Метод для обновления метки банкролла пользователя
    public void updateBankrollLabel(int bankroll) {
        bankrollLabel.setText("Bankroll: $" + bankroll); // Обновление текста метки банкролла
    }

    // Метод для обновления метки банкролла бота
    public void updateBotBankrollLabel(int botIndex, int bankroll) {
        botBankrollLabels.get(botIndex).setText("Bankroll bota " + (botIndex + 1) + ": $" + bankroll); // Обновление текста метки банкролла бота
    }

    // Класс для обработки нажатий кнопок
    class BetActionListener implements ActionListener {
        private final char betChoice; // Выбор ставки

        BetActionListener(char betChoice) {
            this.betChoice = betChoice; // Инициализация выбора ставки
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int bet;
            try {
                bet = Integer.parseInt(betField.getText()); // Получение суммы ставки из текстового поля
            } catch (NumberFormatException ex) {
                resultLabel.setText("Nieprawidłowa kwota zakładu."); // Сообщение об ошибке, если введено не число
                return;
            }
            gameController.playRound(betChoice, bet); // Запуск раунда игры с выбором ставки и суммой
        }
    }
}
