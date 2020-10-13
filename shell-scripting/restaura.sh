#!/bin/bash
# Luis Alvarez Abrio, Marc Elias del Pozzo
# 16/05/2014, v1.4
# Aquest script permet restaurar la caperta personal de l'usuari que l'executa. Aquesta carpeta es troba dins d'un fitxer .tgz amb altres carpetes de tots els usuaris.

if [[ $1 = "-h" ]]
then
	echo "Aquest script permet restaurar una copia de seguretat del directori /backup."
	echo "Només restaura la carpeta de l'usuari que executa l'script i de la copia seleccionada."
	echo "No rep cap parametre, ja que llista el contingut del directori y l'usuari indica a partir d'aquesta llista."
	exit 0
fi


cd /backup

usr=$(id -run)

echo ""
echo "Fitxers disponibles:"

ls /backup

echo ""
echo "Indica quina copia vols restaurar:"
echo "(no cal indicar l'extensio .tgz)"

read copia

copia="$copia.tgz"

if [ -e $copia ]
then
	tar -zxf $copia -C /tmp
	cp -r /tmp/home/"$usr"/* /home/"$usr"/
	rm -rf /tmp/home
	echo "S'ha restaurat correctament."
else
	echo "No s'ha trobat el fitxer indicat."
fi
