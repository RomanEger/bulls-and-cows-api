package org.example;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Writer {
    public int[] write(int length){
        int[] attempt = new int[length];
        while (true){
            try {
                String next = new Scanner(System.in).next("[0-9]{"+length+"}");
                char[] chars = next.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    attempt[i] = Character.getNumericValue(chars[i]);
                }
                return attempt;
            }
            catch (NoSuchElementException e){
                System.out.printf("Необходимо вводить целое число, состоящее из %d цифр!\n" +
                        "Попробуйте ещё раз\n", length);
            }
            catch (Exception e) {
                System.out.println("Необходимо вводить целое число!\n" +
                        "Попробуйте ещё раз");
            }
        }
    }
}
