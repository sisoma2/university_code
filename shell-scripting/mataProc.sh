#!/bin/bash
#Luis Alvarez Abrio, Marc Elias del Pozzo
#20/03/2014 v1.0
#Servei que atura tots els processos que hagin consumit mes de 10 minuts de temps de CPU.

ps aux > file2.txt

sed '1d' file2.txt > file.txt

IFS=$'\n'
for par in $(cat file.txt)
do
	var=$( echo $par | tr -s ' ' | cut -f10 -d' ' )
	pid=$( echo $par | tr -s ' ' | cut -f2 -d' ' )
	time=$( echo $var | cut -d':' -f1 )
	if [ $time -ge 10 ]
	then
		echo "$time $pid"
		$( kill -SIGSTOP $pid )		#-19
	fi
done

rm file.txt
rm file2.txt
