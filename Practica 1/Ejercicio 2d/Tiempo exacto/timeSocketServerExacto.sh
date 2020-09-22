#!/bin/bash

#echo " ================= Algoritmo calculo de comunicación exacto =================== "
#echo " -------------------------- Tiempos totales"
echo "Tiempo en microsegundos"
for (( port=49001; port<=50000; port++ ))
do
	while [ $(ss -ta | grep -c $port) -ne 0 ]
	do
		sleep 1
	done
	./serverTiempoExacto $port
done
