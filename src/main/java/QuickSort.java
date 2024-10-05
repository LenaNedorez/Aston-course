import java.util.Comparator;

/**
 * Class for sorting MyArrayList using quicksort algorithm.
 */
public class QuickSort {

    /**
     * Recursive QuickSort method for sorting MyArrayList.
     *
     * @param data Array of objects to be sorted.
     * @param low  Starting index.
     * @param high Ending index.
     */
    public static <E> void sort(Object[] data, int low, int high) {
        if (low < high) {
            int pi = partition(data, low, high);
            sort(data, low, pi - 1);
            sort(data, pi + 1, high);
        }
    }

    /**
     * Recursive QuickSort method for sorting MyArrayList with the specified comparator.
     *
     * @param data       Array of objects to be sorted.
     * @param low        Starting index.
     * @param high       Ending index.
     * @param comparator Comparator for comparing elements.
     */
    public static <E> void sort(Object[] data, int low, int high, Comparator<? super E> comparator) {
        if (low < high) {
            int pi = partition(data, low, high, comparator);
            sort(data, low, pi - 1, comparator);
            sort(data, pi + 1, high, comparator);
        }
    }

    /**
     * Splitting an array over a pivot element.
     *
     * @param data       Array of objects to be sorted.
     * @param low        Starting index.
     * @param high       Ending index.
     * @return           Index of a pivot element.
     */
    private static <E> int partition(Object[] data, int low, int high) {
        E pivot = (E) data[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (((Comparable) data[j]).compareTo(pivot) <= 0) {
                i++;
                swap(data, i, j);
            }
        }
        swap(data, i + 1, high);
        return i + 1;
    }

    /**
     * Splitting an array over a pivot element using a comparator.
     *
     * @param data       Array of objects to be sorted.
     * @param low        Starting index.
     * @param high       Ending index.
     * @param comparator Comparator for comparing elements.
     * @return           Index of a pivot element.
     */
    private static <E> int partition(Object[] data, int low, int high, Comparator<? super E> comparator) {
        E pivot = (E) data[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (comparator.compare((E) data[j], pivot) <= 0) {
                i++;
                swap(data, i, j);
            }
        }
        swap(data, i + 1, high);
        return i + 1;
    }

    /**
     * Swaps two elements in an array.
     *
     * @param data Array of objects to be sorted.
     * @param i Index of the first element.
     * @param j Index of the second element.
     */
    private static void swap(Object[] data, int i, int j) {
        Object temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
