#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "memoria.h"
#include "winsuport2.h"

int main(int n_args, char *ll_args[])
{
	void *p_win;
	int id_win,n_fil,n_col,l_pal,retard, id_tec, id_pil, ipo_pf, ipo_pc, ind, f_h;
	int *cond_tec,*cond_pilota;
	float po_pf, v_pal;
 /* char rh,rv,rd; */
 
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
  
  cond_tec = map_mem(id_tec);
  cond_pilota = map_mem(id_pil);
  win_set(p_win,n_fil,n_col);	/* crea acces a finestra oberta pel proces pare */

 do{
		f_h = po_pf + v_pal;											/* posicio hipotetica de la paleta */
		if (f_h != ipo_pf){												/* si pos. hipotetica no coincideix amb pos. actual */
			if (v_pal > 0.0){												/* verificar moviment cap avall */
				if (win_quincar(f_h+l_pal-1,ipo_pc) == ' '){   			/* si no hi ha obstacle */
					win_escricar(ipo_pf,ipo_pc,' ',NO_INV);      			/* esborra primer bloc */
					po_pf += v_pal; ipo_pf = po_pf;					/* actualitza posicio */
					win_escricar(ipo_pf+l_pal-1,ipo_pc,'1'+ind,INVERS); 	/* impr. ultim bloc */
				}else{															/* si hi ha obstacle, canvia el sentit del moviment */
					v_pal = -v_pal;
				}
			}else{																/* verificar moviment cap amunt */
				if (win_quincar(f_h,ipo_pc) == ' '){       				/* si no hi ha obstacle */
					win_escricar(ipo_pf+l_pal-1,ipo_pc,' ',NO_INV); 		/* esbo. ultim bloc */
					po_pf += v_pal; ipo_pf = po_pf;					/* actualitza posicio */
					win_escricar(ipo_pf,ipo_pc,'1'+ind,INVERS);				/* impr. primer bloc */
				}else{															/* si hi ha obstacle, canvia el sentit del moviment */
					v_pal = -v_pal;
				}
			}
		}else{
			po_pf += v_pal; 	/* actualitza posicio vertical real de la paleta */
		}
		win_retard(retard);
}while ((*cond_tec==0) && (*cond_pilota==0));
return (0);
}

