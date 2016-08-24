/**
 * This class represents the Basher type of a ship, extends SpaceShip class.
 * it will try to collide with other ships.
 */
public class Basher extends SpaceShip{

    //class constants

    /** The distance in which the spaceship will activate her shields */
    private static final double distanceToTurnOnShields = 0.19;

    /**
     * class constructor. reaches the parent class constructor.
     * @param theShipType the type represents the subclass of the ship
     */
    public Basher(int theShipType){
        super(theShipType);
    }

    /**
     * implementation of the abstract method from the class it inherits from.
     * will perform additional actions for each round - the uniqe actions for the specific type.
     * @param game the SpaceWars object of the game the ship belongs to
     */
    protected void additionalShipActions(SpaceWars game){

        //always accelerates
        this.getPhysics().move(true,SpaceShip.noTurn);

        //checking the position of the closest ship
        SpaceShip closestShip = game.getClosestShipTo(this);

        //checking if the basher ship is too close to another ship.
        if (this.getPhysics().distanceFrom(closestShip.getPhysics())<=distanceToTurnOnShields){
            this.shieldOn();
        }
        //if negative - turn right
        if (this.getPhysics().angleTo(closestShip.getPhysics())<0){
            this.getPhysics().move(true,SpaceShip.turnRight);
        }

        //if positive - turn left
        if(this.getPhysics().angleTo(closestShip.getPhysics())>0){
            this.getPhysics().move(true,SpaceShip.turnLeft);
        }
    }
}
