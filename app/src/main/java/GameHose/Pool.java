package GameHose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/21/2017.
 */

public class Pool<T> {//create generic objects,,so that our recycler class can handle any objects
    public interface PoolObjectFactory<T> {
        public T createObject();}
        private final List< T > freeObjects;
        private final PoolObjectFactory <T> factory;
        private final int maxSize;
        public Pool(PoolObjectFactory <T> factory, int maxSize) {
            this.factory = factory;
            this.maxSize = maxSize;
            this.freeObjects = new ArrayList< T >(maxSize);
        }
        public T newObject() {
            T object = null;
            if (freeObjects.isEmpty())
                object = factory.createObject();
            else
                object = freeObjects.remove(freeObjects.size() - 1);
            return object;
        }
        public void free(T object) {
            if (freeObjects.size() < maxSize)
                freeObjects.add(object);
        }
    }
