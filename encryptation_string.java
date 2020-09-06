/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author macbook
 */
public class encryptation_string {
    
    public static final Integer EOF = -1;
    public static String nombre;
    public static void main(String[] args){
        
        Scanner leer = new Scanner(System.in);  
        lista listita = new lista();
        lista_circular inicio = new lista_circular();
        lista_simple inicio2 = new lista_simple();
        lista_simple inicio2_copia = new lista_simple();
        char resp;
        String clave, cadena, direccion;
        
        System.out.println("Encriptar string desde consola(1)");// MENÚ DE OPCIONES
        System.out.println("Encriptar un archivo(2)");
        System.out.println("Desencriptar un archivo(3)");
        System.out.println("Desencriptar desde consola(4)");
        System.out.println("Ingrese la opción: ");
        resp = leer.next().charAt(0);
        leer.nextLine();
        
        System.out.println("Ingrese la clave: ");//SE INGRESA LA CLAVE
        clave = leer.nextLine();
        
        if(clave.length()<3){                   //SE VALIDA LA CLAVE
            System.out.println("Clave inválida");
            return;
        }
        for(int i=0; i<clave.length();i++){     //SE HACE LA LISTA CIRCULAR QUE CONTIENE LA CLAVE
            inicio = listita.insertar(clave.charAt(i));  
        }
        switch(resp){
            case '1':                              //EL FLUJO SE IRÁ AL CASO DE ENCRIPTAR ARREGLO INTRODUCIDO POR CONSOLA
                System.out.println("Introduzca la cadena a encriptar");
                cadena = leer.nextLine();
                for(int i=0; i<cadena.length();i++){            //SE CREA LA LISTA SIMPLE QUE CONTIENE EL STRING
                    inicio2 = listita.insertar_s(cadena.charAt(i)); 
                }

                inicio2 = listita.encriptar(inicio, inicio2);   //SE LLAMA A ENCRIPTAR, CON LA CLAVE Y EL STRING COMO PARÁMETROS
                System.out.println("La cadena está encriptada, es :");
                listita.recorrer_s(inicio2);                    //SE MUESTRA LA LISTA ENCRIPTADA
            break;   
            case '2':                         //EL FLUJO SE VA AL CASO DE QUE SE VAYA A ENCRIPTAR UN ARCHIVO
                System.out.println("Qué tipo de archivo desea encriptar, de texto(T) o binario(B)? ");
                char tipo = leer.next().charAt(0);              
                leer.nextLine();
                switch(tipo){
                    case 'T':                   //EN CASO DE QUE SEA UN ARCHIVO DE TEXTO, EL FLUJO ESTARÁ AQUÍ
                        System.out.println("Introduzca la dirección del archivo: ");//SE OBTIENE LA DIRECCIÓN DEL ARCHIVO
                        direccion = leer.nextLine();
                        try{
                            File file = new File(direccion);        //SE CREA EL ARCHIVO CON AL DIRECCIÓN DADA
                            FileReader reader = new FileReader(file);  
                            nombre = file.getName();
                            int ascii;                           
                            while((ascii = reader.read()) != EOF){  //SE VA LEER EL ARCHIVO HASTA QUE LLEGUE AL FIN
                                inicio2 = listita.insertar_s(ascii);//SE INSERTARÁ EN UNA LISTA LOS ELEMENTOS DEL ARCHIVO
                            }
                            inicio2 = listita.encriptar(inicio, inicio2);//SE ENCRIPTARÁ LO QUE ESTÁ EN LA LISTA                                                       
                            reader.close();    
        
                        }catch (IOException ex) {
                            System.out.println("Error al abrir archivo");
                            Logger.getLogger(lista.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try{
                            File file_1 = new File(nombre);    //AQUÍ SE VA A CREAR EL ARCHIVO EN EL QUE SE TIENE LA INFORMACIÓN ENCRIPTADA
                            FileWriter writer = new FileWriter(file_1);
                            PrintWriter pwriter = new PrintWriter(writer);
                            String path = file_1.getAbsoluteFile().getParent();
                            lista_simple aux = inicio2;
                            while(aux!=null){                  // CON ESTE CICLO SE ESCRIBE EL ARCHIVO CON LOS DATOS ENCRIPTADOS
                                pwriter.print(Character.toString((char) aux.dato));
                                aux=aux.siguiente;            
                            }
                            pwriter.close();   
                            System.out.println("El archivo encriptado se encuentra en el path:");
                            System.out.print(path);
                            System.out.print("/");
                            System.out.print(nombre);                                                       
                        }catch (IOException ex) {
                            System.out.println("Error al abrir archivo");
                            Logger.getLogger(lista.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    break;  
                    case 'B':               //EN CASO DE QUE SE QUIERA LEER UN ARCHIVO BINARIO EL FLUJO ESTARÁ AQUÍ
                        System.out.println("Introduzca la dirección del archivo: ");
                        direccion = leer.nextLine();
                        try{
                        File origen = new File(direccion);
                        File destino = new File(direccion);
     
                        FileInputStream input = new FileInputStream(destino);
                        DataInputStream reader = new DataInputStream(input);
                        
                        FileOutputStream output = new FileOutputStream(destino);
                        DataOutputStream writer = new DataOutputStream(output);
                        
                        int byte_info = 0;
                        lista_circular aux = inicio;
                        while ((byte_info = reader.read())!= EOF){              //SE LEE LO QUE ESTÁ EN EL ARCHIVO, Y A CADA BYTE SE LE SUMA LA CLAVE     
                            writer.writeByte(byte_info+aux.dato);
                            aux = aux.siguiente;
                        }
                        
                        reader.close();
                        input.close();
                        
                        writer.close();
                        output.close();
                        }catch (IOException ex) {
                            System.out.println("Error al abrir archivo");
                            Logger.getLogger(lista.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    break;
                }
   
                
            break;
            case '3':           //AQUÍ SE EN
            System.out.println("Qué tipo de archivo desea desencriptar, de texto(T) o binario(B)? ");
            char tipo_2 = leer.next().charAt(0);   
            leer.nextLine();
            switch(tipo_2){
                case 'T':
                     System.out.println("Introduzca la dirección del archivo: ");//SE OBTIENE LA DIRECCIÓN DEL ARCHIVO
                        direccion = leer.nextLine();
                        try{
                            File file = new File(direccion);        //SE CREA EL ARCHIVO CON AL DIRECCIÓN DADA
                            FileReader reader = new FileReader(file);  
                            nombre = file.getName();
                            int ascii;                           
                            while((ascii = reader.read()) != EOF){  //SE VA LEER EL ARCHIVO HASTA QUE LLEGUE AL FIN
                                inicio2 = listita.insertar_s(ascii);//SE INSERTARÁ EN UNA LISTA LOS ELEMENTOS DEL ARCHIVO
                            }
                            inicio2 = listita.desencriptar(inicio, inicio2);//SE ENCRIPTARÁ LO QUE ESTÁ EN LA LISTA                                                       
                            
                            reader.close();    
        
                        }catch (IOException ex) {
                            System.out.println("Error al abrir archivo");
                            Logger.getLogger(lista.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try{
                            File file_1 = new File(nombre);    //AQUÍ SE VA A CREAR EL ARCHIVO EN EL QUE SE TIENE LA INFORMACIÓN ENCRIPTADA
                            FileWriter writer = new FileWriter(file_1);
                            PrintWriter pwriter = new PrintWriter(writer);
                            String path = file_1.getAbsoluteFile().getParent();
                            lista_simple aux = inicio2;
                            while(aux!=null){                  // CON ESTE CICLO SE ESCRIBE EL ARCHIVO CON LOS DATOS DESENCRIPTADOS
                                pwriter.print(Character.toString ((char) aux.dato));
                                aux=aux.siguiente;            
                            }
                            writer.close();  
                            System.out.println("El archivo encriptado se encuentra en el path:");
                            System.out.print(path);
                            System.out.print("/");
                            System.out.print(nombre);
        
                        }catch (IOException ex) {
                            System.out.println("Error al abrir archivo");
                            Logger.getLogger(lista.class.getName()).log(Level.SEVERE, null, ex);
                        }
                break;
                case 'B':                   //AQUÍ DESENCRIPTARÁ UN ARCHIVO BINARIO
                    System.out.println("Introduzca la dirección del archivo: ");
                    direccion = leer.nextLine();
                    try{
                    File origen = new File(direccion);
                    File destino = new File(direccion);

                    FileInputStream input = new FileInputStream(destino);
                    DataInputStream reader = new DataInputStream(input);

                    FileOutputStream output = new FileOutputStream(destino);
                    DataOutputStream writer = new DataOutputStream(output);

                    int byte_info;
                    lista_circular aux = inicio;
                    while ((byte_info = reader.read())!= EOF){                            
                        writer.writeByte(byte_info-aux.dato);
                        aux = aux.siguiente;
                    }

                    reader.close();
                    input.close();

                    writer.close();
                    output.close();
                    }catch (IOException ex) {
                        System.out.println("Error al abrir archivo");
                        Logger.getLogger(lista.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;    
            }               
                        
                        
                        
           break;    
            case '4':                   //ESTE ES EL CASO EN EL QUE SE VAYA A DESENCRIPTAR UNA CADENA DE ASCIIS LEÍDOS EN CONSOLA
                int ascii=0, num;
                System.out.println("Introduzca el número de asciis que introducirá");                
                num = leer.nextInt();
                leer.nextLine();
                System.out.println("Introduzca los asciis"); 
                for(int i=0; i<num;i++){                    
                    ascii = leer.nextInt();
                    if(ascii >255){             //AQUÍ SE HACE LA SUMA DEL DATO ENCRIPTADO, MÁS 255, MENOS LA CLAVE, EN CASO DE SER MAYOR A 255, SE LE RESTARÁ 255, SIGNIFICA QUE SE ENCRIPTÓ SUMÁNDOLE LA CLAVE.
                        System.out.println("El ascii no es válido");
                        return;
                    }
                    inicio2 = listita.insertar_s(ascii); 
                }
                inicio2 = listita.desencriptar(inicio, inicio2); //SE DESENCRIPTA Y SE MUESTRAN LOS CARACTERES ASCIIS
                System.out.println("La cadena de Asciis desencriptada es:"); 
                listita.recorrer_s(inicio2);
                System.out.println("\n"); 
                System.out.println("Los asciis son:"); 
                listita.recorrer_ascii(inicio2);                                
            break;    
        }   
    }  
}

class lista{
    lista_circular inicio = null;
    lista_simple inicio_s = null;
    lista_simple inicio_s1 = null;
    public lista_simple encriptar(lista_circular clave, lista_simple inicio){
        if (inicio == null){
            return inicio;
        }
        lista_simple aux = inicio;
        lista_circular aux2= clave;
        
        while(aux!=null){
            aux.dato = aux.dato+aux2.dato;
            if(aux.dato >255){
                aux.dato = aux.dato-255;
            }
            aux = aux.siguiente;
            aux2=aux2.siguiente;
        }
        return inicio_s;
    }
    
    public void recorrer_ascii(lista_simple inicio){
        lista_simple aux = inicio;
        while(aux!=null){
            System.out.print(Character.toString((char) aux.dato));
            aux=aux.siguiente;           
        }
    }
    
    public lista_simple desencriptar(lista_circular clave, lista_simple inicio){
        if(inicio == null) return inicio;
        lista_simple aux = inicio;
        
        while(aux!=null){
            aux.dato = aux.dato +255 - clave.dato;
            if(aux.dato >255){
                aux.dato = aux.dato -255;
            }
            aux = aux.siguiente;
            clave = clave.siguiente;
        }
        
        
        return inicio;
    }
    
    public lista_simple insertar_s(int dato){
        lista_simple nuevo = new lista_simple();
        nuevo.dato = dato;
        lista_simple aux = inicio_s;
        nuevo.siguiente = null;
        if(inicio_s == null){
            inicio_s = nuevo;            
        }else{
            while(aux.siguiente!=null){

                aux=aux.siguiente;
            }
            aux.siguiente = nuevo;
        }
        return inicio_s;
    }

    public lista_circular insertar(int dato){
        lista_circular nuevo = new lista_circular();        
        nuevo.dato=dato;
        lista_circular aux = inicio;
        
        if(inicio == null){
            inicio = nuevo;
            inicio.siguiente = inicio;
        } else{
            while(aux.siguiente!=inicio){
                aux=aux.siguiente;
            }
            aux.siguiente = nuevo;
            nuevo.siguiente = inicio;
        }       
        return inicio;
    }
    public void recorrer(lista_circular inicio, int len){
        if(inicio == null) return;
        lista_circular aux = inicio;
        for(int i = 0; i<len;i++){
            System.out.print(aux.dato);
            aux=aux.siguiente;
        }
          
    }
    public void recorrer_s(lista_simple inicio){
        if (inicio == null) return;
        lista_simple aux = inicio;
        
        while (aux!= null){
            System.out.print(aux.dato);
            aux = aux.siguiente;
        }
        
        
    }
    
    
    
    
    
    
    
}
class lista_simple{
    int dato;
    lista_simple siguiente;
}
class lista_circular{
    int dato;
    lista_circular siguiente;
}