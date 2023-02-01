package com.fernando.Mapeado;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Mapeado {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        ConnectionString connectionString = new ConnectionString("mongodb://localhost/mascotas");
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
        try (
                MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase db = mongoClient.getDatabase("mascotas");
            MongoCollection<Gato> gatos = db.getCollection("gatos", Gato.class);
            MongoCursor<Gato> mc = gatos.find().iterator();
            while(mc.hasNext())
                System.out.println(mc.next());
            System.out.println("Nombre del gato: ");
            String nombre = sc.nextLine();
            System.out.println("Raza del gato");
            String raza = sc.nextLine();
            System.out.println("Peso: ");
            double peso = sc.nextDouble();
            //LIMPIAMOS EL BUFFER
            sc.nextLine();

            System.out.println("Introduzca nombre del pienso: ");
            String nomPienso = sc.nextLine();
            System.out.println("Introduzca marca del pienso: ");
            String marca = sc.nextLine();
            System.out.println("Introduzca cantidad de componentes del pienso: ");
            int nComponentes = sc.nextInt();
            //LIMPIAMOS EL BUFFER
            sc.nextLine();
            String componente = "";
            List<String> componentes = new ArrayList<>();
            for(int i=0; i<nComponentes; i++){
                System.out.println("Componente " + i +":");
                componente = sc.nextLine();
                componentes.add(componente);
            }

            Pienso p = new Pienso(nomPienso,marca, componentes);

            Gato g = new Gato();
            g.setNombre(nombre);
            g.setRaza(raza);
            g.setPeso(peso);
            g.addPienso(p);

            gatos.insertOne(g);
            System.out.println("¡Mascota añadida con éxito en la colección gatos!");
        }
    }
}
