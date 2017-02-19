import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D; 
import java.awt.BasicStroke; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import javax.swing.JComponent;
import java.awt.geom.Line2D;


/**
 * A RecursiveTreePainting class that makes a GUI for a tree that can be draw by the user 
 * @author Sara Harda
 **/

public class RecursiveTreePainting extends JComponent implements MouseListener
{
	// Number of generations to create branches  
	public static final int NUM_GENERATIONS = 8;

    // Number of children for each branch
    public static final int NUM_CHILDREN = 3;  
 
	// Diameter of the blossoms. 
	public static final int BLOSSOM_DIAM = 4;

    // Number of copies of the trunk. Play with this for denser trees
    public static final int TRUNK_TWINS = 4;
 
	// Golden ratio that makes the child branches aesthetically appealing
	public static final double GOLDEN_RATIO = 1.618;

    // Maximum branching angle of children from a parent stick 
    public static final double MAX_BRANCHING_ANGLE = .5*Math.PI;

    // Green color for the trunk 
    protected Color brown = new Color (102, 51, 0);

    // Pink color for the branchs 
    protected Color green = new Color (0, 102, 0);

    // Random colors for the tree blossoms
    protected Color random = new Color ((int) (256*Math.random()), (int) (256*Math.random()), (int) (256*Math.random()));

    // Point which represents the top of the trunk
    protected Point2D p;

    // Point which represents the tip of the tree branch
    protected Point2D tip;

    // Coordinates for drawing the tree
    protected int start1;
    protected int end1;
    protected int start2 ;
    protected int end2;

    // Initializes the branch angle 
    protected double branch_angle;

    // Initializes and declares a boolean which indicates if the mouse has been pressed or released
    protected boolean press = false;
    protected boolean release = false;


    /** 
	 * Makes the TreePainting class sensitive to the mouse listener
	 */
    public RecursiveTreePainting () 
    {
    	addMouseListener(this);
    }

	/**
     * Method for drawing on this component
     * Overrides the paint method specified in JComponent (parent)
     * Paints a tree when the mouse is pressed then released
     * @param g the Graphics object to draw the target on
     */
    public void paint( Graphics g )
	{
        // Paints the background
        paintBackground(g);  

        // Creates a new graphics object named g2
        Graphics g2 = (Graphics) g;

        // Paints a tree if the user has pressed and released the mouse
        if( press && release ) 
        {
            // Calls the trunkMaking method
        	trunkMaKing(g);

        	// Calculates the angle of the trunk against a horizontal line
            double trunkAngle;        
            trunkAngle = Math.atan2 (end2 - end1, start2 - start1);

            // Creates the branches and the blossoms on each branch
            paintBranch(g, g2, p, lengthOfTrunk(start1, end1, start2, end2), trunkAngle, NUM_GENERATIONS);
        }
    }    

    /** 
     * Paints the background of the component in black
     * @param g the Graphics object to draw the target on
     */
    protected void paintBackground(Graphics g)
    {
        g.fillRect(0,0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
    }

    /*
     * Computes the point that is length away from point p at the specified angle
     * Uses cosine to get the new x coordinate, sine to get the new y coordinate
     */
    public Point2D computeEndpoint (Point2D p, double length, double angle)
    {    
        return new Point2D.Double( p.getX() + length*Math.cos(angle), p.getY() + length*Math.sin(angle));
    }

    /**
     * Method for making a tree trunk in brown 
     * @param g the Graphics object to draw the target on
     */
    public void trunkMaKing( Graphics g )
	{
        // Sets the color of the trunk
        g.setColor(brown);

        // Draws the trunk
        g.drawLine(start1, end1, start2, end2);
    }

    /*
     * Method for making the tree blossoms in random colors
     */
    public void blossomMaking (Graphics g, Point2D p)
    {
        //Sets random colors for the blossoms
        g.setColor(random);

        //draws the blossoms 
        g.fillOval ((int)(p.getX() - BLOSSOM_DIAM/2), (int)(p.getY() - BLOSSOM_DIAM/2), BLOSSOM_DIAM, BLOSSOM_DIAM);    
    }

    /**
     * Method for making the tree branches and their blossoms 
     */
    public void paintBranch( Graphics g, Graphics g2, Point2D p, double length, double angle, int generation )
	{
        // Base case of the recursion
        if (generation == 0){

            blossomMaking(g, p);
        }

        // Uses recursion for drawing the branches in every generation  
        else {
            for (int i = 0; i < NUM_CHILDREN; i++)
            {

                // Sets length of branches relative to length of the trunk
                double length_branch = (length/GOLDEN_RATIO);

                // Angle between the branchs
                double branch_angle = (Math.random()*(Math.PI))+(angle - (0.5*Math.PI));

                // Sets the coordinates of the branch tips
                tip = computeEndpoint (p, length_branch, branch_angle);
                    
                // Sets the color for the branches in green 
                g2.setColor(green);

                // Draws the first branches of the tree
                g2.drawLine((int)p.getX(), (int)p.getY(), (int)tip.getX(), (int)tip.getY());
                
                // Draws the children branches of the tree recursively
                paintBranch (g, g2, tip, length_branch, branch_angle, generation - 1);          
            }
        }       
    }

    /**
     * Method that returns the length of the tree trunk using Pythagoras theorem
     */
    public double lengthOfTrunk (int x1, int y1, int x2, int y2){

        // Pythagoras theorem
        double length_trunk = Math.hypot(x1 - x2, y1 - y2);

        // Returns the length of the trunk
        return length_trunk;
    }
    
    /** 
     * Records the pressed point as the starting point for the tree 
     */
    public void mousePressed ( MouseEvent e )
    {
        // Indicates that the mouse has been pressed
    	press = true;

        // Gets the coordinates of the point where the user presses the mouse
    	start1 = e.getX();
    	end1 = e.getY();
    }

    /** 
     * Records the pressed point as the starting point for the tree 
     */
    public void mouseReleased ( MouseEvent e )
    {
        // Indicates that the mouse has been released
    	release = true;

        // Gets the coordinates of the point where the user releases the mouse
    	start2 = e.getX();
    	end2 = e.getY();

        // Saves the x and y coordinates as a new point 'p'
        p = new Point2D.Double (start2, end2);

        // Repaints the canvas
    	repaint();
    }

    /**
	 * Invoked when the mouse enters a component
	 */
	public void mouseEntered(MouseEvent e){}

	/**
	 * Invoked when the mouse leaves a component
	 */
	public void mouseExited(MouseEvent e){}

	/**
	 * Invoked when the mouse is clicked 
	 */
	public void mouseClicked(MouseEvent e){}

}