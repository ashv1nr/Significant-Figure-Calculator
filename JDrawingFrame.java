//
//	CS1 - JDrawingFrame                          © 2007 - Cypress Creek H.S.
//
// 	   Thread:	Introduction to Graphics
//	Criterion:	JDrawingFrame class
//	========================================================================
//
//	Version:	1.09
//	   Date:	10/29/2009
//

// package drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;


public class JDrawingFrame extends JFrame
{
	private final double	VERSION		= 1.09;
	private boolean 		debug 		= false;

	private BufferedImage	bgImage 	= null;
	private BufferedImage	staticImage	= null;

    private int FRAMES_PER_SECOND       = 100;			// Controls how often we redraw
    private int	speed                   = 1000 / FRAMES_PER_SECOND;
	private javax.swing.Timer timer     = null;

	private boolean 		keypressed 	= false;
	private char 			key     	= 0;

	protected int 			width		= 800;
	protected int 			height		= 600;
	private Color 			background	= Color.black;

	private boolean         refresh   	= false;
	public  Graphics2D		pen         = null;

	public JDrawingFrame()
	{
		setTitle("JDrawingFrame - " + VERSION);
		setSize(width,height);
		setResizable(false);

		setImages();
		setContentPane( new DrawingPanel() );

        timer = new javax.swing.Timer( speed, new TimerListener() );

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener( new KeyBoard() );
	}

	class DrawingPanel extends JPanel
	{
		DrawingPanel()
		{
//			super(false);
		}

    	public void paintComponent(Graphics g)
    	{
			if(refresh)
				clear();

			draw();

			g.drawImage( bgImage, 0, 0, null );			//	draw background
			g.drawImage( staticImage, 0, 0, null ); 	//	draw the image

			draw(g);
		}
    }

	public void setRefresh(boolean r)
	{
		refresh = r;
	}

