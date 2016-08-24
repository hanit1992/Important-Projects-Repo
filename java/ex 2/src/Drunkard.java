import java.util.Random;

/**
 * This class represents the Drunkard ship type, extends SpaceShip class.
 * it will behave in a 'drunken' way while playing -  will always accelerate, try to move in the direction of
 * other ships, and each loop it will spin in a spot for a few rounds.
 */
public class Drunkard extends SpaceShip{

   //class constants

   /** Maximal number of spins each round */
   private static final int maxRounds = 20;

   //data members

   /** A random object for drunkard to use */
   Random shipRandom;

   /**
    * class constructor. reaches the parent class constructor.
    * @param theShipType the type represents the subclass of the ship
    */
   public Drunkard(int theShipType){
      super(theShipType);
      shipRandom = new Random();
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

      //producing a random number for this round
      int distanceSpinningRate = shipRandom.nextInt(maxRounds);

      //it will spin on the spot for a number of rounds. the number will be decided randomly
      for (int i=0; i<distanceSpinningRate;i++){
         this.getPhysics().move(false,turnRight);
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



