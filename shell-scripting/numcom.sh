#!/bin/bash
#Luis Alvarez Abrio, Marc Elias del Pozzo
#03/Març/2014 v1.0
#Mostra quantes vegades s'ha executat una comanda i el seu temps mitja d'execució

if [ $# -ne 2 ] || [ $1 == '-h' ]
then
	echo "Aquest script mostra el numero de cops que un usuari ha executat una comanda i el temps mitja d'execucio."
	echo "Necessita com a parametre el nom d'usuari i el nom de la comanda."
	echo "Tambe necessita el servei d'accounting i permisos de root."
	exit 0
fi

if  [ $(id -u) != 0 ]
then
	echo "Has de ser root!"
	echo "Introdueix contrasenya de root!"
	$( read | sudo su ) 
fi

var=$( dpkg -l | grep acct )
if [ $var="" ]
then
	echo "No es troba l'accounting, procedint a instal·lar-lo i activarlo..."

	apt-get install acct	
	service acct start
fi	

login=$1
comanda=$2

lastcomm --strict-match --command $comanda --user $login >> file.txt

num=$( wc file.txt | tr -s ' ' | cut -f2 -d' ')

if [ $num -eq 0 ]
then
	echo "La comanda $comanda no ha sigut executada per l'usuari $login"
	exit 0
else

	IFS=$'\n'
	time=0

	for com in $(cat file.txt)
	do
		time2=$(echo $com | tr -s ' ' | cut -f4 -d' ' | cut -f1 -d'.')
		time=`expr $time + $time2`
	done

	time=`expr $time / $num`
	echo "La comanda \"$comanda\" s'ha executat $num cops i ha trigat una mitjana de $time segons."
fi

rm file.txt