	public boolean getRefresh()
	{
		return refresh;
	}

	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			step();
	    	repaint();
		}
	}

	public void step()
	{
	}

	public void draw(Graphics g)
	{
	}

	public void draw()
	{
	}

	public void showFrame()
	{
		setVisible(true);
		toFront();
	}

	public void hideFrame()
	{
		setVisible(false);
		toBack();
	}

	class KeyBoard extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			if(  Character.isValidCodePoint(e.getKeyCode()) )
			{
				keypressed = true; key = e.getKeyChar();
				if(debug)
					System.out.println(key + " : " + (int)key );
			}

			switch( e.getKeyCode() )
			{
				case KeyEvent.VK_F11:	 	timer.start();	break;
				case KeyEvent.VK_F12:	 	timer.stop();	break;
				case KeyEvent.VK_ESCAPE: 	System.exit(0);	break;
			}
		}
	}

	public void refresh()
	{
		repaint();
	}

	public void clear()
	{
		Graphics2D g2d = staticImage.createGraphics();
		    Color transparent = new Color(0, 0, 0, 0);	// 	Make all filled
		    g2d.setColor(transparent);					//	pixels transparent
		    g2d.setComposite(AlphaComposite.Src);
		    g2d.fillRect(0,0,getWidth(), getHeight());
	    g2d.dispose();
	}

	public void setBackground(Object name)
	{
		if(name instanceof String)
		{
			if (!(new File((String)name).exists() ))
			{
				System.out.println("\nBackground file \""+name+"\" not found.\n");
				System.exit(0);
			}

			ImageIcon imageSource = new ImageIcon( (String)name );
			bgImage = new BufferedImage( imageSource.getIconWidth(), imageSource.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

			bgImage.createGraphics().drawImage( imageSource.getImage(), 0, 0, null );

			setSize( bgImage.getWidth(), bgImage.getHeight() );
		}
		else
			setBackgroundImage();
	}

	public void setBackground(Color c)
	{
		super.setBackground(c);
		background = c;

		if(bgImage != null)
		{
			Graphics g = bgImage.getGraphics();
				g.setColor( (Color)c );
				g.fillRect(0,0,getWidth(),getHeight());
				g.setColor( getForeground() );
			g.dispose();
		}
	}

	public void setForeground(Color c)
	{
		super.setForeground(c);
		pen.setColor(c);
	}

	public void setSize(int w, int h)
    {
    	super.setSize(w, h);
    	width  = w;
    	height = h;

		setLocationRelativeTo(null);

    	setImages();
	}

	protected void setImages()
	{
		setBackgroundImage();
		setStaticImage();

		pen = staticImage.createGraphics();
		pen.setColor( getForeground() );
		validate();
	}

	private void setBackgroundImage()
	{
		if( bgImage == null ||
			bgImage.getWidth()  != width ||
			bgImage.getHeight() != height  )
		{
			bgImage  = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );

			Graphics bc = bgImage.createGraphics();
				bc.setColor( background );
				bc.fillRect(0,0,getWidth(),getHeight());
				bc.setColor( getForeground() );
			bc.dispose();
		}
	}

	private void setStaticImage()
	{
		if( staticImage == null )
			staticImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB);

		if( staticImage.getWidth()  != width ||
			staticImage.getHeight() != height )
		{
			BufferedImage tempImage  = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB);
			tempImage.getGraphics().drawImage( staticImage, 0, 0, null );

			staticImage = tempImage;
		}
	}

	public void showFullScreen()
	{
		Dimension screenSize = getToolkit().getScreenSize();
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();

		setSize( width, height );

		setLocation(0,0);
		setUndecorated(true);
		setVisible(true);
		toFront();
	}

	public void setLineWidth(int size)
	{
		pen.setStroke( new BasicStroke(size, ((BasicStroke)pen.getStroke()).getEndCap(), ((BasicStroke)pen.getStroke()).getLineJoin()) );
	}

	public void setLineWidth(Graphics g, int size)
	{
		((Graphics2D)g).setStroke( new BasicStroke(size, ((BasicStroke)((Graphics2D)g).getStroke()).getEndCap(), ((BasicStroke)((Graphics2D)g).getStroke()).getLineJoin()) );
	}

	public void setLineEndCap(int endCap)
	{
		pen.setStroke( new BasicStroke(((BasicStroke)pen.getStroke()).getLineWidth(), endCap , ((BasicStroke)pen.getStroke()).getLineJoin()) );
	}

	public void setLineEndCap(Graphics g, int endCap)
	{
		((Graphics2D)g).setStroke( new BasicStroke(((BasicStroke)((Graphics2D)g).getStroke()).getLineWidth(), endCap , ((BasicStroke)((Graphics2D)g).getStroke()).getLineJoin()) );
	}

	public void setLineJoin(int join)
	{
		pen.setStroke( new BasicStroke( ((BasicStroke)pen.getStroke()).getLineWidth(), ((BasicStroke)pen.getStroke()).getEndCap(), join ));
	}

	public void setLineJoin(Graphics g, int join)
	{
		((Graphics2D)g).setStroke( new BasicStroke( ((BasicStroke)((Graphics2D)g).getStroke()).getLineWidth(), ((BasicStroke)((Graphics2D)g).getStroke()).getEndCap(), join ));
	}

	public Graphics2D getPen()
	{
		return pen;
	}

	public void setColor(Color c)
	{
		pen.setColor(c);
	}

	public void setColor(Graphics g, Color c)
	{
		g.setColor(c);
	}

	public void resetColor()
	{
		pen.setColor( getForeground() );
	}

	public void resetColor(Graphics g)
	{
		g.setColor( getForeground() );
	}

	public void setFontName(String name)
	{
		pen.setFont(new Font(name,pen.getFont().getStyle(),pen.getFont().getSize()));
	}

	public void setFontName(Graphics g, String name)
	{
		g.setFont(new Font(name,pen.getFont().getStyle(),pen.getFont().getSize()));
	}

	public void setFontStyle(int style)
	{
		pen.setFont(new Font(pen.getFont().getName(),style,pen.getFont().getSize()));
	}

	public void setFontStyle(Graphics g, int style)
	{
		g.setFont(new Font(pen.getFont().getName(),style,pen.getFont().getSize()));
	}

	public void setFontSize(int size)
	{
		pen.setFont(new Font(pen.getFont().getName(),pen.getFont().getStyle(),size));
	}

	public void setFontSize(Graphics g, int size)
	{
		g.setFont(new Font(pen.getFont().getName(),pen.getFont().getStyle(),size));
	}

	public void setFont(Font font)
	{
		pen.setFont(font);
	}

	public void setFont(Graphics g, Font font)
	{
		g.setFont(font);
	}

	public boolean keypressed()
	{
		if(!this.keypressed)
			return false;

		this.keypressed = false;
		return true;
	}

	public char getKey()
	{
		return key;
	}

	public void delay(int time)
	{
		try { Thread.sleep(time);
		} catch( InterruptedException e ){}
	}

	public Color getRandomColor()
	{
		return 	new Color(
			(int)(Math.random()*255),
			(int)(Math.random()*255),
			(int)(Math.random()*255)
		);
	}

	public void beep()
	{
		System.out.print("\u0007");
	}

	public void setDebug(boolean b)
	{
		debug = b;
	}

	public void setFrameRate(int r)
	{
		FRAMES_PER_SECOND = r;
		speed = 1000 / FRAMES_PER_SECOND;

		timer.setDelay(speed);
	}

	public void setTimer(boolean run)
	{
		if(timer!=null)
		{
			if(run)
				timer.start();
			else
				timer.stop();
		}
	}
}





