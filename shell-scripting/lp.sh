#!/bin/bash
# Luis Alvarez Abrio, Marc Elias del Pozzo
# 16/05/2014, v1.6
# Aquest script reemplaça la comanda lp que ve de serie per a poder validar els usuaris que vulguin utilitzar l'impresora "impre-pdf".

if [ $# == 0 ]
then
	echo "Falten paràmetres."
	exit 0
fi

if [[ $1 = "-h" ]]
then
	echo "Aquest script valida els usuaris que vulguin utilitzar l'impresora \"impre-pdf\"."
	echo "Accepta els mateixos parametres que la comanda \"lp\" normal."
	exit 0
fi

params=$*

i=0

while [[ $i -le $# ]]
do
	if [[ $1 == "-d" ]]
	then
		shift 1
		imp=$1
	else
		shift 1
	fi
	i=$(( i+1 ))
done

if [[ $imp == "impre-pdf" ]]
then
	usr=$( id -un )
	psw=$( echo  | grep $usr /usr/local/secret/usr | tr -s ':' | cut -f2 -d':' )
	echo "Introdueix la contrasenya:"
	stty -echo
	read cnt
	stty echo

	if [[ $cnt != $psw ]]
	then
		echo "La contrasenya es incorrecta."
		exit 0
	else
		echo "Imprimint..."
		lp2 $params
	fi
else
	lp2 $params
fi
