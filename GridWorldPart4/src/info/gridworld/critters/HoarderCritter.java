package info.gridworld.critters;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;

/**
 * A <code>HoarderCritter</code> picks up an adjacent <code>Actor</code> and carries it back to its home location.
 * It will not pick up the same <code>Actor</code> twice.
 * 
 * @author Perry Tsai
 * @version 11/17/2019
 */
public class HoarderCritter extends Critter
{
    private Location home;
    private Actor heldActor;

    /**
     * Constructs a <code>HoarderCritter</code> with a specified home location.
     * 
     * @param home the home location for this <code>HoarderCritter</code>
     */
    public HoarderCritter(Location home)
    {
        this.home = home;
        heldActor = null;
    }

    /**
     * If holding an <code>Actor</code>, moves towards home. Otherwise, moves like a
     * <code>Critter</code>.
     */
    public void act()
    {
        if (heldActor != null)
        {
            moveTowardHome();
        }
        else
        {
            super.act();
        }
    }

    /**
     * If adjacent to other <code>Actor</code>s, randomly picks one up.
     */
    public void processActors(ArrayList<Actor> actors)
    {
        if (!actors.isEmpty() && heldActor == null)
        {
            int randomIndex = (int) (Math.random() * actors.size());
            Actor actorToPickUp = actors.get(randomIndex);

            // Ensure it doesn't pick up the same actor twice
            if (!actorToPickUp.equals(heldActor))
            {
                heldActor = actorToPickUp;
                heldActor.removeSelfFromGrid();
            }
        }
    }

    /**
     * Moves the <code>HoarderCritter</code> towards its home location, following
     * specific rules.
     */
    private void moveTowardHome()
    {
        Location currentLoc = getLocation();

        if (currentLoc.equals(home))
        {
            dropActor();
            return;
        }

        ArrayList<Location> validMoves = getValidHomewardMoves(currentLoc);

        if (!validMoves.isEmpty())
        {
            int randomIndex = (int) (Math.random() * validMoves.size());
            moveTo(validMoves.get(randomIndex));
        }
        else
        {
            dropActor();
        }
    }

    /**
     * Gets the valid move locations for the <code>HoarderCritter</code> to move
     * towards home.
     * 
     * @param currentLoc the current location of the <code>HoarderCritter</code>
     * @return an <code>ArrayList</code> of valid move locations
     */
    private ArrayList<Location> getValidHomewardMoves(Location currentLoc)
    {
        ArrayList<Location> validMoves = new ArrayList<Location>();
        Grid<Actor> gr = getGrid();

        if (currentLoc.getRow() == home.getRow())
        {
            addValidMoveInDirection(validMoves, currentLoc, currentLoc.getDirectionToward(home));
        }
        else if (currentLoc.getCol() == home.getCol())
        {
            addValidMoveInDirection(validMoves, currentLoc, currentLoc.getDirectionToward(home));
        }
        else
        {
            int dir = currentLoc.getDirectionToward(home);
            addValidMoveInDirection(validMoves, currentLoc, dir);
            addValidMoveInDirection(validMoves, currentLoc, dir + Location.RIGHT);
            addValidMoveInDirection(validMoves, currentLoc, dir + Location.LEFT);
        }

        return validMoves;
    }

    /**
     * Adds a move location to the list of valid moves if it is valid and empty.
     * 
     * @param validMoves the list of valid move locations
     * @param currentLoc the current location of the <code>HoarderCritter</code>
     * @param direction  the direction to check for a valid move
     */
    private void addValidMoveInDirection(ArrayList<Location> validMoves, Location currentLoc, int direction)
    {
        Grid<Actor> gr = getGrid();
        Location nextLoc = currentLoc.getAdjacentLocation(direction);
        if (gr.isValid(nextLoc) && gr.get(nextLoc) == null)
        {
            validMoves.add(nextLoc);
        }
    }

    /**
     * Drops the held <code>Actor</code> at the <code>HoarderCritter</code>'s
     * current location and moves the <code>HoarderCritter</code> to a random
     * empty adjacent location. If it cannot move, it removes itself from the grid
     * and places the held <code>Actor</code> in its old location.
     */
    private void dropActor()
    {
        if (heldActor != null)
        {
            ArrayList<Location> emptyLocs = getGrid().getEmptyAdjacentLocations(getLocation());
            if (!emptyLocs.isEmpty())
            {
                int randomIndex = (int) (Math.random() * emptyLocs.size());
                Location dropLoc = getLocation();
                moveTo(emptyLocs.get(randomIndex));
                heldActor.putSelfInGrid(getGrid(), dropLoc);
            }
            else
            {
                Location oldLoc = getLocation();
                removeSelfFromGrid();
                heldActor.putSelfInGrid(getGrid(), oldLoc);
            }
            heldActor = null;
        }
    }
}
