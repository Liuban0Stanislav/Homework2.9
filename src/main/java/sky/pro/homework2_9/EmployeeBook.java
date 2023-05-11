package sky.pro.homework2_9;

import com.skypro.homework2_8.execptions.FullMapException;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@Service
public class EmployeeBook {

    private Map<String, Employee> employees = new HashMap<>(Map.of(
            "Попова Варвара Богдановна",
            new Employee("Попова Варвара Богдановна", 85_000, 5),
            "Петрова Елена Павловна",
            new Employee("Петрова Елена Павловна", 87_000, 2),
            "Васильев Денис Андреевич",
            new Employee("Васильев Денис Андреевич", 65_000, 1),
            "Лянге Александр Григорьевич",
            new Employee("Лянге Александр Григорьевич", 90_000, 3),
            "Кузнецов Александр Семенович",
            new Employee("Кузнцов Александр Семенович", 67_000, 4),
            "Скворцов Сергей Денисович",
            new Employee("Скворцов Сергей Денисович", 63_000, 4),
            "Александров Михаил Богданович",
            new Employee("Александров Михаил Богданович", 99_000, 2),
            "Карчемный Владимир Георгиевич",
            new Employee("Карчемный Владимир Георгиевич", 72_000, 1),
            "Юницин Сергей Михайлович",
            new Employee("Юницин Сергей Михайлович", 76_000, 5),
            "Рыбкин Данил Амвросиевич",
            new Employee("Рыбкин Данил Амвросиевич", 75_000, 3)
    ));


    public StringBuilder printEmployees() {
        StringBuilder rezString = new StringBuilder("");
        if (employees.isEmpty()) {
            return rezString.append("интерфейс Map не содержит ни одного сотрудника");
        }
        employees.values().stream()
                .forEach(employee -> rezString.append("- " + employee.getFullName() +
                        ", зарплата: " + employee.getSalary() +
                        ", отдел: " + employee.getDept() + "\n"));
        return rezString;
    }


    public void addEmployee(String fullName, int salary, int dept) {
        if (Employee.getIdCounter() > 10) {
            throw new FullMapException();
        }
        employees.put(fullName, new Employee(fullName, salary, dept));
    }

    public void removeEmployee(String fullName) {
        if (employees.containsKey(fullName)) {
            employees.remove(fullName);
        } else {
            throw new RuntimeException();
        }
    }


    public Map<String, Employee> getEmployees() {
        return employees;
    }

    public void changeEmployee(String fullNameDeletingEmlpoyee,
                               String fullNameNewEmployee,
                               Integer newSalary,
                               Integer newDept) {
        if (employees.containsKey(fullNameDeletingEmlpoyee)) {
            removeEmployee(fullNameDeletingEmlpoyee);
            addEmployee(fullNameNewEmployee, newSalary, newDept);
        } else {
            throw new NullPointerException();
        }
    }

    public StringBuilder printEmployeesWithoutDept() {
        StringBuilder rezString = new StringBuilder("");
        if (employees.isEmpty()) {
            return rezString.append("интерфейс Map не содержит ни одного сотрудника");
        }
        employees.values().stream()
                .forEach(employee -> rezString.append("- " + employee.getFullName() +
                        ", зарплата: " + employee.getSalary() +
                        "\n"));
        return rezString;
    }

    public StringBuilder printEmployeesAccordingToDept() {
        /**
         * Беру из Map только номера отделов, переписываю Map в List,
         * сортирую, удаляю повторяющиеся номера и сохраняю в List
         */
        List<Integer> sortedList = employees.values().stream()
                .map(e -> e.getDept())
                .sorted()
                .distinct()
                .collect(Collectors.toList());
        /**
         * Создаю редактируемую строку sbRez.
         * Прохожусь по списку sortedList и каждую итерацию добавляю в строку заголовок
         * с новым номером отдела. Внутри этого потока sortedList, создаю поток
         * в котором, пробегаю по Map. И фильтрую сотрудников у которых,
         * номер департамента совпадает с номером департамента из текущей
         * итерации списка sortedList. Таких сотрудников дописываем в строку sbRez
         */
        StringBuilder sbRez = new StringBuilder("");
        sortedList.stream()
                .forEach(dept -> {
                    sbRez.append("\nОтдел №" + dept + "\n");
                    employees.values().stream()
                            .filter(employee -> employee.getDept() == dept)
                            .forEach(employee -> sbRez.append(employee.getFullName() + " \n"));
                });
        return sbRez;
    }

    public void salaryIndexing(double percentOfIndexing) {
        employees.values().stream().forEach(employee -> {
            double increasedSalary = employee.getSalary() * (1 + percentOfIndexing / 100);
            employee.setSalary((int) increasedSalary);
        });
    }

