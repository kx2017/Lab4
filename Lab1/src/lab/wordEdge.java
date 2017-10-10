package lab;

//test for B2

public class wordEdge{
	wordPoint followingPoint;
	int num;
	wordEdge bro;
	public wordEdge(wordPoint w){
		followingPoint = w;
		num = 1;
		bro = null;
	}
}