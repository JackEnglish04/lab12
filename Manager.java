package lab12;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Manager
{
    private static int idCounter = 1;

    private static String generateEmployeeId()
    {
        return String.format("%06d", idCounter++);
    }

    private final Map<String, EmployeeRecord> employeeRecords = new HashMap<>();

    public void addEmployee(String name, String department, String salaryStr) throws InvalidEntryException
    {
        if (name == null || name.isEmpty())
        {
            throw new InvalidEntryException("Name cannot be empty");
        }

        double salary;
        try
        {
            salary = Double.parseDouble(salaryStr);
            if (salary < 0) throw new NumberFormatException();
        }
        catch (NumberFormatException e)
        {
            throw new InvalidEntryException("Invalid salary: " + salaryStr);
        }

        if (!List.of("Business", "Administrative", "Technical").contains(department.toUpperCase()))
        {
            throw new InvalidEntryException("Invalid department: " + department);
        }

        String id = generateEmployeeId();
        EmployeeRecord record = new EmployeeRecord(name, id, department.toUpperCase(), salary);
        employeeRecords.put(id, record);
    }

    public void addEmployeesFromFile(String filename)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null)
            {
                row++;
                System.out.println("Debug line " + row + ": " + line);

                if (row == 1) continue;

                String[] parts = line.split(",");

                if (parts.length < 5) {
                    System.err.println("Row " + row + ": Not enough data fields");
                    continue;
                }
                try
                {
                    addEmployee(parts[1].trim(), parts[3].trim(), parts[2].trim());
                }
                catch (InvalidEntryException e)
                {
                    System.err.println("Row " + row + ": " + e.getMessage());
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("Error reading file: " + filename);
        }
    }

    public void lookupEmployee(String id)
    {
        EmployeeRecord record = employeeRecords.get(id);
        if (record != null)
        {
            printEmployee(record);
        }
        else
        {
            System.out.println("Employee with ID " + id + " not found");
        }
    }

    public void printAllEmployeesAlphabetically()
    {
        employeeRecords.values().stream()
            .sorted(Comparator.comparing(EmployeeRecord::name))
            .forEach(this::printEmployee);
    }

    private void printEmployee(EmployeeRecord record)
    {
        System.out.println("Name: " + record.name());
        System.out.println("ID: " + record.id());
        System.out.println("Department: " + record.department());
        System.out.println("Salary: " + record.salary());
        System.out.println("\n");
    }
}
