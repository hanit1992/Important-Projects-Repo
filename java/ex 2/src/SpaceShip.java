import java.awt.Image;
import oop.ex2.*;

/**
 * The SpaceShip class is an abstract class, who is being inherited from each of the different space ships types.
 * the common methods of all the ships are implemented here, and the method who identify them is an abstract method
 * of the class.
 */
public abstract class SpaceShip{

    /** class constants*/


    /** The initial maximal level of health*/
    private static final int initialMaximalHealth = 22;

    /** The initial maximal level of energy*/
    private static final int initialMaximalEnergyLevel = 210;

    /** The initial current level of energy*/
    private static final int initialCurrentEnergyLevel = 190;

    /** The amount of energy that will be added to a ship while "bashing"*/
    private static final int additionalEnergyWhileBashing = 18;

    /** The amount of health that will be reduced while colliding without a shield*/
    private static final int reduceHealthDueToDamage = 1;

    /** The amoust of energy that will be reduced while colliding without a shield*/
    private static final int reduceEnergyDueToDamage = 10;

    /** The amount of energy requierd for a single shot*/
    private static final int firingEnergyAmount = 19;

    /** The amount of energy required for a shield use*/
    private static final int shieldsEnergyAmount = 3;

    /** The amount of energy required for teleporting*/
    private static final int teleportingEnergyAmount = 140;

    /** The amount of energy increasing per round*/
    private static final int increasingEnergyAmount = 1;

    //protected class constants. only for the class who inherits from it.

    /** The int indicating turnning left*/
    protected static final int turnLeft = 1;

    /** The int indicating turnning right*/
    protected static final int turnRight = -1;

    /** The int indicating not to turn*/
    protected static final int noTurn = 0;


    //class data members

    /** A spaceShipPhysics object - in charge for the ship position in the game.*/
    SpaceShipPhysics spaceShipPhysics;

    /** Maximal Spaceship energy level.*/
    int maximalEnergy;

    /** Current Spaceship energy level*/
    int currentEnergy;

    /** Health level. health level is between 0-22*/
    int currentHealth;

    /** State of shields of the ship. default - false*/
    boolean areShieldsOn;

    /** rounds counter for firing control*/
    int countRoundsFromFiringTime = 0;

    /** The image of the ship*/
    Image shipImage;

    /** The specific type of the current ship*/
    int shipType;


    /**
     *class constructor
     * @param theShipType the current ship subclass
     */
    public SpaceShip(int theShipType){
        spaceShipPhysics = new SpaceShipPhysics();
        currentHealth = initialMaximalHealth;
        maximalEnergy = initialMaximalEnergyLevel;
        currentEnergy = initialCurrentEnergyLevel;
        areShieldsOn = false;
        shipType = theShipType;
        //addMyPhoto();
        if (this.shipType == SpaceWars.HUMAN_CONTROLLED_SHIP){
            shipImage = GameGUI.SPACESHIP_IMAGE;
        }
        else{
            shipImage = GameGUI.ENEMY_SPACESHIP_IMAGE;
        }
    }

    /**
     * Does all the common actions of the ships for a certain ship.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game){

        //turns of the activated shields from last round
        if (this.areShieldsOn){
            this.areShieldsOn=false;
            if (this.shipType == SpaceWars.HUMAN_CONTROLLED_SHIP){
                this.shipImage = GameGUI.SPACESHIP_IMAGE;
            }
            else {
                this.shipImage = GameGUI.ENEMY_SPACESHIP_IMAGE;
            }
        }
        //activating the ship movements oer round
        this.additionalShipActions(game);
        //increasing the energy
        this.currentEnergy+=increasingEnergyAmount;
        //updates rounds counter
        if(this.countRoundsFromFiringTime>0 && this.countRoundsFromFiringTime<8){
            this.countRoundsFromFiringTime+=1;
        }
        else if (this.countRoundsFromFiringTime==8){
            this.countRoundsFromFiringTime = 0;
        }
    }
    /**
     * This method is an abstract method for all the ship type to implement.
     * it includes all the actions of a ship that are uniqe for her type.
     * @param game the current game that the ship is taking place in.
     */
    protected abstract void additionalShipActions(SpaceWars game);

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip(){
        //shields are off.
        if(!areShieldsOn){
            this.currentHealth-=reduceHealthDueToDamage;
            this.maximalEnergy-=reduceEnergyDueToDamage;
            if(this.currentEnergy>this.maximalEnergy){this.currentEnergy=this.maximalEnergy;}
        }
        //shields are on.
        else{
            this.maximalEnergy+=additionalEnergyWhileBashing;
            this.currentEnergy+=additionalEnergyWhileBashing;
        }

    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset(){
        this.currentEnergy = initialCurrentEnergyLevel;
        this.maximalEnergy = initialMaximalEnergyLevel;
        this.currentHealth = initialMaximalHealth;
        this.areShieldsOn = false;
        this.countRoundsFromFiringTime = 0;
        //the class constructor is automatically creates a new ship in a new position.
        this.spaceShipPhysics = new SpaceShipPhysics();

    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        if(this.currentHealth==0){
            return true;
        }
        else {return false;}

    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.spaceShipPhysics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        //pays a price as long as the ship has no shield at the current round
        if(!this.areShieldsOn){
            this.currentHealth-=reduceHealthDueToDamage;
            this.maximalEnergy-=reduceEnergyDueToDamage;
            if(this.currentEnergy>maximalEnergy){this.currentEnergy = this.maximalEnergy;}
        }
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage(){
        return this.shipImage;
    }

    /**
     * Attempts to fire a shot.
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        //will activate if the ship has enough energy to do so.
        if(this.currentEnergy>= firingEnergyAmount && this.countRoundsFromFiringTime==0){
            game.addShot(spaceShipPhysics);
            this.currentEnergy-=firingEnergyAmount;
            this.countRoundsFromFiringTime=1;
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        //will activate if the ship has enough energy to do so.
        if(this.currentEnergy>=shieldsEnergyAmount){
            this.areShieldsOn=true;
            this.currentEnergy-=shieldsEnergyAmount;
            if (this.shipType == SpaceWars.HUMAN_CONTROLLED_SHIP){
                this.shipImage=GameGUI.SPACESHIP_IMAGE_SHIELD;
            }
            else {
                this.shipImage=GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
            }
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        //will activate if the ship has enough energy to do so.
        if (this.currentEnergy>=teleportingEnergyAmount){
            this.spaceShipPhysics = new SpaceShipPhysics();
            this.currentEnergy-=teleportingEnergyAmount;
        }

    }

}

