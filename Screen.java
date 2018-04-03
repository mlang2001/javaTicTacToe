import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Screen extends JPanel implements MouseListener, ActionListener
{
	private Game game;
	private String notificationText;
	private Font font, font2;
	private JButton restartButton, twoPlayerButton, onePlayerButton;
	private boolean pointsAdded;
	private int oneTwoPlayer;
	private BufferedImage spaceImg;
	public Screen()
	{
		this.setLayout(null);
		game = new Game();
		notificationText = "";
		addMouseListener(this);
		font = new Font("Futura", Font.BOLD, 50);
		font2 = new Font("Futura", Font.BOLD, 30);
		pointsAdded = false;
		oneTwoPlayer = 0;

		restartButton = new JButton("Restart");
		restartButton.setBounds(140,300,240,50);
		restartButton.addActionListener(this);
		this.add(restartButton);
		restartButton.setVisible(false);
		restartButton.setFont(font2);
		restartButton.setForeground(Color.WHITE);
		restartButton.setBackground(Color.BLACK);
		restartButton.setBorderPainted(false);

		onePlayerButton = new JButton("One Player");
		onePlayerButton.setBounds(30,300,240,50);
		onePlayerButton.addActionListener(this);
		this.add(onePlayerButton);
		onePlayerButton.setVisible(true);
		onePlayerButton.setFont(font2);
		onePlayerButton.setForeground(Color.WHITE);
		onePlayerButton.setBackground(Color.BLACK);
		onePlayerButton.setBorderPainted(false);

		twoPlayerButton = new JButton("Two Player");
		twoPlayerButton.setBounds(250,300,240,50);
		twoPlayerButton.addActionListener(this);
		this.add(twoPlayerButton);
		twoPlayerButton.setVisible(true);
		twoPlayerButton.setVisible(true);
		twoPlayerButton.setFont(font2);
		twoPlayerButton.setForeground(Color.WHITE);
		twoPlayerButton.setBackground(Color.BLACK);
		twoPlayerButton.setBorderPainted(false);

		try {
            spaceImg = ImageIO.read(new File("space.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


		this.setFocusable(true);
	}

	public Dimension getPreferredSize() 
	{
		//Sets the size of the panel
		return new Dimension(500,500);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.drawImage(spaceImg, 0, 0, null);
		if(oneTwoPlayer == 1)
		{
			onePlayerButton.setVisible(false);
			twoPlayerButton.setVisible(false);
			g.setFont(font);
			game.printTable();
			if(game.checkTicTacToe() == 0 && !game.checkFull())
			{
				game.drawMe(g);
			}
			else if(game.checkTicTacToe() == 1)
			{
				g.drawString("Player 1 wins!", 65, 200);
				if(pointsAdded == false)
				{
					game.incrementWins1();
					pointsAdded = true;
					playSoundWin();
				}
				restartButton.setVisible(true);
			}
			else if(game.checkTicTacToe() == 2)
			{
				g.drawString("Player 2 wins!", 65, 200);
				if(pointsAdded == false)
				{
					game.incrementWins2();
					pointsAdded = true;
					playSoundLose();
				}
				restartButton.setVisible(true);
			}
			else if(game.checkFull())
			{
				g.drawString("Cat's Game", 100, 200);
				restartButton.setVisible(true);
				if(pointsAdded == false)
				{
					playSoundCat();
					pointsAdded = true;
				}
			}
		}
		else if(oneTwoPlayer == 2)
		{
			onePlayerButton.setVisible(false);
			twoPlayerButton.setVisible(false);
			g.setFont(font);
			if(game.checkTicTacToe() == 0 && !game.checkFull())
			{
				game.drawMe(g);
			}
			else if(game.checkTicTacToe() == 1)
			{
				g.drawString("Player 1 wins!", 65, 200);
				if(pointsAdded == false)
				{
					game.incrementWins1();
					pointsAdded = true;
					playSoundWin();
				}
				restartButton.setVisible(true);
			}
			else if(game.checkTicTacToe() == 2)
			{
				g.drawString("Player 2 wins!", 65, 200);
				if(pointsAdded == false)
				{
					game.incrementWins2();
					pointsAdded = true;
					playSoundWin();
				}
				restartButton.setVisible(true);
			}
			else if(game.checkFull())
			{
				g.drawString("Cat's Game", 100, 200);
				restartButton.setVisible(true);
				if(pointsAdded == false)
				{
					playSoundCat();
					pointsAdded = true;
				}
			}
		}
	}

	public void playSound()
	{
		try {
			URL url = this.getClass().getClassLoader().getResource("sounds/hitmarker.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(url));
			clip.start();
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}

	public void playSoundWin()
	{
		try {
			URL url = this.getClass().getClassLoader().getResource("sounds/win.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(url));
			clip.start();
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}

	public void playSoundLose()
	{
		try {
			URL url = this.getClass().getClassLoader().getResource("sounds/lose.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(url));
			clip.start();
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}

	public void playSoundCat()
	{
		try {
			URL url = this.getClass().getClassLoader().getResource("sounds/cat.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(url));
			clip.start();
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == restartButton)
		{
			game.reset();
			restartButton.setVisible(false);
			pointsAdded = false;
			oneTwoPlayer = 0;
			onePlayerButton.setVisible(true);
			twoPlayerButton.setVisible(true);
		}
		else if(e.getSource() == onePlayerButton)
		{
			oneTwoPlayer = 1;
		}
		else if(e.getSource() == twoPlayerButton)
		{
			oneTwoPlayer = 2;
		}
		repaint();
	}

	public void mousePressed(MouseEvent e) 
	{	if(oneTwoPlayer != 0)
		{
			if (e.getX() >= 50 && e.getX() <= 150 && e.getY() >= 50 && e.getY() <= 150) 
	        {
	           	game.insertXO(0,0);
	            playSound();
	        } 
	        else if (e.getX() >= 175 && e.getX() <= 325 && e.getY() >= 50 && e.getY() <= 150) 
	        {
	            game.insertXO(0,1);
	            playSound();
	        } 
	        else if (e.getX() >= 350 && e.getX() <= 450 && e.getY() >= 50 && e.getY() <= 150) 
	        {
	            game.insertXO(0,2);
	            playSound();
	        } 
	        else if (e.getX() >= 50 && e.getX() <= 150 && e.getY() >= 175 && e.getY() <= 325) 
	        {
	            game.insertXO(1,0);
	            playSound();
	        } 
	        else if (e.getX() >= 175 && e.getX() <= 325 && e.getY() >= 175 && e.getY() <= 325) 
	        {
	            game.insertXO(1,1);
	            playSound();
	        } 
	        else if (e.getX() >= 350 && e.getX() <= 450 && e.getY() >= 175 && e.getY() <= 325) 
	        {
	            game.insertXO(1,2);
	            playSound();
	        } 
	        else if (e.getX() >= 50 && e.getX() <= 150 && e.getY() >= 335 && e.getY() <= 450) 
	        {
	            game.insertXO(2,0);
	            playSound();
	        } 
	        else if (e.getX() >= 175 && e.getX() <= 325 && e.getY() >= 335 && e.getY() <= 450) 
	        {
	            game.insertXO(2,1);
	            playSound();
	        } 
	        else if (e.getX() >= 350 && e.getX() <= 450 && e.getY() >= 335 && e.getY() <= 450) 
	        {
	            game.insertXO(2,2);
	            playSound();
	        } 
	        else 
	        {
	            notificationText = "";
	        }
		} 
		if(oneTwoPlayer == 1)
		{
			System.out.println("Computer");
			System.out.println(game.getTurn());
	 		game.computer();	
		}   
        repaint();
    }
 
    public void mouseReleased(MouseEvent e) {}
 
    public void mouseEntered(MouseEvent e) {}
 
    public void mouseExited(MouseEvent e) {}
 
    public void mouseClicked(MouseEvent e) {}
}