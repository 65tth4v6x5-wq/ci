package apsd.classes.containers.sequences.abstractbases;

import apsd.classes.utilities.MutableNatural;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.iterators.MutableBackwardIterator;
 import apsd.interfaces.containers.iterators.MutableForwardIterator;
 import apsd.interfaces.containers.sequences.MutableSequence;
 import apsd.interfaces.containers.sequences.Vector;

/** Object: Abstract vector base implementation. */
abstract public class VectorBase<Data> implements Vector<Data>{ // Must implement Vector

  protected Data[] arr;

  // VectorBase
  public VectorBase() {
      ArrayAlloc(Natural.ZERO);
  }
  
  public VectorBase(Natural insize) {
      if (insize == null) {
          throw new NullPointerException("Natural cannot be null");
      }
      ArrayAlloc(insize);
  }
  
  public VectorBase(TraversableContainer<Data> con) {
      if (con == null) {
          throw new NullPointerException("Traversable container cannot be null");
      }

      ArrayAlloc(con.Size());

      final MutableNatural index = new MutableNatural();
      con.TraverseForward(dat -> {
          SetAt(dat, index.GetNIncrement());
          return false;
      });
  }
  
  protected VectorBase(Data[] arr) {
      this.arr = arr;
  }
  
  // NewVector
  protected abstract VectorBase<Data> NewVector(Data[] arr);
  
  
  @SuppressWarnings("unchecked")
  protected void ArrayAlloc(Natural newsize) {

      long length = newsize.ToLong();

      if (length >= Integer.MAX_VALUE) {
          throw new ArithmeticException("Overflow: size cannot exceed Integer.MAX_VALUE");
      }

      arr = (Data[]) new Object[(int) length];
  }
  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */
  @Override
  public void Clear() {
      ArrayAlloc(Natural.ZERO);
  }
  /* ************************************************************************ */
  /* Override specific member functions from ResizableContainer               */
  /* ************************************************************************ */

  @Override
  public Natural Capacity() {
      return Natural.Of(arr.length);
  }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */
  protected class VectorFIterator implements MutableForwardIterator<Data> {

      protected long cur = 0L;

      @Override
      public boolean IsValid() {
          return cur < Size().ToLong();
      }

      @Override
      public void Reset() {
          cur = 0L;
      }

      @Override
      public Data GetCurrent() {
          if (!IsValid()) {
              throw new IllegalStateException("Iterator terminated");
          }
          return GetAt(Natural.Of(cur));
      }

      @Override
     public void SetCurrent(Data dat){
          if (!IsValid()) {
              throw new IllegalStateException("Iterator terminated");
          }
          SetAt(dat, Natural.Of(cur));
      }

      @Override
      public void Next() {
          if (!IsValid()) {
              throw new IllegalStateException("Iterator terminated");
          }
          cur++;
      }

      @Override
      public Data DataNNext() {
          if (!IsValid()) {
              throw new IllegalStateException("Iterator terminated");
          }
          Data value = GetAt(Natural.Of(cur));
          cur++;
          return value;
      }
  }

  @Override
  public MutableForwardIterator<Data> FIterator() {
      return new VectorFIterator();
  }

  
  
  protected class VectorBIterator implements MutableBackwardIterator<Data> {

      protected long cur = Size().ToLong() - 1;

      @Override
      public boolean IsValid() {
          return cur >= 0;
      }

      @Override
      public void Reset() {
          cur = Size().ToLong() - 1;
      }

      @Override
      public Data GetCurrent() {
          if (!IsValid()) {
              throw new IllegalStateException("Iterator terminated");
          }
          return GetAt(Natural.Of(cur));
      }

      @Override
      public void SetCurrent(Data dat) {
          if (!IsValid()) {
              throw new IllegalStateException("Iterator terminated");
          }
          SetAt(dat, Natural.Of(cur));
      }

      @Override
      public void Prev() {
          if (!IsValid()) {
              throw new IllegalStateException("Iterator terminated");
          }
          cur--;
      }

      @Override
      public Data DataNPrev() {
          if (!IsValid()) {
              throw new IllegalStateException("Iterator terminated");
          }
          Data value = GetAt(Natural.Of(cur));
          cur--;
          return value;
      }
  }

  @Override
  public MutableBackwardIterator<Data> BIterator() {
      return new VectorBIterator();
  }
  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
  public abstract Data GetAt(Natural pos);
  

  @Override
  @SuppressWarnings("unchecked")
  public MutableSequence<Data> SubSequence(Natural from, Natural to) {

      long fromIndex = from.ToLong();
      long toIndex = to.ToLong();

      long newLength = (toIndex - fromIndex) + 1;

      Data[] copy = (Data[]) new Object[(int) newLength];

      int write = 0;
      for (long read = fromIndex; read <= toIndex; read++, write++) {
          copy[write] = GetAt(Natural.Of(read));
      }

      return NewVector(copy);
  }


  /* ************************************************************************ */
  /* Override specific member functions from MutableSequence                  */
  /* ************************************************************************ */

  @Override
  public abstract void SetAt(Data dat, Natural pos);

}
