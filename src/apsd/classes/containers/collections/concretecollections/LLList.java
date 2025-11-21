package apsd.classes.containers.collections.concretecollections;

import apsd.classes.containers.collections.concretecollections.bases.LLChainBase;
import apsd.classes.containers.collections.concretecollections.bases.LLNode;
import apsd.classes.utilities.Box;
import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.TraversableContainer;
import apsd.interfaces.containers.collections.List;
import apsd.interfaces.containers.iterators.ForwardIterator;
import apsd.interfaces.containers.iterators.MutableBackwardIterator;
import apsd.interfaces.containers.iterators.MutableForwardIterator;
import apsd.interfaces.containers.sequences.MutableSequence;

/** Object: Concrete list implementation on linked-list. */
public class LLList<Data> extends LLChainBase<Data> implements List<Data> 
{

    public LLList() 
    {
      
    }

    public LLList(TraversableContainer<Data> contenitoreInput) 
    { 
        super(contenitoreInput); 
    }

    protected LLList(long grandezza, LLNode<Data> testa, LLNode<Data> coda) 
    { 
        super(grandezza, testa, coda); 
    }

    @Override
    protected LLChainBase<Data> NuovaCatena(long grandezza, LLNode<Data> testa, LLNode<Data> coda) 
    { 
        return new LLList<>(grandezza, testa, coda); 
    }

    /* ************************************************************************ */
    /* Override specific member functions from MutableIterableContainer         */
    /* ************************************************************************ */

    @Override
    public MutableForwardIterator<Data> FIterator() 
    { 
        return new ListFIterator(); 
    }

    @Override
    public MutableBackwardIterator<Data> BIterator() 
    { 
        return new ListBIterator(); 
    }

    /* ************************************************************************ */
    /* Override specific member functions from MutableSequence                  */
    /* ************************************************************************ */

   public void SetAt(Data dat, Natural pos) {
        //if (dat == null) return;
        long idx = ExcIfOutOfBound(pos);
        ForwardIterator<Box<LLNode<Data>>> itr = FRefIterator();
        itr.Next(idx);
        itr.GetCurrent().Get().Set(dat);
    }

    @Override
    public void SetFirst(Data dato) 
    {
        if (dato == null) 
        {
            return;
        }
        
        if (riferimentoTesta.IsNull()) 
        {
            throw new IndexOutOfBoundsException("First element does not exist!");
        }
        
        // Imposta il dato nel primo nodo
        riferimentoTesta.Get().Set(dato);
    }

    @Override
    public void SetLast(Data dato) 
    {
        if (dato == null) 
        {
            return;
        }
        
        if (riferimentoCoda.IsNull()) 
        {
            throw new IndexOutOfBoundsException("Last element does not exist!");
        }
        
        // Imposta il dato nell'ultimo nodo
        riferimentoCoda.Get().Set(dato);
    }

    @Override
    public MutableSequence<Data> SubSequence(Natural da, Natural a) 
    {
        return (MutableSequence<Data>) super.SubSequence(da, a);
    }

    /* ************************************************************************ */
    /* Override specific member functions from InsertableAtSequence             */
    /* ************************************************************************ */

    @Override
    public void InsertAt(Data dato, Natural posizione) 
    {
        if (dato == null) 
        {
            return;
        }
        
        if (posizione == null) 
        {
            throw new NullPointerException("Natural number cannot be null!");
        }

        long indice = posizione.ToLong();
        
        // Controllo se l'indice è valido (può essere uguale a size per l'inserimento in coda)
        if (indice > dimensione.ToLong()) 
        {
            throw new IndexOutOfBoundsException("Index out of bounds: " + indice + "; Size: " + dimensione.ToLong());
        }

        if (indice == dimensione.ToLong()) 
        {
            InsertLast(dato);
        } 
        else 
        {
            // Uso l'iteratore per arrivare alla posizione giusta
            ForwardIterator<Box<LLNode<Data>>> iteratore = FRefIterator();
            iteratore.Next(indice);
            
            Box<LLNode<Data>> cursore = iteratore.GetCurrent();
            // Creo il nuovo nodo collegandolo al successivo e aggiorno il cursore
            cursore.Set(new LLNode<>(dato, cursore.Get()));
            
            dimensione.Increment();
        }
    }

    @Override
    public void InsertFirst(Data dato) 
    {
        if (dato == null) 
        {
            return;
        }
        
        // Creo il nuovo nodo che punta alla vecchia testa
        LLNode<Data> nuovoNodo = new LLNode<>(dato, riferimentoTesta.Get());
        riferimentoTesta.Set(nuovoNodo);
        
        // Se la lista era vuota, questo nodo è anche la coda
        if (riferimentoCoda.IsNull()) 
        {
            riferimentoCoda.Set(nuovoNodo);
        }
        
        dimensione.Increment();
    }

    @Override
    public void InsertLast(Data dato) 
    {
        if (dato == null) 
        {
            return;
        }
        
        LLNode<Data> nuovoNodo = new LLNode<>(dato, null);
        
        if (riferimentoCoda.IsNull()) 
        {
            // Se è vuota, diventa anche la testa
            riferimentoTesta.Set(nuovoNodo);
        } 
        else 
        {
            // Altrimenti lo collego dopo l'attuale coda
            riferimentoCoda.Get().SetNext(nuovoNodo);
        }
        
        riferimentoCoda.Set(nuovoNodo);
        dimensione.Increment();
    }
  }