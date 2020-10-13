/*****************************************************************************/
/*									    									 */
/*				     Tennis0.c				     							 */
/*									     									 */
/*  Programa inicial d'exemple per a les practiques 2 i 3 de ISO.	     	 */
/*     Es tracta del joc del tennis: es dibuixa un camp de joc rectangular   */
/*     amb una porteria a cada costat, una paleta per l'usuari, una paleta   */
/*     per l'ordinador i una pilota que va rebotant per tot arreu; l'usuari  */
/*     disposa de dues tecles per controlar la seva paleta, mentre que l'or- */
/*     dinador mou la seva automaticament (amunt i avall). Evidentment, es   */
/*     tracta d'intentar col.locar la pilota a la porteria de l'ordinador    */
/*     (porteria de la dreta), abans que l'ordinador aconseguixi col.locar   */
/*     la pilota dins la porteria de l'usuari (porteria de l'esquerra).      */
/*									     									 */
/*  Arguments del programa:						     						 */
/*     per controlar la posicio de tots els elements del joc, cal indicar    */
/*     el nom d'un fitxer de text que contindra la seguent informacio:	     */
/*		n_fil n_col m_por l_pal					     						 */
/*		pil_pf pil_pc pil_vf pil_vc				     						 */
/*		ipo_pf ipo_pc po_vf					     							 */
/*									     									 */
/*     on 'n_fil', 'n_col' son les dimensions del taulell de joc, 'm_por'    */
/*     es la mida de les dues porteries, 'l_pal' es la longitud de les dues  */
/*     paletes; 'pil_pf', 'pil_pc' es la posicio inicial (fila,columna) de   */
/*     la pilota, mentre que 'pil_vf', 'pil_vc' es la velocitat inicial;     */
/*     finalment, 'ipo_pf', 'ipo_pc' indicara la posicio del primer caracter */
/*     de la paleta de l'ordinador, mentre que la seva velocitat vertical    */
/*     ve determinada pel parametre 'po_fv'.				     			 */
/*									     									 */
/*     A mes, es podra afegir un segon argument opcional per indicar el      */
/*     retard de moviment de la pilota i la paleta de l'ordinador (en ms);   */
/*     el valor d'aquest parametre per defecte es 100 (1 decima de segon).   */
/*									     */
/*  Compilar i executar:					  	     */
/*     El programa invoca les funcions definides en 'winsuport.o', les       */
/*     quals proporcionen una interficie senzilla per a crear una finestra   */
/*     de text on es poden imprimir caracters en posicions especifiques de   */
/*     la pantalla (basada en CURSES); per tant, el programa necessita ser   */
/*     compilat amb la llibreria 'curses':				     */
/*									     */
/*	   $ gcc tennis0.c winsuport.o -o tennis0 -lcurses		     */
/*	   $ tennis0 fit_param [retard]					     */
/*									     */
/*  Codis de retorn:						  	     */
/*     El programa retorna algun dels seguents codis al SO:		     */
/*	0  ==>  funcionament normal					     */
/*	1  ==>  numero d'arguments incorrecte 				     */
/*	2  ==>  fitxer no accessible					     */
/*	3  ==>  dimensions del taulell incorrectes			     */
/*	4  ==>  parametres de la pilota incorrectes			     */
/*	5  ==>  parametres d'alguna de les paletes incorrectes		     */
/*	6  ==>  no s'ha pogut crear el camp de joc (no pot iniciar CURSES)   */
/*****************************************************************************/



#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "memoria.h"
#include "winsuport2.h"
#include "semafor.h"
#include "missatge.h"
#include <pthread.h>


#define MIN_FIL 7		/* definir limits de variables globals */
#define MAX_FIL 25
#define MIN_COL 10
#define MAX_COL 80
#define MIN_PAL 3
#define MIN_VEL -1.0
#define MAX_VEL 1.0



