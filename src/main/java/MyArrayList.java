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


    private void resize(int newSize) {
        Object[] temp = new Object[newSize];
        System.arraycopy(data, 0, temp, 0, size);
        data = temp;
    }
}