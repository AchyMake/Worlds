package org.achymake.worlds.handlers;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class CreatorHandler {
    private WorldCreator getWorldCreator(String worldName) {
        return new WorldCreator(worldName);
    }
    public WorldInfo add(String worldName) {
        var creator = getWorldCreator(worldName);
        return creator.createWorld();
    }
    public WorldInfo create(String worldName, World.Environment environment) {
        var creator = getWorldCreator(worldName);
        creator.environment(environment);
        return creator.createWorld();
    }
    public WorldInfo create(String worldName, World.Environment environment, long seed) {
        var creator = getWorldCreator(worldName);
        creator.environment(environment);
        creator.seed(seed);
        return creator.createWorld();
    }
    public WorldInfo createRandom(String worldName, World.Environment environment) {
        var creator = getWorldCreator(worldName);
        creator.environment(environment);
        creator.seed(new Random().nextLong());
        return creator.createWorld();
    }
}