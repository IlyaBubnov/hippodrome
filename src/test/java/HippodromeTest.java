import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {

    @Test
    public void ifListIsNull() {
        IllegalArgumentException exception
                = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void ifListIsEmpty() {
        IllegalArgumentException exception
                = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void getHorses() {
        List <Horse> horseList = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            horseList.add(new Horse("horse - " + i, Math.random(), Math.random()));
        }
        assertEquals(horseList, new Hippodrome(horseList).getHorses());
    }

    @Test
    public void move() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horseList.add(mock(Horse.class));
        }
        new Hippodrome(horseList).move();

        for (Horse horse : horseList) {
            verify(horse).move();
        }
    }

    @Test
    public void getWinner() {
        Horse caseyRyback = new Horse("Casey Ryback", 3, 34);
        Horse johnMcClane = new Horse("John McClane", 5, 50);
        Horse bottom = new Horse("bottom", 1, 4);

        assertSame(johnMcClane, new Hippodrome(List.of(caseyRyback, johnMcClane, bottom)).getWinner());
    }
}
