/*Jason Cai
 *EID: jsc3234
 * Vishakh Shukla
 * EID: vys77
 */

import java.lang.Math;
import javafx.scene.image.Image;


public class WhiteKing extends ChessPieces {
	
	protected boolean start = true;
	
	public WhiteKing(int x, int y){
		this.x=x;
		this.y=y; 
	}
	public boolean isWhite(){
		return true; 
	}
	
	public boolean isValidMove(int xPos, int yPos){
		if(xPos < 0 || xPos >= ChessBoard.width || yPos < 0 || yPos >= ChessBoard.height){
			return false;
		}
		
		if(xPos == this.x && yPos == this.y){
			return false;
		}
		
		int xDiff = Math.abs(xPos - x);
		int yDiff = Math.abs(yPos - y);
		if((xDiff > 1 || yDiff > 1) && (!start)){
			return false;
		}else if(start && board.positions.get(xPos).get(this.y) != null){
			ChessPieces piece = board.positions.get(xPos).get(yPos);
			if(piece instanceof BlackRook){
				BlackRook rook = (BlackRook) piece;
				if(rook.start){
					if(rook.x < this.x){
						rook.x = this.x + 1;
						rook.castled();
						start = false;
						return true;
					}else{
						rook.x = this.x - 1;
						rook.castled();
						start = false;
						return true;
					}
				}
			}
		}


		if(board.posValid(xPos, yPos)){
			if(causeCheck(xPos,yPos,getBlack())){
				return false;
			}
			start = false;
			return true;
		}else if(!board.positions.get(xPos).get(yPos).isWhite()){
			ChessPieces take = board.positions.get(xPos).get(yPos);
			removePiece(take);
			if(causeCheck(xPos,yPos,getBlack())){
				returnPiece(take);
				return false;
			}
			
			start = false;
			return true;
		}
		return false; 
	 }

	Image piece() {
		Image image = new Image("whiteKing.png");
		return image;
	}
}
