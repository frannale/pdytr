#!/bin/bash

echo " ================= Algoritmo RMI =================== "
echo "Servidor de archivos remotos esperando clientes"
java -classpath . -Djava.rmi.server.codebase=file:./ StartRemoteObject
done
