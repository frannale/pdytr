#!/bin/bash

echo " ================= Algoritmo RMI =================== "
echo "Registrando objeto RMI"
java -classpath . -Djava.rmi.server.codebase=file:./ StartRemoteObject
done
