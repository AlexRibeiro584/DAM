package com.cateringfx.utils;

import com.cateringfx.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//class FileUtils in which we store the methods related to file management
public class FileUtils {
    //method in which we load the dishes and aliments from the specified txt files
    public static List<MenuElement> loadElements() {
        List<MenuElement> elementList = new ArrayList<>();
        if (new File("aliments.txt").exists() && new File("dishes.txt").exists()) {
            try (Stream<String> alimentsStream = Files.lines(Paths.get("aliments.txt"));
                 Stream<String> dishesStream = Files.lines(Paths.get("dishes.txt"));) {
                    elementList = alimentsStream
                        .map(line -> new Aliment(
                                line.split(";")[0],
                                line.split(";")[1],
                                line.split(";")[2],
                                Boolean.parseBoolean(line.split(";")[3]),
                                Boolean.parseBoolean(line.split(";")[4]),
                                Boolean.parseBoolean(line.split(";")[5]),
                                Boolean.parseBoolean(line.split(";")[6]),
                                Double.parseDouble(line.split(";")[7]),
                                Double.parseDouble(line.split(";")[8]),
                                Double.parseDouble(line.split(";")[9])


                        )).collect(Collectors.toList());
                elementList.addAll(dishesStream.map(line-> {
                    String[] parts = line.split(";");
                    Dish dish = new Dish(parts[0], parts[1]);
                    for (int i = 2; i< parts.length; i +=11){
                        dish.addIngredient(new Ingredient(Double.parseDouble(parts[i]),
                                new Aliment(parts[i +1], parts[i+2], parts[i+3],
                                        Boolean.parseBoolean(parts[i+4]),
                                        Boolean.parseBoolean(parts[i+5]),
                                        Boolean.parseBoolean(parts[i+6]),
                                        Boolean.parseBoolean(parts[i+7]),
                                        Double.parseDouble(parts[i+8]),
                                        Double.parseDouble(parts[i+9]),
                                        Double.parseDouble(parts[i+10]))));
                    }
                    return dish;
                }).collect(Collectors.toList()));

            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
            catch (IOException e2){
                System.out.println("Unexpected error");
            }
        }
        return elementList;
    }
    //method in which we store the desired aliment into the txt file we already have
    public static void storeAliment(Aliment a){
        try(PrintWriter pw = new PrintWriter(
                new FileWriter(
                        Paths.get("aliments.txt").toAbsolutePath().toString(),
                        true)))
        {
            pw.println(a);

        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    //method in which we store the desired dish into the txt file we already have
    public static void storeDish(Dish d){
        try(PrintWriter pw = new PrintWriter(
                new FileWriter(
                        Paths.get("dishes.txt").toAbsolutePath().toString(),
                        true)))
        {
            pw.println(d);

        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    //method in which we store the desired menu into a txt file with the specified date
    public static void storeMenu(Menu m){
        String filenameMenu = m.getDate()+".menu.txt";
        try(PrintWriter pw = new PrintWriter(
                new FileWriter(
                        Paths.get(filenameMenu).toAbsolutePath().toString(),
                        true)))
        {
            pw.println(m);

        }catch(IOException e1){
            System.out.println("An error occurred while write the Menu.");
            e1.printStackTrace();
        }
    }
}
