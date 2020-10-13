#include <stdio.h>
#include <stdlib.h>
#define MAX 11
#define MIN 6

struct posicio {
  int pos_x;
  int pos_y;
  int tocat;
};

struct v_portaavions {
  struct posicio pos[4];
  int enfonsat;
};

struct v_destructor {
  struct posicio pos[3];
  int enfonsat;
};

struct v_dragamines {
  struct posicio pos[2];
  int enfonsat;
};

struct v_submari {
  struct posicio pos;
  int enfonsat;
};

struct v_portaavions portaavions;
struct v_destructor destructor[2];
struct v_dragamines dragamines[3];
struct v_submari submari[4];

void llegir_portaavions (int pos_x, int pos_y, int j, int an_fil, int an_col)
{
    portaavions.pos[j].pos_x = pos_x+(j*an_fil);
    portaavions.pos[j].pos_y = pos_y+(j*an_col);
    portaavions.pos[j].tocat = 0;
    portaavions.enfonsat = 0;
}

void llegir_destructors (int pos_x, int pos_y, int num_vaixell, int j, int an_fil, int an_col)
{
    destructor[num_vaixell].pos[j].pos_x = pos_x+(j*an_fil);
    destructor[num_vaixell].pos[j].pos_y = pos_y+(j*an_col);
    destructor[num_vaixell].pos[j].tocat = 0;
    destructor[num_vaixell].enfonsat = 0;
}


void llegir_dragamines (int pos_x, int pos_y, int num_vaixell, int j, int an_fil, int an_col)
{
      dragamines[num_vaixell].pos[j].pos_x = pos_x+(j*an_fil);
      dragamines[num_vaixell].pos[j].pos_y = pos_y+(j*an_col);
      dragamines[num_vaixell].pos[j].tocat = 0;
      dragamines[num_vaixell].enfonsat = 0;
}

void llegir_submari (int pos_x, int pos_y,int num_vaixell, int j, int an_fil, int an_col)
{
    submari[num_vaixell].pos.pos_x = pos_x+(j*an_fil);
    submari[num_vaixell].pos.pos_y = pos_y+(j*an_col);
    submari[num_vaixell].pos.tocat = 0;
    submari[num_vaixell].enfonsat = 0;
}

void mostrarTaulell(char taulell[MAX][MAX],int col){
    int i,j;
    printf("\n\t");
    for(i=0;i<col;i++){
        for(j=0;j<col;j++){
            printf("%c ",taulell[i][j]);
        }
        printf("\n\t");
    }
}

void calcular_puntos(int cont_sim, int cont_exp){
    int punts=0;
    //system("cls");
    printf("\n\nFI DEL JOC.");
    punts=500/(cont_sim+(cont_exp*2));
    printf("\nHas aconseguit %d punts.\n\n",punts);

}

void colocar(char taulell[MAX][MAX], int mida_v, int orientacio, int pos_x, int pos_y, int an_fil, int an_col, int col, int freq[5]){
    int i,err=0,num_vaixell;

    num_vaixell = freq[mida_v];

    if(pos_x < (col-mida_v+1) && pos_y < (col-mida_v+1)){
        for(i=0; i<mida_v; i++){
            if(taulell[pos_x+(i*an_fil)][pos_y+(i*an_col)] == '*'){
                if(mida_v == 1){
                   llegir_submari(pos_x,pos_y,num_vaixell,i,an_fil,an_col);
                   taulell[pos_x+(i*an_fil)][pos_y+(i*an_col)]= 'S';
                } if (mida_v == 2){
                    llegir_dragamines(pos_x,pos_y,num_vaixell,i,an_fil,an_col);
                    taulell[pos_x+(i*an_fil)][pos_y+(i*an_col)]= 'M';
                }  if (mida_v == 3){
                    llegir_destructors(pos_x,pos_y,num_vaixell,i,an_fil,an_col);
                    taulell[pos_x+(i*an_fil)][pos_y+(i*an_col)]= 'D';
                } if(mida_v == 4) {
                    llegir_portaavions(pos_x,pos_y,i,an_fil,an_col);
                    taulell[pos_x+(i*an_fil)][pos_y+(i*an_col)]= 'P';
                }

            } else {
                err=1;
            }
        }
        if(err==0){
            freq[mida_v]++;
        } else {
            printf("\nAquesta posicio ja esta ocupada. Torna a introduir les dades.\n");
        }

    } else {
        printf("\nValors mes grans que el taulell de joc. Torna a introduir les dades.\n");
    }
}

