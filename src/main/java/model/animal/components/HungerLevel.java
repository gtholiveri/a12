package model.animal.components;
/**
 * TODO: Class description
 *
 * @author Kendra Walther
 * email: kwalther@usc.edu
 * ITP 265, Fall 2024, Coffee/Tea Section
 * Date created: 11/18/24
 */
public enum HungerLevel {
  HUNGRY(0, 3, "I'm hungry, please feed me"),
  OKAY(4,7, "I'm feeling okay - not really hungry"),
  FULL(8,10, "I'm stuffed, no more food please!"),
  UNKNOWN(-1,-1, "Unknown hunger level");

  private int min;
  private int max;
  private String message;

  private HungerLevel(int min, int max, String message){
   this.min = min;
   this.max = max;
   this.message = message;
  }

  public String getMessage() {
   return message;
  }


 /**
  * Gets the associated hunger level
  * @param num, between 0 and 10
  * @return
  */
  public static HungerLevel getHungerLevel(int num) {
   HungerLevel level = UNKNOWN;

      if (num >= HUNGRY.min && num <= HUNGRY.max){
        level = HUNGRY;
      }
      else if(num >= OKAY.min && num <= OKAY.max){
       level = OKAY;
      }
      else if(num >= FULL.min && num <= FULL.max){
          level = FULL;
      }
      return level;
  }

}
