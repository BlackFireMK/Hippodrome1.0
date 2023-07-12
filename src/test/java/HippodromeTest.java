import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class HippodromeTest {
    @Test
    void emptyHorseException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    void nullList() {
        Throwable nullList = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", nullList.getMessage());
    }

    @Test
    void emptyList() {
        Throwable emptyList = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(Collections.emptyList()));
        assertEquals("Horses cannot be empty.", emptyList.getMessage());
    }

    @Test
    void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            horses.add(new Horse("" + i, i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void moveAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            horses.add(mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        Horse horse1 =  new Horse("Horse1", 10, 200);
        Horse horse2 =  new Horse("Horse2", 10, 100);
        Horse horse3 =  new Horse("Horse3", 10, 150);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));

        assertSame(horse1, hippodrome.getWinner());
    }


}
