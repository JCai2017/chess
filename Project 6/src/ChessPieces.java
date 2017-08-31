/*Jason Cai
 *EID: jsc3234
 * Vishakh Shukla
 * EID: vys77
 */

import java.util.ArrayList;
import java.util.Scanner;

public abstract class ChessPieces {
	
	protected int x = 0;
	protected int y = 0;
	private static int oldX = 0;
	private static int oldY = 0;
	public static ChessBoard board = new ChessBoard();
	private static ArrayList<ChessPieces> white = new ArrayList<ChessPieces>();
	private static ArrayList<ChessPieces> black = new ArrayList<ChessPieces>();
	private static ArrayList<ChessPieces> whiteTaken = new ArrayList<ChessPieces>();
	private static ArrayList<ChessPieces> blackTaken = new ArrayList<ChessPieces>();
	static Scanner sc = new Scanner(System.in);
	
	private static ArrayList<ChessPieces> currentTeam = white;
	private static ArrayList<ChessPieces> opponent = black;
	
	private static boolean whiteTurn = true;
	
	public  int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public static boolean getTurn(){return whiteTurn;}
	public boolean testMove(int x, int y){return true;}
	
	public ArrayList<ChessPieces> getBlack(){return black;}
	public ArrayList<ChessPieces> getWhite(){return white;}
	
	public static void placePieces(){
		white = new ArrayList<ChessPieces>();
		black = new ArrayList<ChessPieces>();
		whiteTaken = new ArrayList<ChessPieces>();
		blackTaken = new ArrayList<ChessPieces>();
		board = new ChessBoard();
		
		ChessPieces king = new WhiteKing(3,7); 
		white.add(king);
		board.positions.get(3).set(7, king);
		king = new BlackKing(3,0); 
		black.add(king);
		board.positions.get(3).set(0, king);
		
		/*ChessPieces queen = new WhiteQueen(4,7); 
		white.add(queen);
		board.positions.get(4).set(7, queen);
		queen = new BlackQueen(4,0); 
		black.add(queen);
		board.positions.get(4).set(0, queen);*/
		
		/*for(int i = 0; i< 8; i += 1){
			ChessPieces pawn = new WhitePawn(i,6);
			white.add(pawn);
			board.positions.get(i).set(6, pawn);
			pawn = new BlackPawn(i,1);
			black.add(pawn);
			board.positions.get(i).set(1, pawn);
		}*/
		
		for(int i = 0; i < 2; i += 1){
			ChessPieces rook = new WhiteRook(i*7,7);
			white.add(rook);
			board.positions.get(i*7).set(7, rook);
			/*rook = new BlackRook(i*7,0);
			black.add(rook);
			board.positions.get(i*7).set(0, rook);*/
		}
		
		/*for(int i = 0; i < 2; i += 1){
			ChessPieces knight = new WhiteKnight((i*5) + 1,7);
			white.add(knight);
			board.positions.get((i* 5) + 1).set(7, knight);
			knight = new BlackKnight((i*5) + 1,0);
			black.add(knight);
			board.positions.get((i* 5) + 1).set(0, knight);
		}
		
		for(int i = 0; i < 2; i += 1){
			ChessPieces bishop = new WhiteBishop((i * 3) + 2, 7);
			white.add(bishop);
			board.positions.get((i*3) + 2).set(7, bishop);
			bishop = new BlackBishop((i * 3) + 2, 0);
			black.add(bishop);
			board.positions.get((i*3) + 2).set(0, bishop);
		}*/
		
		currentTeam = white;
		opponent = black;
	}
	
	public boolean move(int xPos, int yPos){
		if(isValidMove(xPos,yPos)){
			oldX = this.x;
			oldY = this.y;
			board.positions.get(this.x).set(this.y, null);
			this.x=xPos;
			this.y=yPos;
			board.positions.get(this.x).set(this.y, this);
			return true;
		}else{
			System.out.println("Invalid Move. Choose another move.");
			return false;
		}
	}
	abstract boolean isValidMove(int xPos, int yPos);
	
	abstract javafx.scene.image.Image piece();
	
	public boolean isWhite(){
		return false;
	}
	
	public static boolean causeCheck(int xPos, int yPos, ArrayList<ChessPieces> opponent){
		if(xPos < 0 || yPos < 0 || xPos > 7 || yPos > 7){
			return false;
		}
		
		int checkX = xPos + 1;
		int checkY = yPos + 1;
		while(checkX < 8 && checkY < 8){
			if(!board.posValid(checkX, checkY)){
				if(checkX == xPos + 1){
					int ind = opponent.indexOf(board.positions.get(checkX).get(checkY));
					if((opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof BlackKing) 
							|| (!opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof WhiteKing)){
						
					}else if(ind >=0 ){
						if(!opponent.get(ind).isWhite()){
							if(opponent.get(ind) instanceof BlackKing || opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackBishop){
								return true;
							}else{
								break;
							}
						}else if(opponent.get(ind).isWhite()){
							if(opponent.get(ind) instanceof WhitePawn || opponent.get(ind) instanceof WhiteKing || opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteBishop){
								return true;
							}else{
								break;
							}
						}
					}else{
						break;
					}
				}
				int ind = opponent.indexOf(board.positions.get(checkX).get(checkY));
				if((opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof BlackKing) 
						|| (!opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof WhiteKing)){
					
				}if(ind >=0 ){
					if(!opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackBishop){
							return true;
						}else{
							break;
						}
					}else if(opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteBishop){
							return true;
						}else{
							break;
						}
					}
				}else{
					break;
				}
			}
			
