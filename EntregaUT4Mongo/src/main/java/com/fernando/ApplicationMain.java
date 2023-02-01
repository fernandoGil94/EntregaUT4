package com.fernando;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;

public class ApplicationMain {
    static Long id; static String nombre; static String raza; static double peso;
    static MongoClient mc = new MongoClient("localhost",27017);
    static MongoDatabase mdb = mc.getDatabase("mascotas");
    static MongoCollection mco = mdb.getCollection("perros");

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        String opcion; boolean continuar = true; int opt = 0;

        System.out.println("¿Qué operación desea realizar?\n" + "1. Alta.\n2. Actualización.\n3. Borrado.\n" +
                "4. Búsqueda.\n5. Listar mascotas.\n6. Ejemplo mapeado. \n7. Ejemplo de consulta con agregaciones.\n8. Ejemplo de consulta con proyecciones.\n0. Salir");
        opcion = sc.nextLine();
        while(continuar) {
            switch (opcion) {
                case "1":
                    System.out.println("Pulse: \n1. Insertar una mascota.\n2. Insertar varias mascotas.");
                    opt = sc.nextInt();
                    if(opt == 1)
                        insertaMascota();
                    if(opt == 2)
                        insertaMascotas();
                    break;
                case "2":
                    System.out.println("Pulse: \n1. Actualizar una mascota.\n2. Actualizar varias mascotas.");
                    opt = sc.nextInt();
                    if(opt == 1)
                        actualizaMascota();
                    if(opt == 2)
                        actualizaMascotas();
                    break;
                case "3":
                    System.out.println("Pulse: \n1. Eliminar una mascota.\n2. Eliminar varias mascotas.");
                    opt = sc.nextInt();
                    if(opt == 1)
                        borraMascota();
                    if(opt == 2)
                        borraMascotas();
                    break;
                case "4":
                    System.out.println("Pulse: \n1. Buscar una mascota.\n2. Buscar varias mascotas.");
                    opt = sc.nextInt();
                    if(opt == 1)
                        buscaRegistro();
                    if(opt == 2)
                        buscaRegistros();
                    break;
                case "5": listaPerros(); break;
                case "6":
                    //LLAMAMOS A LA FUNCIÓN MAIN DE LA CLASE MAPEADO PARA PODER EJECUTARLA DESDE EL PROGRAMA
                    try {
                        //OBTENEMOS LA CLASE QUE CONTIENE EL MAIN A EJECUTAR
                    Class<?> cls = Class.forName("com.fernando.Mapeado.Mapeado");
                    // OBTENEMOS EL MÉTODO MAIN DE ESA CLASE
                    Method mainMethod = cls.getMethod("main", String[].class);
                    //CREAMOS UNA NUEVA INSTANCIA DE ARGUMENTOS PARA PASAR AL MÉTODO
                    String[] mainArgs = {"arg1", "arg2"};
                    // INVOCAMOS EL MÉTODO
                    mainMethod.invoke(null, (Object) mainArgs);//LA SIGUIENTE CONSULTA MUESTRA AQELLAS MASCOTAS QUE PESAN MÁS DE 20KG, ORDENADAS POR PESO DESCENDENTE,
        //E INCLUYEN ÚNICAMENTE LOS CAMPOS NOMBRE, RAZA Y PESO A LA VEZ QUE EXCLUYE EL ID
                } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                         InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
                case "7":
                    System.out.println("Pulse intro para insertar mascotas de ejemplo y continuar: ");
                    consultaAgregaciones();
                    break;
                case "8":
                    System.out.println("Pulse intro para insertar mascotas de ejemplo y continuar: ");
                    consultaProyecciones();
                    break;
                case "0":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Escoja un número entre 1 y 8 para escoger una opción. 0 para cerrar el programa");
            }
            sc.nextLine();
            System.out.println("¿Desea realizar otra operación?\n1. Sí\n2. No");
            String sino = sc.nextLine();
            if(!sino.equals("1")) {
                continuar = false;
            }else {
                System.out.println("¿Qué operación desea realizar?\n" + "1. Alta.\n2. Actualización.\n3. Borrado.\n" +
                        "4. Búsqueda.\n5. Listar mascotas.\n6. Ejemplo mapeado. \n7. Ejemplo de consulta con agregaciones.\n8. Ejemplo de consulta con proyecciones.\n0. Salir");
                opcion = sc.nextLine();
            }
        }
        mc.close();
    }
    public static void insertaMascota(){

        System.out.println(" --------------------- DATOS NUEVA MASCOTA --------------------- ");
        System.out.println("Introduzca id de la mascota");
        id = sc.nextLong();
        //LIMPIAMOS EL BUFFER
        sc.nextLine();

        System.out.println("Introduzca nombre del perro");
        nombre = sc.nextLine();

        System.out.println("Introduzca raza del perro");
        raza = sc.nextLine();

        System.out.println("Introduzca peso del perro");
        peso = sc.nextDouble();

        Perro p1 = new Perro(id, nombre, raza, peso);
        Document doc = new Document("_id", p1.getId())
                .append("nombre", p1.getNombre())
                .append("raza",p1.getRaza())
                .append("peso",p1.getPeso());
        mco.insertOne(doc);
        System.out.println("Mascota insertada con éxito");
    }
    public static void insertaMascotas(){

        Perro p = null;
        System.out.println("¿Cuántas mascotas desea añadir?");
        int num = sc.nextInt();
        sc.nextLine();
        List<Document> docs = new ArrayList<>();
        for(int i=0; i<num; i++){
            System.out.println(" --------------------- DATOS NUEVA MASCOTA --------------------- ");
            System.out.println("Introduzca id de la mascota");
            id = sc.nextLong();
            //LIMPIAMOS EL BUFFER
            sc.nextLine();

            System.out.println("Introduzca nombre del perro");
            nombre = sc.nextLine();

            System.out.println("Introduzca raza del perro");
            raza = sc.nextLine();

            System.out.println("Introduzca peso del perro");
            peso = sc.nextDouble();
            p = new Perro(id, nombre, raza, peso);
            Document d = new Document("_id", p.getId()).append("nombre", p.getNombre()).append("raza", p.getRaza()).append("peso",p.getPeso());
            docs.add(d);
            System.out.println("");
        }
        mco.insertMany(docs);
        System.out.println("¡Mascotas añadidas con éxito!");
    }
    public static void actualizaMascota(){
        System.out.println("Introduzca Id de la mascota que desea actualizar: ");
        Long Idmascota = sc.nextLong();
        sc.nextLine(); //LIMPIAMOS EL BUFFER POR SI EL PRÓXIMO DATO ES STRING
        Document filtro = new Document("_id",Idmascota);
        // DOCUMENTO QUE GUARDA LOS CAMBIOS
        Document actualizacion = null;
        System.out.println("Escoja campo a modificar: \n1.- Id.\n2.- Nombre.\n3.- Raza.\n4.- Peso.");
        String opc = sc.nextLine();
        switch (opc){
            case "1":
                System.out.println("Introduzca el nuevo Id: ");
                Long newId = sc.nextLong();
                sc.nextLine();  //LIMPIAMOS EL BUFFER
                actualizacion = new Document("$set", new Document("_id", newId));
                break;
            case "2":
                System.out.println("Introduzca el nuevo nombre: ");
                String newName = sc.nextLine();
                actualizacion = new Document("$set", new Document("nombre", newName));
                break;
            case "3": System.out.println("Introduzca nueva raza: ");
                String newBreed = sc.nextLine();
                actualizacion = new Document("$set", new Document("raza", newBreed));
                break;
            case "4": System.out.println("Introduzca nuevo peso: ");
                double newWeight = sc.nextDouble();
                sc.nextLine();
                actualizacion = new Document("$set", new Document("peso", newWeight));
                break;
        }
        mco.updateOne(filtro,actualizacion);
        System.out.println("¡Mascota actualizada con éxito!");
    }
    public static void actualizaMascotas(){
        sc.nextLine();
        Document filtro = null; Document actualizacion = null; String valorActualizacion;

        //NO FILTRAMOS POR ID, AL SER ÚNICO, LA APLICACIÓN SÓLO SE APLICARÍA A UN REGISTRO
        System.out.println("Escoja campo de búsqueda: \n1.- Nombre.\n2.- Raza.\n3.- Peso.");
        String campoFiltrado = sc.nextLine();
        campoFiltrado.toLowerCase();
        //NOMBRE, RAZA O PESO QUE HAN DE COINCIDIR PARA SER MODIFICADO
        System.out.println("Introduzca valor del campo de búsqueda");
        String campoFiltro = sc.nextLine();
        if(campoFiltrado.equals("1")){
            filtro = new Document("nombre", campoFiltro);
        }else if(campoFiltrado.equals("2")){
            filtro = new Document("raza", campoFiltro);
        }else{
            double filter = Double.parseDouble(campoFiltro);
            filtro = new Document("peso", filter);
        }

        System.out.println("Introduzca el campo que desea modificar: \n1.- Nombre.\n2.- Raza.\n3.- Peso.");
        String campoModificar = sc.nextLine();

        switch (campoModificar){
            case "1":
                System.out.println("Introduzca nuevo nombre de las mascotas: ");
                valorActualizacion = sc.nextLine();
                actualizacion = new Document("$set", new Document("nombre", valorActualizacion));
                break;
            case "2":
                System.out.println("Introduzca nueva raza de las mascotas: ");
                valorActualizacion = sc.nextLine();
                actualizacion = new Document("$set", new Document("raza", valorActualizacion));
                break;
            case "3":
                System.out.println("¿Desea establecer el mismo peso para ambas mascotas?\n1. Sí\n2. No");
                String s = sc.nextLine();
                if(s.equals("1")) {
                    System.out.println("Introduzca nuevo peso: ");
                    double peso = sc.nextDouble();
                    actualizacion = new Document("$set", new Document("peso", peso));
                }else {
                    System.out.println("Escriba número positivo si quiere sumar o negativo para restar al peso existente: ");
                    double peso = sc.nextDouble();
                    actualizacion = new Document("$inc", new Document("peso", peso));
                }
                break;
        }
        UpdateResult resultado = mco.updateMany(filtro, actualizacion);
        int modificados = (int) resultado.getModifiedCount();
        if (modificados > 0)
            System.out.println("¡" +modificados + " mascotas modificadas correctamente!");
        else {
            System.out.println("No se ha modificado ninguna mascota, compruebe que el valor del campo de filtrado coincide con algún valor de campo en la base de datos.");
        }
    }
    public static void borraMascota(){
        //DATOS DE ENTRADA REQUERIDOS ÚNICAMENTE PARA ID, VALOR ÚNICO
        System.out.println("Introduzca Id de la mascota que desea eliminar: ");
        Long Idmascota = sc.nextLong();
        Document documento = new Document("_id", Idmascota);
        mco.deleteOne(documento);
        System.out.println("¡Registro eliminado correctamente!");
    }
    public static void borraMascotas(){
        sc.nextLine();
        Document eliminacion = null; String toDelete = ""; DeleteResult resultado = null;
        System.out.println("Escoja campo de búsqueda de los valores de las mascotas a eliminar: \n1.- Nombre.\n2.- Raza.\n3.- Peso.");
        String campoCriterio = sc.nextLine();

        if(campoCriterio.equals("1")){
            System.out.println("Introduzca nombre de las mascotas que desea eliminar");
            toDelete = sc.nextLine();
            eliminacion = new Document("nombre", toDelete);

        }else if(campoCriterio.equals("2")){
            System.out.println("Introduzca raza de las mascotas que desea eliminar");
            toDelete = sc.nextLine();
            eliminacion = new Document("raza", toDelete);

        }else{
            System.out.println("Introduzca peso de las mascotas a eliminar");
            double aBorrar = sc.nextDouble();
            eliminacion = new Document("peso", aBorrar);

        }
        resultado = mco.deleteMany(eliminacion);
        System.out.println("!Se han eliminado "+resultado.getDeletedCount()+" mascotas correctamente!" );
    }
    public static void buscaRegistro(){
        //ÚNICAMENTE HACEMOS BÚSQUEDA POR ID, AL SER EL ÚNICO CAMPO QUE NO PUEDE TENER NÚMEROS REPETIDOS
        System.out.println("Introduzca id de la mascota: ");
        Long idMasc = sc.nextLong();
        Document doc = new Document("_id", idMasc);

        //HACEMOS USO DEL MÉTODO LIMIT PARA QUE MUESTRE EL PRIMER
        FindIterable findIterable = mco.find(doc).limit(1);

        MongoCursor cursor = findIterable.iterator();
        while(cursor.hasNext()){
            doc = (Document) cursor.next();
            System.out.println(doc);
        }
    }
    public static void buscaRegistros(){
        //CREACIÓN DE ARRAY DE DOCUMENTS QUE AÑADIREMOS A LA COLECCIÓN
        System.out.println("Debido a la gran cantidad de posibilidades y operadores para realizar una búsqueda compleja, añadimos las mascotas " +
                "manualmente.\nComo ejemplo, a continuación se muestran aquellas mascotas cuya raza sea 'Dogo' o 'Mastín' y pesen más de 85kg.");
        sc.nextLine();
        List<Document> documentos = new ArrayList<>();
        Document d1 = new Document("_id", 50).append("nombre", "Coco").append("raza", "Dogo").append("peso", 88.15);
        Document d2 = new Document("_id", 51).append("nombre", "Odín").append("raza", "Dogo").append("peso", 84.19);
        Document d3 = new Document("_id", 52).append("nombre", "Luna").append("raza", "Labrador").append("peso", 24.84);
        Document d4 = new Document("_id", 53).append("nombre", "Dana").append("raza", "Teckel").append("peso", 9.25);
        Document d5 = new Document("_id", 54).append("nombre", "Waldo").append("raza", "Mastin").append("peso", 92.18);
        documentos.add(d1);
        documentos.add(d2);
        documentos.add(d3);
        documentos.add(d4);
        documentos.add(d5);
        //LOS INSERTAMOS A LA COLECCIÓN PARA PODER HACER LAS BÚSQUEDAS EN CASO DE QUE NO EXISTA NINGÚN DOCUMENTO
        mco.insertMany(documentos);
        System.out.println("¡Mascotas añadidas correctamente!");

        Document query = new Document();
        List<Document> orConditions = new ArrayList<>();
        orConditions.add(new Document("raza", "Dogo"));
        orConditions.add(new Document("raza", "Mastin"));
        query.append("$or", orConditions);
        query.append("peso", new Document("$gt", 85));

        FindIterable findIterable = mco.find(query);

        MongoCursor cursor = findIterable.iterator();
        while(cursor.hasNext()){
            query = (Document) cursor.next();
            System.out.println(query);
        }
    }
    //MÉTODO QUE PERMITE LISTAR TODOS LOS PERROS DE LA COLECCIÓN
    public static void listaPerros(){

        List<Perro> perros = new ArrayList<>();
        // DOCUMENTO DE FILTRADO VACÍO, POR TANTO ESCOGERÁ TODOS
        FindIterable findIterable = mco.find();
        MongoCursor mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()){
            Document doc = (Document)mongoCursor.next();
            Perro p = new Perro();

            p.setId(doc.getLong("_id"));
            p.setNombre(doc.getString("nombre"));
            p.setRaza(doc.getString("raza"));
            p.setPeso(doc.getDouble("peso"));
            perros.add(p);
        }
        //MOSTRAMOS TODOS LOS PERROS CON EXPRESIÓN LAMBDA PARA
        perros.stream().forEach(System.out::println);
    }
    //MÉTODO QUE MUESTRA UN EJEMPLO DE CONSULTA CON AGREGACIONES
    public static void consultaAgregaciones(){
        //AÑADIMOS LOS DATOS MANUALMENTE POR SI NO EXISTIERA NINGÚN REGISTRO
        sc.nextLine();
        List<Document> documentos = new ArrayList<>();
        Document d1 = new Document("_id", 60).append("nombre", "Tyson").append("raza", "Beagle").append("peso", 12.84);
        Document d2 = new Document("_id", 61).append("nombre", "Pep").append("raza", "San Bernardo").append("peso", 68.31);
        Document d3 = new Document("_id", 62).append("nombre", "Bella").append("raza", "Border Collie").append("peso", 24.14);
        Document d4 = new Document("_id", 63).append("nombre", "Peter").append("raza", "Presa Canario").append("peso", 46.89);
        Document d5 = new Document("_id", 64).append("nombre", "Beethoven").append("raza", "San Bernardo").append("peso", 62.18);
        Document d6 = new Document("_id", 65).append("nombre", "Dina").append("raza", "San Bernardo").append("peso", 58.60);
        Document d7 = new Document("_id", 66).append("nombre", "Julio").append("raza", "Presa Canario").append("peso", 51.85);
        documentos.add(d1);
        documentos.add(d2);
        documentos.add(d3);
        documentos.add(d4);
        documentos.add(d5);
        documentos.add(d6);
        documentos.add(d7);

        mco.insertMany(documentos);
        System.out.println("¡Mascotas añadidas correctamente!");
        System.out.println("---------------- CALCULAMOS LA MEDIA DE PESO DE TODOS LOS PERROS ORDENADOS POR RAZA ----------------");
        //CALCULAMOS LA MEDIA DE PESO DE TODOS LOS PERROS AGRUPADOS POR RAZA
        MongoCursor<Document> cursor = mco.aggregate(Arrays.asList(group("$raza", sum("sumapeso", "$peso"), avg("mediapeso", "$peso")))).iterator();
        while (cursor.hasNext()){
            Document doc =(Document) cursor.next();
            System.out.println(doc);
        }
    }

    public static void consultaProyecciones(){
        //AÑADIMOS LOS DATOS MANUALMENTE POR SI NO EXISTIERA NINGÚN REGISTRO
        sc.nextLine();
        List<Document> documentos = new ArrayList<>();
        Document d1 = new Document("_id", 70).append("nombre", "Bob").append("raza", "Caniche").append("peso", 12.84);
        Document d2 = new Document("_id", 71).append("nombre", "Duna").append("raza", "Pasto Alemán").append("peso", 48.31);
        Document d3 = new Document("_id", 72).append("nombre", "Trini").append("raza", "Border Collie").append("peso", 24.14);
        Document d4 = new Document("_id", 73).append("nombre", "Aldo").append("raza", "Pastor Belga").append("peso", 46.89);
        Document d5 = new Document("_id", 74).append("nombre", "Mikey").append("raza", "Caniche").append("peso", 11.18);
        Document d6 = new Document("_id", 75).append("nombre", "Juan").append("raza", "Pastor Belga").append("peso", 53.60);
        Document d7 = new Document("_id", 76).append("nombre", "Lina").append("raza", "Dogo Alemán").append("peso", 61.85);
        documentos.add(d1);
        documentos.add(d2);
        documentos.add(d3);
        documentos.add(d4);
        documentos.add(d5);
        documentos.add(d6);
        documentos.add(d7);

        mco.insertMany(documentos);
        System.out.println("¡Mascotas añadidas correctamente!");
        System.out.println("---------------- LA SIGUIENTE CONSULTA MUESTRA AQELLAS MASCOTAS QUE PESAN MÁS DE 50KG, ORDENADAS POR PESO DESCENDENTE" +
                " E INCLUYEN ÚNICAMENTE LOS CAMPOS NOMBRE, RAZA Y PESO A LA VEZ QUE EXCLUYE EL ID ----------------");

        MongoCursor<Document> cursor = mco.find(gt("peso", 50)).sort(descending("peso")).projection(fields(include("nombre", "raza", "peso"), exclude("_id"))).iterator();
        while (cursor.hasNext()){
            Document query = (Document) cursor.next();
            System.out.println(query);
        }
    }
}
