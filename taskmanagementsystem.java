import java.util.*;
import java.io.*;

class TasksManagementSystem {
    static int _id = 0;
    private static final String FILE_NAME = "tasks.csv";
    private static final String DELIMITER = ",";

    public static void main(String[] args) {
        List<Task> tasks = loadTasksFromFile();

        if (tasks.size() > 0)
            _id = (tasks.get(tasks.size() - 1)).get_id() + 1;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add new Task");
            System.out.println("2. Edit Task");
            System.out.println("3. Complete Task");
            System.out.println("4. Show All Task");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addTask(scanner, tasks);
                    break;
                case 2:
                    editTask(scanner, tasks);
                    break;
                case 3:
                    completeTask(scanner, tasks);
                    break;
                case 4:
                    showAllTask(tasks);
                    break;
                case 5:
                    saveTasksToFile(tasks);
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addTask(Scanner scanner, List<Task> tasks) {
        System.out.println("Enter name of Task : ");
        String name = scanner.nextLine();
        tasks.add(new Task(_id++, name, false));
        System.out.println("Added tasks successfully");
    }

    private static void editTask(Scanner scanner, List<Task> tasks) {
        System.out.println("Enter task _id : ");
        int id = scanner.nextInt();
        System.out.println("Enter modified task name : ");
        scanner.nextLine();
        String newTask = scanner.nextLine();

        for (Task task : tasks) {
            if (task.get_id() == id) {
                task.setTaskName(newTask);
                System.out.println("Task updated successfully");
                break;
            }
        }
    }

    private static void completeTask(Scanner scanner, List<Task> tasks) {
        System.out.println("Enter task _id : ");
        int id = scanner.nextInt();

        for (Task task : tasks) {
            if (task.get_id() == id) {
                task.setIsComplete(true);
                System.out.println("Task is completed ");
                break;
            }
        }
    }

    private static void showAllTask(List<Task> tasks) {
        Collections.sort(tasks, new SortById());

        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    static class SortById implements Comparator<Task> {
        public int compare(Task t1, Task t2) {
            if (t1.get_id() == t2.get_id())
                return 0;
            else if (t1.get_id() > t2.get_id())
                return 1;
            else
                return -1;
        }
    }

    private static List<Task> loadTasksFromFile() {
        List<Task> tasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                tasks.add(new Task(Integer.parseInt(data[0]), data[1], Boolean.parseBoolean(data[2])));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    private static void saveTasksToFile(List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.write(task.get_id() + DELIMITER + task.getTaskName() + DELIMITER + task.getIsComplete());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }
}

class Task {
    int _id;
    String name;
    boolean isComplete = false;

    public Task(int _id, String name, boolean isComplete) {
        this._id = _id;
        this.name = name;
        this.isComplete = isComplete;
    }

    public String getTaskName() {
        return name;
    }

    public int get_id() {
        return _id;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    void setTaskName(String taskName) {
        this.name = taskName;
    }

    void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public String toString() {
        if (isComplete)
            return _id + " " + name + " " + "Tasks is completed";
        else
            return _id + " " + name + " " + "Tasks is pending";
    }
}
