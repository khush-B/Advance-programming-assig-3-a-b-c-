package dk.dtu.compute.course02324.assignment3.lists.implementations;

import dk.dtu.compute.course02324.assignment3.lists.types.List;

import jakarta.validation.constraints.NotNull;
import java.util.Comparator;

/**
 * An implementation of the interface {@link List} based on basic Java
 * arrays, which dynamically are adapted in size when needed.
 *
 * @param <E> the type of the list's elements.
 */
public class ArrayList<E> implements List<E> {

    /**
     * Constant defining the default size of the array when the
     * list is created. The value can be any (strictly) positive
     * number. Here, we have chosen <code>10</code>, which is also
     * Java's default for some array-based collection implementations.
     */
    final private int DEFAULT_SIZE = 10;

    /**
     * Current size of the list.
     */
    private int size = 0;

    /**
     *  The array for storing the elements of the
     */
    private E[] list = createEmptyArray(DEFAULT_SIZE);

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public @NotNull E get(int pos) throws IndexOutOfBoundsException {

        // TODO needs implementation (Assignment 3a)
        if (pos < 0 || pos >= size)
        {
            throw new UnsupportedOperationException("This operation is not yet implemented!");
        }
        return list[pos];
    }

    @Override
    public E set(int pos, @NotNull E e) throws IndexOutOfBoundsException {

        // TODO needs implementation (Assignment 3a)
        // check null element
        if (e == null)
        {
            throw new UnsupportedOperationException("This operation is not yet implemented!");
        }
        // check invalid position
        if (pos < 0 || pos >= size)
        {
            throw new UnsupportedOperationException("This operation is not yet implemented!");
        }
        // replace the element at the given position and return the old element
        E previousElement = list[pos];
        list[pos] = e;
        return previousElement;

    }

    @Override
    public boolean add(@NotNull E e) {

        // TODO needs implementation (Assignment 3a)
        // This fuction only add the element at the end of the list, so we will call the add(pos,e) function with pos = size
        if (e == null)
        {
            throw new UnsupportedOperationException("Null value is not supported!");
        }
        // Check if the array is full, if it is full we will create a new array with the size of double of the previous array. e,g default array size = 10 which will be 10*2 = 20
        ensureCapacity(size+1);
        // Now add the element at the end of the list and increment the size
        list[size] = e;
        size++;
        return true;

    }

    @Override
    public boolean add(int pos, @NotNull E e) throws IndexOutOfBoundsException {
        // TODO needs implementation (Assignment 3a)
        // In this function we will add the element at the given position, so we need to shift all the elements from the given position and upwards by one position to make space for the new element. Then we will add the new element at the given position and increment the size.
        // condition 1: check null element
        if (e == null)
        {
            throw new UnsupportedOperationException("Null value is not supported!");
        }
        // condition 2: check invalid position, but pos == size() is OK because we can add the element at the end of the list
        if (pos < 0 || pos > size)
        {
            throw new UnsupportedOperationException("This operation is not yet implemented!");
        }
        // Check if the array is full, if it is full we will create a new array with the size of double of the previous array. e,g default array size = 10 which will be 10*2 = 20
        ensureCapacity(size+1);
        // Now shift all the elements from the given position and upwards by one position to make space for the new element
        shiftRight(pos);
        // Now add the new element at the given position and increment the size
        list[pos] = e;
        size++;
        return true;

    }

    @Override
    public E remove(int pos) throws IndexOutOfBoundsException {
        // TODO needs implementation (Assignment 3a)
        // In this function we will remove the element at the given position, so we need to shift all the elements from the given position and upwards by one position to make space for the new element. Then we will add the new element at the given position and increment the size.
        // condition 1: check invalid position
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("Invalid position!");
        }
        E removedElement = list[pos];
        // Now shift all the elements from the given position and upwards by one position to make space
        shiftLeft(pos);
        // Now decrement the size
        size--;
        return removedElement;


    }

    @Override

    public boolean remove(E e) {
        // TODO needs implementation (Assignment 3a)
        // Remove the value of the first occurrence of the given element, so we need to find the index of the first occurrence of the given element and then call the remove(pos) function with the index of the first occurrence of the given element
        // IndexOf(e) search for element provided by the user and return the index of the first occurrence of the given element, if the element is not found it will return -1
        int index = indexOf(e);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;

    }

    @Override
    public int indexOf(E e) {

        // TODO needs implementation (Assignment 3a)
        // Find the index of the first occurrence of the given element, so we need to iterate through the list and compare each element with the given element using the equals() method. If we find an element that is equal to the given element, we will return its index. If we reach the end of the list and we haven't found an element that is equal to the given element, we will return -1.
        // condition 1: check null element
        if (e == null)
        {
            throw new UnsupportedOperationException("Null value is not supported!");
        }
        // implementation of the function to find the index of the first occurrence of the given element
        for (int i = 0; i < size; i++) {
            if (list[i]== e) {
                return i;
            }
        }
        return -1;

    }

    @Override
    public void sort(@NotNull Comparator<? super E> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This operation is not yet implemented!");
        // TODO needs implementation (Assignment 3b)

    }

    /**
     * Creates a new array of type <code>E</code> with the given size.
     *
     * @param length the size of the array
     * @return a new array of type <code>E</code> and the given length
     */
    private E[] createEmptyArray(int length) {
        // there is unfortunately no really easy and elegant way to initialize
        // an array with a type coming in as a generic type parameter, but
        // the following is simple enough. And it is OK, since the array
        // is never passed out of this class.
        return (E[]) new Object[length];
    }

     // TODO probably some private helper methods here (avoiding duplicated code)
     //      (Assignment 3a)
     //New Array creation and copy
     private void ensureCapacity(int minCapacity) {
       // If the minimum capacity is greater than the length of the current array, we need to create a new array with the size of double of the previous array. e,g default array size = 10 which will be 10*2 = 20
        if (minCapacity > list.length) {
            // This line tell us the index of the previous array and the new array, so we can copy the data from the previous array to the new array
            int newCapacity = Math.max(minCapacity, list.length * 2);
            // Create a new array with the new capacity and copy the data from the previous array to the new array
            E[] newList = createEmptyArray(newCapacity);
            // This line copy the data from the previous array to the new array, it takes the source array, the starting index of the source array, the destination array, the starting index of the destination array and the number of elements to be copied
            System.arraycopy(list, 0, newList, 0, size);
            // Now replace the previous array with the new array
            list = newList;
        }
    }

    // shift left = increment the index of the elements by one position, so we can remove the element at the given position and return it
    // This function will be used in the remove(pos) function, so we can remove the element at the given position and return it
    private E shiftLeft(int pos) {
            E removedElement = list[pos];
            for (int i = pos; i < size - 1; i++) {
                list[i] = list[i + 1];
            }
            list[size - 1] = null; // Clear the last element after shifting
            return removedElement;
        }
        // shift right = decrement the index of the elements by one position, so we can add the element at the given position and return it
        // Shift right all the elements from the given position and upwards by one position to make space for the new element
        private void shiftRight(int pos) {
            for (int i = size; i > pos; i--) {
                list[i] = list[i - 1];
            }
        }
}
