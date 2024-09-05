package info.gridworld.critters;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * A <code>CaterpillarCritter</code> only moves forward. When it is unable to
 * move forward (because it is blocked by the edge of the Grid or another
 * Actor), it turns 90 degrees to the right. CaterpillarCritter is a herbivore
 * and eats only Flowers.
 */
public class CaterpillarCritter extends Critter
{
    /**
     * A CaterpillarCritter gets food from eating flowers.
     * @param loc the location to process
     */
    public void processFood(Location loc)
    {
        Grid<Actor> gr = getGrid();
        Actor a = gr.get(loc);
        if (a instanceof Flower)
        {
            a.removeSelfFromGrid();
        }
    }

    /**
     * A <code>CaterpillarCritter</code> moves by following these steps. <br />
     * 1. If it can move forward, it moves forward. <br />
     * 2. Otherwise, it turns 90 degrees to the right.
     */
    public void makeMove()
    {
        if (canMove())
        {
            move();
        }
        else
        {
            setDirection(getDirection() + Location.RIGHT);
        }
    }

    /**
     * Tests whether this critter can move forward into a location that is empty
     * or contains a flower.
     * @return <code>true</code> if this critter can move forward into a location that is empty
     * or contains a flower.
     */
    public boolean canMove()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
        {
            return false;
        }
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (!gr.isValid(next))
        {
            return false;
        }
        Actor neighbor = gr.get(next);
        return (neighbor == null) || (neighbor instanceof Flower);
        // ok to move into empty location or onto flower
        // not ok to move onto any other actor
    }
}

