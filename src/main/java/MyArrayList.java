import java.util.Comparator;


public class MyArrayList<E> {
    private Object[] data;
    private int size;


    public MyArrayList() {
        this.data = new Object[10];
        this.size = 0;
    }


    public void add(E element) {
        if (size == data.length) {
            resize(data.length * 2);
        }
        data[size++] = element;
    }


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


    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (E) data[index];
    }


    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E removedElement = (E) data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null;
        return removedElement;
    }


    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }


    public int size() {
        return size;
    }


    public void sort() {
        quickSort(0, size - 1);
    }


    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }


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


    private void swap(int i, int j) {
        Object temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }


    private void resize(int newSize) {
        Object[] temp = new Object[newSize];
        System.arraycopy(data, 0, temp, 0, size);
        data = temp;
    }
}