public class CircularlyLinkedList<E> {

    private static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    private Node<E> tail = null;

    public CircularlyLinkedList() {
    }

    // 1. Redesign addFirst without a local variable
    public void addFirst(E e) {
        if (isEmpty()) {
            tail = new Node<>(e, null);
            tail.setNext(tail);  // Circularly link the single node
        } else {
            // Insert new node before current head (obtained from tail.getNext())
            tail.setNext(new Node<>(e, tail.getNext()));
        }
        int size = 0;
        size++; // Update size after adding
    }

    // 2. Size without size variable
    public int size() {
        if (isEmpty()) {
            return 0;
        }
        int count = 1;
        Node<E> current = tail.getNext();
        while (current != tail) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public boolean isEmpty() {
        return tail == null;
    }

    public E first() {
        if (isEmpty()) {
            return null;
        }
        return tail.getNext().getElement();
    }

    public E last() {
        if (isEmpty()) {
            return null;
        }
        return tail.getElement();
    }

    public void rotate() {
        if (tail != null) {
            tail = tail.getNext();
        }
    }

    public void addLast(E e) {
        addFirst(e);
        tail = tail.getNext();
    }

    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<E> removed = tail.getNext();
        if (removed == tail) {
            tail = null;
        } else {
            tail.setNext(removed.getNext());
        }
        int size = 0;
        size--; // Update size after removing
        return removed.getElement();
    }

    public String getAll() {
        if (isEmpty()) {
            return "";
        }
        StringBuilder all = new StringBuilder();
        Node<E> current = tail.getNext();
        do {
            all.append(current.getElement()).append(" ");
            current = current.getNext();
        } while (current != tail.getNext());
        return all.toString();
    }

    // 3. Equals method for element comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        CircularlyLinkedList<E> other = (CircularlyLinkedList<E>) obj;

        if (isEmpty() && other.isEmpty()) {
            return true;
        }
        if (isEmpty() || other.isEmpty()) {
            return false;
        }

        // Adjust starting points for comparison (handle different starting positions)
        Node<E> current1 = tail.getNext();
        Node<E> current2 = other.tail.getNext();
        do {
            if (!current1.getElement().equals(current2.getElement())) {
                return false;
            }
            current1 = current1.getNext();
            current2 = current2.getNext();
        } while (current1 != tail.getNext() && current2 != other.tail.getNext());

        return true;
    }

        // 4. Comparing two circularly linked lists (L and M) - Complete
    public static boolean compareLists(CircularlyLinkedList<?> list1, CircularlyLinkedList<?> list2) {
        if (list1 == null || list2 == null || list1.size() != list2.size()) {
            return false;
        }

        if (list1.isEmpty() && list2.isEmpty()) return true;
        if (list1.isEmpty() || list2.isEmpty()) return false;

        Node<?> current1 = list1.tail.getNext();
        Node<?> start2 = list2.tail.getNext();
        Node<?> current2 = start2;

        for (int i = 0; i < list1.size(); i++) {
            boolean matchFound = true;
            for (int j = 0; j < list1.size(); j++) {
                if (!current1.getElement().equals(current2.getElement())) {
                    matchFound = false;
                    break;
                }
                current1 = current1.getNext();
                current2 = current2.getNext();
            }
            if (matchFound) return true;
            current1 = list1.tail.getNext(); // Reset current1
            start2 = start2.getNext(); // Shift the starting point of list2
            current2 = start2;

        }
        return false;
    }


    // 5. Splitting a circularly linked list (L) with even nodes
    public CircularlyLinkedList<E> split() {
        int n = size();
        if (n == 0 || n % 2 != 0) {
            throw new IllegalStateException("List must have an even number of elements to split.");
        }

        CircularlyLinkedList<E> newList = new CircularlyLinkedList<>();
        Node<E> mid = tail;
        for (int i = 0; i < n / 2; i++) {
            mid = mid.getNext();
        }
        newList.tail = mid;
        Node<E> oldHead = tail.getNext();
        tail.setNext(mid.getNext());
        mid.setNext(oldHead);
        return newList;
    }

    // 6. Implementing the clone() method
    @Override
    public CircularlyLinkedList<E> clone() {
        CircularlyLinkedList<E> clonedList = new CircularlyLinkedList<>();
        if (!isEmpty()) {
            Node<E> current = tail.getNext();
            do {
                clonedList.addLast(current.getElement());
                current = current.getNext();
            } while (current != tail.getNext());
        }
        return clonedList;
    }


    public static void main(String[] args) {
        CircularlyLinkedList<Integer> list = new CircularlyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);

        CircularlyLinkedList<Integer> list2 = new CircularlyLinkedList<>();
        list2.addLast(3);
        list2.addLast(4);
        list2.addLast(1);
        list2.addLast(2);

        System.out.println(CircularlyLinkedList.compareLists(list,list2));
        CircularlyLinkedList<Integer> splittedList = list.split();
        System.out.println(list.getAll());
        System.out.println(splittedList.getAll());
        CircularlyLinkedList<Integer> cloned = list.clone();
        System.out.println(cloned.getAll());
    }
}