			checkX += 1;
			checkY += 1;
		}
		
		checkX = xPos - 1;
		checkY = yPos - 1;
		while(checkX >= 0 && checkY >= 0){
			if(!board.posValid(checkX, checkY)){
				if(checkX == xPos - 1){
					int ind = opponent.indexOf(board.positions.get(checkX).get(checkY));
					if((opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof BlackKing) 
							|| (!opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof WhiteKing)){
						
					}else if(ind >=0 ){
						if(!opponent.get(ind).isWhite()){
							if(opponent.get(ind) instanceof BlackPawn || opponent.get(ind) instanceof BlackKing || opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackBishop){
								return true;
							}else{
								break;
							}
						}else if(opponent.get(ind).isWhite()){
							if(opponent.get(ind) instanceof WhiteKing || opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteBishop){
								return true;
							}else{
								break;
							}
						}
					}else{
						break;
					}
				}
				int ind = opponent.indexOf(board.positions.get(checkX).get(checkY));
				if((opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof BlackKing) 
						|| (!opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof WhiteKing)){
					
				}else if(ind >=0 ){
					if(!opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackBishop){
							return true;
						}else{
							break;
						}
					}else if(opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteBishop){
							return true;
						}else{
							break;
						}
					}
				}else{
					break;
				}
			}
			
			checkX -= 1;
			checkY -= 1;
		}
		
		checkX = xPos + 1;
		checkY = yPos - 1;
		while(checkX < 8 && checkY >= 0){
			if(!board.posValid(checkX, checkY)){
				if(checkX == xPos + 1){
					int ind = opponent.indexOf(board.positions.get(checkX).get(checkY));
					if((opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof BlackKing) 
							|| (!opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof WhiteKing)){
						
					}else if(ind >=0 ){
						if(!opponent.get(ind).isWhite()){
							if(opponent.get(ind) instanceof BlackPawn || opponent.get(ind) instanceof BlackKing || opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackBishop){
								return true;
							}else{
								break;
							}
						}else if(opponent.get(ind).isWhite()){
							if(opponent.get(ind) instanceof WhiteKing || opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteBishop){
								return true;
							}else{
								break;
							}
						}
					}else{
						break;
					}
				}
				int ind = opponent.indexOf(board.positions.get(checkX).get(checkY));
				if((opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof BlackKing) 
						|| (!opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof WhiteKing)){
					
				}else if(ind >=0 ){
					if(!opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackBishop){
							return true;
						}else{
							break;
						}
					}else if(opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteBishop){
							return true;
						}else{
							break;
						}
					}
				}else{
					break;
				}
			}
			
			checkX += 1;
			checkY -= 1;
		}
		
		checkX = xPos - 1;
		checkY = yPos + 1;
		while(checkX >= 0 && checkY < 8){
			if(!board.posValid(checkX, checkY)){
				if(checkX == xPos - 1){
					int ind = opponent.indexOf(board.positions.get(checkX).get(checkY));
					if((opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof BlackKing) 
							|| (!opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof WhiteKing)){
						
					}else if(ind >=0 ){
						if(!opponent.get(ind).isWhite()){
							if(opponent.get(ind) instanceof BlackKing || opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackBishop){
								return true;
							}else{
								break;
							}
						}else if(opponent.get(ind).isWhite()){
							if(opponent.get(ind) instanceof WhitePawn || opponent.get(ind) instanceof WhiteKing || opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteBishop){
								return true;
							}else{
								break;
							}
						}
					}else{
						break;
					}
				}
				int ind = opponent.indexOf(board.positions.get(checkX).get(checkY));
				if((opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof BlackKing) 
						|| (!opponent.get(0).isWhite() && board.positions.get(checkX).get(checkY) instanceof WhiteKing)){
					
				}else if(ind >=0 ){
					if(!opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackBishop){
							return true;
						}else{
							break;
						}
					}else if(opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteBishop){
							return true;
						}else{
							break;
						}
					}
				}else{
					break;
				}
			}
			
			checkX -= 1;
			checkY += 1;
		}
		
		checkX = xPos + 1;
		while(checkX < 8){
			if(!board.posValid(checkX, yPos)){
				if(checkX == xPos + 1){
					int ind = opponent.indexOf(board.positions.get(checkX).get(yPos));
					if((opponent.get(0).isWhite() && board.positions.get(checkX).get(yPos) instanceof BlackKing) 
							|| (!opponent.get(0).isWhite() && board.positions.get(checkX).get(yPos) instanceof WhiteKing)){
						
					}else if(ind >=0){
						if(!opponent.get(ind).isWhite()){
							if( opponent.get(ind) instanceof BlackKing || opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackRook){
								return true;
							}else{
								break;
							}
						}else if(opponent.get(ind).isWhite()){
							if(opponent.get(ind) instanceof WhiteKing || opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteRook){
								return true;
							}
						}else{
							break;
						}
					}else{
						break;
					}
				}
				int ind = opponent.indexOf(board.positions.get(checkX).get(yPos));
				if((opponent.get(0).isWhite() && board.positions.get(checkX).get(yPos) instanceof BlackKing) 
						|| (!opponent.get(0).isWhite() && board.positions.get(checkX).get(yPos) instanceof WhiteKing)){
					
				}else if(ind >=0 ){
					if(!opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackRook){
							return true;
						}else{
							break;
						}
					}else if(opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteRook){
							return true;
						}else{
							break;
						}
					}
				}else{
					break;
				}
			}
			
			checkX += 1;
		}
		
		checkX = xPos - 1;
		
		while(checkX >= 0){
			if(!board.posValid(checkX, yPos)){
				if(checkX == xPos - 1){
					int ind = opponent.indexOf(board.positions.get(checkX).get(yPos));
					if((opponent.get(0).isWhite() && board.positions.get(checkX).get(yPos) instanceof BlackKing) 
							|| (!opponent.get(0).isWhite() && board.positions.get(checkX).get(yPos) instanceof WhiteKing)){
						
					}else if(ind >=0 ){
						if(!opponent.get(ind).isWhite()){
							if( opponent.get(ind) instanceof BlackKing || opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackRook){
								return true;
							}else{
								break;
							}
						}else if(opponent.get(ind).isWhite()){
							if(opponent.get(ind) instanceof WhiteKing || opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteRook){
								return true;
							}
						}else{
							break;
						}
					}else{
						break;
					}
				}
				int ind = opponent.indexOf(board.positions.get(checkX).get(yPos));
				if((opponent.get(0).isWhite() && board.positions.get(checkX).get(yPos) instanceof BlackKing) 
						|| (!opponent.get(0).isWhite() && board.positions.get(checkX).get(yPos) instanceof WhiteKing)){
					
				}else if(ind >=0 ){
					if(!opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackRook){
							return true;
						}else{
							break;
						}
					}else if(opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteRook){
							return true;
						}else{
							break;
						}
					}
				}else{
					break;
				}
			}
			
			checkX -= 1;
		}
		
		checkY = yPos + 1;
		while(checkY < 8){
			if(!board.posValid(xPos, checkY)){
				if(yPos == yPos + 1){
					int ind = opponent.indexOf(board.positions.get(xPos).get(checkY));
					if((opponent.get(0).isWhite() && board.positions.get(xPos).get(checkY) instanceof BlackKing) 
							|| (!opponent.get(0).isWhite() && board.positions.get(xPos).get(yPos) instanceof WhiteKing)){
						
					}else if(ind >=0 ){
						if(!opponent.get(ind).isWhite()){
							if( opponent.get(ind) instanceof BlackKing || opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackRook){
								return true;
							}else{
								break;
							}
						}else if(opponent.get(ind).isWhite()){
							if(opponent.get(ind) instanceof WhiteKing || opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteRook){
								return true;
							}else{
								break;
							}
						}
					}else{
						break;
					}
				}
				int ind = opponent.indexOf(board.positions.get(xPos).get(checkY));
				if((opponent.get(0).isWhite() && board.positions.get(xPos).get(checkY) instanceof BlackKing) 
						|| (!opponent.get(0).isWhite() && board.positions.get(xPos).get(checkY) instanceof WhiteKing)){
					
				}else if(ind >=0 ){
					if(!opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackRook){
							return true;
						}else{
							break;
						}
					}else if(opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteRook){
							return true;
						}else{
							break;
						}
					}
				}else{
					break;
				}
			}
			
			checkY += 1;
		}
		
		checkY = yPos - 1;
		while(checkY >= 0){
			if(!board.posValid(xPos, checkY)){
				if(yPos == yPos - 1){
					int ind = opponent.indexOf(board.positions.get(xPos).get(checkY));
					if((opponent.get(0).isWhite() && board.positions.get(xPos).get(checkY) instanceof BlackKing) 
							|| (!opponent.get(0).isWhite() && board.positions.get(xPos).get(checkY) instanceof WhiteKing)){
						
					}else if(ind >=0 ){
						if(!opponent.get(ind).isWhite()){
							if( opponent.get(ind) instanceof BlackKing || opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackRook){
								return true;
							}else{
								break;
							}
						}else if(opponent.get(ind).isWhite()){
							if(opponent.get(ind) instanceof WhiteKing || opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteRook){
								return true;
							}else{
								break;
							}
						}
					}else{
						break;
					}
				}
				int ind = opponent.indexOf(board.positions.get(xPos).get(checkY));
				if((opponent.get(0).isWhite() && board.positions.get(xPos).get(checkY) instanceof BlackKing) 
						|| (!opponent.get(0).isWhite() && board.positions.get(xPos).get(checkY) instanceof WhiteKing)){
					
				}else if(ind >=0 ){
					if(!opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof BlackQueen || opponent.get(ind) instanceof BlackRook){
							return true;
						}else{
							break;
						}
					}else if(opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof WhiteQueen || opponent.get(ind) instanceof WhiteRook){
							return true;
						}else{
							break;
						}
					}
				}else{
					break;
				}
			}
			
			checkY -= 1;
		}
		
		checkX = xPos + 2;
		checkY = yPos + 1;
		for(int i = 0; i < 2; i += 1){
			if(checkX > 7 || checkY > 7){
				break;
			}
			if(!board.posValid(checkX, checkY)  && (checkX < 8) &&(checkY < 8) && (checkY >= 0)){
				int ind = opponent.indexOf(board.positions.get(checkX).get(checkY));
				if(ind >=0 ){
					if(!opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof BlackKnight){
							return true;
						}
					}else if(opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof WhiteKnight){
							return true;
						}
					}
				}
			}
			
			checkY -= 2;
		}
		
		checkX = xPos - 2;
		checkY = yPos + 1;
		for(int i = 0; i < 2; i += 1){
			if(checkX < 0 || checkY > 7){
				break;
			}
			if(!board.posValid(checkX, checkY) && (checkX >= 0) &&(checkY < 8) && (checkY >= 0) && (checkX < 8)){
				int ind = opponent.indexOf(board.positions.get(checkX).get(checkY));
				if(ind >=0 ){
					if(!opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof BlackKnight){
							return true;
						}
					}else if(opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof WhiteKnight){
							return true;
						}
					}
				}
			}
			
			checkY -= 2;
		}
		
		checkX = xPos + 1;
		checkY = yPos + 2;
		for(int i = 0; i < 2; i += 1){
			if(checkX > 7 || checkY > 7){
				break;
			}
			if(!board.posValid(checkX, checkY) && (checkY < 8) &&(checkX < 8) && (checkX >= 0)){
				int ind = opponent.indexOf(board.positions.get(checkX).get(checkY));
				if(ind >=0 ){
					if(!opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof BlackKnight){
							return true;
						}
					}else if(opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof WhiteKnight){
							return true;
						}
					}
				}
			}
			
			checkX -= 2;
		}
		
		checkX = xPos + 1;
		checkY = yPos - 2;
		for(int i = 0; i < 2; i += 1){
			if(checkX > 7 || checkY < 0){
				break;
			}
			if(!board.posValid(checkX, checkY) && (checkY > 0) &&(checkX < 8) && (checkX >= 0)){
				int ind = opponent.indexOf(board.positions.get(checkX).get(checkY));
				if(ind >=0 ){
					if(!opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof BlackKnight){
							return true;
						}
					}else if(opponent.get(ind).isWhite()){
						if(opponent.get(ind) instanceof WhiteKnight){
							return true;
						}
					}
				}
			}
			
			checkX -= 2;
		}
		
		
		return false;
	}
	
	public static boolean inWhite(ChessPieces piece){
		if(white.contains(piece)){
			return true;
		}
		
		return false;
	}
	
	public static boolean wTurn(){
		return whiteTurn;
	}
	
	public void removePiece(ChessPieces taken){
		if(taken == null){
			return;
		}
		ChessPieces toRemove = null;
		int index = -1;
		if(taken.isWhite()){
			index = white.indexOf(taken);
			if(index != -1){
				toRemove = white.remove(index);
				board.positions.get(toRemove.x).set(toRemove.y, null);
				blackTaken.add(toRemove);
			}
		}else{
			index = black.indexOf(taken);
			if(index != -1){
				toRemove = black.remove(index);
				whiteTaken.add(toRemove);
			}
		}
	}
	
	public void returnPiece(ChessPieces notValid){
		if(notValid.isWhite()){
			blackTaken.remove(notValid);
			white.add(notValid);
		}else{
			whiteTaken.remove(notValid);
			black.add(notValid);
		}
		board.positions.get(notValid.x).set(notValid.y, notValid);
	}
	
	public static int moveOther(int xPos, int yPos, ArrayList<ChessPieces> currentTeam){
		int checkX = xPos;
		int checkY = yPos;
		while(checkX >= 0){
			if(!board.posValid(checkX, checkY)){
				ChessPieces piece = board.positions.get(checkX).get(checkY);
				if(currentTeam.contains(piece) && ((currentTeam.get(0).isWhite() && (piece instanceof WhiteRook || piece instanceof WhiteQueen))) 
						|| (!currentTeam.get(0).isWhite() && (piece instanceof BlackRook || piece instanceof BlackQueen))){
					return 1;
				}else{
					break;
				}
			}
			checkX -= 1;
		}
		
		checkX = xPos;
		while(checkX < 8){
			if(!board.posValid(checkX, checkY)){
				ChessPieces piece = board.positions.get(checkX).get(checkY);
				if(currentTeam.contains(piece) && ((currentTeam.get(0).isWhite() && (piece instanceof WhiteRook || piece instanceof WhiteQueen))) 
						|| (!currentTeam.get(0).isWhite() && (piece instanceof BlackRook || piece instanceof BlackQueen))){
					return 1;
				}else{
					break;
				}
			}
			checkX += 1;
		}
		
		checkX = xPos;
		while(checkY >= 0){
			if(!board.posValid(checkX, checkY)){
				ChessPieces piece = board.positions.get(checkX).get(checkY);
				if(currentTeam.contains(piece) && ((currentTeam.get(0).isWhite() && (piece instanceof WhiteRook || piece instanceof WhiteQueen))) 
						|| (!currentTeam.get(0).isWhite() && (piece instanceof BlackRook || piece instanceof BlackQueen))){
					return 1;
				}else{
					break;
				}
			}
			checkY -= 1;
		}
		
		checkY = yPos;
		while(checkY < 8){
			if(!board.posValid(checkX, checkY)){
				ChessPieces piece = board.positions.get(checkX).get(checkY);
				if(currentTeam.contains(piece) && ((currentTeam.get(0).isWhite() && (piece instanceof WhiteRook || piece instanceof WhiteQueen))) 
						|| (!currentTeam.get(0).isWhite() && (piece instanceof BlackRook || piece instanceof BlackQueen))){
					return 1;
				}else{
					break;
				}
			}
			checkY += 1;
		}
		
		checkY = yPos;
		while(checkX >= 0 && checkY >= 0){
			ChessPieces piece = board.positions.get(checkX).get(checkY);
			if(!board.posValid(checkX, checkY)){
				if(checkX == xPos - 1){
					if(currentTeam.contains(piece) && ((currentTeam.get(0).isWhite() && (piece instanceof WhiteBishop || piece instanceof WhiteQueen))) 
							|| (!currentTeam.get(0).isWhite() && (piece instanceof BlackPawn || piece instanceof BlackBishop || piece instanceof BlackQueen))){
						return 1;
					}else{
						break;
					}
				}
				if(currentTeam.contains(piece) && ((currentTeam.get(0).isWhite() && (piece instanceof WhiteBishop || piece instanceof WhiteQueen))) 
						|| (!currentTeam.get(0).isWhite() && (piece instanceof BlackBishop || piece instanceof BlackQueen))){
					return 1;
				}else{
					break;
				}
			}
			checkX -= 1;
			checkY -= 1;
		}
		
		checkX = xPos;
		checkY = yPos;
		while(checkX < 8 && checkY >= 0){
			ChessPieces piece = board.positions.get(checkX).get(checkY);
			if(!board.posValid(checkX, checkY)){
				if(checkX == xPos - 1){
					if(currentTeam.contains(piece) && ((currentTeam.get(0).isWhite() && (piece instanceof WhiteBishop || piece instanceof WhiteQueen))) 
							|| (!currentTeam.get(0).isWhite() && (piece instanceof BlackPawn || piece instanceof BlackBishop || piece instanceof BlackQueen))){
						return 1;
					}else{
						break;
					}
				}
				if(currentTeam.contains(piece) && ((currentTeam.get(0).isWhite() && (piece instanceof WhiteBishop || piece instanceof WhiteQueen))) 
						|| (!currentTeam.get(0).isWhite() && (piece instanceof BlackBishop || piece instanceof BlackQueen))){
					return 1;
				}else{
					break;
				}
			}
			checkX += 1;
			checkY -= 1;
		}
		
		checkX = xPos;
		checkY = yPos;
		while(checkX < 8 && checkY < 8){
			ChessPieces piece = board.positions.get(checkX).get(checkY);
			if(!board.posValid(checkX, checkY)){
				if(checkX == xPos - 1){
					if(currentTeam.contains(piece) && ((currentTeam.get(0).isWhite() && (piece instanceof WhitePawn || piece instanceof WhiteBishop || piece instanceof WhiteQueen))) 
							|| (!currentTeam.get(0).isWhite() && ( piece instanceof BlackBishop || piece instanceof BlackQueen))){
						return 1;
					}else{
						break;
					}
				}
				if(currentTeam.contains(piece) && ((currentTeam.get(0).isWhite() && (piece instanceof WhiteBishop || piece instanceof WhiteQueen))) 
						|| (!currentTeam.get(0).isWhite() && (piece instanceof BlackBishop || piece instanceof BlackQueen))){
					return 1;
				}else{
					break;
				}
			}
			checkX += 1;
			checkY += 1;
		}
		
		checkX = xPos;
		checkY = yPos;
		while(checkX >= 0 && checkY < 8){
			ChessPieces piece = board.positions.get(checkX).get(checkY);
			if(!board.posValid(checkX, checkY)){
				if(checkX == xPos - 1){
					if(currentTeam.contains(piece) && ((currentTeam.get(0).isWhite() && (piece instanceof WhitePawn || piece instanceof WhiteBishop || piece instanceof WhiteQueen))) 
							|| (!currentTeam.get(0).isWhite() && ( piece instanceof BlackBishop || piece instanceof BlackQueen))){
						return 1;
					}else{
						break;
					}
				}
				if(currentTeam.contains(piece) && ((currentTeam.get(0).isWhite() && (piece instanceof WhiteBishop || piece instanceof WhiteQueen))) 
						|| (!currentTeam.get(0).isWhite() && (piece instanceof BlackBishop || piece instanceof BlackQueen))){
					return 1;
				}else{
					break;
				}
			}
			checkX -= 1;
			checkY += 1;
		}
		
		checkX = xPos + 2;
		checkY = yPos - 1;
		for(int i = 0; i < 2; i += 1){
			if(checkX > 7 || checkY < 0 || checkY > 7){
				break;
			}
			if(!board.posValid(checkX, checkY)){
				ChessPieces piece = board.positions.get(checkX).get(checkY);
				if(currentTeam.contains(piece) && (currentTeam.get(0).isWhite() && piece instanceof WhiteKnight) || 
						(!currentTeam.get(0).isWhite() && piece instanceof BlackKnight)){
					return 1;
				}
			}
			
			checkY += 2;
		}
		
		checkX = xPos - 2;
		checkY = yPos - 1;
		for(int i = 0; i < 2; i += 1){
			if(checkX < 0 || checkY < 0 || checkY > 7){
				break;
			}
			if(!board.posValid(checkX, checkY)){
				ChessPieces piece = board.positions.get(checkX).get(checkY);
				if(currentTeam.contains(piece) && (currentTeam.get(0).isWhite() && piece instanceof WhiteKnight) || 
						(!currentTeam.get(0).isWhite() && piece instanceof BlackKnight)){
					return 1;
				}
			}
			
			checkY += 2;
		}
		
		checkX = xPos - 1;
		checkY = yPos - 2;
		for(int i = 0; i < 2; i += 1){
			if(checkX < 0 || checkY < 0 || checkX > 7){
				break;
			}
			if(!board.posValid(checkX, checkY)){
				ChessPieces piece = board.positions.get(checkX).get(checkY);
				if(currentTeam.contains(piece) && (currentTeam.get(0).isWhite() && piece instanceof WhiteKnight) || 
						(!currentTeam.get(0).isWhite() && piece instanceof BlackKnight)){
					return 1;
				}
			}
			
			checkX += 2;
		}
		
		checkX = xPos - 1;
		checkY = yPos + 2;
		for(int i = 0; i < 2; i += 1){
			if(checkX < 0 || checkY > 7 || checkX > 7){
				break;
			}
			if(!board.posValid(checkX, checkY)){
			ChessPieces piece = board.positions.get(checkX).get(checkY);
				if(currentTeam.contains(piece) && (currentTeam.get(0).isWhite() && piece instanceof WhiteKnight) || 
						(!currentTeam.get(0).isWhite() && piece instanceof BlackKnight)){
					return 1;
				}
			}
			
			checkX += 2;
		}
		
		return 0;
	}
	
	public static boolean[] turn(ChessPieces toMove, int xPos, int yPos){
		int index = currentTeam.indexOf(toMove);
		boolean[] results = new boolean[2];
		results[0] = false;
		results[1] = false;
		boolean moved = false;
		if(index < 0){
			return results;
		}
		if(causeCheck(currentTeam.get(0).getX(), currentTeam.get(0).getY(), opponent)){
			moved = currentTeam.get(index).move(xPos, yPos);
			if(!moved){
				return results;
			}else if(causeCheck(currentTeam.get(0).getX(), currentTeam.get(0).getY(), opponent)){
				board.positions.get(currentTeam.get(index).x).set(currentTeam.get(index).y, null);
				currentTeam.get(index).x = oldX;
				currentTeam.get(index).y = oldY;
				board.positions.get(oldX).set(oldY, currentTeam.get(index));
				System.out.println("Invalid Move. Choose another move.");
				return results;
			}
		}else{
			moved = currentTeam.get(index).move(xPos, yPos);
		}
		if(!moved){
			return results;
		}
		if(moved){
			if(currentTeam.get(index) instanceof WhitePawn){
				if(currentTeam.get(index).y == 0){
					while(true){
						System.out.println("Choose a piece type to promote to: ");
						String s = sc.nextLine();
						if(!s.equals("Queen") && !s.equals("Rook") && !s.equals("Knight") && !s.equals("Bishop")){
							System.out.println("Invalid Choice Choose Again");
						}else{
							String piece = "White";
							String promoteTo = piece.concat(s);
							ChessPieces promoted = ((WhitePawn) (currentTeam.get(index))).promote(promoteTo);
							if(promoted == null){
								System.out.println("Piece not Found");
							}else{
								currentTeam.remove(index);
								currentTeam.add(promoted);
								board.positions.get(promoted.x).set(promoted.y, promoted);
								break;
							}
						}
					}
				}
			}else if(currentTeam.get(index) instanceof BlackPawn){
				if(currentTeam.get(index).y == 7){
					while(true){
						System.out.println("Choose a piece type to promote to: ");
						String s = sc.nextLine();
						if(!s.equals("Queen") && !s.equals("Rook") && !s.equals("Knight") && !s.equals("Bishop")){
							System.out.println("Invalid Choice Choose Again");
						}else{
							String piece = "Black";
							String promoteTo = piece.concat(s);
							ChessPieces promoted = ((BlackPawn) (currentTeam.get(index))).promote(promoteTo);
							if(promoted == null){
								System.out.println("Piece not Found");
							}else{
								currentTeam.remove(index);
								currentTeam.add(promoted);
								board.positions.get(promoted.x).set(promoted.y, promoted);
								break;
							}
						}
					}
				}
			}
		}
		
		ChessPieces opponentKing = opponent.get(0);
		if(causeCheck(opponentKing.x, opponentKing.y, currentTeam)){
			System.out.println("Check");
		}
		
		int possibleMoves = 0;
		int cantMove = 0;
		int xPoss = opponentKing.x;
		int yPoss = opponentKing.y;
		if(causeCheck(opponentKing.x, opponentKing.y, currentTeam)){
			if(opponentKing.testMove(xPoss, yPoss - 1)){
				possibleMoves += 1;
			}if(opponentKing.testMove(xPoss + 1, yPoss - 1)){
				possibleMoves += 1;
			}if(opponentKing.testMove(xPoss + 1, yPoss)){
				possibleMoves += 1;
			}if(opponentKing.testMove(xPoss + 1, yPoss + 1)){
				possibleMoves += 1;
			}if(opponentKing.testMove(xPoss, yPoss + 1)){
				possibleMoves += 1;
			}if(opponentKing.testMove(xPoss - 1, yPoss + 1)){
				possibleMoves += 1;
			}if(opponentKing.testMove(xPoss - 1, yPoss)){
				possibleMoves += 1;
			}if(opponentKing.testMove(xPoss - 1, yPoss - 1)){
				possibleMoves += 1;
			}
			
			possibleMoves = possibleMoves + moveOther(toMove.x, toMove.y, opponent);
			if(causeCheck(xPoss, yPoss - 1, currentTeam)){
				possibleMoves = possibleMoves + moveOther(xPoss, yPoss - 1, opponent);
			}else if(causeCheck(xPoss + 1, yPoss - 1, currentTeam)){
				possibleMoves = possibleMoves + moveOther(xPoss + 1, yPoss - 1, opponent);
			}else if(causeCheck(xPoss + 1, yPoss, currentTeam)){
				possibleMoves = possibleMoves + moveOther(xPoss + 1, yPoss, opponent);
			}else if(causeCheck(xPoss + 1, yPoss + 1, currentTeam)){
				possibleMoves = possibleMoves + moveOther(xPoss + 1, yPoss + 1, opponent);
			}else if(causeCheck(xPoss, yPoss + 1, currentTeam)){
				possibleMoves = possibleMoves + moveOther(xPoss, yPoss + 1, opponent);
			}else if(causeCheck(xPoss - 1, yPoss + 1, currentTeam)){
				possibleMoves = possibleMoves + moveOther(xPoss - 1, yPoss + 1, opponent);
			}else if(causeCheck(xPoss - 1, yPoss, currentTeam)){
				possibleMoves = possibleMoves + moveOther(xPoss - 1, yPoss, opponent);
			}else if(causeCheck(xPoss - 1, yPoss - 1, currentTeam)){
				possibleMoves = possibleMoves + moveOther(xPoss - 1, yPoss - 1, opponent);
			}
			
			if(opponentKing.x < toMove.x){
				if((toMove.isWhite() && toMove instanceof WhiteQueen || toMove instanceof WhiteRook) ||
						(!toMove.isWhite() && (toMove instanceof BlackQueen || toMove instanceof BlackRook))){
					int checkX = toMove.x - 1;
					while(checkX > opponentKing.x){
						possibleMoves = possibleMoves + moveOther(checkX, toMove.y, opponent);
						checkX -= 1;
					}
				}else if((toMove.isWhite() && (toMove instanceof WhiteQueen || toMove instanceof WhiteBishop || toMove instanceof WhitePawn)) ||
						(!toMove.isWhite() && (toMove instanceof BlackQueen || toMove instanceof BlackRook || toMove instanceof BlackPawn))){
					int checkX = toMove.x - 1;
					if(opponentKing.y < toMove.y){
						int checkY = toMove.y - 1;
						while(checkX > opponentKing.x){
							if(toMove instanceof WhitePawn){
								break;
							}if(toMove instanceof BlackPawn && checkX < toMove.x - 1){
								break;
							}
							possibleMoves = possibleMoves + moveOther(checkX, checkY, opponent);
							checkX -= 1;
							checkY -= 1;
						}
					}else if(opponentKing.y > toMove.y){
						int checkY = toMove.y - 1;
						while(checkX > opponentKing.x){
							if(toMove instanceof WhitePawn){
								break;
							}if(toMove instanceof BlackPawn && checkX < toMove.x - 1){
								break;
							}
							possibleMoves = possibleMoves + moveOther(checkX, checkY, opponent);
							checkX -= 1;
							checkY += 1;
						}
					}
					
				}
					
			}else if(opponentKing.x > toMove.x){
				if((toMove.isWhite() && toMove instanceof WhiteQueen || toMove instanceof WhiteRook) ||
						(!toMove.isWhite() && (toMove instanceof BlackQueen || toMove instanceof BlackRook))){
					int checkX = toMove.x + 1;
					while(checkX < opponentKing.x){
						possibleMoves = possibleMoves + moveOther(checkX, toMove.y, opponent);
						checkX += 1;
					}
				}else if((toMove.isWhite() && (toMove instanceof WhiteQueen || toMove instanceof WhiteBishop || toMove instanceof WhitePawn)) ||
						(!toMove.isWhite() && (toMove instanceof BlackQueen || toMove instanceof BlackRook || toMove instanceof BlackPawn))){
					int checkX = toMove.x - 1;
					if(opponentKing.y < toMove.y){
						int checkY = toMove.y - 1;
						while(checkX < opponentKing.x){
							if(toMove instanceof BlackPawn){
								break;
							}if(toMove instanceof WhitePawn && checkX > toMove.x + 1){
								break;
							}
							possibleMoves = possibleMoves + moveOther(checkX, checkY, opponent);
							checkX += 1;
							checkY -= 1;
						}
					}else if(opponentKing.y > toMove.y){
						int checkY = toMove.y - 1;
						while(checkX > opponentKing.x){
							if(toMove instanceof BlackPawn){
								break;
							}if(toMove instanceof WhitePawn && checkX > toMove.x + 1){
								break;
							}
							possibleMoves = possibleMoves + moveOther(checkX, checkY, opponent);
							checkX += 1;
							checkY += 1;
						}
					}
					
				}
					
			}
			
			if(yPoss -1 < 0){
				cantMove += 1;
			}if(xPoss + 1 >= 8 && yPoss - 1 < 0){
				cantMove += 1;
			}if((xPoss + 1) >= 8){
				cantMove += 1;
			}if((xPoss + 1 >= 8) && (yPoss + 1) >= 8){
				cantMove += 1;
			}if(yPoss + 1 >= 8){
				cantMove += 1;
			}if(xPoss - 1 < 0 && yPoss + 1 >= 8){
				cantMove += 1;
			}if(xPoss - 1 < 0){
				cantMove += 1;
			}if(xPoss - 1 < 0 && (yPoss - 1) < 0){
				cantMove += 1;
			}
			
			if((yPoss -1) >= 0){
				if(board.positions.get(xPoss).get(yPoss - 1) != null){
					if(board.positions.get(xPoss).get(yPoss - 1).isWhite() == opponentKing.isWhite()){
						cantMove += 1;
					}
				}
			}if(xPoss + 1 < 8 && (yPoss -1) >= 0){
				if(board.positions.get(xPoss + 1).get(yPoss - 1) != null){
					if(board.positions.get(xPoss + 1).get(yPoss - 1).isWhite() == opponentKing.isWhite()){
						cantMove += 1;
					}
				}
			}if(xPoss + 1 < 8){
				if(board.positions.get(xPoss + 1).get(yPoss) != null){
					if((xPoss + 1 < 8) && board.positions.get(xPoss + 1).get(yPoss).isWhite() == opponentKing.isWhite() ){
						cantMove += 1;
					}
				}
			}if(xPoss + 1 < 8 && yPoss + 1 < 8){
				if(board.positions.get(xPoss + 1).get(yPoss + 1) != null){
					if(board.positions.get(xPoss + 1).get(yPoss + 1).isWhite() == opponentKing.isWhite() ){
						cantMove += 1;
					}
				}
			}if(yPoss + 1 < 8){
				if(board.positions.get(xPoss).get(yPoss + 1) != null){
					if(board.positions.get(xPoss).get(yPoss + 1).isWhite() == opponentKing.isWhite() ){
						cantMove += 1;
					}
				}
			}if(xPoss - 1 >= 0 && yPoss + 1 < 8){
				if(board.positions.get(xPoss - 1).get(yPoss + 1) != null){
					if(board.positions.get(xPoss - 1).get(yPoss + 1).isWhite() == opponentKing.isWhite()){
						cantMove += 1;
					}
				}
			}if(xPoss - 1 >= 0){
				if(board.positions.get(xPoss - 1).get(yPoss) != null){
					if(board.positions.get(xPoss - 1).get(yPoss).isWhite() == opponentKing.isWhite() ){
						cantMove += 1;
					}
				}
			}if(xPoss - 1 >= 0 && yPoss - 1 >= 0){
				if(board.positions.get(xPoss - 1).get(yPoss - 1) != null){
					if(board.positions.get(xPoss - 1).get(yPoss - 1).isWhite() == opponentKing.isWhite()){
						cantMove += 1;
					}
				}
			}
			
			if(possibleMoves == 0 && cantMove < 8){
				System.out.println("CheckMate");
				results[1] = true;
			}else if(possibleMoves == 0 && cantMove >= 8){
				System.out.println("StaleMate");
				results[1] = true;
			}
		}
		
		
		whiteTurn = !whiteTurn;
		if(whiteTurn){
			currentTeam = white;
			opponent = black;
		}else{
			currentTeam = black;
			opponent = white;
		}
		
		results[0] = true;
		return results;
	}
	
	public static void displayBoard(){
		javaFx.updateCanvas(white, black);
		javaFx.updateTaken(whiteTaken, blackTaken);
	}

}
