#!/bin/bash

echo " ================= Algoritmo RMI =================== "
echo "Levantando server gRPC"
mvn -DskipTests package exec:java -Dexec.mainClass=pdytr.example.grpc.App
done
