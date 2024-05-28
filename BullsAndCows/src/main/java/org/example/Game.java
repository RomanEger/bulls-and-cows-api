package org.example;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Game {

    Writer writer = new Writer();

    public String getRules(boolean isBot){
        if(isBot){
            return "\n" +
                    "--------------------\n" +
                    "Правила игры:\n" +
                    "\n" +
                    "Компьютер задумывает Х* цифр из 0,1,2,...9. Игрок делает ходы, чтобы узнать эти цифры и их порядок.\n" +
                    "Каждый ход состоит из Х цифр, 0 может стоять на первом месте.\n" +
                    "В ответ компьютер показывает число отгаданных цифр, стоящих на своих местах (число быков) и число отгаданных цифр, стоящих не на своих местах (число коров).\n" +
                    "Пример:\n" +
                    "Компьютер задумал 0834.\n" +
                    "Игрок сделал ход 8134.\n" +
                    "Компьютер ответил: 2 быка (цифры 3 и 4) и 1 корова (цифра 8).\n" +
                    "(Угаданные цифры остаются неизвестными)\n" +
                    "*2 >= X <= 10\n" +
                    "--------------------\n";
        }
        else {
            return  "\n" +
                    "--------------------\n" +
                    "Правила игры:\n" +
                    "\n" +
                    "Игроки по очереди задумывают X цифр из 0,1,2,...9.\n" +
                    "Игроки по очереди делают ходы, чтобы узнать эти цифры и их порядок.\n"+
                    "Каждый ход состоит из Х цифр, 0 может стоять на первом месте.\n" +
                    "Пример:\n" +
                    "Игрок 1 задумал 0834.\n" +
                    "Игрок 2 сделал ход 8134.\n" +
                    "Игрок 2 получил ответ: 2 быка (цифры 3 и 4) и 1 корова (цифра 8).\n" +
                    "(Угаданные цифры остаются неизвестными)\n" +
                    "--------------------\n";
        }
    }

    public int gameVsBot(Bulls bulls){
        int steps = 0;

        int[] arrBulls = bulls.getBulls();

        boolean isWin = false;

        while (!isWin){
            System.out.println("Введите Ваш ответ: ");
            int[] attempt = writer.write(arrBulls.length);
            steps++;
            int countBulls = 0, countCows = 0;

            Tuple<Integer, Integer> bullsAndCows = getBullsAndCows(arrBulls, attempt);

            countBulls = bullsAndCows.getFirst();
            countCows = bullsAndCows.getSecond();

            System.out.printf("Кол-во быков: %d\tКол-во коров: %d\n", countBulls, countCows);

            if (countBulls == arrBulls.length)
                isWin = true;
        }

        return steps;
    }

    public Tuple<String, Integer> gameVsPlayer(Tuple<Bulls, Bulls> bulls, Tuple<String, String> nickNames){
        int steps = 0;

        int length = bulls.getFirst().getBulls().length;

        boolean isWin = false;
        int Winner = 0;
        int firstPlayerSteps = 0, secondPlayerSteps = 0;

        boolean isFirstPlayerStep = true;

        while (!isWin){
            System.out.println((isFirstPlayerStep ? nickNames.getFirst() : nickNames.getSecond()) + ", введите Ваш ответ");
            int[] attempt = writer.write(length);

            int countBulls = 0, countCows = 0;

            Tuple<Integer, Integer> bullsAndCows;

            if(isFirstPlayerStep){
                firstPlayerSteps++;

                Bulls bull = bulls.getSecond();
                int[] arr = bull.getBulls();

                bullsAndCows = getBullsAndCows(arr, attempt);

                countBulls = bullsAndCows.getFirst();
                countCows = bullsAndCows.getSecond();

                System.out.printf("Кол-во быков: %d\tКол-во коров: %d\n", countBulls, countCows);
            }
            else
            {
                secondPlayerSteps++;

                Bulls bull = bulls.getFirst();
                int[] arr = bull.getBulls();

                bullsAndCows = getBullsAndCows(arr, attempt);

                countBulls = bullsAndCows.getFirst();
                countCows = bullsAndCows.getSecond();

                System.out.printf("Кол-во быков: %d\tКол-во коров: %d\n", countBulls, countCows);
            }

            if(countBulls == length){
                if(!isFirstPlayerStep){
                    isWin = true;
                    if(Winner == 1){
                        Winner = 0;
                    } else {
                        Winner = 2;
                    }
                } else{
                    Winner = 1;
                }
            } else {
                if(Winner != 0){
                    isWin = true;
                }
            }
            isFirstPlayerStep = !isFirstPlayerStep;
        }

        String winner;
        steps = Math.min(firstPlayerSteps, secondPlayerSteps);
        if(Winner == 1){
            winner = nickNames.getFirst();
        }
        else if(Winner == 2){
            winner = nickNames.getSecond();
        }
        else {
            winner = "";
            steps = -1;
        }

        return new Tuple<>(winner, steps);
    }

    public Tuple<Integer, Integer> getBullsAndCows(int[] arrBulls, int[] attempt){
        int countBulls = 0;
        int countCows = 0;
//        HashMap<Integer, Integer> attemptMap = new HashMap<>();
//
//        for(int i = 0; i < attempt.length; i++){
//            attemptMap.put(i, attempt[i]);
//        }

        HashMap<Integer, Integer> bullsMap = new HashMap<>();

        for(int i = 0; i < arrBulls.length; i++){
            bullsMap.put(i, arrBulls[i]);
        }

        for (int i = 0; i < arrBulls.length; i++) {
            if (attempt[i] == bullsMap.get(i)) {
                countBulls++;
                bullsMap.remove(i);
            }
            else {
                for (int j = 0; j < arrBulls.length; j++) {
                    if (bullsMap.containsKey(j) && attempt[i] == bullsMap.get(j) && i != j) {
                        countCows++;
                        bullsMap.remove(j);
                    }
                }
            }
        }
        return new Tuple<>(countBulls, countCows);
    }
}