/* variables globals */
int n_fil, n_col, m_por;	/* dimensions del taulell i porteries */
int l_pal;			/* longitud de les paletes */
float v_pal[9];			/* velocitat de la paleta del programa */

int ipu_pf, ipu_pc;      	/* posicio del la paleta d'usuari */
int ipo_pf[9], ipo_pc[9];      	/* posicio del la paleta de l'ordinador */
float po_pf[9];	/* pos. vertical de la paleta de l'ordinador, en valor real */

int ipil_pf, ipil_pc;		/* posicio de la pilota, en valor enter */
float pil_pf, pil_pc;		/* posicio de la pilota, en valor real */
float pil_vf, pil_vc;		/* velocitat de la pilota, en valor real*/

int retard;		/* valor del retard de moviment, en mil.lisegons */

int tec=0, cont, num_paletes=0;

void *p_win;
int id_win;
int *cond_tec, *cond_pilota;
int id_tec, id_pil;

pthread_t tid[2];
pthread_mutex_t mutex= PTHREAD_MUTEX_INITIALIZER;
pid_t tpid[9];	


/* funcio per realitzar la carrega dels parametres de joc emmagatzemats */
/* dins un fitxer de text, el nom del qual es passa per referencia en   */
/* 'nom_fit'; si es detecta algun problema, la funcio avorta l'execucio */
/* enviant un missatge per la sortida d'error i retornant el codi per-	*/
/* tinent al SO (segons comentaris del principi del programa).		*/
void carrega_parametres(const char *nom_fit)
{
  FILE *fit;
  fit = fopen(nom_fit,"rt");		/* intenta obrir fitxer */
  if (fit == NULL)
  {	fprintf(stderr,"No s'ha pogut obrir el fitxer \'%s\'\n",nom_fit);
  	exit(2);
  }

  if (!feof(fit)) fscanf(fit,"%d %d %d %d\n",&n_fil,&n_col,&m_por,&l_pal);
  if ((n_fil < MIN_FIL) || (n_fil > MAX_FIL) ||
	(n_col < MIN_COL) || (n_col > MAX_COL) || (m_por < 0) ||
	 (m_por > n_fil-3) || (l_pal < MIN_PAL) || (l_pal > n_fil-3))
  {
	fprintf(stderr,"Error: dimensions del camp de joc incorrectes:\n");
	fprintf(stderr,"\t%d =< n_fil (%d) =< %d\n",MIN_FIL,n_fil,MAX_FIL);
	fprintf(stderr,"\t%d =< n_col (%d) =< %d\n",MIN_COL,n_col,MAX_COL);
	fprintf(stderr,"\t0 =< m_por (%d) =< n_fil-3 (%d)\n",m_por,(n_fil-3));
	fprintf(stderr,"\t%d =< l_pal (%d) =< n_fil-3 (%d)\n",MIN_PAL,l_pal,(n_fil-3));
	fclose(fit);
	exit(3);
  }

  if (!feof(fit)) fscanf(fit,"%d %d %f %f\n",&ipil_pf,&ipil_pc,&pil_vf,&pil_vc);
  if ((ipil_pf < 1) || (ipil_pf > n_fil-3) ||
	(ipil_pc < 1) || (ipil_pc > n_col-2) ||
	(pil_vf < MIN_VEL) || (pil_vf > MAX_VEL) ||
	(pil_vc < MIN_VEL) || (pil_vc > MAX_VEL))
  {
	fprintf(stderr,"Error: parametre pilota incorrectes:\n");
	fprintf(stderr,"\t1 =< ipil_pf (%d) =< n_fil-3 (%d)\n",ipil_pf,(n_fil-3));
	fprintf(stderr,"\t1 =< ipil_pc (%d) =< n_col-2 (%d)\n",ipil_pc,(n_col-2));
	fprintf(stderr,"\t%.1f =< pil_vf (%.1f) =< %.1f\n",MIN_VEL,pil_vf,MAX_VEL);
	fprintf(stderr,"\t%.1f =< pil_vc (%.1f) =< %.1f\n",MIN_VEL,pil_vc,MAX_VEL);
	fclose(fit);
	exit(4);
  }
	while (num_paletes<9 && !feof(fit)){
		if (!feof(fit)) fscanf(fit,"%d %d %f\n",&ipo_pf[num_paletes],&ipo_pc[num_paletes],&v_pal[num_paletes]);
		if ((ipo_pf[num_paletes] < 1) || (ipo_pf[num_paletes]+l_pal > n_fil-2) ||
			(ipo_pc[num_paletes] < 5) || (ipo_pc[num_paletes] > n_col-2) ||
			(v_pal[num_paletes] < MIN_VEL) || (v_pal[num_paletes] > MAX_VEL))
			{
			fprintf(stderr,"Error: parametres paleta ordinador incorrectes:\n");
			fprintf(stderr,"\t1 =< ipo_pf (%d) =< n_fil-l_pal-3 (%d)\n",ipo_pf[num_paletes],(n_fil-l_pal-3));
			fprintf(stderr,"\t5 =< ipo_pc (%d) =< n_col-2 (%d)\n",ipo_pc[num_paletes],(n_col-2));
			fprintf(stderr,"\t%.1f =< v_pal (%.1f) =< %.1f\n",MIN_VEL,v_pal[num_paletes],MAX_VEL);
			fclose(fit);
			exit(5);
		}
	num_paletes++;
	}
	num_paletes--;
  fclose(fit);			/* fitxer carregat: tot OK! */
}




