/*Jason Cai
 *EID: jsc3234
 * Vishakh Shukla
 * EID: vys77
 */

import javafx.scene.image.Image;


public class WhiteRook extends ChessPieces {

	protected boolean start = true;
	public WhiteRook(){}
	
	public WhiteRook(int x, int y){
		this.x=x;
		this.y=y; 
	}
    public boolean isWhite(){
    	return true; 
    }
    
    public void castled(){
		start = false;
	}
    
    public boolean isValidMove(int xPos, int yPos){
    	if(xPos < 0 || xPos >= ChessBoard.width || yPos < 0 || yPos >= ChessBoard.height){
			return false;
		}
    	
    	if(xPos == this.x && yPos == this.y){
			return false;
		}
    	
    	int checkX = this.x;
		int checkY = this.y; 
		int xDec = 0;
		int yDec = 0;
		int xDiff = xPos - x;
		int yDiff = yPos - y;
		if(xDiff != 0 && yDiff != 0){
			return false;
		}
		
		if(xPos > this.x){
			xDec = 1;
		}else if(xPos < this.x){
			xDec = -1;
		}
		
		if(yPos > this.y){
			yDec = 1;
		}else if(yPos < this.y){
			yDec = -1;
		}
		while((checkX != xPos) || (checkY != yPos)){
			if(!board.posValid(xPos, yPos) && board.positions.get(xPos).get(yPos).isWhite()){
				return false;
			}
			
			checkX = checkX + xDec;
			checkY = checkY + yDec;
		}
		if(board.posValid(xPos, yPos)){
			start = false;
			return true;
		}else if(!board.positions.get(xPos).get(yPos).isWhite()){
			ChessPieces take = board.positions.get(xPos).get(yPos);
			removePiece(take);
			start = false;
			return true;
		}
		
		return false;
	 }

	@Override
	Image piece() {
		Image image = new Image("whiteRook.png");
		return image;
	}
}
