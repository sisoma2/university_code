package IG;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
 
public class AccioBotons implements ActionListener {
	Finestra finestra;
	JButton idBotocal;
	JButton idBotoact;
	boolean primerCopcal;
	boolean primerCopact;
	Color colorcal;
	Color coloract;
	
	public AccioBotons(Finestra finestra){
		this.finestra = finestra; 
		idBotocal = null;
		idBotoact = null;
		primerCopcal = true;
		primerCopact = true;
		colorcal = null;
	 }
	
	public void actionPerformed(ActionEvent e) {
		JButton boto=(JButton)e.getSource();
		if(boto.getName().equals("calendari")){
			if(!primerCopcal){
				idBotocal.setBackground(colorcal);
			}
			idBotocal = boto;
			colorcal = boto.getBackground();
			primerCopcal = false;
			int dia=Integer.parseInt(boto.getText());
			finestra.mostraInformacio(dia,boto);
			boto.setBackground(Color.red);
		} 
		
		if(boto.getName().equals("exposicio") || boto.getName().equals("teatre") || boto.getName().equals("concert")) {
			if(!primerCopact){
				idBotoact.setBorder(BorderFactory.createLineBorder(Color.black,2));
			}
			idBotoact = boto;
			primerCopcal = true;
			primerCopact = false;
			boto.setBorder(BorderFactory.createLineBorder(Color.red,2));
			String nomBoto=boto.getName();
			finestra.pintaCalendari(nomBoto);
		}
	}
}
