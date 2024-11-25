package hazards.domain.inter;



public interface TreeInterface<E> {

    void add(E element);

    void clear();

    void remove(E element);

    boolean contain(E element);

    boolean isEmpty();
}
