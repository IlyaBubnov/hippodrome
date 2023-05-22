import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {

    @Test
    public void ifNameNull() {
        IllegalArgumentException exception
                = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource (strings = {" ", "  ", "   ", "\t", "\t\t", "\n", "\n\n", " \t", " \n", "\t\n"})
    public void ifNameEmpty(String name) {
        IllegalArgumentException exception
                = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void ifSpeedNegative() {
        IllegalArgumentException exception
                = assertThrows(IllegalArgumentException.class, () -> new Horse("name", -1,1));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void ifDistanceNegative() {
        IllegalArgumentException exception
                = assertThrows(IllegalArgumentException.class, () -> new Horse("name", 1,-1));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void getName () {
        assertEquals("name", new Horse("name",1,1).getName());
    }

    @Test
    public void getSpeed () {
        assertEquals(3, new Horse("name",3,1).getSpeed());
    }

    @Test
    public void getDistance () {
        assertEquals(4, new Horse("name",3,4).getDistance());
    }

    @Test
    public void getDistanceIfZero () {
        assertEquals(0, new Horse("name",3).getDistance());
    }

    @Test
    public void moveCanUseRandom () {
        try (MockedStatic <Horse> mockedStatic = Mockito.mockStatic(Horse.class)){
            new Horse("name",1,2).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            //если важно проверить любые параметры, переданные в метод, то
            //mockedStatic.verify(() -> Horse.getRandomDouble(anyDouble(), anyDouble()));
            //если важно проверить один параметр д.б. четко такой-то, а другой любой, то
            //mockedStatic.verify(() -> Horse.getRandomDouble(anyDouble(), eq(0.9)));
        }
    }

    @ParameterizedTest
    @ValueSource (doubles = {0.0, 0.1, 0.2, 0.5, 0.9, 1.0})
    public void moveCanAssignAValue (double random) {
        try (MockedStatic <Horse> mockedStatic = Mockito.mockStatic(Horse.class)){
            Horse horse = new Horse("name",1,2);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            horse.move();
            assertEquals((2 + 1*random), horse.getDistance());
        }
    }
}