import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

public class MyArrayListTest {

    private MyArrayList<String> stringList;
    private MyArrayList<Integer> integerList;

    @BeforeEach
    void setUp() {
        stringList = new MyArrayList<>();
        integerList = new MyArrayList<>();
    }

    @Test
    void add() {
        stringList.add("Hello");
        stringList.add("World");
        assertEquals(2, stringList.size());
        assertEquals("Hello", stringList.get(0));
        assertEquals("World", stringList.get(1));
    }

    @Test
    void testAddNull() {
        stringList.add(null);
        assertEquals(1, stringList.size());
        assertNull(stringList.get(0));
    }

    @Test
    void addAtIndex() {
        integerList.add(1);
        integerList.add(2);
        integerList.add(1, 3);
        assertEquals(3, integerList.size());
        assertEquals(1, integerList.get(0));
        assertEquals(3, integerList.get(1));
        assertEquals(2, integerList.get(2));
    }

    @Test
    void testAddNullAtIndex() {
        stringList.add("Hello");
        stringList.add(0, null);
        assertEquals(2, stringList.size());
        assertNull(stringList.get(0));
        assertEquals("Hello", stringList.get(1));
    }

    @Test
    void get() {
        stringList.add("Hello");
        stringList.add("World");
        assertEquals("Hello", stringList.get(0));
        assertEquals("World", stringList.get(1));
    }

    @Test
    void testGetNull() {
        stringList.add(null);
        stringList.add("World");
        assertNull(stringList.get(0));
        assertEquals("World", stringList.get(1));
    }

    @Test
    void remove() {
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        assertEquals(3, integerList.size());
        assertEquals(2, integerList.remove(1));
        assertEquals(2, integerList.size());
        assertEquals(1, integerList.get(0));
        assertEquals(3, integerList.get(1));
    }

    @Test
    void clear() {
        stringList.add("Hello");
        stringList.add("World");
        stringList.clear();
        assertEquals(0, stringList.size());
    }

    @Test
    void size() {
        assertEquals(0, integerList.size());
        integerList.add(1);
        assertEquals(1, integerList.size());
        integerList.add(2);
        assertEquals(2, integerList.size());
    }

    @Test
    void testSort() {
        integerList.add(5);
        integerList.add(2);
        integerList.add(8);
        integerList.add(1);
        integerList.add(9);

        integerList.sort();

        assertEquals(1, integerList.get(0));
        assertEquals(2, integerList.get(1));
        assertEquals(5, integerList.get(2));
        assertEquals(8, integerList.get(3));
        assertEquals(9, integerList.get(4));
    }

    @Test
    void testSortWithComparator() {
        stringList.add("apple");
        stringList.add("pineapple");
        stringList.add("cherry");
        stringList.add("kiwi");

        stringList.sort(Comparator.comparingInt(String::length));

        assertEquals("kiwi", stringList.get(0));
        assertEquals("apple", stringList.get(1));
        assertEquals("cherry", stringList.get(2));
        assertEquals("pineapple", stringList.get(3));
    }

    @Test
    void testSortWithNullComparator() {
        stringList.add("Hello");
        stringList.add("World");
        stringList.add("!");

        // Пробуем отсортировать список с null компаратором
        assertThrows(NullPointerException.class, () -> stringList.sort(null));
    }
}
