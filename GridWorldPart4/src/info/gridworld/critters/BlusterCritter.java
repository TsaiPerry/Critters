package info.gridworld.critters;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

/**
 * A <code>BlusterCritter</code> looks at all of the neighbors within two steps of its current
 * location. (For a BlusterCritter not near an edge, this includes 24
 * locations). It counts the number of critters in those locations. If there are fewer
 * than c critters, the BlusterCritter’s color gets brighter (color values
 * increase). If there are c or more critters, the BlusterCritter’s color
 * darkens (color values decrease). Here, c is a value that indicates the courage of the
 * critter. It should be set in the constructor.
 * 
 * @author Perry Tsai
 * @version 11/17/2019
 */
public class BlusterCritter extends Critter
{
    private int courage;

    /**
     * Constructs a BlusterCritter with default courage of 0.
     */
    public BlusterCritter()
    {
        this(0);
    }

    /**
     * Constructs a BlusterCritter with specified courage.
     * 
     * @param c the courage of this BlusterCritter
     */
    public BlusterCritter(int c)
    {
        courage = c;
    }

    /**
     * Gets the neighbors of this critter within two steps.
     * 
     * @return a list of actors within two steps of this critter
     */
    public ArrayList<Actor> getNeighbors2Steps()
    {
        ArrayList<Actor> neighbors = new ArrayList<Actor>();
        Location loc = getLocation();
        Grid<Actor> gr = getGrid();

        for (int r = loc.getRow() - 2; r <= loc.getRow() + 2; r++)
        {
            for (int c = loc.getCol() - 2; c <= loc.getCol() + 2; c++)
            {
                // skip current location
                if (r == loc.getRow() && c == loc.getCol())
                {
                    continue;
                }

                Location tempLoc = new Location(r, c);
                if (gr.isValid(tempLoc))
                {
                    Actor a = gr.get(tempLoc);
                    if (a != null)
                    {
                        neighbors.add(a);
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Counts the number of critters within two steps.
     * 
     * @return the number of critters within two steps
     */
    public int countCritterNeighbors2Steps()
    {
        int count = 0;
        for (Actor a : getNeighbors2Steps())
        {
            if (a instanceof Critter)
            {
                count++;
            }
        }
        return count;
    }

    /**
     * A BlusterCritter gets brighter if there are fewer than c critters within
     * two steps, and darker if there are c or more critters within two steps.
     */
    public void processActors(ArrayList<Actor> actors)
    {
        int n = countCritterNeighbors2Steps();
        if (n < courage)
        {
            brighter();
        }
        else
        {
            darker();
        }
    }

    /**
     * Makes the color of this critter brighter.
     */
    public void brighter()
    {
        Color c = getColor();
        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();

        // brighten each color component by 10, up to 255
        red = Math.min(red + 20, 255);
        green = Math.min(green + 20, 255);
        blue = Math.min(blue + 20, 255);

        setColor(new Color(red, green, blue));
    }

    /**
     * Makes the color of this critter darker.
     */
    public void darker()
    {
        Color c = getColor();
        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();

        // darken each color component by 10, down to 0
        red = Math.max(red - 20, 0);
        green = Math.max(green - 20, 0);
        blue = Math.max(blue - 20, 0);

        setColor(new Color(red, green, blue));
    }
}


