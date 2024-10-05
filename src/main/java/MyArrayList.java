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
            resize(data.length * 2);
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
        if (size == data.length) {
            resize(data.length * 2);
        }
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
        quickSort(0, size - 1);
    }

    /**
     * Sorts MyArrayList using the QuickSort algorithm with the specified comparator.
     *
     * @param comparator Comparator for comparing elements.
     */
    public void sort(Comparator<? super E> comparator) {
        quickSort(0, size - 1, comparator);
    }

    /**
     * Recursive QuickSort method for sorting MyArrayList.
     *
     * @param low  Starting index.
     * @param high Ending index.
     */
    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    /**
     * Recursive QuickSort method for sorting MyArrayList with the specified comparator.
     *
     * @param low        Starting index.
     * @param high       Ending index.
     * @param comparator Comparator for comparing elements.
     */
    private void quickSort(int low, int high, Comparator<? super E> comparator) {
        if (low < high) {
            int pi = partition(low, high, comparator);
            quickSort(low, pi - 1, comparator);
            quickSort(pi + 1, high, comparator);
        }
    }

    /**
     * Splitting an array over a pivot element.
     *
     * @param low        Starting index.
     * @param high       Ending index.
     * @return           Index of a pivot element.
     */
    private int partition(int low, int high) {
        E pivot = (E) data[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (((Comparable) data[j]).compareTo(pivot) <= 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    /**
     * Splitting an array over a pivot element using a comparator.
     *
     * @param low        Starting index.
     * @param high       Ending index.
     * @param comparator Comparator for comparing elements.
     * @return           Index of a pivot element.
     */
    private int partition(int low, int high, Comparator<? super E> comparator) {
        E pivot = (E) data[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (comparator.compare((E) data[j], pivot) <= 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    /**
     * Swaps two elements in an array.
     *
     * @param i Index of the first element.
     * @param j Index of the second element.
     */
    private void swap(int i, int j) {
        Object temp = data[i];
        data[i] = data[j];
        data[j] = temp;
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