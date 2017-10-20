package lab;

public class General {
  General[] next;
  wordPoint real;
  
  /**
   * Construct function.
   */
  public General() {
    next = new General[26];
    real = null;
  }
}