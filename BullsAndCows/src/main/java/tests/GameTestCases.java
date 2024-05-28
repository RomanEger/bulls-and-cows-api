package tests;

import org.example.Bulls;
import org.example.Game;
import org.example.Tuple;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameTestCases {

    @Test
    public void TestGenerateBulls(){
        Bulls bulls = new Bulls(2, true);
        int[] arr = bulls.getBulls();
        int a = arr[0];
        int b = arr[1];
        boolean result = b == a;
        assertFalse(result);
    }

    @Test
    public void TestCount_0_0(){
        int[] arrBulls = new int[]{4, 3, 1, 2};
        Game game = new Game();
        int[] attempt = new int[]{5,6,7,8};
        Tuple<Integer, Integer> actualTuple = game.getBullsAndCows(arrBulls, attempt);
        Tuple<Integer, Integer> expectedTuple = new Tuple<>(0,0);
        assertEquals(expectedTuple, actualTuple);
    }

    @Test
    public void TestCount_1_0(){
        int[] arrBulls = new int[]{7, 5};
        Game game = new Game();
        int[] attempt = new int[]{7, 7};
        Tuple<Integer, Integer> actualTuple = game.getBullsAndCows(arrBulls, attempt);
        Tuple<Integer, Integer> expectedTuple = new Tuple<>(1,0);
        assertEquals(expectedTuple, actualTuple);
    }

    @Test
    public void TestCount_4_0(){
        int[] arrBulls = new int[]{4, 3, 1, 2};
        Game game = new Game();
        int[] attempt = new int[]{4,3,1,2};
        Tuple<Integer, Integer> actualTuple = game.getBullsAndCows(arrBulls, attempt);
        Tuple<Integer, Integer> expectedTuple = new Tuple<>(4,0);
        assertEquals(expectedTuple, actualTuple);
    }


}
