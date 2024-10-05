import java.util.Comparator;

/**
 * ArrayList realisation on the base of Array. Not thread safe.
 *
 * @param <E> The element type in MyArrayList.
 */
public class MyArrayList<E> {
    private Object[] data;
    private int size;

    /**
     * Default constructor.
     */
    public MyArrayList() {
        this.data = new Object[10];
        this.size = 0;
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param element The element to be added.
     */
    public void add(E element) {
        if (size == data.length) {
            resize(data.length * 3 / 2);
        }
        data[size++] = element;
    }

    /**
     * Adds an element at the given index.
     *
     * @param index   Index at which the element should be added.
     * @param element The element to be added.
     * @throws IndexOutOfBoundsException if the index is out of acceptable bounds.
     */
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    /**
     * Returns the element at the given index.
     *
     * @param index The index of the element.
     * @return The element at a given index.
     * @throws IndexOutOfBoundsException if the index is out of acceptable bounds.
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (E) data[index];
    }

    /**
     * Removes an element at a given index.
     *
     * @param index The index of the element to be removed.
     * @return Deleted element.
     * @throws IndexOutOfBoundsException if the index is out of acceptable bounds.
     */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E removedElement = (E) data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null;
        return removedElement;
    }

    /**
     * Clears the list.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    /**
     * Returns the size of the list.
     *
     * @return The size of the list.
     */
    public int size() {
        return size;
    }

    /**
     * Sorts MyArrayList using the QuickSort algorithm.
     */
    public void sort() {
        QuickSort.sort(data, 0, size - 1);
    }

    /**
     * Sorts MyArrayList using the QuickSort algorithm with the specified comparator.
     *
     * @param comparator Comparator for comparing elements.
     */
    public void sort(Comparator<? super E> comparator) {
        if(comparator == null){
            throw new NullPointerException("Comparator cannot be null");
        }
        QuickSort.sort(data, 0, size - 1, comparator);
    }

    /**
     * Increases the capacity of the array if the current capacity is less than the minimum required.
     *
     * @param minCapacity Minimum required capacity.
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            resize(data.length * 3 / 2);
        }
    }

    /**
     * Changes the size of an array.
     *
     * @param newSize New size of an array.
     */
    private void resize(int newSize) {
        Object[] temp = new Object[newSize];
        System.arraycopy(data, 0, temp, 0, size);
        data = temp;
    }
}