/* funcio per inicialitar les variables i visualitzar l'estat inicial del joc */
int inicialitza_joc(void)
{
  int i, j, i_port, f_port, retwin;

  retwin = win_ini(&n_fil,&n_col,'+',INVERS);   /* intenta crear taulell */
  id_win = ini_mem(retwin);	/* crear zona mem. compartida */
  p_win = map_mem(id_win);	/* obtenir adres. de mem. compartida */
  win_set(p_win,n_fil,n_col);		/* crea acces a finestra oberta */
  
  if (retwin < 0)       /* si no pot crear l'entorn de joc amb les curses */
  { fprintf(stderr,"Error en la creacio del taulell de joc:\t");
    switch (retwin)
    {   case -1: fprintf(stderr,"camp de joc ja creat!\n");
                 break;
        case -2: fprintf(stderr,"no s'ha pogut inicialitzar l'entorn de curses!\n");
 		 break;
        case -3: fprintf(stderr,"les mides del camp demanades son massa grans!\n");
                 break;
        case -4: fprintf(stderr,"no s'ha pogut crear la finestra!\n");
                 break;
     }
     return(retwin);
  }
  
  i_port = n_fil/2 - m_por/2;	    /* crea els forats de la porteria */
  if (n_fil%2 == 0) i_port--;
  if (i_port == 0) i_port=1;
  f_port = i_port + m_por -1;
  for (i = i_port; i <= f_port; i++)
  {	win_escricar(i,0,' ',NO_INV);
	win_escricar(i,n_col-1,' ',NO_INV);
  }


  ipu_pf = n_fil/2; ipu_pc = 3;		/* inicialitzar pos. paletes */
  if (ipu_pf+l_pal >= n_fil-3) ipu_pf = 1;
  for (i=0; i< l_pal; i++)	    /* dibuixar paleta inicialment */ //Nombre de paletes global
  {	win_escricar(ipu_pf + i, ipu_pc, '0',INVERS);
	for(j=0; j<num_paletes ; j++){
	win_escricar(ipo_pf[j] +i, ipo_pc[j], '1'+j,INVERS);
	po_pf[j] = ipo_pf[j];		/* fixar valor real paleta ordinador */
	}
  }
  

  pil_pf = ipil_pf; pil_pc = ipil_pc;	/* fixar valor real posicio pilota */
  win_escricar(ipil_pf, ipil_pc, '.',INVERS);	/* dibuix inicial pilota */

  return(0);
}



