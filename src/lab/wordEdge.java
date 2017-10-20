package lab;

public class wordEdge {
  wordPoint followingPoint;
  int num;
  wordEdge bro;
  
  /**
   * Construct function.
   * @param w : edge w
   */
  public wordEdge(wordPoint w) {
    followingPoint = w;
    num = 1;
    bro = null;
  }
}