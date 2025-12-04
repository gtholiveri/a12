package model.animal.components;


/**
 * This interface has no reason to exist so I removed it. It's ccompletely redundant to have an additional contract that guarantees a given object has a takeWalk() method when there's an entire subclass to do exactly that already, and there's no need to guarantee this behavior across multiple hierarchies.
 */
public interface Walkable {
    public void takeWalk(double minutes);
}
