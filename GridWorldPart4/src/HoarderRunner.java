import java.awt.Color;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.critters.HoarderCritter;
import info.gridworld.grid.Location;

public class HoarderRunner {
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        world.add(new Location(2,4), new Critter());
        world.add(new Location(5,9), new Critter());
        world.add(new Location(2, 3), new HoarderCritter(new Location(1, 2)));
//        world.add(new Location(5, 8), new HoarderCritter(new Location(2, 3)));
        world.show();
    }
}
