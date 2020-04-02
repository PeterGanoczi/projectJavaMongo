package sk.itsovy.ganoczi.projectMongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try(MongoClient mongoClient= MongoClients.create("mongodb://localhost:27017")){

            MongoDatabase database=mongoClient.getDatabase("company");
            //Using already made DB company, create new collection employees
            database.createCollection("employees");

            System.out.println("database name: " + database.getName());
            //print all collections
            for (String name: database.listCollectionNames()){
                System.out.println(name);
            }

            //fill collection employees
            MongoCollection <Document> collection=database.getCollection("employees");
            Document doc= new Document("name", "Peter Toth")
                    .append("age", 25 )
                    .append("possition", "Programmer")
                    .append("startInCompany", "10.2.2019");

            //insertOne
            collection.insertOne(doc);

            List<Document> docList=new ArrayList<>();

            doc=new Document("name", "Marek Velky")
                    .append("age", 29)
                    .append("possition", "Team Leader")
                    .append("startInCompany", "5.6.2015");
            docList.add(doc);

            doc=new Document("name", "Patrik Vereb")
                    .append("age", 35)
                    .append("possition", "Senior Java Developer")
                    .append("startInCompany", "2.12.2018");
            docList.add(doc);

            doc=new Document("name", "Kamila Ostrovska")
                    .append("age", 41)
                    .append("possition", "HR manager")
                    .append("startInCompany", "16.8.2019");
            docList.add(doc);

            doc=new Document("name", "Monika Mala")
                    .append("age", 32)
                    .append("possition", "HR specialist")
                    .append("startInCompany", "15.3.2019");
            docList.add(doc);

            //insertMany
            collection.insertMany(docList);

            //print all employees
            System.out.println("_________All employees_________");
            MongoCursor<Document> cursor = collection.find().iterator();
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    System.out.println(document.toJson());
                }

                //update one employee
                Bson updateQuery=new Document("possition", "HR specialist");
                Bson newValue=new Document("possition", "HR Senior specialist");
                Bson update=new Document("$set", newValue);

                collection.updateOne(updateQuery, update);


            //print all employees after update
            System.out.println("_________Update employees________");
            MongoCursor<Document> cursor1 = collection.find().iterator();
            while (cursor1.hasNext()) {
                Document document = cursor1.next();
                System.out.println(document.toJson());
            }

            //delete one employee
            BasicDBObject deleteQuery=new BasicDBObject();
            deleteQuery.put("name","Patrik Vereb");
            collection.deleteOne(deleteQuery);

            //print all employees after delete
            System.out.println("_________Employees after delete________");
            MongoCursor<Document> cursor2 = collection.find().iterator();
            while (cursor2.hasNext()) {
                Document document = cursor2.next();
                System.out.println(document.toJson());
            }

            //command to drop whole collection employees
            //collection.drop();


        }



    }


}
