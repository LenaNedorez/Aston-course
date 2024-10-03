import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class MyArrayListTest {

    /**
     Checks that the sort method correctly sorts a list of ints.
     */
    @Test
    void testSort() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(5);
        list.add(2);
        list.add(8);
        list.add(1);
        list.add(9);

        list.sort();

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(5, list.get(2));
        assertEquals(8, list.get(3));
        assertEquals(9, list.get(4));
    }

    /**
     * Checks that the sort method correctly sorts a list of strings by length using provided comparator.
     */
    @Test
    void testSortWithComparator() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("apple");
        list.add("pineapple");
        list.add("cherry");
        list.add("grape");

        list.sort(Comparator.comparingInt(String::length));

        assertEquals("grape", list.get(0));
        assertEquals("apple", list.get(1));
        assertEquals("cherry", list.get(2));
        assertEquals("pineapple", list.get(3));
    }
}
