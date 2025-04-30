package lab12;

public class Main {
    public static void main(String[] args)
    {
        Manager manager = new Manager();

        manager.addEmployeesFromFile("lab12/lab12_data.csv");

        System.out.println("=== Lookup: ID 000003 ===");

        manager.lookupEmployee("000003");

        System.out.println("=== Lookup: ID 000017 ===");

        manager.lookupEmployee("000017");

        System.out.println("=== Lookup: ID 000100 ===");

        manager.lookupEmployee("000100");

        System.out.println("=== All Employees (Alphabetical Order) ===");
        
        manager.printAllEmployeesAlphabetically();
    }
}

