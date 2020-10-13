package IG;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
 
public class AccioComprar implements ActionListener {
	Finestra finestra;
	JButton[] calendari;
	public AccioComprar(Finestra finestra, JButton[] calendari){
		this.finestra = finestra; 
		this.calendari = calendari;
	 }
	
	public void actionPerformed(ActionEvent e) {
		for(int i=1;i<calendari.length;i++){
			if(calendari[i].getBackground() == Color.red){
				int dia = Integer.parseInt(calendari[i].getText());
				finestra.compraEntrades(dia);
			}
		}
	}
}
