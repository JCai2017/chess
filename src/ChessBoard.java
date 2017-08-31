/*Jason Cai
 *EID: jsc3234
 * Vishakh Shukla
 * EID: vys77
 */
import java.util.ArrayList;

import javafx.scene.image.Image;

public class ChessBoard {

	ArrayList<ArrayList<ChessPieces>> positions = new ArrayList<ArrayList<ChessPieces>>();
	
	static final int width = 8;
	static final int height = 8;
	
	public ChessBoard() {
		for(int i = 0; i < width; i += 1){
			positions.add(new ArrayList<ChessPieces>());
			for(int j = 0; j < height; j += 1){
				positions.get(i).add(null);
			}
		}
	}
	
	public static int getWidth(){
		return width;
	}
	
	public static int getHeight(){
		return height;
	}

	public boolean posValid(int xPos, int yPos){
		if(xPos >= width || yPos >= height){
			return false;
		}if(xPos < 0 || yPos < 0){
			return false;
		}
		if(positions.get(xPos).get(yPos)==null){
			return true;
		}
		return false; 
	}
	
	public static javafx.scene.image.Image board(){
		Image image = new Image("Chess_Board.png");
		return image;
	}
}