/* funcio per moure la pilota; retorna un valor amb alguna d'aquestes	*/
/* possibilitats:							*/
/*	-1 ==> la pilota no ha sortit del taulell			*/
/*	 0 ==> la pilota ha sortit per la porteria esquerra		*/
/*	>0 ==> la pilota ha sortit per la porteria dreta		*/
void * moure_pilota(void * cap)
{
  int f_h, c_h;
  char rh,rv,rd;
  
  do{
	f_h = pil_pf + pil_vf;						/* posicio hipotetica de la pilota */
	c_h = pil_pc + pil_vc;
	rh = rv = rd = ' ';
		   
	
	if ((f_h != ipil_pf) || (c_h != ipil_pc)){	/* si posicio hipotetica no coincideix amb la pos. actual */
		if (f_h != ipil_pf){						/* provar rebot vertical */
			 rv = win_quincar(f_h,ipil_pc);			/* veure si hi ha algun obstacle */
			if (rv != ' '){							/* si no hi ha res */
				pil_vf = -pil_vf;				/* canvia velocitat vertical */
				f_h = pil_pf+pil_vf;				/* actualitza posicio hipotetica */
			}

		}
		if (c_h != ipil_pc){						/* provar rebot horitzontal */
			 rh = win_quincar(ipil_pf,c_h);			/* veure si hi ha algun obstacle */
			if (rh != ' '){							/* si no hi ha res */
				pil_vc = -pil_vc;				/* canvia velocitat horitzontal */
				c_h = pil_pc+pil_vc;				/* actualitza posicio hipotetica */
			}
		
		}
		if ((f_h != ipil_pf) && (c_h != ipil_pc)){	/* provar rebot diagonal */
			 rd = win_quincar(f_h,c_h);
			if (rd != ' '){							/* si no hi ha obstacle */
				pil_vf = -pil_vf; pil_vc = -pil_vc;	/* canvia velocitats */
				f_h = pil_pf+pil_vf;
				c_h = pil_pc+pil_vc;				/* actualitza posicio entera */
			}
		
		}

		if (win_quincar(f_h,c_h) == ' '){			/* verificar posicio definitiva */
			win_escricar(ipil_pf,ipil_pc,' ',NO_INV);       /* esborra pilota */
			pil_pf += pil_vf; pil_pc += pil_vc;
			ipil_pf = f_h; ipil_pc = c_h;			/* actualitza posicio actual */
			if ((ipil_pc > 0) && (ipil_pc <= n_col)){	/* si no surt */
				 win_escricar(ipil_pf,ipil_pc,'.',INVERS); /* imprimeix pilota */
				}else{
				cont = ipil_pc;				/* codi de finalitzacio de partida */
				*cond_pilota= 1;			/* codi de finalitzacio de partida */
			}
		}else{
			pil_pf += pil_vf; pil_pc += pil_vc; }
	}
	win_retard(retard);
	}while ((*cond_tec==0) && (*cond_pilota==0));
	pthread_exit((void *) 1);
}




/* funcio per moure la paleta de l'usuari en funcio de la tecla premuda */
void * mou_paleta_usuari(void * cap)
{
	int tec;
	do				/********** bucle principal del joc **********/
  {  
	  tec = win_gettec();
	  
	  if (tec != 0){
		  if ((tec == TEC_AVALL) && (win_quincar(ipu_pf+l_pal,ipu_pc) == ' '))
		  {
			win_escricar(ipu_pf,ipu_pc,' ',NO_INV);	   /* esborra primer bloc */
			ipu_pf++;					   /* actualitza posicio */
			win_escricar(ipu_pf+l_pal-1,ipu_pc,'0',INVERS); /* impri. ultim bloc */
		  }
		  if ((tec == TEC_AMUNT) && (win_quincar(ipu_pf-1,ipu_pc) == ' '))
		  {
			win_escricar(ipu_pf+l_pal-1,ipu_pc,' ',NO_INV); /* esborra ultim bloc */
			ipu_pf--;					    /* actualitza posicio */
			win_escricar(ipu_pf,ipu_pc,'0',INVERS);	    /* imprimeix primer bloc */
		  }
	  }
	
	  if (tec == TEC_RETURN){ 
		  *cond_tec=1;
	  }
	  
	  win_retard(retard);
  }while ((*cond_tec==0) && (*cond_pilota==0));
  pthread_exit((void *) 2);
}

