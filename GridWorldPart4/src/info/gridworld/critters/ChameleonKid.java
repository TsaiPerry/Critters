package info.gridworld.critters;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

/**
 * A <code>ChameleonKid</code> takes on the color of neighboring actors as
 * it moves through the grid. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class ChameleonKid extends ChameleonCritter
{
    /**
     * A ChameleonKid changes its color to the color of one
     * of the actors immediately in front or behind. If there is no actor in either of these
     * locations, then the ChameleonKid darkens like the modified
     * ChameleonCritter.
     */
    public void processActors(ArrayList<Actor> actors)
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
        {
            return;
        }
        Location loc = getLocation();
        Location front = loc.getAdjacentLocation(getDirection());
        Location back = loc.getAdjacentLocation(getDirection() + Location.HALF_CIRCLE);
        
        if (gr.isValid(front) && gr.get(front) != null) {
            setColor(gr.get(front).getColor());
        } else if (gr.isValid(back) && gr.get(back) != null) {
            setColor(gr.get(back).getColor());
        } else {
            darker();
        }
    }
}
