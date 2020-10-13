#!/bin/bash
#Luis Alvarez Abrio, Marc Elias del Pozzo
#10/02/2014 v1.0
#Mostrar per pantalla el path de treball. Si rep per parametre un fitxer mostra els permisos, el grup, el propietari i el nom del fixer. Si rep per parametre un directori mostra tota la informacio anterior per a tots els fitxers del directori. Tambe redirecciona tota aquesta informacio a un fitxer de sortida.

if [ $# -ne 1 ] || [ $1 == '-h' ] || [ $(id -u) != 0 ] || [ ! -d $1 ] && [ ! -f $1 ]
then
	echo "Aquest script mostra per pantalla i guarda a un fitxer els permisos, grup, propietari i nom d'un fitxer o d'un directori 	sencer."
	echo "Com a parametre necessita el path del fitxer o directori. S'ha d'executar amb permisos de root."
	exit 0
fi

direc=$1

echo $direc >fitxer.txt

echo "Directori de treball: $(pwd)"

if [ -d $direc ]
then
	ls -lR $direc | tr -s ' ' | cut -f1,3,4,9 -d' ' | tee -a fitxer.txt
else
	ls -l $direc |  tr -s ' ' | cut -f1,3,4,9 -d' ' | tee -a fitxer.txt
fi
