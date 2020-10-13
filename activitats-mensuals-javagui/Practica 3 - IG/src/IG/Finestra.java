package IG;

import javax.swing.*;

import classes.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Finestra extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton expBtn,teatreBtn,concertBtn ,botoblanc, botocomprar;
	private JButton[] calendari;
	private JPanel panellcalendari,panellbotons,panellesq;
	
	private JLabel titol,dies,mes;
	private JTextArea missatge,textb1,textb2,textb3;
	
	private LlistaActivitats activitats;
	
	public Finestra(String titol) throws Exception {
		super(titol); // Crida el constructor de la classe mare JFrame.
		iniComponents();
	}
	
	private void iniComponents() throws IOException  {
		activitats = new LlistaActivitats("activitats");
		
		calendari=new JButton[32];
		missatge=new JTextArea();
		missatge.setEditable(false);	
		// Si el text no cap en una linia que es parteixi en varies
		missatge.setLineWrap(true);
		// I a mes al partir en diferents linies demanem que no parteixi cap paraula pel mitg
		missatge.setWrapStyleWord(true);
		missatge.setBackground(Color.LIGHT_GRAY);
		missatge.setVisible(false);
		
		Container interior=getContentPane();
		interior.setLayout(new BorderLayout(20,5));
		interior.add(missatge,BorderLayout.SOUTH);
		
		titol=new JLabel("AGENDA");
		interior.add(titol,BorderLayout.NORTH);
		
		panellesq = new JPanel();
		panellesq.setLayout(new BorderLayout());
		
		mes=new JLabel("<< Desembre >>");
		mes.setHorizontalAlignment(JLabel.CENTER);
		panellesq.add(mes,BorderLayout.NORTH);
		
		panellcalendari=new JPanel();
		panellcalendari.setLayout(new GridLayout(7,7));
		dies = new JLabel("     Dl");
		panellcalendari.add(dies);
		dies = new JLabel("     Dt");
		panellcalendari.add(dies);
		dies = new JLabel("     Dc");
		panellcalendari.add(dies);
		dies = new JLabel("     Dj");
		panellcalendari.add(dies);
		dies = new JLabel("     Dv");
		panellcalendari.add(dies);
		dies = new JLabel("     Ds");
		panellcalendari.add(dies);
		dies = new JLabel("     Dg");
		panellcalendari.add(dies);
		AccioBotons accioBoto1= new AccioBotons(this);
	
		 for(int i=0;i<5;i++){
			 botoblanc=new JButton();
			 botoblanc.setBackground(Color.white);
			 botoblanc.setOpaque(false);
			 panellcalendari.add(botoblanc);
		 }
			 
			for(int i=1;i<32; i++){
			   calendari[i]=new JButton(""+i);
			   calendari[i].setBackground(Color.cyan);
			   calendari[i].setName("calendari");
			   calendari[i].addActionListener(accioBoto1);
			   panellcalendari.add(calendari[i]);
			}
			
			for(int i=0;i<6;i++){
				 botoblanc=new JButton();
				 botoblanc.setBackground(Color.white);
				 botoblanc.setOpaque(false);
				 panellcalendari. add(botoblanc);
			 }
			
			panellesq.add(panellcalendari,BorderLayout.CENTER);
			interior.add(panellesq,BorderLayout.WEST);
			
			panellbotons = new JPanel();
			panellbotons.setLayout(new GridLayout(4,2,20,60));
			
			expBtn = new JButton(" ");
	        expBtn.setBackground(Color.yellow);
	        expBtn.setName("exposicio");
	        expBtn.setBorder(BorderFactory.createLineBorder(Color.black,2));
	        expBtn.setPreferredSize(new Dimension(30, 30));
	        expBtn.addActionListener(accioBoto1);
	        panellbotons.add(expBtn);
	        
	        textb1 = new JTextArea("Marca dies on hi ha previstes exposicions");
	        textb1.setLineWrap(true);
			textb1.setWrapStyleWord(true);
			textb1.setEditable(false);
			textb1.setBackground(Color.LIGHT_GRAY);
	        panellbotons.add(textb1);
	        
	        concertBtn = new JButton(" ");
	        concertBtn.setBackground(Color.blue);
	        concertBtn.setName("concert");
	        concertBtn.setBorder(BorderFactory.createLineBorder(Color.black,2));
	        concertBtn.setPreferredSize(new Dimension(30, 30));
	        concertBtn.addActionListener(accioBoto1);
	        panellbotons.add(concertBtn,BorderLayout.CENTER);
	        
	        textb2 = new JTextArea("Marca dies on hi ha previst concerts");
	        textb2.setLineWrap(true);
			textb2.setWrapStyleWord(true);
			textb2.setEditable(false);
			textb2.setBackground(Color.LIGHT_GRAY);
	        panellbotons.add(textb2);
	        
	        teatreBtn = new JButton(" ");
	        teatreBtn.setBackground(Color.green);
	        teatreBtn.setName("teatre");
	        teatreBtn.setBorder(BorderFactory.createLineBorder(Color.black,2));
	        teatreBtn.setPreferredSize(new Dimension(30, 30));
	        teatreBtn.addActionListener(accioBoto1);
	        panellbotons.add(teatreBtn,BorderLayout.SOUTH);
	        
	        textb3 = new JTextArea("Marca dies on hi ha previst obres de teatre");
	        textb3.setLineWrap(true);
			textb3.setWrapStyleWord(true);
			textb3.setEditable(false);
			textb3.setBackground(Color.LIGHT_GRAY);
	        panellbotons.add(textb3);
	        
	        AccioComprar accioComprar = new AccioComprar(this,calendari);
	        
	        botocomprar = new JButton("Comprar entrades");
	        botocomprar.setBackground(Color.orange);
	        botocomprar.setBorder(BorderFactory.createLineBorder(Color.black,2));
	        botocomprar.addActionListener(accioComprar);
	        panellbotons.add(botocomprar);
	        
	        interior.add(panellbotons,BorderLayout.CENTER);
	        
	        // Necessari per alliverar la memòria quan tanquem la finestra.
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			// Forcem les mides de l'objecte contenidor, es a dir, la finestra.
			setSize(700,500);
			// Fem la finestra visible.
			setVisible(true);
		    }

	public void pintaCalendari(String nomBoto){
		for(int i=1; i<calendari.length;i++){
			calendari[i].setBackground(Color.cyan);
		}
		
		List<Activitat> actNom=activitats.consultaNom(nomBoto);
		for(int i=0;i<actNom.size();i++){
			Activitat a = actNom.get(i);
			for(int k=0;k<a.getDies().length;k++){
				if(a.getTipusActivitat().equalsIgnoreCase("exposicio")){
					calendari[a.getDies()[k]].setBackground(Color.yellow);
				} else if(a.getTipusActivitat().equalsIgnoreCase("concert")){
					calendari[a.getDies()[k]].setBackground(Color.blue);
				} else if (a.getTipusActivitat().equalsIgnoreCase("teatre")){
					calendari[a.getDies()[k]].setBackground(Color.green);
				}
			}
		}
	}
	
	public void mostraInformacio(int dia, JButton boto){
		List<Activitat> vectAct;
		String text;
		this.missatge.setText("");
		this.missatge.append(dia+"/12/2012 ");
		
		if(boto.getBackground() == Color.cyan){
			vectAct=activitats.consultaDia(dia);
		} else if (boto.getBackground() == Color.yellow) {
			vectAct=activitats.consultaDataNom(dia,"exposicio");
		} else if (boto.getBackground() == Color.blue){
			vectAct=activitats.consultaDataNom(dia,"concert");
		} else {
			vectAct=activitats.consultaDataNom(dia,"teatre");
		}
		
        for(int i = 0; i < vectAct.size(); i++){
        	Activitat a = vectAct.get(i);
        	String tipusActivitat = a.getTipusActivitat();
        	String nomActivitat = a.getNomActivitat();
        	if(a.getTipusActivitat().equalsIgnoreCase("exposicio")){
                Exposicio ex = (Exposicio)a;
                String horaInici = ex.getFranjaHorari()[0];
                String horaFi = ex.getFranjaHorari()[1];
                text = tipusActivitat + " \" " + nomActivitat + " \" " + horaInici + "-" + horaFi + " ";
        	} else if(a.getTipusActivitat().equalsIgnoreCase("concert")){
        		Concert oc = (Concert)a;
        		String hora = oc.getHoraIniciActivitat();
        		text = tipusActivitat + " \" " + nomActivitat + " \" " + hora + " ";
        	} else {
        		Teatre t = (Teatre)a;
        		String hora = t.getHoraIniciActivitat();
        		text = tipusActivitat + " \" " + nomActivitat + " \" " + hora + " ";
        	}  
        	this.missatge.append(text);
        }
        this.missatge.setVisible(true);
	}

	public void compraEntrades(int dia) {
		String nomActivitat = JOptionPane.showInputDialog(this, "Introdueix el nom de l'espectacle:", "Nom de l'espectacle",JOptionPane.QUESTION_MESSAGE);
		while(nomActivitat != null && nomActivitat.isEmpty()){
			nomActivitat = JOptionPane.showInputDialog(this, "Introdueix el nom de l'espectacle:", "Torna a introduir el nom de l'espectacle", JOptionPane.QUESTION_MESSAGE);
		}
		String numEntrades = JOptionPane.showInputDialog(this, "Introdueix el nombre d'entrades a comprar:", "Numero d'entrades", JOptionPane.QUESTION_MESSAGE);
		while(numEntrades != null && (numEntrades.isEmpty() || !Character.isDigit(numEntrades.charAt(0)))){
			numEntrades = JOptionPane.showInputDialog(this, "Introdueix el nombre d'entrades a comprar:", "Torna a introduir el numero d'entrades", JOptionPane.QUESTION_MESSAGE);
		}
		if(nomActivitat != null && numEntrades != null){
			int numEntrades2 = Integer.parseInt(numEntrades);
			switch(activitats.compraEntrades(dia,nomActivitat,numEntrades2)){
				case 0: JOptionPane.showMessageDialog(this, "No existeix cap activitat que coincideixi amb els parametres indicats", "Compra Erronea", JOptionPane.INFORMATION_MESSAGE);
				break;
				case 1: JOptionPane.showMessageDialog(this, "Has comprat " + numEntrades +" entrades de l'activitat " + nomActivitat + " per al dia " + dia, "Entrades Comprades", JOptionPane.INFORMATION_MESSAGE);
				break;
				case 2: JOptionPane.showMessageDialog(this, "S'han acabat les entrades per a l'activitat " + nomActivitat +" prevista per al dia " + dia, "No hi ha entrades", JOptionPane.INFORMATION_MESSAGE);
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		try{
			new Finestra("Agenda del mes de Desembre");
		 }
		 catch(NumberFormatException e){
	    		System.out.println("Has introduit dades no numeriques a la opcio!!");
	    	}
	        catch(FileNotFoundException e){
	    		System.out.println("No trobo el fitxer de dades!!");
	    	}
			catch(IOException e){
				System.out.println("Error d'entrada-sortida!!");
			}
	        catch(NoSuchElementException e) {
			   	  System.out.println("Falten parametres al fitxer!!");
			}
	        catch(Exception ex){
	        	System.out.println("Error: "+ex.toString());
	        }
	}
}