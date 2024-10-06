# Task 1 of Aston intensive course

##The task
The task is to implement ArrayList (not thread safe). Methods: add element, add element by index, get element, remove element, clear all collection and sort. Implement quicksort algorithm for implemented ArrayList. QuickSort should take ArrayList of any type and sort it, using Java generics or Comparable, Comparator. All code should be covered with tests and documented by javadoc.

## Description
This code is a simple implementation of ArrayList in Java using an array and the QuickSort algorithm. It demonstrates the basic principles of list implementation and sorting algorithms, as well as the use of generics and comparators.

## Classes and Methods
### MyArrayList class
This class provides a basic implementation of a dynamic array, similar to the ArrayList class in Java’s standard library. It allows you to store and manipulate a collection of elements.

• Add method inserts the given element to the end of the array.
• Add at index method inserts the given element at the specified index.
• Get method returns the element at the given index.
• Remove method removes the element at the given index.
• Clear method removes all elements from the array.
• Resize method is used to increase the size of an array when adding elements.

### Quicksort class
QuickSort implementation uses a recursive algorithm that splits instance of MyArrayList into two parts over a pivot element and recursively sorts each of them.

• Sort method can take a comparator as an input for sorting list by criteria, provided by this comparator.
• Partition method implements splitting a list by choosing the last element as the pivot.

## Tests
add(): Checks that adding elements to a list works correctly, the size of the list was increased, and elements were added to the correct location.

addAtIndex(): Checks that an element was added at the given index, the size of the list was increased, and existing elements were shifted.

get(): Checks that an element at the given index was correctly selected.

remove(): Checks that an element at the given index was correctly removed, the size of the list was decreased, and the remaining elements were shifted.

clear(): Checks that the clear() method clears the list, making it empty.

size(): Checks that the size() method returns the correct size of the list.
sort(): Checks that the sort method correctly sorts a list of integers.

sortWithComparator(): Checks that the sort method correctly sorts a list of strings by length using a comparator.

## Documentation
All methods and classes have javadoc documentation.
