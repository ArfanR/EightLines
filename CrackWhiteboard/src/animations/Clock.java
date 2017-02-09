package animations;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Clock implements Runnable  {

	Thread fThread;
	private JLabel clockLabel;
	private ImageIcon[] images;
	private int totalImages = 143, currentImage = 0;
	
	public Clock() {
		clockLabel = new JLabel();
		//clockLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		clockLabel.setPreferredSize(new Dimension(70, 70));
		images = new ImageIcon[totalImages];
		for (int i = 0; i < images.length; ++i ) {
	         images[i] = new ImageIcon(new ImageIcon( "images/clockAnimation/frame_" + i + "_delay-0.06s.jpg" ).getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT));
		}
		this.start();
	}
	 
    public final void start() {
        if (fThread == null){
            fThread = new Thread (this);
            fThread.start();
        }
    } 

	@Override
	public void run() {
        while (fThread != null){
            try{
                Thread.sleep(100);
                ++currentImage;
                clockLabel.setIcon(images[currentImage]);
            }
            catch (InterruptedException e) { }
            if (this.isDone())  {
            	
            }
        }
	}

    public boolean isDone() {
        boolean temp = false;
  
        if (currentImage == totalImages-1) {
            temp = true;
            currentImage = 0;
        }
    
        return temp;
    }
    
    public JLabel getLabel() {
    	return clockLabel;
    }
    
    public void setInvisible() {
    	clockLabel.setVisible(false);
    }
    
    public void setVisible() {
    	clockLabel.setVisible(true);
    }
    
    public static void main(String[] args) {
    	Clock clock = new Clock();
    	JFrame frame = new JFrame("Arf");
    	//frame.setSize(1086, 1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(clock.getLabel());
        frame.setVisible(true);
    }

}