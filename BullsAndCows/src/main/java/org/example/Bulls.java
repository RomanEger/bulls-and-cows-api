package org.example;

import java.util.Arrays;
import java.util.Random;

public class Bulls {
    private final int MIN_LENGTH = 2;
    private final int MAX_LENGTH = 10;

    private final int length;

    private int[] bulls;

    public int[] getBulls(){
        return bulls;
    }

    private final boolean isUnique;

    public Bulls(int length, boolean isUnique){
        if(length > MAX_LENGTH || length < MIN_LENGTH)
            throw new IllegalArgumentException("Размер должен быть в диапазоне от 2 до 10 (включительно).");

        this.length = length;
        this.isUnique = isUnique;
        generateBulls();
    }

    public Bulls(int[] bulls, boolean isUnique){

        length = bulls.length;

        if(length > MAX_LENGTH || length < MIN_LENGTH)
            throw new IllegalArgumentException("Размер должен быть в диапазоне от 2 до 10 (включительно).");

        this.isUnique = isUnique;

        if(isUnique){
            check(bulls);
        }

        this.bulls = bulls;
    }

    public void check(int[] a){
        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                if(a[j] == a[i] && i!=j){
                    throw new IllegalArgumentException("Ошибка! Цифры не уникальны.");
                }
            }
        }
    }

    private void generateBulls(){
        Random random = new Random();
        bulls = new int[length];
        for(int i = 0; i < length; i++){
            int nextInt = random.nextInt(10);
            if(isUnique){
                while(true){
                    int finalNextInt = nextInt;
                    boolean b = Arrays.stream(bulls).anyMatch(x -> x == finalNextInt);
                    if(b)
                        nextInt = random.nextInt(10);
                    else
                        break;
                }
            }
            bulls[i] = nextInt;
        }
    }
}
