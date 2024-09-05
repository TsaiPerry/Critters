import java.awt.Color;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.critters.BlusterCritter;
import info.gridworld.grid.Location;

public class BlusterRunner {
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        world.add(new Location(2,4), new Critter());
        world.add(new Location(5,9), new Critter());
        world.add(new Location(2, 3), new BlusterCritter(1));
        world.add(new Location(5, 8), new BlusterCritter(2));
        world.show();
    }
}
