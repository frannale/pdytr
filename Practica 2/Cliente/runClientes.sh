#!/bin/bash

#echo "PRIMER CLIENTE -> C1"
cd ./C1

bash runClienteC.sh $1 $2 &
#echo "SEGUNDO CLIENTE -> C2"
cd ..
cd ./C2
bash runClienteC.sh $1 $2 &
#echo "TERCER CLIENTE -> C3"
cd ..
cd ./C3
bash runClienteC.sh $1 $2 &

cd ..
done

