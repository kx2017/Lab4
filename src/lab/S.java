package lab;


public class S {
  static final int N = 100;
  wordPoint[] S;
  int esp;

  public S() { 
    S = new wordPoint[N];
    esp = -1;
  } //initialize
  
  /**
   * Push to stack.
   * @param point : the point to push
   */
  public void Push(wordPoint point) {
    if (esp >= N) {
      System.out.println("Srack is full!");
    }
    esp ++;
    S[esp] = point;
  }
  
  /**
   * Pull from stack.
   * @return :
   */
  public wordPoint Pull() {
    if (esp == -1) {
      System.out.println("Stack is empty!");
    }
    return S[esp --];
  }
  
  /**
   * Get the top element of stack.
   * @return the top element
   */
  public wordPoint getTop() {
    if (esp == -1) {
      System.out.println("Stack is empty!");
    }
    return S[esp];
  }
  
  /**
   * Check whether the stack is empty.
   * @return If empty, return 1. If not, return 0.
   */
  public int isEmpty() {
    if (esp < 0) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * Check whether there is an element in stack equals w.
   * @param w : the point to check
   * @return If exist, return true. If not, return false.
   */
  public boolean isRepeat(wordPoint w) {
    for (int i = 0; i <= esp; i ++) {
      if (S[i].word.equals(w.word)) {
        return true;
      }
    }
    return false;
  }
}
