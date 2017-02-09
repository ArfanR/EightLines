package animations;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Fish implements Runnable{

	Thread fThread;
	private JLabel fishLabel;
	private ImageIcon[] images;
	private int totalImages = 8, currentImage = 0;
	
	public Fish() {
		fishLabel = new JLabel();
		//clockLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		fishLabel.setPreferredSize(new Dimension(70, 70));
		images = new ImageIcon[totalImages];
		for (int i = 0; i < images.length; ++i ) {
	         images[i] = new ImageIcon(new ImageIcon( "images/waitingAnimation/frame_" + i + "_delay-0.1s.jpg" ).getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT));
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
                fishLabel.setIcon(images[currentImage]);
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
    	return fishLabel;
    }
    
    public void setInvisible() {
    	fishLabel.setVisible(false);
    }
    
    public void setVisible() {
    	fishLabel.setVisible(true);
    }
    
}
