package org.example.bullsandcowsapi.service;

public class IntToIntArrayService {
    public static int[] toIntArray(int number){
        final int len = String.valueOf(number).length();
        int[] result = new int[len];
        char[] numbers = String.valueOf(number).toCharArray();
        for(int i = 0; i < len; i++){
            result[i] = Character.getNumericValue(numbers[i]);
        }
        return result;
    }
}
