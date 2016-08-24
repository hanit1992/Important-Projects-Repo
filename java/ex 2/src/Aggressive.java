
/**
 * This class represents the Aggressive type of a ship, and extends SpaceShip class.
 * this type will always accelerate, and will try to shoot the closest ship whenever he has a close angle to it.
 */
public class Aggressive extends SpaceShip {

    //class constants

    /** The angle in which the spaceship will shoot the other */
    private static final double angleToShoot = 0.21;

    /**
     * class constructor. reaches the parent class constructor.
     * @param theShipType the type represents the subclass of the ship
     */
    public Aggressive(int theShipType){
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

        //checking if the angle of the Aggressive ship from the closest ship is small enough, in order to shoot it.
        if (Math.abs(this.getPhysics().angleTo(closestShip.getPhysics()))<angleToShoot){
            this.fire(game);
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