    public StringBuilder salariesBILO() {
        Map<String, Employee> emloyeeSalaryesBILO = employees;
        StringBuilder rezString = new StringBuilder("");
        if (emloyeeSalaryesBILO.isEmpty()) {
            return rezString.append("интерфейс Map не содержит ни одного сотрудника");
        }
        employees.values().stream()
                .forEach(employee -> rezString.append("- " + employee.getFullName() +
                        ", зарплата: " + employee.getSalary() +
                        ", отдел: " + employee.getDept() + "\n"));
        return rezString;
    }

    public StringBuilder salaryLessThan(int lessThanThisNum) {
        int lessSalariesCounter;
        StringBuilder rezString = new StringBuilder("");
        lessSalariesCounter = (int) employees.values().stream()
                .filter(employee -> employee.getSalary() < lessThanThisNum)
                .peek(employee -> {
                    rezString.append("- " + employee.getFullName() +
                            ", зарплата: " + employee.getSalary() +
                            ", отдел: " + employee.getDept() + "\n");
                }).count();
        if (lessSalariesCounter == 0) {
            return rezString.append("Сотрудников с зарплатой ниже " + lessThanThisNum + " рублей " + " - нет");
        }
        return rezString;
    }

    public StringBuilder salaryMoreThan(int moreThanThisNum) {
        int moreSalariesCounter;
        StringBuilder rezString = new StringBuilder("");
        moreSalariesCounter = (int) employees.values().stream()
                .filter(employee -> employee.getSalary() > moreThanThisNum)
                .peek(employee -> rezString.append("- " + employee.getFullName() +
                        ", зарплата: " + employee.getSalary() +
                        ", отдел: " + employee.getDept() + "\n"))
                .count();
        if (moreSalariesCounter == 0) {
            return rezString.append("Сотрудников с зарплатой выше " + moreThanThisNum + " рублей " + " - нет");
        }
        return rezString;
    }


    public String findEmployeesMinimalSalary() {
        return employees.values().stream()
                .min(Comparator.comparingInt(employee -> employee.getSalary()))
                .map(employee -> employee.getFullName()).orElse("");
    }

    public String findEmployeesMaximalSalary() {
        return employees.values().stream()
                .max(Comparator.comparingInt(e -> e.getSalary()))
                .map(employee -> employee.getFullName()).orElse("");
    }

    public String findAndPrintEmployeeById(int id) {
        return employees.values().stream()
                .filter(e -> id == e.getId())
                .map(employee -> employee.getFullName())
                .findFirst()
                .orElse(null);
    }

    public int monthSumSalary() {
        int sum = 0;
        for (Employee employee : employees.values()) {
            sum = employee.getSalary() + sum;
        }
        return sum;
    }

    public String monthMiddleSalary(int sum) {
        return new DecimalFormat("###,###.##").format((double) sum / employees.size());
    }

    public String middleSalaryByDept(int deptOfEmployee) {
        if (deptOfEmployee >= 6 && deptOfEmployee < 1) {
            throw new RuntimeException();
        }
        int sumSalaries = 0;
        int deptsCounter = 0;
        for (Employee employee : employees.values()) {
            if (employee.getDept() == deptOfEmployee) {
                sumSalaries = employee.getSalary() + sumSalaries;
                deptsCounter++;
            }
        }
        return Double.toString(sumSalaries / deptsCounter);
    }


    public String getMaxSalaryByDept(int deptOfEmployee) {
        if (deptOfEmployee >= 6 && deptOfEmployee < 1) {
            throw new RuntimeException();
        }
        List<Employee> employeeList = employees.values().stream().collect(Collectors.toList());
        String employeeMaxSalary = String.valueOf(employeeList.stream()
                .filter(employee -> employee.getDept() == deptOfEmployee)
                .mapToInt(Employee::getSalary)
                .max()
                .orElse(0));

        return employeeMaxSalary;
    }

    public String getMinSalaryByDept(int deptOfEmployee) {
        if (deptOfEmployee >= 6 && deptOfEmployee < 1) {
            throw new RuntimeException();
        }
        List<Employee> employeeList = employees.values().stream().collect(Collectors.toList());
        int MinSalary = Integer.MAX_VALUE;
        String employeeMinSalary = String.valueOf(employeeList.stream()
                .filter(employee -> employee.getDept() == deptOfEmployee)
                .mapToInt(Employee::getSalary)
                .min()
                .orElse(0));

        return employeeMinSalary;
    }

    public String findAllEmployeesAccordingDept(int deptOfEmployee) {
        if (deptOfEmployee >= 6 && deptOfEmployee < 1) {
            throw new RuntimeException();
        }
        List<Employee> employeeList = employees.values().stream().collect(Collectors.toList());
        List<String> employeesFromDept = employeeList.stream()
                .filter(e -> e.getDept() == deptOfEmployee)
                .map(e -> e + "\n")
                .collect(Collectors.toList());
        return employeesFromDept.toString();
    }
}
