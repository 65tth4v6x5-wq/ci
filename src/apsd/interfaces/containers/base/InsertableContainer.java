package apsd.interfaces.containers.base;
import apsd.classes.utilities.Box;

/** Interface: Container con supporto all'inserimento di un dato. */
public interface InsertableContainer<Data> extends Container { // Must extend Container

  // Insert
	boolean insert (Data dat);

  // InsertAll

	default boolean insertAll(TraversableContainer<Data> conn) {
		final Box<Boolean> flag = new Box<Boolean>(true);
		if(conn!= null) conn.TraverseForward(dat -> {flag.Set(flag.Get() && insert(dat)); return false;});
		return flag.Get();
	}
	
  // InsertSome
	default boolean insertSome(TraversableContainer<Data> conn) {
		final Box<Boolean> flag = new Box<Boolean>(false);
		if(conn!= null) conn.TraverseForward(dat -> {flag.Set(flag.Get() || insert(dat)); return false;});
		return flag.Get();
	}
	
	
}
