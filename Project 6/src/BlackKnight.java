/*Jason Cai
 *EID: jsc3234
 * Vishakh Shukla
 * EID: vys77
 */

import java.lang.Math;

import javafx.scene.image.Image;

public class BlackKnight extends ChessPieces {

	public BlackKnight(){}
	
	public BlackKnight(int x, int y){
		this.x=x;
		this.y=y; 
	}
	
	public boolean isValidMove(int xPos, int yPos){
		if(xPos < 0 || xPos >= ChessBoard.width || yPos < 0 || yPos >= ChessBoard.height){
			return false;
		}
		
		if(xPos == this.x && yPos == this.y){
			return false;
		}
		
		int xDiff = Math.abs(this.x - xPos);
		int yDiff = Math.abs(this.y - yPos);
		
		if((xDiff != 2 || yDiff != 1) && (xDiff != 1 || yDiff != 2)){
			 return false;
		}
		
		if(board.posValid(xPos, yPos)){
			return true;
		}else if(board.positions.get(xPos).get(yPos).isWhite()){
			ChessPieces take = board.positions.get(xPos).get(yPos);
			removePiece(take);
			return true;
		}
		
		return false;
	 }

	Image piece() {
		Image image = new Image("blackKnight.png");
		return image;
	}
}