/* programa principal				    */
int main(int n_args, const char *ll_args[])
{
  //int tec, cont;		/* variables locals */
  int temps=0,i,t,n=0;
  char strin[25];
  char a1[20],a2[20],a3[20],a4[20],a5[20],a6[20],a7[20],a8[20],a9[20],a10[20],a11[20],a12[20];
  
  if ((n_args != 2) && (n_args !=3))
  {	fprintf(stderr,"Comanda: tennis0 fit_param [retard]\n");
  	exit(1);
  }
  carrega_parametres(ll_args[1]);

  if (n_args == 3) retard = atoi(ll_args[2]);
  else retard = 100;
  
  if (inicialitza_joc() !=0)    /* intenta crear el taulell de joc */
     exit(4);   /* aborta si hi ha algun problema amb taulell */
  
  id_tec = ini_mem(sizeof(int));	/* crear zona mem. compartida */
  id_pil = ini_mem(sizeof(int));	/* crear zona mem. compartida */
  cond_tec = map_mem(id_tec);	/* obtenir adres. de mem. compartida */
  cond_pilota = map_mem(id_pil);	/* inicialitza variable compartida */
  
  sprintf(a1,"%i",id_win);
  sprintf(a2,"%i",n_fil);	/* convertir mides camp en string */
  sprintf(a3,"%i",n_col);
  sprintf(a4,"%i",l_pal);
  sprintf(a5,"%i",retard);
  sprintf(a6,"%i",id_tec);
  sprintf(a7,"%i",id_pil);
  
   for(i=0;i<num_paletes;i++){
    tpid[n] = fork();
	if (tpid[n] == (pid_t) 0){  /* branca del fill */
		sprintf(a8,"%i",ipo_pf[n]);
		sprintf(a9,"%i",ipo_pc[n]);
		sprintf(a10,"%f",po_pf[n]);
		sprintf(a11,"%f",v_pal[n]);
		sprintf(a12,"%i",n);
		execlp("./pal_ord3", "pal_ord3", a1, a2, a3, a4, a5, a6, a7, a8 , a9 , a10, a11, a12, (char *)0);
		exit(0);
	} 
	else if(tpid[n]>0) n++;
  }
 
 pthread_create(&tid[0],NULL,moure_pilota,NULL);
 pthread_create(&tid[1],NULL,mou_paleta_usuari, NULL);

  do				/********** bucle principal del joc **********/
  {	win_retard(100);
	temps++;
	win_escristr(strin);
	sprintf(strin,"Tecles: \'%c\'-> amunt, \'%c\'-> avall, RETURN-> sortir.\n El temps es %d", TEC_AMUNT, TEC_AVALL,temps/10);
	win_escristr(strin);
	win_update();
 }while ((*cond_tec==0) && (*cond_pilota==0));

for (i = 0; i < num_paletes; i++)
  {
    waitpid(tpid[i],&t,0);	/* espera finalitzacio d'un fill */
  }
 
  win_fi(id_win);
  elim_mem(id_win);
  elim_mem(id_pil);
  elim_mem(id_tec);
  
  pthread_join(tid[0],(void **)&t); 
  pthread_join(tid[1],(void **)&t);

  if (*cond_tec==1) printf("S'ha aturat el joc amb la tecla RETURN!\n");
  else { if (cont == 0) printf("Ha guanyat l'ordinador!\n");
         else printf("Ha guanyat l'usuari!\n"); }
  return(0);
}
