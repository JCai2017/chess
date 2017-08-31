/*Jason Cai
 *EID: jsc3234
 * Vishakh Shukla
 * EID: vys77
 */

import java.lang.Math;

import javafx.scene.image.Image;

public class BlackBishop extends ChessPieces{

	public BlackBishop(){}
	
	BlackBishop(int x, int y){
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
		
		if(this.x == xPos || this.y == yPos){
			return false;
		}
		int checkX = this.x;
		int checkY = this.y; 
		int xDec = 0;
		int yDec = 0;
		int xDiff = xPos - x;
		int yDiff = yPos - y;
		if(Math.abs(xDiff)!= Math.abs(yDiff)){
			return false;
		}
		
		if(xPos > this.x){
			xDec = 1;
		}else{
			xDec = -1;
		}
		
		if(yDec > this.y){
			yDec = 1;
		}else{
			yDec = -1;
		}
		while(checkX != xPos){
			if(!board.posValid(xPos, yPos) && !board.positions.get(xPos).get(yPos).isWhite()){
				return false;
			}
			
			checkX = checkX + xDec;
			checkY = checkY + yDec;
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
		Image image = new Image("blackBishop.png");
		return image;
	}
}