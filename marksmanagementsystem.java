import java.util.*;
import java.io.*;
class MarksManagementSystem {
private static final String FILE_NAME = "marks.csv";
private static final String DELIMITER = ",";
public static void main(String[] args) {
List<Mark> marks = loadMarksFromFile();
Scanner scanner = new Scanner(System.in);
while (true) {
System.out.println("1. View Marks(Teacher)");
System.out.println("2. Update Mark(Teacher)");
System.out.println("3. view Final Result(Student)");
System.out.println("4. Add student and Marks(Teacher)");
System.out.println("5. Exit");
System.out.print("Enter your choice: ");
int choice = scanner.nextInt();
scanner.nextLine(); // Consume newline
switch (choice) {
case 1:
viewMarks(scanner, marks);
break;
case 2:
updateMark(scanner, marks);
break;
case 3:
publishResult(marks);
break;
case 4:
addStudentMarks(scanner, marks);
break;
case 5:
saveMarksToFile(marks);
System.out.println("Exiting...");
return;
default:
System.out.println("Invalid choice. Please try
again.");
}
}
}
private static void addStudentMarks(Scanner scanner,
List<Mark> marks) {
System.out.println("Enter Roll No of student : ");
int rollNo = scanner.nextInt();
scanner.nextLine();
System.out.println("Enter name of student : ");
String name = scanner.nextLine();
System.out.println("Enter marks of Software Engineering :
");
double swe = scanner.nextInt();
System.out.println("Enter marks of Sanskrit : ");
double snk = scanner.nextInt();
System.out.println("Enter marks of EVS : ");
double evs = scanner.nextInt();
marks.add(new Mark(rollNo, name, swe, snk, evs));
System.out.println("Added Student details and marks
successfully");
}
private static void viewMarks(Scanner scanner, List<Mark>
marks) {
System.out.println("Enter Student Roll No:");
int rollNo = scanner.nextInt();
System.out.println("Printing Marks of Student of Roll No " +
rollNo + ": ");
for (Mark mark : marks) {
if (mark.getRollNo() == rollNo) {
System.out.println(mark);
break;
}
}
}
private static void publishResult(List<Mark> marks) {
Collections.sort(marks, new SortByTotalMark());
System.out.println("Publishing Result");
for (Mark mark : marks) {
System.out.println(mark);
}
}
private static void updateMark(Scanner scanner, List<Mark>
marks) {
System.out.println("Enter Student Roll No:");
int rollNo = scanner.nextInt();
System.out.println("1. Software Engineering");
System.out.println("2. Sanskrit");
System.out.println("3. EVS");
System.out.println("Enter Subject number");
int subInd = scanner.nextInt();
System.out.println("Enter updated mark ");
double updateMark = scanner.nextDouble();
for (Mark mark : marks) {
if (mark.getRollNo() == rollNo) {
if (subInd == 1)
mark.setSWE(updateMark);
else if (subInd == 2)
mark.setSNK(updateMark);
else
mark.setEVS(updateMark);
System.out.println("Updated Successfully");
break;
}
}
}
private static List<Mark> loadMarksFromFile() {
List<Mark> marks = new ArrayList<>();
try (BufferedReader reader = new BufferedReader(new
FileReader(FILE_NAME))) {
String line;
while ((line = reader.readLine()) != null) {
String[] data = line.split(DELIMITER);
marks.add(new Mark(Integer.parseInt(data[0]), data[1],
Double.parseDouble(data[2]), Double.parseDouble(data[3]),
Double.parseDouble(data[4])));
}
} catch (IOException | NumberFormatException e) {
System.err.println("Error loading marks: " +
e.getMessage());
}
return marks;
}
private static void saveMarksToFile(List<Mark> marks) {
try (BufferedWriter writer = new BufferedWriter(new
FileWriter(FILE_NAME))) {
for (Mark mark : marks) {
writer.write(mark.getRollNo() + DELIMITER +
mark.getName() + DELIMITER + mark.getsweMark() + DELIMITER
+ mark.getsnkMark() + DELIMITER +
mark.getevsMark());
writer.newLine();
}
} catch (IOException e) {
System.err.println("Error saving marks: " +
e.getMessage());
}
}
}
class Mark {
int rollNo;
String name;
double swe, snk, evs;
public Mark(int rollNo, String name, double swe, double snk,
double evs) {
this.rollNo = rollNo;
this.name = name;
this.swe = swe;
this.snk = snk;
this.evs = evs;
}
public String getName() {
return name;
}
public int getRollNo() {
return rollNo;
}
public double getsweMark() {
return swe;
}
public double getsnkMark() {
return snk;
}
public double getevsMark() {
return evs;
}
void setSWE(double swe) {
this.swe = swe;
}
void setSNK(double snk) {
this.snk = snk;
}
void setEVS(double evs) {
this.evs = evs;
}
public String toString() {
return rollNo + " " + name + " " + swe + " " + snk + " " +
evs + " ";
}
public double totalMark() {
return swe + snk + evs;
}
}
class SortByTotalMark implements Comparator<Mark> {
// Used for sorting in descending order
public int compare(Mark m1, Mark m2) {
if (m2.totalMark() == m1.totalMark())
return 0;
else if (m2.totalMark() > m1.totalMark())
return 1;
else
return -1;
}
}
