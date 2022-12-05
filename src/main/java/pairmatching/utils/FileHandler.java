package pairmatching.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pairmatching.domain.course.Course;

public class FileHandler {

    public static List<String> readCrewNames(Course course) {
        List<String> crewNames = new ArrayList<>();
        Scanner scanner = readFile(course);
        while(scanner.hasNextLine()) {
            crewNames.add(scanner.nextLine());
        }
        scanner.close();
        return crewNames;
    }

    private static Scanner readFile(Course course) {
        try {
            File file = new File("./src/main/resources/" + course.getFileName() + ".md");
            Scanner scanner = new Scanner(file);
            return scanner;
        } catch (FileNotFoundException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }
}
