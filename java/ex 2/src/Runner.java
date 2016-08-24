/**
 * This class represents the runner space ship type. it extends the class SpaceShip.
 * the runner type will run from any close ship and will teleport when it feels too close.
 */
public class Runner extends SpaceShip {

    //class constants

    /** The minimal angel rate for the ship to teleport. */
    private static final double angleTeleportingRate = 0.23;

    /** The minimal distance rate for the ship to teleport. */
    private static final double distanceTeleportingRate = 0.25;

    /**
     * class constructor. reaches the parent class constructor.
     * @param theShipType the type represents the subclass of the ship
     */
    public Runner(int theShipType){
        super(theShipType);
    }

    /**
     * implementation of the abstract method from the class it inherits from.
     * will perform additional actions for each round - the uniqe actions for the specific type.
     * @param game the SpaceWars object of the game the ship belongs to
     */
    protected void additionalShipActions(SpaceWars game){

        //always accelerates
        this.spaceShipPhysics.move(true,SpaceShip.noTurn);

        //checking the position of the closest ship
        SpaceShip closestShip = game.getClosestShipTo(this);

        //checking if the runner ship is too close to another ship. uses the angle to method, because if
        //the angle it should turn is less than the minimal setted number, it is too close.
        if (Math.abs(this.spaceShipPhysics.angleTo(closestShip.spaceShipPhysics))<angleTeleportingRate
                && this.spaceShipPhysics.distanceFrom(closestShip.spaceShipPhysics)<distanceTeleportingRate){
            this.teleport();
        }

        //if negative - turn left - (this method is being used as the exact opposite recommendation)
        if (this.spaceShipPhysics.angleTo(closestShip.spaceShipPhysics)<0){
            this.spaceShipPhysics.move(true,SpaceShip.turnLeft);
        }

        //if positive - turn right - (this method is being used as the exact opposite recommendation)
        if(this.spaceShipPhysics.angleTo(closestShip.spaceShipPhysics)>0){
            this.spaceShipPhysics.move(true,SpaceShip.turnRight);
        }
    }
}


