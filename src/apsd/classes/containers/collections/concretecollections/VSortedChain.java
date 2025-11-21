package apsd.classes.containers.collections.concretecollections;

 import apsd.classes.containers.collections.concretecollections.bases.VChainBase;
 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.base.TraversableContainer;
 import apsd.interfaces.containers.collections.SortedChain;
 import apsd.interfaces.containers.sequences.DynVector;

/** Object: Concrete set implementation via (dynamic circular) vector. */
public class VSortedChain<Data extends Comparable<? super Data>> extends VChainBase<Data> implements SortedChain<Data> { // Must extend VChainBase and implements SortedChain

   public VSortedChain() { super(); }

   public VSortedChain(VSortedChain<Data> chn) { super(chn); }

   public VSortedChain(TraversableContainer<Data> con) {
       con.TraverseForward(dat -> { Insert(dat); return false; });
   }

   public VSortedChain(DynVector<Data> vec) { super(vec); }

  // NewChain
    @Override
    protected VChainBase<Data> NewChain(DynVector<Data> vec) {
       return new VSortedChain<>(vec);
    }

  /* ************************************************************************ */
  /* Override specific member functions from InsertableContainer              */
  /* ************************************************************************ */

  @Override
    public boolean Insert(Data dat) {
      if(dat == null) return false;
      Natural prd = SearchPredecessor(dat);
      Natural pos = (prd == null) ? Natural.ZERO : prd.Increment();
      //vec.ShiftRight(pos);
      //vec.SetAt(dat,pos);
      vec.InsertAt(dat, pos);
      return true;
  }

  /* ************************************************************************ */
  /* Override specific member functions from Chain                            */
  /* ************************************************************************ */

  @Override
    /*public boolean InsertIfAbsent(Data dat) {
      //if (dat == null) return false;
      Natural prd = SearchPredecessor(dat);
      Natural pos = (prd == null) ? Natural.ZERO : prd.Increment();
      if (IsInBound(pos) && vec.GetAt(pos).equals(dat)) return false;
      vec.ShiftRight(pos);
      vec.SetAt(dat,pos);
      return true;
  }*/
  public boolean InsertIfAbsent(Data dat) {
      if (dat == null) return false;
      Natural prd = SearchPredecessor(dat);
      Natural pos = (prd == null) ? Natural.ZERO : prd.Increment();
      if (IsInBound(pos)) {
          Data elm = vec.GetAt(pos);
          if (elm != null && elm.equals(dat)) {
              return false;
          }
      }
      //vec.ShiftRight(pos);
      //vec.SetAt(dat, pos);
      vec.InsertAt(dat, pos);
      return true;
  }

  @Override
    /*public void RemoveOccurrences(Data dat) {
      //if (dat == null) return;
      Natural prd = SearchPredecessor(dat);
      long cur = (prd == null) ? 0 : prd.ToLong() + 1;
      long ini = cur;
      long size = vec.Size().ToLong();
      for (; cur < size && vec.GetAt(Natural.Of(cur)).compareTo(dat) == 0; cur++) {
      }
      long del = cur - ini;
      vec.ShiftLeft(Natural.Of(ini), Natural.Of(del));
  }*/
  public void RemoveOccurrences(Data dat) {
      if (dat == null) return;
      Natural prd = SearchPredecessor(dat);
      long cur = (prd == null) ? 0 : prd.ToLong() + 1;
      long ini = cur;
      long size = vec.Size().ToLong();
      while (cur < size) {
          Data elm = vec.GetAt(Natural.Of(cur));
          if (elm != null && elm.compareTo(dat) == 0) {
              cur++;
          } else {
              break;
          }
      }
      long del = cur - ini;
      if (del > 0) {
          vec.ShiftLeft(Natural.Of(ini), Natural.Of(del));
      }
  }

}