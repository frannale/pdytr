#!/bin/bash

#echo " ================= Algoritmo calculo de comunicación =================== "
#echo " -------------------------- Tiempos totales"
#echo
for (( port=1; port<=1000; port++ ))
do
	java  -classpath . AskRemote localhost
done
