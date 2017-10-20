package lab;

public class S_wordEdge implements Cloneable {
  static final int N = 100;
  public wordEdge[] S;
  int esp;
  
  public S_wordEdge() {
    S = new wordEdge[N];
    esp = -1;
  }
  
  /**
   * Push to stack.
   * @param point : the point to push
   */
  public void Push(wordEdge point) {
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
  public wordEdge Pull() {
    if (esp == -1) {
      System.out.println("Stack is empty!");
    }
    return S[esp --];
  }
  
  /**
   * Get the top element of stack.
   * @return the top element
   */
  public wordEdge getTop() {
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
   * @param w : the edge to check
   * @return If exist, return 1. If not, return 0.
   */
  public int isRepeat(wordEdge w) {
    for (int i = 0; i <= esp; i ++) {
      if (S[i].equals(w)) {
        return 1;
      }
    }
    return 0;
  }

  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
  
  /**
   * Construct function.
   * @param oldStack : the old stack
   */
  public S_wordEdge(S_wordEdge oldStack) {
    esp = oldStack.esp;
    S = new wordEdge[esp + 1];
    for (int i = 0; i <= oldStack.esp; i ++) {
      S[i] = oldStack.S[i];
    }
  }
}