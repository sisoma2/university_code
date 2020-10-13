#! /bin/bash
#Luis Alvarez Abrio i Marc Elias Del Pozzo
#25/03/2014
#Llegeix els usuaris d'un fitxer de text i els afegeix o els modifica al sistema segons correspongui

if [ $# -eq 1 ]
then
	fitxer=$1
else
	echo "Falten parametres, inserta el nom del fitxer o el directori"
	exit 1
fi

sudo mkdir -p /usuaris
sudo mkdir -p /projectes/enestudi
sudo mkdir -p /projectes/encurs
sudo mkdir -p /projectes/finalitzats

IFS=$'\n'
for linea in $( cat $fitxer ); do

dni=$( echo $linea | cut -f1 -d':' )
nom=$( echo $linea | cut -f2 -d':' | tr ',' ' ')
telf=$( echo $linea | cut -f3 -d':' )
dept=$( echo $linea | cut -f4 -d':' )
projectes=$( echo $linea | cut -f5 -d':' )
	
if [ "$dni" == "" -o  "$nom" == "" -o "$telf" == "" -o "$dept" == "" -o "$projectes" == "" ]
then
	echo "Error de parametres al fitxer"
	exit 2
fi

if ! [ "$telf" -eq "$telf" 2> /dev/null ]
then 
	echo "El telefon nomes pot contenir valors numerics"
	exit 3
fi

sudo groupadd $dept -f
sudo mkdir -p /usuaris/$dept
sudo chgrp $dept /usuaris/$dept

OLDIFS=$IFS
IFS=$','
for A in $projectes ; do
	sudo groupadd $A -f
	sudo mkdir -p /projectes/encurs/$A
	sudo chgrp $A /projectes/encurs/$A
done
IFS=$OLDIFS

sudo useradd -m -d /usuaris/$dept/$dni -c "$nom" -g $dept -G $projectes -s /bin/bash $dni

if [ $? -eq 9 ]
then
	echo "Vols modificar el nom?? (S/N)"
	read opcio
		if [ "$opcio" == "S" ]
		then
			sudo chfn -f $nom $dni
		fi

	echo "Vols modificar el telefon?? (S/N)"
	read op
		if [ "$op" == "S" ]
		then
			sudo chfn -w $telf $dni
		fi
	
	echo "Vols modificar el departament?? (S/N)"
	read op
		if [ "$op" == "S" ]
		then
			sudo usermod -g $dept $dni
		fi
	
	echo "Vols modificar els projectes?? (S/N)"
	read op
		if [ "$op" == "S" ]
		then
			sudo usermod -G $projectes $dni
		fi
else
	sudo chfn -w $telf $dni
	echo "Usuari $dni afegit correctament"
fi

done
