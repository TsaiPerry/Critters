package info.gridworld.critters;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;

/**
 * A <code>CellCritter</code> replicates based on its number of "cell neighbors"
 * - neighbors of type CellCritter.
 * <br />
 * Rules:
 * <ul>
 * <li>No neighbors of type CellCritter - it dies (of loneliness)</li>
 * <li>5 or more CellCritter neighbors - it dies (of overcrowding)</li>
 * <li>1 - 3 CellCritter neighbors - it produces a new CellCritter in a random
 * neighboring empty cell (if there is one available – if all are filled, it does
 * nothing).</li>
 * </ul>
 */
public class CellCritter extends Critter
{
    /**
     * A <code>CellCritter</code> processes nothing.
     * 
     * @param loc
     *            the location to process
     */
    public void processActors(ArrayList<Actor> loc)
    {
    }

    /**
     * Determines the number of CellCritter neighbors.
     * 
     * @return the number of neighbors of type CellCritter
     */
    private int countCellNeighbors()
    {
        int count = 0;
        Grid<Actor> gr = getGrid();
        Location loc = getLocation();
        for (Location neighborLoc : gr.getValidAdjacentLocations(loc))
        {
            Actor neighbor = gr.get(neighborLoc);
            if (neighbor instanceof CellCritter)
            {
                count++;
            }
        }
        return count;
    }

    /**
     * A <code>CellCritter</code> moves by following these steps. <br />
     * 1. Counts the number of CellCritter neighbors. <br />
     * 2. If the count is 0 or >= 5, it removes itself from the grid. <br />
     * 3. If the count is 1 - 3, it attempts to create a new CellCritter in a
     * random empty neighboring cell.
     */
    public void makeMove(Location loc)
    {
        int neighborCount = countCellNeighbors();

        if (neighborCount == 0 || neighborCount >= 5)
        {
            removeSelfFromGrid(); // Dies of loneliness or overcrowding
        }
        else if (neighborCount >= 1 && neighborCount <= 3)
        {
            ArrayList<Location> emptyNeighbors = getGrid().getEmptyAdjacentLocations(getLocation());
            if (!emptyNeighbors.isEmpty())
            {
                int randomIndex = (int) (Math.random() * emptyNeighbors.size());
                getGrid().put(emptyNeighbors.get(randomIndex), new CellCritter());
            }
        }
    }
}
