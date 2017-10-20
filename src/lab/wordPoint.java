package lab;

public class wordPoint {
  StringBuffer word;
  wordEdge next;
  int numChild;
  
  /**
   * Construct function.
   * @param wordList : word
   */
  public wordPoint(StringBuffer wordList) {
    word = wordList;
    numChild = 0;
    next = null;
  } //initialize function
}
