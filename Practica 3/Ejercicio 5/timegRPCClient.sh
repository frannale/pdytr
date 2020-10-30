#!/bin/bash

#echo " ================= Algoritmo calculo de comunicación =================== "
#echo " -------------------------- Tiempos totales"
#echo
for (( port=1; port<=10; port++ ))
do
	mvn -q -DskipTests exec:java -Dexec.mainClass=pdytr.example.grpc.Client
done
