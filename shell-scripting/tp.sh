#! /bin/bash
#Luis Alvarez Abrio i Marc Elias Del Pozzo
#25/03/2014
#Cambia a l'usuari de projecte temporalment

if [ $# -eq 1 ]
then
	projecte=$1
else
	echo "Falten parametres, inserta el nom del fitxer o el directori"
	exit 1
fi

usuari=$( whoami )

sudo adduser $usuari $projecte

directori=$( find /projectes -name $projecte )

cd $directori

time newgrp $projecte

