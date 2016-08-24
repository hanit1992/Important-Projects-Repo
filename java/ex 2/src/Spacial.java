import java.util.Random;

/**
 * This class represents the Spacial ship type. the spacial ship will pick a random ship in the game, and will
 * always go in her direction and will try to shoot it.
 */
public class Spacial extends SpaceShip {

    //data members

    /** A random object for Spacial to use */
    Random shipRandom = new Random();

    /** A ship in the game that the spacial ship will chase after */
    SpaceShip theChosenShip;


    /**
     * class constructor. reaches the parent class constructor.
     * @param theShipType the type represents the subclass of the ship
     */
    public Spacial(int theShipType) {
        super(theShipType);
    }

    /**
     * an helping method for the spacial ship, to implement her strategy.
     * being called from SpaceShipFactory in order to choose the spacial ship
     * for each game once.
     * @param aShip a Spacial object of a ship.
     * @param isAllSpacial indicates whether all the ships are spacial.
     */
    public static void choosingSpaceShip(Spacial aShip,boolean isAllSpacial){
        //in case there are only Spacial ships in the game, return the ship itself.
        int numberOfShips = SpaceShipFactory.shipsArray.length;
        if (numberOfShips==1 || isAllSpacial){
            aShip.theChosenShip = aShip;
            return;
        }
        //choosing a ship in the current game randomly. if the ship is Spacial, will try again.
        //continue is used for intention clarification
        while(true){
            int theShipNumber = aShip.shipRandom.nextInt(numberOfShips);
            SpaceShip aShipCandidate = SpaceShipFactory.shipsArray[theShipNumber];
            if (aShipCandidate.shipType == SpaceWars.SPECIAL_SHIP){continue;}
            else{
                aShip.theChosenShip = aShipCandidate;
                break;
            }
        }
    }


    /**
     * implementation of the abstract method from the class it inherits from.
     * will perform additional actions for each round - the uniqe actions for the specific type.
     * @param game the SpaceWars object of the game the ship belongs to
     */
    protected void additionalShipActions(SpaceWars game){

        //if there are only spacial ships in the game, just turn and fire.
        if(this.theChosenShip.shipType == SpaceWars.SPECIAL_SHIP){
            this.getPhysics().move(true,turnRight);
            this.fire(game);
        }

        //if negative - turn right
        if (this.getPhysics().angleTo(theChosenShip.getPhysics())<0){
            this.getPhysics().move(true,SpaceShip.turnRight);
            this.fire(game);
        }

        //if positive - turn left
        if(this.getPhysics().angleTo(theChosenShip.getPhysics())>0){
            this.getPhysics().move(true,SpaceShip.turnLeft);
            this.fire(game);
        }
    }
}