int jugar(int mida,char taulell[MAX][MAX],int freq[5]){

    int i,j,k,bucle=0,bucle2=0,pos_x,pos_y,tipus_bomba,sup_x,sup_y,inf_x,inf_y,cont_enf=0,cont_sim=0,cont_exp=0,cont_toc=0,num_tocats=0,num_enfonsats=0,fi=0;
    char taulell_jugador[mida][mida];

    system("cls");

    for(i=0; i<mida; i++){
        for(j=0; j<mida; j++){
            taulell_jugador[i][j]= '#';
        }
    }

    for(k=0; k<5; k++){
        num_tocats = num_tocats + (freq[k] *k);
        num_enfonsats = num_enfonsats + freq[k];
    }

    while(cont_toc < num_tocats && cont_enf < num_enfonsats){
        printf("\nIndica les coordenades on vols posicionar la bomba.\n");
        scanf("%d",&pos_x);
        scanf("%d",&pos_y);

        while(pos_x > mida-1 || pos_y > mida-1){
            printf("\nTorna a introduir uns valors valids.\n");
            scanf("%d",&pos_x);
            scanf("%d",&pos_y);
        }

        while(taulell_jugador[pos_x][pos_y] == 'T' || taulell_jugador[pos_x][pos_y] == 'A' || taulell_jugador[pos_x][pos_y] == 'E'){
            printf("\nPosicio ja ocupada. Torna a introduir uns valors valids.\n");
            scanf("%d",&pos_x);
            scanf("%d",&pos_y);
        }

        printf("\nIndica quin tipus de bomba vols utilitzar. (0 per a bomba simple i 1 per a bomba expansiva)\n");
        scanf("%d",&tipus_bomba);

        while(tipus_bomba !=0 && tipus_bomba!=1){
            printf("\nIntrodueix 0 per a la bomba simple o 1 per a la bomba expansiva. \n");
            scanf("%d", &tipus_bomba);
        }

        if(tipus_bomba == 0){
            cont_sim++;
            if(taulell[pos_x][pos_y] == 'P'){
                if (portaavions.enfonsat == 0) {
                    bucle = 0;
                    while (((portaavions.pos[bucle].pos_x != pos_x) || (portaavions.pos[bucle].pos_y != pos_y)) && (bucle < 3)) {
                        bucle++;
                    }
                    if ((portaavions.pos[bucle].pos_x == pos_x) && (portaavions.pos[bucle].pos_y == pos_y)) {
                        portaavions.pos[bucle].tocat = 1;
                        taulell_jugador[pos_x][pos_y] = 'T';
                        if ((portaavions.pos[0].tocat == 1) && (portaavions.pos[1].tocat == 1) && (portaavions.pos[2].tocat == 1) && (portaavions.pos[3].tocat == 1)) {
                            portaavions.enfonsat = 1;
                            for(bucle=0;bucle<4;bucle++){
                                taulell_jugador[portaavions.pos[bucle].pos_x][portaavions.pos[bucle].pos_y] = 'E';
                            }
                            cont_enf++;
                        }
                    cont_toc++;
                    }
                }
            } else if(taulell[pos_x][pos_y] == 'D') {
                for (bucle2 = 0; bucle2 < 2; bucle2++) {
                    if (destructor[bucle2].enfonsat == 0) {
                        bucle = 0;
                        while (((destructor[bucle2].pos[bucle].pos_x != pos_x) || (destructor[bucle2].pos[bucle].pos_y != pos_y)) && (bucle < 2)) {
                            bucle++;
                        }
                        if ((destructor[bucle2].pos[bucle].pos_x == pos_x) && (destructor[bucle2].pos[bucle].pos_y == pos_y)) {
                            destructor[bucle2].pos[bucle].tocat = 1;
                            taulell_jugador[pos_x][pos_y] = 'T';
                            if ((destructor[bucle2].pos[0].tocat == 1) && (destructor[bucle2].pos[1].tocat == 1) && (destructor[bucle2].pos[2].tocat == 1)) {
                                destructor[bucle2].enfonsat = 1;
                                for(bucle=0;bucle<3;bucle++){
                                    taulell_jugador[destructor[bucle2].pos[bucle].pos_x][destructor[bucle2].pos[bucle].pos_y] = 'E';
                                }
                                cont_enf++;
                            }
                            cont_toc++;
                        }
                    }
                }
            } else if(taulell[pos_x][pos_y] == 'M') {
                for (bucle2 = 0; bucle2 < 3; bucle2++) {
                    if (dragamines[bucle2].enfonsat == 0) {
                        if ((dragamines[bucle2].pos[0].pos_x == pos_x) && (dragamines[bucle2].pos[0].pos_y == pos_y)) {
                            dragamines[bucle2].pos[0].tocat = 1;
                            taulell_jugador[pos_x][pos_y] = 'T';
                            if ((dragamines[bucle2].pos[0].tocat == 1) && (dragamines[bucle2].pos[1].tocat == 1)) {
                                dragamines[bucle2].enfonsat = 1;
                                for(bucle=0;bucle<2;bucle++){
                                    taulell_jugador[dragamines[bucle2].pos[bucle].pos_x][dragamines[bucle2].pos[bucle].pos_y] = 'E';
                                }
                                cont_enf++;
                            }
                        cont_toc++;
                        }

                        if ((dragamines[bucle2].pos[1].pos_x == pos_x) && (dragamines[bucle2].pos[1].pos_y == pos_y)) {
                            dragamines[bucle2].pos[1].tocat = 1;
                            taulell_jugador[pos_x][pos_y] = 'T';
                            if ((dragamines[bucle2].pos[0].tocat == 1) && (dragamines[bucle2].pos[1].tocat == 1)) {
                                dragamines[bucle2].enfonsat = 1;
                                for(bucle=0;bucle<2;bucle++){
                                    taulell_jugador[dragamines[bucle2].pos[bucle].pos_x][dragamines[bucle2].pos[bucle].pos_y] = 'E';
                                }
                                cont_enf++;
                                }
                            cont_toc++;
                            }
                    }
                }
            } else if(taulell[pos_x][pos_y] == 'S') {
                for (bucle2 = 0; bucle2 < 4; bucle2++) {
                    if (submari[bucle2].enfonsat == 0) {
                        if ((submari[bucle2].pos.pos_x == pos_x) && (submari[bucle2].pos.pos_y == pos_y)) {
                            submari[bucle2].pos.tocat = 1;
                            submari[bucle2].enfonsat = 1;
                            taulell_jugador[pos_x][pos_y] = 'E';
                            cont_enf++;
                            cont_toc++;
                        }
                    }
                }
            } else{
               taulell_jugador[pos_x][pos_y] = 'A';
            }
        }

        sup_x = pos_x-1;
        sup_y = pos_y-1;
        inf_x = pos_x+1;
        inf_y = pos_y+1;

        if(tipus_bomba == 1) {
            if(cont_exp < 2){
                if((sup_x >= 0 && sup_y >= 0) && (inf_x < mida && inf_y < mida)){
                for(i=pos_x-1; i<=pos_x+1; i++){
                    for(j=pos_y-1; j<=pos_y+1; j++){
                        if((i>=0 && i<mida) && (j>=0 && j<mida)){
                            if(taulell[i][j] == 'P'){
                                if (portaavions.enfonsat == 0) {
                                    bucle = 0;
                                    while (((portaavions.pos[bucle].pos_x != i) || (portaavions.pos[bucle].pos_y != j)) && (bucle < 3)) {
                                        bucle++;
                                    }
                                    if ((portaavions.pos[bucle].pos_x == i) && (portaavions.pos[bucle].pos_y == j)) {
                                        portaavions.pos[bucle].tocat = 1;
                                        taulell_jugador[i][j] = 'T';
                                        if ((portaavions.pos[0].tocat == 1) && (portaavions.pos[1].tocat == 1) && (portaavions.pos[2].tocat == 1) && (portaavions.pos[3].tocat == 1)) {
                                        portaavions.enfonsat = 1;
                                        for(bucle=0;bucle<4;bucle++){
                                            taulell_jugador[portaavions.pos[bucle].pos_x][portaavions.pos[bucle].pos_y] = 'E';
                                        }
                                        cont_enf++;
                                        }
                                    cont_toc++;
                                    }
                                }
                            } else if(taulell[i][j] == 'D') {
                                for (bucle2 = 0; bucle2 < 2; bucle2++) {
                                    if (destructor[bucle2].enfonsat == 0) {
                                        bucle = 0;
                                        while (((destructor[bucle2].pos[bucle].pos_x != i) || (destructor[bucle2].pos[bucle].pos_y != j)) && (bucle < 2)) {
                                            bucle++;
                                        }
                                        if ((destructor[bucle2].pos[bucle].pos_x == i) && (destructor[bucle2].pos[bucle].pos_y == j)) {
                                                destructor[bucle2].pos[bucle].tocat = 1;
                                                taulell_jugador[i][j] = 'T';
                                                if ((destructor[bucle2].pos[0].tocat == 1) && (destructor[bucle2].pos[1].tocat == 1) && (destructor[bucle2].pos[2].tocat == 1)) {
                                                    destructor[bucle2].enfonsat = 1;
                                                    for(bucle=0;bucle<3;bucle++){
                                                        taulell_jugador[destructor[bucle2].pos[bucle].pos_x][destructor[bucle2].pos[bucle].pos_y] = 'E';
                                                    }
                                                    cont_enf++;
                                                }
                                        cont_toc++;
                                        }
                                    }
                                }
                            } else if(taulell[i][j] == 'M') {
                                for (bucle2 = 0; bucle2 < 3; bucle2++) {
                                    if (dragamines[bucle2].enfonsat == 0) {
                                        if ((dragamines[bucle2].pos[0].pos_x == i) && (dragamines[bucle2].pos[0].pos_y == j)) {
                                            dragamines[bucle2].pos[0].tocat = 1;
                                            taulell_jugador[i][j] = 'T';
                                            if ((dragamines[bucle2].pos[0].tocat == 1) && (dragamines[bucle2].pos[1].tocat == 1)) {
                                                dragamines[bucle2].enfonsat = 1;
                                                for(bucle=0;bucle<2;bucle++){
                                                    taulell_jugador[dragamines[bucle2].pos[bucle].pos_x][dragamines[bucle2].pos[bucle].pos_y] = 'E';
                                                }
                                                cont_enf++;
                                            }
                                        cont_toc++;
                                        }

                                        if ((dragamines[bucle2].pos[1].pos_x == i) && (dragamines[bucle2].pos[1].pos_y == j)) {
                                            dragamines[bucle2].pos[1].tocat = 1;
                                            taulell_jugador[i][j] = 'T';
                                            if ((dragamines[bucle2].pos[0].tocat == 1) && (dragamines[bucle2].pos[1].tocat == 1)) {
                                                dragamines[bucle2].enfonsat = 1;
                                                for(bucle=0;bucle<2;bucle++){
                                                    taulell_jugador[dragamines[bucle2].pos[bucle].pos_x][dragamines[bucle2].pos[bucle].pos_y] = 'E';
                                                }
                                                cont_enf++;
                                            }
                                        cont_toc++;
                                        }
                                }
                            }
                            } else if(taulell[i][j] == 'S') {
                                for (bucle2 = 0; bucle2 < 4; bucle2++) {
                                    if (submari[bucle2].enfonsat == 0) {
                                        if ((submari[bucle2].pos.pos_x == i) && (submari[bucle2].pos.pos_y == j)) {
                                            submari[bucle2].pos.tocat = 1;
                                            submari[bucle2].enfonsat = 1;
                                            taulell_jugador[i][j] = 'E';
                                            cont_enf++;
                                            cont_toc++;
                                        }
                                    }
                                }
                            } else {
                                taulell_jugador[i][j] = 'A';
                            }
                        }
                    }
                }
                cont_exp++;
                } else {
                    printf("\nError la bomba surt dels limits.\n");
                }
            } else {
                printf("\nS'han esgotat les bombes expansives.\n");
            }
        }

        printf("\n\t");
        for(i=0;i<mida;i++){
            for(j=0;j<mida;j++){
                printf("%c ",taulell_jugador[i][j]);
            }
            printf("\n\t");
        }
        printf("\nHas tocat %d .",cont_toc);
        printf("\nHas enfonsat %d vaixells.\n",cont_enf);
    }

    calcular_puntos(cont_sim,cont_exp);
    fi=1;

    return fi;
}

