/*
 * 
 */
package Diffusion;
import javax.swing.*;

import Animation.PanelAnimationConfiguration;
import DiffusionPlayers.PlayerForDiffusion;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
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

/**
 * The Class PanelTournamentTree.
 */
public class PanelTournamentTree extends JPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The players. */
	private ArrayList<PlayerForDiffusion> players;
	
	/** The selected player index. */
	private int selectedPlayerIndex = -10;
    
    /** The horizontal spacing. */
    private int horizontalSpacing = 150;
    
    /** The line width. */
    private int lineWidth = 2;
    
    /** The tree color. */
    private Color treeColor = Color.BLACK; // Couleur par défaut
    
    /** The selected player path color. */
    private Color selectedPlayerPathColor = Color.RED;    
    
    /** The path color. */
    private Color pathColor = Color.RED;    
    
    /** The x left P layer. */
    private int xLeftPLayer = 10;
    
    /** The x right P layer. */
    private int xRightPLayer = 500;
    
    /** The nb joueur. */
    private int nbJoueur;
    
    /** The animation timer. */
    private Timer animationTimer;
    
    /** The animation duration. */
    private int animationDuration;
    
    /** The blink count. */
    private int blinkCount;
    
    /** The current blink count. */
    private int currentBlinkCount;
    
    /** The is blinking. */
    private boolean isBlinking = false;
    
    /**
     * Sets the x left tree.
     *
     * @param x the new x left tree
     */
    public void setXLeftTree(int x) {
    	xLeftPLayer = x;
    	repaint();
    }
    
    /**
     * Sets the x right tree.
     *
     * @param x the new x right tree
     */
    public void setXRightTree(int x) {
    	xRightPLayer = x;
    	repaint();
    }

    /**
     * Instantiates a new panel tournament tree.
     *
     * @param nbJoueurs the nb joueurs
     * @param windowAnimationConfiguration the window animation configuration
     * @param players2 the players 2
     * @param horizontalSpacing the horizontal spacing
     * @param lineWidth the line width
     */
    public PanelTournamentTree(int nbJoueurs, PanelAnimationConfiguration windowAnimationConfiguration, ArrayList<PlayerForDiffusion> players2, int horizontalSpacing, int lineWidth) {
        this.nbJoueur = nbJoueurs;
    	this.players = players2;
        this.horizontalSpacing = horizontalSpacing;
        this.lineWidth = lineWidth;
        this.xLeftPLayer = windowAnimationConfiguration.getXLeftTree();
        this.xRightPLayer = windowAnimationConfiguration.getXRightTree();
        this.setOpaque(false);
    }

    /**
     * Paint component.
     *
     * @param g the g
     */
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
//        g2.draw(new Line2D.Double(lastXLeft, lastYLeft, lastXRight, lastYRight));
        // Dessiner la ligne divisée en deux parties
        int middleX = (lastXLeft + lastXRight) / 2;  // Point de séparation de la ligne
        g2.draw(new Line2D.Double(lastXLeft, lastYLeft, middleX, lastYLeft));
        g2.draw(new Line2D.Double(middleX, lastYRight, lastXRight, lastYRight));
        
     // Draw the selected player's path
        if (selectedPlayerIndex >= 0) {
            g2.setColor(pathColor);
            if (selectedPlayerIndex < halfNumPlayers) {
                g2.draw(new Line2D.Double(lastXLeft, lastYLeft, middleX, lastYLeft)); // Color the left half
            } else {
                g2.draw(new Line2D.Double(middleX, lastYRight, lastXRight, lastYRight)); // Color the right half
            }
        }
        
    }
    
    /** The last X left. */
    private int lastXLeft;
    
    /** The last X right. */
    private int lastXRight;
    
    /** The last Y left. */
    private int lastYLeft;
    
    /** The last Y right. */
    private int lastYRight;
    
    /**
     * Draw tournament tree.
     *
     * @param g2 the g 2
     * @param xPositions the x positions
     * @param yPositions the y positions
     * @param numPlayers the num players
     * @param spacing the spacing
     * @param isLeft the is left
     * @param playerIndex the player index
     */
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
    
    /**
     * Sets the tree color.
     *
     * @param color the new tree color
     */
    public void setTreeColor(Color color) {
        this.treeColor = color;
        repaint(); // Redessine le panel avec la nouvelle couleur
    }
    
    /**
     * Sets the line width.
     *
     * @param width the new line width
     */
    public void setLineWidth(int width) {
        this.lineWidth = width;
        repaint(); // Redessine le panel avec la nouvelle épaisseur de ligne
    }
    
    /**
     * Sets the horizontal spacing.
     *
     * @param spacing the new horizontal spacing
     */
    public void setHorizontalSpacing(int spacing) {
        this.horizontalSpacing = spacing;
        repaint(); // Redessine le panel avec le nouveau espacement horizontal
    }
    
    /**
     * Sets the selectedpath color.
     *
     * @param selectedColor the new selectedpath color
     */
    public void setSelectedpathColor(Color selectedColor) {
    	selectedPlayerPathColor = selectedColor;
    }
    
    /**
     * Highlight player path.
     *
     * @param player the player
     * @param pathColor the path color
     */
    public void highlightPlayerPath(int player, Color pathColor) {
        this.selectedPlayerIndex = player;
        this.pathColor = pathColor;
        repaint();
    }
    
    /**
     * Sets the player.
     *
     * @param index the index
     * @param playerFD the player FD
     */
    public void setPlayer(int index,PlayerForDiffusion playerFD) {
    	this.players.set(index, playerFD);
    	repaint();
    }
    
    /**
     * Animate player path.
     *
     * @param player the player
     * @param durationAnimation the duration animation
     * @param blinkCounts the blink counts
     */
    public void animatePlayerPath(int player,int durationAnimation,int blinkCounts) {
    	this.selectedPlayerIndex = player;
    	
    	this.animationDuration = durationAnimation;
        this.blinkCount = blinkCounts;
        this.currentBlinkCount = 0;
    	
        PanelAnimationConfiguration.animRunning = true;
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
                PanelAnimationConfiguration.animRunning = false;
            });
            animationTimer.setRepeats(false);
        }else {
            // Sinon, on fait clignoter la branche
            isBlinking = true;
            this.pathColor = treeColor;
            int blinkInterval = animationDuration / (2 * blinkCount);
//            PanelAnimationConfiguration.animRunning = true;
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
                    PanelAnimationConfiguration.animRunning = false;
                }
            });
        }        
        animationTimer.start();
        repaint();
    }
}