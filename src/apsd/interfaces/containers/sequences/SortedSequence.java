package apsd.interfaces.containers.sequences;

import apsd.classes.utilities.Natural;
import apsd.interfaces.containers.base.SortedIterableContainer;

/** Interface: Sequence & SortedIterableContainer. */
public interface SortedSequence<Data extends Comparable<? super Data>> extends Sequence<Data> ,SortedIterableContainer <Data>{ // Must extend Sequence and SortedIterableContainer

  /* ************************************************************************ */
  /* Override specific member functions from MembershipContainer              */
  /* ************************************************************************ */

 @Override
	 default boolean Exists(Data dat) {
	     return Search(dat) != null;
	 }
	


  /* ************************************************************************ */
  /* Override specific member functions from Sequence                         */
  /* ************************************************************************ */
 @Override
 default Natural Search(Data dat) {

	        long start = 0;
	        long end = Size().ToLong();

	        while (start < end) {

	            // QUI resta identico al prof: (start + end) / 2
	            long middle = (start + end) / 2;

	            Natural pos = Natural.Of(middle);
	            Data current = GetAt(pos);
	            int comparison = current.compareTo(dat);

	            if (comparison == 0) {
	                return pos;
	            } else if (comparison < 0) {
	                start = middle + 1;
	            } else {
	                end = middle;
	            }
	        }

	        return null;
	    }

	  

}
