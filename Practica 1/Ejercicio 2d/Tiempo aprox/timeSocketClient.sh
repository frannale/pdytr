#!/bin/bash

#echo " ================= Algoritmo calculo de comunicación =================== "
#echo " -------------------------- Tiempos totales"
#echo
echo "Tiempo en microsegundos"
for (( port=51001; port<=52000; port++ ))
do
	while [ $(ss -tl | grep -c $port) -lt 1 ]
	do
		sleep 1
	done 
	./clientTiempo localhost $port
done
