package agh.ics.oop;

import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected HashMap<Vector2d, Animal> animals = new LinkedHashMap<>();
    MapVisualizer map = new MapVisualizer(this);

    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        if (!this.canMoveTo(animal.getPosition())) {
            throw new IllegalArgumentException(animal.getPosition() + " is not legal move specification");
        }
        animals.put(animal.getPosition(), animal);
        animal.addObserver(this);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (!isOccupied(position));
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = animals.get(oldPosition);
        animals.remove(oldPosition);
        animals.put(newPosition, animal);
    }

    protected Vector2d[] getAnimalsAndGrass() {
        Vector2d[] onlyAnimals = new Vector2d[animals.size()];
        int index = 0;
        for (Vector2d key : animals.keySet()) {
            onlyAnimals[index] = key;
            index++;
        }
        return onlyAnimals;
    }


    public abstract Vector2d getUpperRight();

    public abstract Vector2d getLowerLeft();

    @Override
    public String toString() {
        return map.draw(getLowerLeft(), getUpperRight());
    }
}