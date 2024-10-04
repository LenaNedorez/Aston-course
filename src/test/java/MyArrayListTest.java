import org.junit.jupiter.api.Test;

import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

public class MyArrayListTest {

    @Test
    void add() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("Hello");
        list.add("World");
        assertEquals(2, list.size());
        assertEquals("Hello", list.get(0));
        assertEquals("World", list.get(1));
    }

    @Test
    void addAtIndex() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(1, 3);
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(2, list.get(2));
    }

    @Test
    void get() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("Hello");
        list.add("World");
        assertEquals("Hello", list.get(0));
        assertEquals("World", list.get(1));
    }

    @Test
    void remove() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(3, list.size());
        assertEquals(2, list.remove(1));
        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
    }

    @Test
    void clear() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("Hello");
        list.add("World");
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    void size() {
        MyArrayList<Integer> list = new MyArrayList<>();
        assertEquals(0, list.size());
        list.add(1);
        assertEquals(1, list.size());
        list.add(2);
        assertEquals(2, list.size());
    }

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
