#!/bin/bash

echo " ================= Algoritmo envio de mensajes =================== "
for (( port=49001; port<=50000; port++ ))
do
	while [ $(ss -tl | grep -c $port) -lt 1 ]
	do
		sleep 1
	done
	echo "Conectando cliente a puerto" $port
	./clientTiempoExacto localhost $port
done
