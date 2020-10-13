#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "memoria.h"
#include "winsuport2.h"
#include <string.h>
#include "semafor.h"
#include "missatge.h"

int valors_paletes[2]={-1,-1};

void * comprovar_posicio(int dir,int ipo_pc,int ipo_pf,int l_pal,int id_sem){
	int n;
	char pos=0;
	valors_paletes[0]=-1;
	valors_paletes[1]=-1;
	for(n=0;n<l_pal;n++){
		waitS(id_sem);
		pos=win_quincar(ipo_pf+n, ipo_pc+dir);
		signalS(id_sem);
		if(pos!='+'){
			if(pos!=' '){
				if(valors_paletes[0]==-1){
					pos=(int)pos-48;
					valors_paletes[0]=pos;
				}else{
					if(valors_paletes[1]==-1){
						pos=(int)pos-48;
						if(pos!=valors_paletes[0]){
							valors_paletes[1]=pos;
						}
					}
				}
			}else{
				if(valors_paletes[0]==-1){
					valors_paletes[0]=0;
					valors_paletes[1]=0;
				}
			}
		}
		else{
			valors_paletes[0]=-2;
			valors_paletes[1]=-2;
		}
	}
	return((void *) valors_paletes);
}

int main(int n_args, char *ll_args[])
{
	void *p_win;
	int id_win,n_fil,n_col,l_pal,retard, id_tec, id_pil, ipo_pf, ipo_pc, ind, f_h;
	int id_sem, *id_bustia, mis;
	int *cond_tec,*cond_pilota;
	float po_pf, v_pal;
	int dir=0,v=0, n;

  id_win = atoi(ll_args[1]);
  p_win = map_mem(id_win);

  n_fil = atoi(ll_args[2]);		/* obtenir dimensions del camp de joc */
  n_col = atoi(ll_args[3]);
  l_pal = atoi(ll_args[4]);
  retard = atoi(ll_args[5]);
  id_tec = atoi(ll_args[6]);
  id_pil =atoi(ll_args[7]);
  ipo_pf = atoi(ll_args[8]);
  ipo_pc = atoi(ll_args[9]);
  po_pf = atof(ll_args[10]);
  v_pal = atof(ll_args[11]);
  ind = atoi(ll_args[12]);
  id_sem=atoi(ll_args[13]);

  mis=atoi(ll_args[14]);
  id_bustia=map_mem(mis);

  cond_tec = map_mem(id_tec);
  cond_pilota = map_mem(id_pil);
  win_set(p_win,n_fil,n_col);	/* crea acces a finestra oberta pel proces pare */

  waitS(id_sem);
  sendM(id_bustia[ind],&v,sizeof(int));
  signalS(id_sem);

 do{
		waitS(id_sem);
		receiveM(id_bustia[ind], &dir);
		signalS(id_sem);
		if(dir!=0){
			comprovar_posicio(dir,ipo_pc,ipo_pf, l_pal,id_sem);
			if(valors_paletes[0]!=-1){
				if(valors_paletes[0]!=-2 && valors_paletes[0]!=0){
					waitS(id_sem);
					sendM(id_bustia[valors_paletes[0]-1],&dir,sizeof(int));
					if(valors_paletes[1]!=-1) sendM(id_bustia[valors_paletes[1]-1],&dir,sizeof(int));
					signalS(id_sem);
				}else{
					if(valors_paletes[0]==-2){
						for(n=0;n<l_pal;n++){
							win_escricar(ipo_pf+ind,ipo_pc,' ',NO_INV);
						}
						exit(0);
					}
					if(valors_paletes[0]==0){
						for(n=0;n<l_pal;n++){
							win_escricar(ipo_pf+n,ipo_pc,' ',NO_INV);
							win_escricar(ipo_pf+n,ipo_pc+dir,'1'+ind,INVERS);
						}
						ipo_pc+=dir;
					}
				}
			}
		} else {
			f_h = po_pf + v_pal;											/* posicio hipotetica de la paleta */
			if (f_h != ipo_pf){												/* si pos. hipotetica no coincideix amb pos. actual */
				if (v_pal > 0.0){
				waitS(id_sem);											/* verificar moviment cap avall */
					if (win_quincar(f_h+l_pal-1,ipo_pc) == ' '){   			/* si no hi ha obstacle */
						win_escricar(ipo_pf,ipo_pc,' ',NO_INV);      			/* esborra primer bloc */
						po_pf += v_pal; ipo_pf = po_pf;					/* actualitza posicio */
						win_escricar(ipo_pf+l_pal-1,ipo_pc,'1'+ind,INVERS); 	/* impr. ultim bloc */
					}else{															/* si hi ha obstacle, canvia el sentit del moviment */
						v_pal = -v_pal;
					}
					signalS(id_sem);
				}else{
					waitS(id_sem);															/* verificar moviment cap amunt */
					if (win_quincar(f_h,ipo_pc) == ' '){       				/* si no hi ha obstacle */
						win_escricar(ipo_pf+l_pal-1,ipo_pc,' ',NO_INV); 		/* esbo. ultim bloc */
						po_pf += v_pal; ipo_pf = po_pf;					/* actualitza posicio */
						win_escricar(ipo_pf,ipo_pc,'1'+ind,INVERS);				/* impr. primer bloc */
					}else{															/* si hi ha obstacle, canvia el sentit del moviment */
						v_pal = -v_pal;
					}
					signalS(id_sem);
				}
			}else{
				po_pf += v_pal; 	/* actualitza posicio vertical real de la paleta */
			}
			waitS(id_sem);
			sendM(id_bustia[ind],&v,sizeof(int));
			signalS(id_sem);
		}
		win_retard(retard);
}while ((*cond_tec==0) && (*cond_pilota==0));
return (0);
}

