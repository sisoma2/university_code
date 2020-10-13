package Servidor;

import java.io.Serializable;

public class Valor implements Serializable {
	private static final long serialVersionUID = -6753942335421992389L;
	private String v;
	private int versio;
	public Valor(String v, int versio){
		this.v=v;
		this.versio=versio;
	}
	public String getV() {
		return v;
	}
	public int getVersio() {
		return versio;
	}
	public void setVersio(int versio){
		this.versio=versio;
	}
	public void setValor(String v){
		this.v=v;
	}
}
