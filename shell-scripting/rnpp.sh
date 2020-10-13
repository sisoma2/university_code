#!/bin/bash
#Luis Alvarez Abrio, Marc Elias del Pozzo
#10/02/2014 v1.0
#Restaura els permisos, grup i/o propietari d'un fitxer del qual hem guardat la informacio a l'escript 'cnpp.sh'. Si falta algun fitxer o directori dels que estan al fitxer.txt ho mostra per pantalla. Tambe avisa cuan ha modificat algun dels camps d'algun fitxer o directori.

if [ $(id -u) != 0 ] || [ ! -f fitxer.txt ] || [ $# -gt 1 ] || [ $1 == '-h' ]
then
	echo "Aquest script restaura els permisos, propietari o grup dels fitxers i directoris emmagatzemats a l'arxiu fitxer.txt."
	echo "No es necessari cap parametre, nomes que el fitxer.txt existeixi. S'ha d'executar amb permisos de root."
	exit 0
fi

direc=$(awk 'NR==1' fitxer.txt)
echo $direc

while read var
do
	if [[ `echo $var | grep /` = "" ]] && [[ `echo $var | grep total` = "" ]] && [[ $var != "" ]] ;
	then
		perm=$(cut -f1 -d' ' <<< $var | cut -c2-10 )
		usuari=$(cut -f2 -d' ' <<< $var)
		grup=$(cut -f3 -d' ' <<< $var)
		nom=$(cut -f4 -d' ' <<< $var)

		path=$( find $direc -name $nom )
		if [[ `echo $path | grep /` = "" ]]
		then
			echo "No s'ha trobat el ficher o directori: $nom"
		else
			
			infor=$(ls -ld $path)
			
			
			perm2=$(cut -f1 -d' ' <<< $infor | cut -c2-10 )
			usuari2=$(cut -f3 -d' ' <<< $infor)
			grup2=$(cut -f4 -d' ' <<< $infor)
			
			if [[ $perm != $perm2 ]]
			then
				usr="u+$(cut -c1-3 <<< $perm | tr -d '-')"
				grp="g+$(cut -c4-6 <<< $perm | tr -d '-')"
				oth="o+$(cut -c7-9 <<< $perm | tr -d '-')"
				
				chmod $usr $path
				chmod $grp $path
				chmod $oth $path
				echo "S'han restablert els permisos del fitxer $path"
			fi

			if [[ $usuari != $usuari2 ]]
			then
				$(chown $usuari $path)
				echo "S'ha restablert el propietari del fitxer $path"
			fi
			
			if [[ $grup != $grup2 ]]
			then
				echo "chgrp $grup $path"
				$(chgrp $grup $path)
				echo "S'ha restablert el grup del fitxer $path"
			fi
		fi	 
	fi
done < fitxer.txt
