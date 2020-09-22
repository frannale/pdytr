/* A simple server in the internet domain using TCP
   The port number is passed as an argument */
#include <stdio.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <math.h>
#include <limits.h>

void error(char *msg)
{
    perror(msg);
    exit(1);
}

int main(int argc, char *argv[])
{
     int i, sockfd, newsockfd, portno, clilen, datosRestantes, cantidadDatos, checksumRcv, checksum, tamMensaje, buffSize;

     char *mensaje, *buffer;
     struct sockaddr_in serv_addr, cli_addr;
     int n;

     if (argc < 2) {
         fprintf(stderr,"ERROR, no port provided\n");
         exit(1);
     }
     else{
     	if (argc < 3) {
     	    fprintf(stderr,"ERROR, tamaño buffer lectura no indicado\n");
     	    exit(1);
        }
     }

     //Memoria del buffer de lectura
     buffSize = atoi(argv[2]);
     buffer = (char *) malloc(buffSize);

	 //CREA EL FILE DESCRIPTOR DEL SOCKET PARA LA CONEXION
     sockfd = socket(AF_INET, SOCK_STREAM, 0);
	 //AF_INET - FAMILIA DEL PROTOCOLO - IPV4 PROTOCOLS INTERNET
	//SOCK_STREAM - TIPO DE SOCKET 
	
     if (sockfd < 0) 
        error("ERROR opening socket");
     bzero((char *) &serv_addr, sizeof(serv_addr));
     //ASIGNA EL PUERTO PASADO POR ARGUMENTO
	 //ASIGNA LA IP EN DONDE ESCUCHA (SU PROPIA IP)
	 portno = atoi(argv[1]);
     serv_addr.sin_family = AF_INET;
     serv_addr.sin_addr.s_addr = INADDR_ANY;
     serv_addr.sin_port = htons(portno);
	 
	 //VINCULA EL FILE DESCRIPTOR CON LA DIRECCION Y EL PUERTO
     if (bind(sockfd, (struct sockaddr *) &serv_addr,
              sizeof(serv_addr)) < 0) 
              error("ERROR on binding");
			  
	 //SETEA LA CANTIDAD QUE PUEDEN ESPERAR MIENTRAS SE MANEJA UNA CONEXION		  
     listen(sockfd,5);
	 
	 // SE BLOQUEA A ESPERAR UNA CONEXION
     clilen = sizeof(cli_addr);
     newsockfd = accept(sockfd, 
                 (struct sockaddr *) &cli_addr, 
                 &clilen);
				 
     //DEVUELVE UN NUEVO DESCRIPTOR POR EL CUAL SE VAN A REALIZAR LAS COMUNICACIONES
	 if (newsockfd < 0) 
          error("ERROR on accept");

	//Recibir el tamaño del mensaje
     n = read(newsockfd,&tamMensaje,sizeof(tamMensaje));
     datosRestantes = tamMensaje;
     printf("\nCantidad de datos a leer: %d\n\n",datosRestantes);

        //Alocar memoria para recibir el mensaje
     mensaje = (char *) malloc(datosRestantes);
	//Para determinar la cantidad de datos que se van leyendo, al final de la lectura deberia ser igual a la cantidad indicada por el cliente
     cantidadDatos = 0;

	//Recibir checksum
     n = read(newsockfd,&checksumRcv,sizeof(checksumRcv));
     checksum = 0;

     

	//Recibir tantos datos como fueron pactados al recibir el tamaño del mensaje
     while(datosRestantes > 0){
	     bzero(buffer,buffSize);
		//LEE EL MENSAJE DEL CLIENTE
	     n = read(newsockfd,buffer,buffSize);
	     if (n < 0) error("ERROR reading from socket");
	     printf("Datos leidos en esta iteración: %d\n",n);
	     datosRestantes -= n;
             memmove(mensaje+cantidadDatos, buffer, n);
             cantidadDatos += n;
     }


	//Calcular checksum
     for(i = 0; i < cantidadDatos; i++){
	checksum += mensaje[i];
     }
     
     printf("\nCantidad datos leidos: %d\n",cantidadDatos);
     printf("Checksum calculado: %d\n",checksum);
     printf("Checksum recibido: %d\n",checksumRcv);

	//Chequear checksum y avisar del estado al cliente
     if(checksum != checksumRcv){
	n = write(newsockfd,"Los checksum no coinciden",25);
     	if (n < 0) error("ERROR writing to socket");
     }
     else{
 	n = write(newsockfd,"Los checksum coinciden",22);
     	if (n < 0) error("ERROR writing to socket");
     }    
	 
	
     free(buffer);
     free(mensaje);
     return 0; 
}
