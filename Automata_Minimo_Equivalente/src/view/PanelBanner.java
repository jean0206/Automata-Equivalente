package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class PanelBanner extends JPanel {

	public PanelBanner() {
		setBackground(Color.GRAY);
		setForeground(Color.DARK_GRAY);
		setBorder(new EmptyBorder(2, 0, 2, 0));
		try {
			banner();
		}
		catch(Exception e) {
			
		}
	}
	
	
	public void banner() throws IOException{
		String path = "data/logo1.jpg";
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        JLabel label = new JLabel(new ImageIcon(image));
        label.setBackground(Color.DARK_GRAY);
        add(label);
	}
}
