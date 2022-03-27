#!/bin/bash
sum=0
cantidad_promedio=1
promedio=0
for i in $(seq $cantidad_promedio $END);
do 
   # ejecuta cliente con mavn y filtra salida para solo tener resultado
   current_execution=` mvn -DskipTests exec:java -Dexec.mainClass=pdytr.example.grpc.ClienteAula -q 2> /dev/null ` 
   echo "$current_execution"
   # suma al total a promediar
   sum=10
done
promedio= 10
echo "Tiempo promedio: ($promedio)" 
