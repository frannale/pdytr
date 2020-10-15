#!/bin/bash

#echo "PRIMER CLIENTE -> C1"
cd ./C1
bash runClienteCI.sh &
#echo "SEGUNDO CLIENTE -> C2"
cd ..
cd ./C2
bash runClienteCI.sh &
#echo "TERCER CLIENTE -> C3"
cd ..
cd ./C3
bash runClienteCI.sh &
