/**
 * The purpose of this enum is to be able to create ID's for each possible GameObject
 * So that our GameHandler can distinguish between the various types of game objects we can have.
 */
public enum ID {
    Player1(),
    Player2(),
    Bullet(),
    BreakableWall(),
    HealthBoost(),
    AmmoBoost(),
    Wall(),
    LifeBoost();
}
