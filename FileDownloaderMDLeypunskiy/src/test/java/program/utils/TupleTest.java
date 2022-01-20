package program.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TupleTest {

    @Test
    void getFirst() {
        Tuple<Integer, Integer> tuple = new Tuple<>(1, 2);
        assertEquals(1, tuple.getFirst());
    }

    @Test
    void getSecond() {
        Tuple<Integer, Integer> tuple = new Tuple<>(1, 2);
        assertEquals(2, tuple.getSecond());
    }
}