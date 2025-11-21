package apsd.interfaces.containers.collections;

 import apsd.classes.utilities.Natural;
 import apsd.interfaces.containers.sequences.RemovableAtSequence;

public interface Chain<Data> extends RemovableAtSequence<Data> { // Must extend RemovableAtSequence

  default boolean InsertIfAbsent(Data dat){
  return (!Exist(dat))? Insert(dat): false;
  }

  default void RemoveOccurrences (Data dat){
    Filter(elm-> !elm.equals(dat));
  }

  default Chain<Data> SubChain(Natural start, Natural end){
    return ( Chain<Data>) SubSequence(start,end);
  }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
 default Natural Search(Data elementoCercato){
     if(dat==Null) return Null;
     return RemovableAtSequence.Super.Search(dat);
  }

}
