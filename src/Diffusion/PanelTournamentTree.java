package Diffusion;
import javax.swing.*;

import DiffusionPlayers.PlayerForDiffusion;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

class Player {
    String name;
    int x;
    int y;

    public Player(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
}

public class PanelTournamentTree extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<PlayerForDiffusion> players;
	private int selectedPlayerIndex = -10;
    private int horizontalSpacing = 150;
    private int lineWidth = 2;
    private Color treeColor = Color.BLACK; // Couleur par défaut
    private Color selectedPlayerPathColor = Color.RED;    
    private Color pathColor = Color.RED;    
    private int xLeftPLayer = 10;
    private int xRightPLayer = 500;
    private int nbJoueur;
    private Timer animationTimer;
    private int animationDuration;
    private int blinkCount;
    private int currentBlinkCount;
    private boolean isBlinking = false;
    
    public void setXLeftTree(int x) {
    	xLeftPLayer = x;
    	repaint();
    }
    public void setXRightTree(int x) {
    	xRightPLayer = x;
    	repaint();
    }

    public PanelTournamentTree(int nbJoueurs, PanelAnimationConfiguration windowAnimationConfiguration, ArrayList<PlayerForDiffusion> players2, int horizontalSpacing, int lineWidth) {
        this.nbJoueur = nbJoueurs;
    	this.players = players2;
        this.horizontalSpacing = horizontalSpacing;
        this.lineWidth = lineWidth;
        this.xLeftPLayer = windowAnimationConfiguration.getXLeftTree();
        this.xRightPLayer = windowAnimationConfiguration.getXRightTree();
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(lineWidth));
        g2.setColor(treeColor); // Définit la couleur pour le dessin
        
        int numPlayers = this.nbJoueur;
        int halfNumPlayers = numPlayers / 2;
        int[] xPositionsLeft = new int[halfNumPlayers];
        int[] yPositionsLeft = new int[halfNumPlayers];
        int[] xPositionsRight = new int[halfNumPlayers];
        int[] yPositionsRight = new int[halfNumPlayers];

        // Draw players on the left
        for (int i = 0; i < halfNumPlayers; i++) {
        	int positionFlagPLayer = (int)( players.get(i).playerName.getLocation().getY() + (players.get(i).playerName.getBounds().getHeight()/2.0));
            xPositionsLeft[i] = xLeftPLayer;
            yPositionsLeft[i] = positionFlagPLayer;
        }

        // Draw players on the right
        for (int i = halfNumPlayers; i < numPlayers; i++) {
        	int positionFlagPLayer = (int)( players.get(i).playerName.getLocation().getY() + players.get(i).playerName.getBounds().getHeight()/2.0);
            xPositionsRight[i - halfNumPlayers] = xRightPLayer;
            yPositionsRight[i - halfNumPlayers] = positionFlagPLayer;
        }

