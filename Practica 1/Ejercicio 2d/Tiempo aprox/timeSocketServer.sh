#!/bin/bash

echo " ================= Algoritmo creador de sockets =================== "
echo " -------------------------- Sockets creados"
echo
for (( port=51001; port<=52000; port++ ))
do
	while [ $(ss -ta | grep -c $port) -ne 0 ]
	do
		sleep 1
	done
		echo "Creando conexion en puerto" $port
		echo
		./serverTiempo $port
done
