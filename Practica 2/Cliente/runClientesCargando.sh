#!/bin/bash

#echo "PRIMER CLIENTE -> C1"
cd ./C1
bash runClienteC.sh &
#echo "SEGUNDO CLIENTE -> C2"
cd ..
cd ./C2
bash runClienteC.sh &
#echo "TERCER CLIENTE -> C3"
cd ..
cd ./C3
bash runClienteC.sh &
