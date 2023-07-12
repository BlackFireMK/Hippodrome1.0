import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    @Test
    void nullNameException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 1, 1));

        assertEquals("Name cannot be null.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    public void blankStrException(String str) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(str, 1, 1));

        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void negativeSpeed() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse("Буцефал", -1, 1));

        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void negativeDistance() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse("Туз Пик", 2, -1));

        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName() {
        Horse horse = new Horse("Зефир", 1, 1);

        assertEquals("Зефир", horse.getName());
    }

    @Test
    void getSpeed() {
        double expectedSpeed = 443;
        Horse horse = new Horse("Пожар", expectedSpeed, 1);

        assertEquals(expectedSpeed, horse.getSpeed());
    }

    @Test
    void getDistance() {
        Horse horse = new Horse("Лобстер", 1, 283);

        assertEquals(283, horse.getDistance());

    }

    @Test
    void zeroDistanceByDefault() {
        Horse horse = new Horse("Пегас", 1);

        assertEquals(0, horse.getDistance());
    }

    @Test
    void moveUseGetRandom() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("Вишня", 31, 283).move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.5, 0.9, 1.0, 999.999, 0.0})
    void move(double random) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Люцифер", 31, 283);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            assertEquals(283 + 31 * random, horse.getDistance());
        }
    }
}