int configurar(char tau[MAX][MAX], int freq[5]){

    int col,i,j,k,confirmacio,op, mida_v,orientacio, pos_x,pos_y,an_fil,an_col;
    int lim_v[5];

    system("cls");
    printf("\nConfiguracio del Taulell");
    printf("\n\nIntrodueix nombre de files i columnes del taulell: ");
    scanf("%d",&col);
    while(col<MIN || col>MAX){
        printf("\nIntrodueix un nombre valid de files i columnes: ");
        scanf("%d",&col);
    }

    for(k=0; k<5; k++){
        freq[k]= 0;
    }

    for(k=0; k<5; k++){
        lim_v[k]= 5-k;
    }

    for(i=0; i<col; i++){
        for(j=0; j<col; j++){
            tau[i][j]= '*';
        }
    }

    while(op!=5 && (freq[1] != lim_v[1] || freq[2] != lim_v[2] || freq[3] != lim_v[3] || freq[4] != lim_v[4])){
        printf("\nQuin vaixell vols colocar?\n");
        printf("\n\t1.Submari (Disposes de 4 submarins, cada un ocupa 1 casella)");
        printf("\n\t2.Draga mines (Disposes de 3 draga mines, cada un ocupa 2 caselles)");
        printf("\n\t3.Destructor (Disposes de 2 destructors, cada un ocupa 3 caselles)");
        printf("\n\t4.Portaavions (Disposes de 1 portaavions, cada un ocupa 4 caselles)");
        printf("\n\t5.Sortir\n\t");
        scanf("%d",&op);
        while(op<1 || op>5){
            printf("Introdueix un nombre valid: ");
            scanf("%d",&op);
        }

        if(op>0 && op<5){
            if(freq[op] != lim_v[op]){
                mida_v = op;

                printf("\nIndica les coordenades on vols posicionar el vaixell (Primer el valor de x i despres el de y) \n");
                scanf("%d",&pos_x);
                scanf("%d",&pos_y);
                    while(pos_x > col || pos_y > col){
                        printf("Torna a introduir uns valors valids (Primer el valor de x i despres el de y) \n");
                        scanf("%d",&pos_x);
                        scanf("%d",&pos_y);
                    }

                if(mida_v != 1){
                    printf("\nIntrodueix la orientacio (0 Horitzontal, 1 vertical):\n");
                    scanf("%d", &orientacio);
                    while(orientacio!=0 && orientacio!=1){
                        printf("Introdueix 0 per a colocarlo horitzontalment o 1 per a colocarlo verticalment: \n");
                        scanf("%d", &orientacio);
                    }
                }

                if(orientacio == 0){
                an_fil=0;
                an_col=1;
                } else {
                an_fil=1;
                an_col=0;
                }
            colocar(tau, mida_v,orientacio,pos_x,pos_y,an_fil,an_col,col,freq);
            } else {
                system("cls");
                printf("\nS'ha esgotat el vaixell seleccionat.\n");

            }
        }
    }

    printf("\nVols veure com esta configurat el taulell??");
    printf("\n\t0. No");
    printf("\n\t1. Si\n\t");
    scanf("%d",&confirmacio);
    while(confirmacio < 0 || confirmacio > 1){
            printf("Introdueix un nombre valid: ");
            scanf("%d",&confirmacio);
        }

    if(confirmacio==1){
    mostrarTaulell(tau,col);
    }

    return col;
}

int main()
{
    char taulell[MAX][MAX];
    int mida=0,op,fi=0;
    int freq[5];

    while(op!=3 && fi!=1){
        printf("\nIntrodueix un d'aquests nombres per a realitzar l'accio corresponent:\n");
        printf("\n\t1.Jugar una partida");
        printf("\n\t2.Configuracio del Taulell");
        printf("\n\t3.Sortir del joc\n\t");
        scanf("%d",&op);
        while(op<1 || op>3){
            printf("Introdueix un nombre valid: ");
            scanf("%d",&op);
        }
            switch(op){
            case 1 : if(mida!=0){fi=jugar(mida,taulell,freq);} else { printf("\n Per a jugar primer has de configurar el taulell!!\n");}
                break;
            case 2 : mida=configurar(taulell,freq);
                break;
            default : break;
        }
    }
    return 0;
}
