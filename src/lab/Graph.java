package lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Graph {
  General start;
  wordPoint[] pointList;
  int realwordNum;

  //9_11 xmh
  int[] pointMatrix = {0};


  /**
   * Check whether the point of the current word exist.
   * @param currentWord : word to check
   * @param length : the length of word
   * @return If exist, return the point of the current word. If not, return null.
   */
  public wordPoint checkPointExist(StringBuffer currentWord, int length) {
    General currentStage = start;
    int count = 0;
    while (count < length) {
      int index = currentWord.codePointAt(count);
      if (index < 97 || index > 122) {
        return null;
      }
      currentStage = currentStage.next[index - 97];
      if (currentStage == null) {
        break; //走偏
      }
      count++;
    }
    if (count == length && currentStage != null) {
      if (currentStage.real != null) {
        return currentStage.real; //树的尾字母节点
      }
      return null;
    }
    return null;
  }
  
  /**
   * Create a point of current word.
   * @param wordList : the word needed
   * @param length : the length of the word
   * @return The created point of the word.
   */
  public wordPoint createPoint(StringBuffer wordList, int length) {
    General currentStage = start;
    for (int i = 0; i < length; i ++) {
      int edi = wordList.codePointAt(i) - 97;
      if (currentStage.next[edi] == null) { //不存在此字母的通用节点
        currentStage.next[edi] = new General();
        currentStage = currentStage.next[edi];
      } else { //存在此字母的通用节点
        currentStage = currentStage.next[edi];
      }
    } //到此时currentStage为单词尾字母节点
    wordPoint thePoint = new wordPoint(wordList);
    currentStage.real = thePoint;
    return currentStage.real;

  }
  
  /**
   * Check whether the edge between previous word and following word exist.
   * @param previous : the point of previous word
   * @param following : the point of following word
   * @return If exist, return the edge. If not, return null.
   */
  public wordEdge checkEdgeExist(wordPoint previous, wordPoint following) {
    wordEdge current = previous.next;
    while (current != null) {
      if (current.followingPoint.word == following.word) {
        return current;
      }
      current = current.bro;
    }
    return null;
  }
  
  /**
   * Create the edge between previous word and following word.
   * @param previous : the point of previous word
   * @param following : the point of following word
   */
  public void createEdge(wordPoint previous, wordPoint following) {
    wordEdge current = previous.next;
    if (current != null) {
      while (current.bro != null) {
        if (current.followingPoint.word == following.word) {
          break;
        }
        current = current.bro;
      }
        
      current.bro = new wordEdge(following);
    } else {
      previous.next = new wordEdge(following);
    }
    previous.numChild++;
  }
  
  /**
   * The construction function.
   * @param wordList : the list of words
   * @param wordNum : the number of words
   */
  public Graph(StringBuffer[] wordList,int wordNum) {
    //创建第一个节点
    start = new General();
    wordPoint previousWord = createPoint(wordList[0],wordList[0].length());
    pointList = new wordPoint[wordNum];
    realwordNum = 0;
    pointList[realwordNum] = previousWord;
    for (int i = 1; i < wordNum; i ++) {
      StringBuffer currentWord = wordList[i];
      int length = currentWord.length();
      wordPoint resultPoint = checkPointExist(currentWord, length); //检查当前词是否存在
      if (resultPoint != null) { //已存在词
        wordEdge resultEdge = checkEdgeExist(previousWord,resultPoint); //检查当前边是否存在
        if (resultEdge != null) {
          resultEdge.num++; //已存在边，次数加一
        } else { //不存在边，创建边，并且加一
          createEdge(previousWord, resultPoint);
        }
      } else { //不存在点，先创建点
        resultPoint = createPoint(currentWord,length);
        createEdge(previousWord, resultPoint);
        realwordNum++;
        pointList[realwordNum] = resultPoint;
      }
      
      previousWord = resultPoint;
    }
  }
  
  /**
   * Show the graph.
   */
  public void graphShow() {
  
    try {
      File fout = new File("C:/Users/ZhaoYang/Documents/eclipse-workspace-java/Lab4/labGraph.dot");
      FileOutputStream out = new FileOutputStream(fout);
      out.write("digraph labGraph{\n\t".getBytes());
      for (int i = 0; i <= realwordNum; i ++) {
        wordPoint startPoint = pointList[i];
        wordEdge currentPoint = startPoint.next;
        if (currentPoint == null) {
          continue;
        }
        for (int j = 0; j < startPoint.numChild; j ++) {
          out.write((startPoint.word + "->" + currentPoint.followingPoint.word
              + "[label=\"" + currentPoint.num + "\"]" + ";\n").getBytes());
          currentPoint = currentPoint.bro;
        }
      }
      out.write("}".getBytes());
      out.close();
    } catch (FileNotFoundException e) { 
      System.out.println("FileStreamsTest: " + e);
    } catch (IOException e) { 
      System.err.println("FileStreamsTest: " + e); 
    }
  }
  
  /**
   * Overloaded function: show the graph highlight.
   * @param stack : the stack to store edge
   * @param wordNum : the number of words
   * @param num : the word need to highlight
   */
  public void graphShow(S_wordEdge[] stack,int wordNum,int num) {
  
    try {
      for (int index = 0; index <= wordNum; index ++) {
        if (stack[index].esp != -1) {
          File fout = new File("C:/Users/ZhaoYang/Documents/eclipse-workspace-java/Lab4/"
              + "lab1_shortestPath/labGraph_" + pointList[index].word + ".dot");
          FileOutputStream out = new FileOutputStream(fout);
          out.write("digraph labGraph{\n\t".getBytes());
          out.write((pointList[num].word + "[style=\"filled\", color=\"black\", "
              + "fillcolor=\"#59A9FB\"];\n").getBytes());
          for (int i = 1; i <= stack[index].esp; i ++) {
            out.write((stack[index].S[i].followingPoint.word + "[style=\"filled\", "
                + "color=\"black\", fillcolor=\"chartreuse\"];\n").getBytes());
          }
          for (int i = 0;i <= realwordNum; i ++) {
            wordPoint startPoint = pointList[i];
            wordEdge currentPoint = startPoint.next;
            if (currentPoint == null) {
              continue;
            }
            for (int j = 0; j < startPoint.numChild; j ++) {
              out.write((startPoint.word + "->" + currentPoint.followingPoint.word + "[label=\""
                  + currentPoint.num + "\"]" + ";\n").getBytes());
              currentPoint = currentPoint.bro;
            }
          }
          out.write("}".getBytes());
          out.close();
        }
      }
      
    } catch (FileNotFoundException e) { 
      System.out.println("FileStreamsTest: " + e);
    } catch (IOException e) { 
      System.err.println("FileStreamsTest: " + e); 
    }
  }
  
  /**
   * Find the bridge word between word1 and word2.
   * @param word1 : the start word
   * @param word2 : the end word
   * @return the bridge word
   */
  public S findBridge(StringBuffer word1,StringBuffer word2) {
    wordPoint point1;
    wordPoint point2;
    S container = new S();
    byte flag = 0;
    point1 = checkPointExist(word1,word1.length());
    point2 = checkPointExist(word2,word2.length());
    if (point1 == null) {
      //System.out.println("The word is invalid");
      container.esp += -2; //word1不存在
    }
    if (point2 == null) {
      container.esp += -3; //word2不存在
    }
    if (container.esp <= -3) {
      return container;
    }
    S stack = new S();
    wordEdge edge1x = point1.next; 
    if (edge1x == null) { //p1-px 路径不存在
      //System.out.println("no bridge found!");
      container.esp = -7;
      return container;
    }
    for (int i = 0; i < point1.numChild; i ++) { //遍历p1的边
      stack.Push(edge1x.followingPoint);
      wordPoint currentPoint = stack.getTop();
      wordEdge edgex2;
      edgex2 = currentPoint.next; //px-p2的边
      if ((edgex2 == null) || (edgex2.followingPoint == null)) {
        continue;
      }
      wordPoint testWord = null;
      for (int j = 0; j < currentPoint.numChild; j ++) {
        testWord = edgex2.followingPoint;
        if (testWord.word.toString().equals(word2.toString())) {
          //System.out.println(word1+ stack.getTop().word.toString() + word2);
          flag = 1;
          container.Push(stack.getTop());
          stack.Pull();
          break;
        }
        edgex2 = edgex2.bro;
      }
      edge1x = edge1x.bro;
    }
    return container;

  }
  
  /**
   * Create new text through adding bridge words.
   * @param wordNum : the number of words in old text
   * @param wordList : the words in old text
   * @return the new text
   */
  public String buildNewText(int wordNum,StringBuffer[] wordList) {
    S container;
    String resultString = "";
    for (int i = 0; i < wordNum - 1; i ++) {
      StringBuffer currentWord = wordList[i];
      StringBuffer followingWord = wordList[i + 1];
      container = findBridge(currentWord,followingWord);
      resultString = resultString + currentWord.toString() + " ";
      if (container.esp >= 0) {
        java.util.Random r = new java.util.Random(); 
        int result = r.nextInt() % (container.esp + 1);
        resultString = resultString 
            + container.S[result < 0 ? (-result) : result].word.toString() + " ";
      }
    }
    if (wordNum == 0) {
      return "";
    }
    resultString = resultString + wordList[wordNum - 1].toString();
    return resultString;
  }
  
  /**
   * Get the index of word in the word list.
   * @param list : the list of word
   * @param word : the word to find
   * @return the index
   */
  public int getIndex(wordPoint[] list,wordPoint word) {
    for (int i = 0; i < list.length; i ++) {
      if (list[i].equals(word)) {
        return i;
      }
    }
    return -1;
  }
  
  /**
   * Get the shortest from the begin word word.
   * @param wordBegin : the begin word
   * @return the edge list of the path
   * @throws Exception :
   */
  public S_wordEdge[] shortestPath(StringBuffer wordBegin) throws Exception {
    S_wordEdge[] stackList = new S_wordEdge[realwordNum + 1];
    for (int i = 0; i <= realwordNum; i ++) {
      stackList[i] = new S_wordEdge();
    }
    S_wordEdge stack = new S_wordEdge();
    S pointStack = new S();
    wordPoint pointBegin = checkPointExist(wordBegin, wordBegin.length());
    if (pointBegin == null) {
      System.out.print("The word not exist!");
      return null;
    }
    wordEdge currentEdge = pointBegin.next;
    wordPoint currentPrePoint = pointBegin;
    pointStack.Push(currentPrePoint);
    stack.Push(currentEdge);
    currentPrePoint = currentEdge.followingPoint;
    do {
      while (currentEdge != null) {
        currentPrePoint = currentEdge.followingPoint;
        if (pointStack.isRepeat(currentPrePoint)) {
          currentEdge = currentEdge.bro;
          continue;
        }
        stack.Push(currentEdge);
        pointStack.Push(currentPrePoint);
        int index = getIndex(pointList, currentPrePoint);
        if (stackList[index].esp == -1 || stackList[index].esp > stack.esp) {
          stackList[index] = new S_wordEdge(stack);
        }
        currentEdge = currentPrePoint.next;
        if (currentEdge == null) {
          break;
        }
        currentPrePoint = currentEdge.followingPoint;
      }
      currentEdge = stack.Pull();
      pointStack.Pull();
      currentEdge = currentEdge.bro;
    } while (pointStack.isEmpty() == 0);
    return stackList;
  }
  
  /**
   * Random walk.
   * @param start : the start word
   * @param steps : the number of steps
   * @return the stack of the path
   * @throws IOException :
   */
  public S RandomGo(StringBuffer start, int steps) throws IOException {
    S path = new S();
    wordPoint currentPoint = checkPointExist(start,start.length());
    path.Push(currentPoint);
    while (steps != 0) {
      currentPoint = stepOne(currentPoint);
      if (currentPoint == null) {
        System.out.println("no words to explore, stop walking");
        break;
      }
      if (path.isRepeat(currentPoint)) {
        System.out.println("get repeated point, stop walking");
        break;
      }
      path.Push(currentPoint);
      steps --;
    }
    if (steps == 0) {
      System.out.println("length limit, stop walking");
    }
    return path;
  }
  
  /**
   * Overloaded function: random walk.
   * @param start : the start word
   * @return the stack of the path
   * @throws IOException :
   */ 
  public S RandomGo(StringBuffer start) throws IOException {
    S path = new S();
    wordPoint currentPoint = checkPointExist(start,start.length());
    path.Push(currentPoint);
    boolean action = true;
    StringBuffer stringPath = new StringBuffer(currentPoint.word.toString() + "->");
    currentPoint = stepOne(currentPoint); //get random word
    while (action) {
      if (true) {
        int choice;
        choice = JOptionPane.showConfirmDialog(null, "当前路径为" + stringPath
            + "\n是否继续？", "Overwrite file", JOptionPane.YES_NO_OPTION);
        path.Push(currentPoint);
        stringPath.append(currentPoint.word.toString() + "->");
        action = (choice == 0 ? true : false);
        //break;
      } //此处的while...break改为if
      currentPoint = stepOne(currentPoint);
      if (currentPoint == null) {
        System.out.println("no words to explore, stop walking");
        break;
      }
      if (path.isRepeat(currentPoint)) {
        System.out.println("get repeated point, stop walking");
        break;
      }
    }
    if (!action) {
      System.out.println("stop due to user input");
    }
    path.Pull();
    return path;
  }
  
  /**
   * Get the random word following the current word.
   * @param currentPoint : the current word
   * @return the random word following the current word
   */
  public wordPoint stepOne(wordPoint currentPoint) {
    if (currentPoint.numChild == 0) {
      return null;
    }
    java.util.Random r = new java.util.Random(); 
    int result = r.nextInt() % (currentPoint.numChild);
    result = result < 0 ? -result : result;
    wordEdge currentEdge = currentPoint.next;
    for (int i = 0; i < result; i ++) {
      currentEdge = currentEdge.bro;
    }
    return currentEdge.followingPoint;
  }
}
