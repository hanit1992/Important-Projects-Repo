/**
 * This class represents the Human type of the space ships, and extends the SpaceShip class.
 * it will use the gui game object, in order to identify certain typing from the user,
 * and will perform the ships actions accordingly.
 */
public class Human extends SpaceShip {

    /**
     * class constructor. reaches the parent class constructor.
     * @param theShipType the type represents the subclass of the ship
     */
    public Human(int theShipType){
        super(theShipType);
    }

    /**
     * implementation of the abstract method from the class it inherits from.
     * will perform additional actions for each round - the uniqe actions for the specific type.
     * @param game the SpaceWars object of the game the ship belongs to
     */
    protected void additionalShipActions(SpaceWars game){
        if (game.getGUI().isTeleportPressed()){
            this.teleport();
        }
        if (game.getGUI().isLeftPressed()){
            if (game.getGUI().isUpPressed()){
                this.spaceShipPhysics.move(true,SpaceShip.turnLeft);
            }
            else {
                this.spaceShipPhysics.move(false,SpaceShip.turnLeft);
            }
        }
        if (game.getGUI().isRightPressed()){
            if (game.getGUI().isUpPressed()){
                this.spaceShipPhysics.move(false,SpaceShip.turnRight);
            }
            else{
                this.spaceShipPhysics.move(false,SpaceShip.turnRight);
            }
        }
        if (game.getGUI().isUpPressed()){
            this.spaceShipPhysics.move(true,SpaceShip.noTurn);
        }
        if (game.getGUI().isShieldsPressed()){
            this.shieldOn();
        }
        if (game.getGUI().isShotPressed()){
            this.fire(game);
        }
    }
}
