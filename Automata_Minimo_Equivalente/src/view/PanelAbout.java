package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelAbout extends JPanel{
	
	public PanelAbout() {
		add(getPanel());
;	}

	
	private JPanel getPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Automata Minimo Equivalente");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        BufferedImage image = null;
        try {
        	
        	String path = "data/logo_about.png";
            File file = new File(path);
            image = ImageIO.read(file);
            
        } catch(MalformedURLException mue) {
            mue.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } 

        label.setIcon(new ImageIcon(image));
        panel.setLayout(new BorderLayout());
        panel.add(label,BorderLayout.NORTH);
        
        JPanel aux = new JPanel();
        aux.setLayout(new GridLayout(4,0));
        
        
        aux.add(new JLabel());
        JLabel label_2 = new JLabel("Jhusseth Sanchez \n- Jean Carlos Ortiz");
        label_2.setHorizontalAlignment(0);
        aux.add(label_2);
      
        JLabel label_1 = new JLabel("<HTML>Linkedin  <FONT color=\"#000099\"><U>Link</U></FONT></HTML>");
        label_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        goWebsite(label_1);
        label_1.setHorizontalAlignment(0);
        aux.add(label_1);
        
        JLabel label_3 = new JLabel("<HTML>Contact me  <FONT color=\"#000099\"><U>Email</U></FONT></HTML>");
        label_3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sendMail(label_3);
        label_3.setHorizontalAlignment(0);
        aux.add(label_3);
        
        panel.add(aux,BorderLayout.CENTER);

        return panel;
    }
	
	private void goWebsite(JLabel website) {
        website.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("www.linkedin.com/in/Jhusseth-Sanchez"));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
    }
	
	private void sendMail(JLabel contact) {
        contact.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().mail(new URI("mailto:jhussethariass@gmail.com?subject=Automata"));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
    }
}
