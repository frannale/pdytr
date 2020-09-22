#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <string.h>
#include <unistd.h>
#include <math.h>
#include <sys/time.h>


void error(char *msg)
{
    perror(msg);
    exit(0);
}

//Para calcular tiempo
double dwalltime(){
        double sec;
        struct timeval tv;

        gettimeofday(&tv,NULL);
        sec = tv.tv_sec * 1000000.0 + tv.tv_usec;
        return sec;
}

int main(int argc, char *argv[])
{
    int i, sockfd, portno, n;
    double tiempoEnvio;
    struct sockaddr_in serv_addr;
    struct hostent *server;

    char buffer[257];
    if (argc < 3) {
       fprintf(stderr,"usage %s hostname port\n", argv[0]);
       exit(0);
    }
	//TOMA EL NUMERO DE PUERTO DE LOS ARGUMENTOS
    portno = atoi(argv[2]);
	
	//CREA EL FILE DESCRIPTOR DEL SOCKET PARA LA CONEXION
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
	//AF_INET - FAMILIA DEL PROTOCOLO - IPV4 PROTOCOLS INTERNET
	//SOCK_STREAM - TIPO DE SOCKET 
	
    if (sockfd < 0) 
        error("ERROR opening socket");
	
	//TOMA LA DIRECCION DEL SERVER DE LOS ARGUMENTOS
    server = gethostbyname(argv[1]);
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }
    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
	
	//COPIA LA DIRECCION IP Y EL PUERTO DEL SERVIDOR A LA ESTRUCTURA DEL SOCKET
    bcopy((char *)server->h_addr, 
         (char *)&serv_addr.sin_addr.s_addr,
         server->h_length);
     serv_addr.sin_port = htons(portno);
	
	//DESCRIPTOR - DIRECCION - TAMAÑO DIRECCION
    if (connect(sockfd,(struct sockaddr *)&serv_addr,sizeof(serv_addr)) < 0) 
        error("ERROR connecting");
    //printf("Please enter the message: ");
    bzero(buffer,257);
    //fgets(buffer,255,stdin);

    for(i = 0; i < 256; i++){
    	buffer[i] = 'a';
    }

    //Se toma el tiempo en el cual se envio el mensaje
    tiempoEnvio = dwalltime();  

    //ENVIA UN MENSAJE AL SOCKET
	n = write(sockfd,buffer,strlen(buffer));
    if (n < 0) 
         error("ERROR writing to socket");
    
    bzero(buffer,256);
	
    //ESPERA RECIBIR UNA RESPUESTA
	n = read(sockfd,buffer,255);
    if (n < 0) 
         error("ERROR reading from socket");

    //Envia el tiempo en el que comenzo el envio de datos
    n = write(sockfd,&tiempoEnvio,sizeof(tiempoEnvio));

    close(sockfd);
    return 0;
}
