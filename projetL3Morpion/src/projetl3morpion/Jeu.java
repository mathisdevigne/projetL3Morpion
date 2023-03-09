/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetl3morpion;

import java.io.IOException;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author theodusehu
 */
public class Jeu extends ScrollPane{
    
    private Button escape;
    private Data datas;
    private BorderStroke bs = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderWidths.DEFAULT);

    
    public Jeu() throws IOException {
            
        datas = Data.getInstance();
                
        Game monJeu;
        if(datas.getExtension4()){
            if(datas.getPlayStyle() == 0){
                monJeu = new Game(datas.getLink());
            }else{
                monJeu = new Game(datas.getShapeBoard());
                monJeu.updateAllWeight();
            }
            
        }
        else{
            monJeu = new Game((int)datas.getWidth(), (int)datas.getHeight());
        }
        
        //======
        //HEADER
        //======
        VBox header = new VBox();
        Label titre = new Label("Jeu de Morpion");
        titre.setStyle("-fx-font-size: 40px;");

        HBox scoreLabel = new HBox();
        Label labelJoueur = new Label("Joueur : " + monJeu.getScoreJoueur());
        labelJoueur.setStyle("-fx-font-size: 20px;");
        Label labelIA = new Label(monJeu.getScoreIA() + " : IA");
        labelIA.setStyle("-fx-font-size: 20px;");
        Label labelVS = new Label("VS");
        labelVS.setStyle("-fx-text-fill: red; -fx-font-size: 20px;");
        scoreLabel.getChildren().addAll(labelJoueur, labelVS, labelIA);
        scoreLabel.setAlignment(Pos.CENTER);
        scoreLabel.setSpacing(10);

        header.getChildren().addAll(titre, scoreLabel);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20,20,20,20));

        //======
        //FOOTER
        //======
        HBox footer = new HBox();
        Button quit = new Button("Quit");
        quit.setOnAction(f->Platform.exit());
        Button restart = new Button("Restart");
        restart.setOnAction(e->
        {
            if(datas.getExtension4()){
                monJeu.resetBoard();
                monJeu.setGameBoard(datas.getShapeBoard());
            }
            else{
                monJeu.resetBoard();
                labelIA.setText(monJeu.getScoreIA() + " : IA");
                labelJoueur.setText("Joueur : " + monJeu.getScoreJoueur());
            }
        });
        Button menu = new Button("Menu");
        menu.setOnAction(e->
        {
            datas.getStage().setTitle("Menu");
            datas.getStage().setScene(datas.getScene());
        });
        Region ressort1 = new Region();
        HBox.setHgrow(ressort1, Priority.ALWAYS);
        Region ressort2 = new Region();
        HBox.setHgrow(ressort2, Priority.ALWAYS);
        footer.getChildren().addAll(restart,ressort1,menu, ressort2, quit);
        footer.setAlignment(Pos.CENTER);

        //=========
        //GAMEBOARD
        //=========
        VBox board = new VBox();
        board.getChildren().addAll(monJeu.getGameboard(), footer);
        monJeu.printUI();
        board.setAlignment(Pos.CENTER);
        board.setMaxWidth(monJeu.getGameboard().getWidth());
        board.setSpacing(20);
        
        //=============
        //AJOUT AU ROOT
        //=============
        
        BorderPane root = new BorderPane();
        
        root.setCenter(board);
        root.setTop(header);
        root.setPadding(new Insets(0,20,20,20));
        BorderPane.setAlignment(header, Pos.CENTER);
        
        this.setPannable(true);
        this.setContent(root);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        this.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);

        monJeu.getGameboard().setOnMouseClicked(g->
        {
            monJeu.play((int)(g.getX()/monJeu.getGameboard().getSize()), (int)(g.getY()/monJeu.getGameboard().getSize()));
            labelIA.setText(monJeu.getScoreIA() + " : IA");
            labelJoueur.setText("Joueur : " + monJeu.getScoreJoueur());
        });
    }
    
}
