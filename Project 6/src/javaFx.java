/*	Jason Cai	
 * 	EID: jsc3234
 * 	Vishakh Shukla 
 * 	EID: vys77
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.concurrent.*;

import java.util.ArrayList;

public class javaFx extends Application {
	private Canvas chessBoard = new Canvas(ChessBoard.getWidth() * 70, ChessBoard.getHeight() * 70);
	private Canvas wTaken = new Canvas(20, 80);
	private Canvas bTaken = new Canvas(20,80);
	private static GraphicsContext gc1 = null;
	private static GraphicsContext gc2 = null;
	private static GraphicsContext gc3 = null;
	final Label turn = new Label();
	final Label notification = new Label ();
	final Label wht = new Label();
	final Label blk = new Label();
	
	private ChessPieces piece = null;
	private int newX = 0;
	private int newY = 0;
	private boolean[] moved = new boolean[2];
	
	private boolean whiteTurn = true;
	
	class move implements Runnable{
		public void run() {
			moved = ChessPieces.turn(piece, newX, newY);
			piece = null;
			if(moved[0]){
				ChessPieces.displayBoard();
			}
		}
		
	}
	
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Chess");
		primaryStage.show();
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 900, 800, Color.LIGHTGRAY);
        primaryStage.setScene(scene);
        
        
        gc1 = chessBoard.getGraphicsContext2D();
        gc2 = wTaken.getGraphicsContext2D();
        gc3 = bTaken.getGraphicsContext2D();
        
        ChessPieces.displayBoard();
        
        Text scenetitle = new Text("Chess");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setTextAlignment(TextAlignment.CENTER);
        
        wht.setText("White's Taken");
        blk.setText("Black's Taken");
        
        final Button btn = new Button("Start Game");
        final Button qt = new Button("Quit");
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	moved[0] = false;
            	moved[1] = false;
            	turn.setText("White's Turn");
            	ChessPieces.placePieces();
            	ChessPieces.displayBoard();
            	btn.setDisable(true);
            }
        });
        
        qt.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	Stage st = (Stage) qt.getScene().getWindow();
     		 	st.close();
            }
        });
        
		chessBoard.addEventHandler(MouseEvent.MOUSE_CLICKED, 
			       new EventHandler<MouseEvent>() {
			           @Override
			           public void handle(MouseEvent e) {
			        	   if(piece == null && !moved[1]){
			            	   notification.setText("");
			            	   whiteTurn = ChessPieces.wTurn();
			            	   piece = ChessPieces.board.positions.get((int)(e.getX()/70)).get((int)(e.getY()/70));
			            	   if(piece == null || (whiteTurn != ChessPieces.inWhite(piece))){
			            		   notification.setText("You have no piece there");
			            		   piece = null;
			            	   }
			               }else if(piece != null && !moved[1]){
			            	   newX = (int)(e.getX()/70);
			            	   newY = (int)(e.getY()/70);
			            	   (new Thread(new move())).start();
			            	   if(moved[0]){
			            		   if(whiteTurn){
				            	   	turn.setText("Black's Turn");
				               	}else{
				            	   	turn.setText("White's Turn");
				               	}
			            	   }
			               }else{
			            	   turn.setText("Game Over");
			            	   btn.setDisable(false);
			               }
			           }
			       });
		
		turn.setText("");
		
		grid.add(scenetitle, 0, 0);
		grid.add(chessBoard, 0, 1);
		grid.add(btn, 1, 0);
		grid.add(qt, 2, 0);
		grid.add(notification, 0, 2);
		grid.add(wht, 1, 1);
		grid.add(blk, 2, 1);
		grid.add(wTaken, 1, 2);
		grid.add(bTaken, 2, 2);
		grid.add(turn, 1, 2);

	}

	public static void main(String[] args) {
		launch(args);

	}
	
	public static void updateCanvas(ArrayList<ChessPieces> white, ArrayList<ChessPieces> black){
		
		gc1.drawImage(ChessBoard.board(), 0, 0, 560, 560);
		for(int i = 0; i < white.size(); i += 1){
			int x = 70 * white.get(i).getX();
			int y = 70 * white.get(i).getY();
			gc1.drawImage(white.get(i).piece(), x, y, 70, 70);
		}
		
		for(int i = 0; i < black.size(); i += 1){
			int x = 70 * black.get(i).getX();
			int y = 70 * black.get(i).getY();
			gc1.drawImage(black.get(i).piece(), x, y, 70, 70);
		}
	}
	
	public static void updateTaken(ArrayList<ChessPieces> whiteTaken, ArrayList<ChessPieces> blackTaken){
		int x = 0;
		int y = 0;
		for(int i = 0; i < whiteTaken.size(); i += 1){
			gc2.drawImage(whiteTaken.get(i).piece(), x, y, 10, 10);
			x += 10;
			if(x >= 20){
				x = 0;
				y += 10;
			}
		}
		
		x = 0;
		y = 0;
		for(int i = 0; i < blackTaken.size(); i += 1){
			gc3.drawImage(blackTaken.get(i).piece(), x, y, 10, 10);
			x += 10;
			if(x >= 20){
				x = 0;
				y += 10;
			}
		}
	}
}
