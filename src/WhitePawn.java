/*Jason Cai
 *EID: jsc3234
 * Vishakh Shukla
 * EID: vys77
 */

import javafx.scene.image.Image;


public class WhitePawn extends ChessPieces {
	protected boolean start=true;
	private boolean moveTwoPlaces=false;

	WhitePawn(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public boolean isWhite(){
		return true;
	}
	
	public ChessPieces promote(String type){
		Class<?> pieceClass = null;
		try{
			pieceClass = Class.forName(type);
		} catch (ClassNotFoundException e){
			return null;
		}
		Object obj = null;
		try{
			obj = pieceClass.newInstance();
		}catch (InstantiationException e) {
			return null;
		}
		catch (IllegalAccessException e) {
			return null;
		}
		if(obj!= null){
			ChessPieces piece = (ChessPieces)obj;
			piece.x = this.x;
			piece.y = this.y;
			return piece;
		}
		
		return null;
	}
	
	public boolean movedTwo(){
		return moveTwoPlaces;
	}
	
	public boolean isValidMove(int xPos, int yPos){
		if(xPos < 0 || xPos >= ChessBoard.width || yPos < 0 || yPos >= ChessBoard.height){
			return false;
		}
		
		if(xPos == this.x && yPos == this.y){
			return false;
		}
		
		if(start){
			if(yPos==this.y-2 && xPos == this.x && board.posValid(xPos, yPos)==true){
				start=false; 
				moveTwoPlaces=true;
				return true;
			}
		}
		
		moveTwoPlaces=false;
		if(yPos==this.y-1 && xPos == this.x && board.posValid(xPos, yPos)){
			start=false; 
			return true;
		}else if((xPos == this.x + 1) || (xPos == this.x - 1) && yPos == this.y - 1){
			if((!board.posValid(xPos, yPos)) && (!(board.positions.get(xPos).get(yPos).isWhite()))){
				ChessPieces take = board.positions.get(xPos).get(yPos);
				removePiece(take);
				start=false; 
				return true;
			}else if((!board.posValid(xPos, yPos - 1)) && (board.positions.get(xPos).get(yPos) instanceof BlackPawn)){
				BlackPawn take = (BlackPawn)(board.positions.get(xPos).get(yPos));
				if(take.movedTwo()){
					removePiece(take);
					start=false; 
					return true;
				}
			}
		}
		return false; 
	}


	Image piece() {
		Image image = new Image("whitePawn.png");
		return image;
	}
}