        drawTournamentTree(g2, xPositionsLeft, yPositionsLeft, halfNumPlayers, horizontalSpacing, true,selectedPlayerIndex);
        drawTournamentTree(g2, xPositionsRight, yPositionsRight, halfNumPlayers, horizontalSpacing, false,selectedPlayerIndex);
        g2.draw(new Line2D.Double(lastXLeft, lastYLeft, lastXRight, lastYRight));
        
    }
    private int lastXLeft;
    private int lastXRight;
    private int lastYLeft;
    private int lastYRight;
    private void drawTournamentTree(Graphics2D g2, int[] xPositions, int[] yPositions, int numPlayers, int spacing, boolean isLeft,int playerIndex) {
    	g2.setColor(treeColor); // Assurez-vous que la couleur est définie ici aussi
        if (numPlayers <= 1) return;

        int[] newXPositions = new int[numPlayers / 2];
        int[] newYPositions = new int[numPlayers / 2];
        
        boolean playerIndexInTree;
        int indexPlayer = playerIndex;
        if(indexPlayer>=numPlayers) {
        	if(!isLeft)
        		playerIndexInTree = true;
        	else
        		playerIndexInTree = false;
        	indexPlayer=indexPlayer-numPlayers;
        }else {
        	if(isLeft)
        		playerIndexInTree = true;
        	else
        		playerIndexInTree = false;
        }
        
//        System.out.println("\nplayer index, "+playerIndex+" ,index utiliser "+indexPlayer+" ,index in tree "+playerIndexInTree+" ,tree Left? "+isLeft);
        for (int i = 0; i < numPlayers; i += 2) {
            int x1 = xPositions[i];
            int y1 = yPositions[i];
            int x2 = xPositions[i + 1];
            int y2 = yPositions[i + 1];

            int midX = isLeft ? x1 + spacing : x1 - spacing;
            int midY = (y1 + y2) / 2;

            // Draw horizontal lines from players to mid
            if(i == indexPlayer && playerIndexInTree) {
            	g2.setColor(pathColor);
            	g2.draw(new Line2D.Double(x1, y1, midX, y1));
            	g2.setColor(treeColor);
            }else
            	g2.draw(new Line2D.Double(x1, y1, midX, y1));
            
            if(i+1 == indexPlayer && playerIndexInTree) {
            	g2.setColor(pathColor);
            	g2.draw(new Line2D.Double(x2, y2, midX, y2));
            	g2.setColor(treeColor);
            }else
            	g2.draw(new Line2D.Double(x2, y2, midX, y2));
            
            // Draw vertical line connecting two players
            if(i == indexPlayer && playerIndexInTree) {
            	g2.setColor(pathColor);
            	g2.draw(new Line2D.Double(midX, y1, midX, midY));
            	g2.setColor(treeColor);
            }else
            	g2.draw(new Line2D.Double(midX, y1, midX, midY));
            
            if(i+1 == indexPlayer && playerIndexInTree) {
            	g2.setColor(pathColor);
            	g2.draw(new Line2D.Double(midX, y2, midX, midY));
            	g2.setColor(treeColor);
            }else
            	g2.draw(new Line2D.Double(midX, y2, midX, midY));

            
            
            // Draw horizontal line from mid to the next round
            int nextX = isLeft ? midX + spacing : midX - spacing;
            if((i == indexPlayer || i+1 == indexPlayer) && playerIndexInTree) {
            	g2.setColor(pathColor);
            	g2.draw(new Line2D.Double(midX, midY, nextX, midY));
            	g2.setColor(treeColor);
            }else
            	g2.draw(new Line2D.Double(midX, midY, nextX, midY));

            newXPositions[i / 2] = nextX;
            newYPositions[i / 2] = midY;
            if(isLeft)
            	lastXLeft = nextX;
            else 
            	lastXRight = nextX;
            if(isLeft)
            	lastYLeft = midY;
            else 
            	lastYRight = midY;
        }
        
        drawTournamentTree(g2, newXPositions, newYPositions, numPlayers / 2, spacing, isLeft,playerIndex/2);
    }
    public void setTreeColor(Color color) {
        this.treeColor = color;
        repaint(); // Redessine le panel avec la nouvelle couleur
    }
    public void setLineWidth(int width) {
        this.lineWidth = width;
        repaint(); // Redessine le panel avec la nouvelle épaisseur de ligne
    }
    public void setHorizontalSpacing(int spacing) {
        this.horizontalSpacing = spacing;
        repaint(); // Redessine le panel avec le nouveau espacement horizontal
    }
    public void setSelectedpathColor(Color selectedColor) {
    	selectedPlayerPathColor = selectedColor;
    }
    public void highlightPlayerPath(int player, Color pathColor) {
        this.selectedPlayerIndex = player;
        this.pathColor = pathColor;
        repaint();
    }
    public void setPlayer(int index,PlayerForDiffusion playerFD) {
    	this.players.set(index, playerFD);
    	repaint();
    }
    public void animatePlayerPath(int player,int durationAnimation,int blinkCounts) {
    	this.selectedPlayerIndex = player;
    	
    	this.animationDuration = durationAnimation;
        this.blinkCount = blinkCounts;
        this.currentBlinkCount = 0;
    	
    	if (animationTimer != null) {
            animationTimer.stop();
        }
    	if (blinkCount == 0) {
            // Si blinkCount est 0, on change simplement la couleur pendant la durée spécifiée
            isBlinking = false;
            this.pathColor = selectedPlayerPathColor;
            animationTimer = new Timer(animationDuration, e -> {
            	this.pathColor = treeColor;
//                selectedPlayerIndex = -10; // Reset l'index du joueur sélectionné
                repaint();
                ((Timer)e.getSource()).stop();
            });
            animationTimer.setRepeats(false);
        }else {
            // Sinon, on fait clignoter la branche
            isBlinking = true;
            this.pathColor = treeColor;
            int blinkInterval = animationDuration / (2 * blinkCount);
            animationTimer = new Timer(blinkInterval, e -> {
                isBlinking = !isBlinking;
                if(!isBlinking)
                	this.pathColor = selectedPlayerPathColor;
                else
                	this.pathColor = treeColor;
                repaint();
                currentBlinkCount++;
                if (currentBlinkCount >= 2 * blinkCount) {
                	this.pathColor = treeColor;
                	repaint();
//                    selectedPlayerIndex = -10; // Reset l'index du joueur sélectionné
                    ((Timer)e.getSource()).stop();
                }
            });
        }        
        animationTimer.start();
        repaint();
    }
}