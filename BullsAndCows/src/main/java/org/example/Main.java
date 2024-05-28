package org.example;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static String getRules(boolean isBot){
        if(isBot){
            return "\n" +
                    "--------------------\n" +
                    "Правила игры:\n" +
                    "\n" +
                    "Компьютер задумывает четыре различные цифры из 0,1,2,...9. Игрок делает ходы, чтобы узнать эти цифры и их порядок.\n" +
                    "Каждый ход состоит из четырёх цифр, 0 может стоять на первом месте.\n" +
                    "В ответ компьютер показывает число отгаданных цифр, стоящих на своих местах (число быков) и число отгаданных цифр, стоящих не на своих местах (число коров).\n" +
                    "Пример:\n" +
                    "Компьютер задумал 0834.\n" +
                    "Игрок сделал ход 8134.\n" +
                    "Компьютер ответил: 2 быка (цифры 3 и 4) и 1 корова (цифра 8).\n" +
                    "(Угаданные цифры остаются неизвестными)\n" +
                    "--------------------\n";
        }
        else {
            return  "\n" +
                    "--------------------\n" +
                    "Правила игры:\n" +
                    "\n" +
                    //дописать
                    "Пример:\n" +
                    "Игрок 1 задумал 0834.\n" +
                    "Игрок 2 сделал ход 8134.\n" +
                    "Игрок 2 получил ответ: 2 быка (цифры 3 и 4) и 1 корова (цифра 8).\n" +
                    "(Угаданные цифры остаются неизвестными)\n" +
                    "--------------------\n";
        }
    }

    public static void main(String[] args) {
        while(true) {
            String winner;
            boolean isUnique;
            int length;
            int choice;

            System.out.println("Добро пожаловать в игру быки и коровы!\n" +
                    "Выберите формат игры:\n" +
                    "1 Против бота\n" +
                    "2 Против игрока");

            while (true) {
                try {
                    choice = new Scanner(System.in).nextInt();
                    if (choice != 1 && choice != 2) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception ex) {
                    System.out.println("Ответ должен быть 1 или 2!\n" +
                            "Попробуйте ещё раз.");
                }
            }

            System.out.println(getRules(choice == 1));

            System.out.println("Уникальны ли цифры?\n" +
                    "1 Да\n" +
                    "Любая клавиша Нет");

            while (true) {
                try {
                    isUnique = new Scanner(System.in).nextInt() == 1;
                    break;
                } catch (Exception ex) {
                    System.out.println("Ответ должен быть 1 или 2!\n" +
                            "Попробуйте ещё раз.");
                }
            }

            if (choice == 1) {
                Bulls bulls;
                while (true) {
                    try {
                        System.out.print("Задайте размер загаданного числа:\t");

                        length = new Scanner(System.in).nextInt();

                        bulls = new Bulls(length, isUnique);

                        break;
                    }
                    catch (IllegalArgumentException ex){
                        System.out.println(ex.getMessage()+
                                "\nПопробуйте ещё раз.");
                    }
                    catch (Exception ex) {
                        System.out.println("Размер должен быть представлен целым числом!\n" +
                                "Попробуйте ещё раз.");
                    }
                }

                Game game = new Game();
                int steps = game.gameVsBot(bulls);
                winner = ("Победа!\nШагов:\t" + steps);
                System.out.println(winner);
            }
            else {
                String firstNickName, secondNickName;

                System.out.println("Игрок 1, введите Ваше имя");
                firstNickName = new Scanner(System.in).next();

                System.out.println("Игрок 2, введите Ваше имя");
                secondNickName = new Scanner(System.in).next();

                Bulls firstPlayerBulls, secondPlayerBulls;
                while (true){
                    try {
                        System.out.print("Задайте размер загаданного числа:\t");

                        length = new Scanner(System.in).nextInt();
                        break;
                    }
                    catch (Exception ex) {
                        System.out.println("Размер должен быть представлен целым числом!\n" +
                                "Попробуйте ещё раз.");
                    }
                }
                Writer writer = new Writer();
                while (true){
                    try{
                        System.out.printf("%s, Введите Ваше число\n", firstNickName);

                        int[] firstBulls = writer.write(length);

                        firstPlayerBulls = new Bulls(firstBulls, isUnique);
                        break;
                    }
                    catch (Exception ex){
                        System.out.println(ex.getMessage()+
                                "\nПопробуйте ещё раз.");
                    }
                }
                while (true) {
                    try {
                        System.out.printf("%s, Введите Ваше число\n", secondNickName);
                        int[] secondBulls = writer.write(length);

                        secondPlayerBulls = new Bulls(secondBulls, isUnique);
                        break;
                    }
                    catch (Exception ex){
                        System.out.println(ex.getMessage()+
                                "\nПопробуйте ещё раз.");
                    }
                }

                Tuple<String, String> nickNames = new Tuple<>(firstNickName, secondNickName);
                Tuple<Bulls, Bulls> bullsTuple = new Tuple<>(firstPlayerBulls, secondPlayerBulls);
                Tuple<String, Integer> result = new Game().gameVsPlayer(bullsTuple, nickNames);

                if(result.getSecond() < 0){
                    winner = "Ничья!";
                    System.out.println(winner);
                }
                else {
                    winner = "Победил" + result.getFirst() + "!\nШагов:\t" + result.getSecond();
                    System.out.println(winner);
                }
            }
            System.out.println("Хотите записать рекорд в файл?\n" +
                    "1 Да\n" +
                    "2 Нет");
            while(true){
                try{
                    int ans = new Scanner(System.in).nextInt();
                    if(ans == 1){
                        while(true){
                            try{
                                System.out.println("Введите адрес файла");
                                writeResult(winner);
                                break;
                            }
                            catch (Exception e){
                                System.out.println("Файл не найден");
                            }
                        }
                    }
                    else if(ans != 2)
                        throw new Exception();
                    else
                        continue;

                    break;
                }
                catch (Exception e){
                    System.out.println("Некорректный ответ");
                }
            }

            System.out.println("Хотите начать игру заново?\n" +
                    "1 Да\n" +
                    "2 Нет");

            int next;
            try {
                next = new Scanner(System.in).nextInt();
            } catch (Exception ex) {
                System.out.println("До свидания!");
                next = 2;
            }

            if (next != 1) {
                break;
            } else {
                try {
                    final String os = System.getProperty("os.name");

                    if (os.contains("Windows")) {
                        Runtime.getRuntime().exec("cls");
                    } else {
                        Runtime.getRuntime().exec("clear");
                    }
                } catch (final Exception e) {
                    System.out.println("Ошибка при очистке консоли");
                }
            }
        }
    }

    private static void writeResult(String winner) throws IOException {
        String filePath = new Scanner(System.in).next();
        File file = new File(filePath);
        FileWriter writer = new FileWriter(file, false);
        writer.write(winner);
        writer.flush();
    }
}