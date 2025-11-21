package apsd.classes.containers.collections.concretecollections.bases;

import apsd.classes.containers.sequences.DynCircularVector;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.Chain;
import apsd.interfaces.containers.iterators.BackwardIterator;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.DynVector;
import apsd.interfaces.containers.sequences.Sequence;
import apsd.interfaces.traits.Predicate;

/** Object: Abstract list base implementation on (dynamic circular) vector. */
abstract public class VChainBase<Data> implements Chain<Data>{ // Must 

  protected final DynVector<Data> vettore;

    public VChainBase() 
    { 
        vettore = new DynCircularVector<>(); 
    }

    public VChainBase(DynVector<Data> vettoreInput) 
    { 
        this.vettore = vettoreInput; 
    }

    public VChainBase(TraversableContainer<Data> contenitoreInput) 
    {
        vettore = new DynCircularVector<>(contenitoreInput);
    }

    protected abstract VChainBase<Data> NewChain(DynVector<Data> vettorePerNuovaCatena);

  /* ************************************************************************ */
  /* Override specific member functions from Container                        */
  /* ************************************************************************ */

  @Override
    public Natural Size() 
    { 
        return vec.Size(); 
    }

  /* ************************************************************************ */
  /* Override specific member functions from ClearableContainer               */
  /* ************************************************************************ */

  @Override
    public void Clear() 
    { 
        vec.Clear(); 
    }

  /* ************************************************************************ */
  /* Override specific member functions from RemovableContainer               */
  /* ************************************************************************ */

  public boolean Remove(Data dato) 
    {
        if (dato == null) 
        {
            return false;
        }
        
        Natural posizione = vec.Search(dato);
        
        if (posizione == null) 
        {
            return false;
        }
        
        vec.ShiftLeft(posizione);
        return true;
    }

  /* ************************************************************************ */
  /* Override specific member functions from IterableContainer                */
  /* ************************************************************************ */

  @Override
    public ForwardIterator<Data> FIterator() 
    { 
        return vec.FIterator(); 
    }

    @Override
    public BackwardIterator<Data> BIterator() 
    { 
        return vec.BIterator(); 
    }

  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */

  @Override
    public Data GetAt(Natural posizione) 
    {
        return vec.GetAt(posizione);
    }

    @Override
    public Sequence<Data> SubSequence(Natural da, Natural a) 
    {
        long inizio = da.ToLong();
        long fine = a.ToLong();
        
        DynCircularVector<Data> nuovoVettore = new DynCircularVector<>(Natural.of(fine - inizio + 1));
        
        for (long r = inizio, w = 0; r <= fine; r++, w++) 
        {
            Data elementoLetto = vec.GetAt(Natural.of(r));
            nuovoVettore.SetAt(elementoLetto, Natural.of(w));
        }
        
        return NewChain(nuovoVettore);
    }
  /* ************************************************************************ */
  /* Override specific member functions from RemovableAtSequence              */
  /* ************************************************************************ */

  @Override
    public Data AtNRemove(Natural posizione) 
    {
        return vec.AtNRemove(posizione);
    }
  /* ************************************************************************ */
  /* Override specific member functions from Collection                       */
  /* ************************************************************************ */

  @Override
    public boolean Filter(Predicate<Data> predicato) 
    {
        long rimossi = 0;
        
        if (predicato != null) 
        {
            MutableForwardIterator<Data> scrittore = vec.FIterator();
            
            for (; scrittore.IsValid(); scrittore.Next()) 
            {
                if (!predicato.Apply(scrittore.GetCurrent())) 
                {
                    rimossi++;
                    scrittore.SetCurrent(null);
                }
            }

            scrittore.Reset();
            MutableForwardIterator<Data> lettore = vec.FIterator();

            for (; lettore.IsValid(); lettore.Next()) 
            {
                if (lettore.GetCurrent() != null) 
                {
                    Data datoValido = lettore.GetCurrent();
                    lettore.SetCurrent(null);
                    scrittore.SetCurrent(datoValido);
                    scrittore.Next();
                }
            }

            vec.Reduce(Natural.of(rimossi));
        }
        
        return (rimossi > 0);
    }
}
