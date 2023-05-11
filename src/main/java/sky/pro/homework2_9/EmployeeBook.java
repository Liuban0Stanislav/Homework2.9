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
        for (Employee employee : employees.values()) {
            rezString.append("- " + employee.getFullName() +
                    ", зарплата: " + employee.getSalary() +
                    ", отдел: " + employee.getDept() + "\n");
        }
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
        for (Employee employee : employees.values()) {
            rezString.append("- " + employee.getFullName() +
                    ", зарплата: " + employee.getSalary() +
                    "\n");
        }
        return rezString;
    }


    public StringBuilder printEmployeesAccordingToDept1() {
        int[] arrDept = new int[employees.size()];
        /**
         * Пытаюсь получить упорядоченный список существующих отделов, где отделы не повторяются.
         * Для этого:
         * Сохдаю массив arrDept и записываю в него номера существующих отделов в произвольном порядке.
         */
        int i = 0;
        for (Employee employee : employees.values()) {
            arrDept[i++] = employee.getDept();
        }
        System.out.println("Несортированный массив arrDept: " + Arrays.toString(arrDept));
        Arrays.sort(arrDept);
        System.out.println("Сортированный массив arrDept: " + Arrays.toString(arrDept));
        int arrElCounter = 1;
        /**
         * После сортировки массива в нем могут быть одинаковые отделы
         * Создаю переменную arrElCounter, которая просуммирует уникальные отделы, и станет количеством элементов
         * в массиве с сортированными неповторяющимися отделами.
         */
        for (i = 0; i < employees.size() - 1; i++) {
            if (arrDept[i] != arrDept[i + 1]) {
                arrElCounter++;
            }
        }
        /**
         * Переменная заполнилась и теперь можно создать итоговый массив с отделами arrDeptUnic.
         */
        int[] arrDeptUnic = new int[arrElCounter];
        /**
         * Теперь нужно пройтись циклом по сортированному массиву arrDept,
         * выбрать из него неповторяющиеся номера отделов, и записать их в массив arrDeptUnic
         * который имеет необходимое количество элементов.
         */
        arrElCounter = 0;
        System.out.println("Пустой массив arrDeptUnic: " + Arrays.toString(arrDeptUnic));
        for (i = 0; i < employees.size() - 1; i++) {
            if (arrDept[i] != arrDept[i + 1]) {
                arrDeptUnic[arrElCounter] = arrDept[i];
                System.arraycopy(arrDept, i, arrDeptUnic, arrElCounter, 1);
                arrElCounter++;
            }
        }
        arrDeptUnic[arrElCounter] = arrDept[arrDept.length - 1];
        System.out.println("Заполненный массив arrDeptUnic: " + Arrays.toString(arrDeptUnic));
        /**
         * Массив с существующими отделами получен, и теперь можно найти
         * каждого сотрудника принадлежащего своему отделу и вывести его в консоль.
         * Для этого создаю цикл в цикле. Внешний цикл - это перебор отделов. Внутренний
         * цикл это перебор сотрудников и отбор тех чей номер отдела совпадает с
         * номером отдела во внешнем цикле.
         */
        StringBuilder sbRez = new StringBuilder("");
        Arrays.stream(arrDeptUnic).
                forEach(e -> {
                    sbRez.append("\nОтдел №" + e + "\n");
                    employees.values().stream()
                            .filter(employee -> employee.getDept() == e)
                            .forEach(employee -> sbRez.append(employee.getFullName() + " \n"));
                });
        return sbRez;
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
        for (Employee employee : employees.values()) {
            double increasedSalary = employee.getSalary() * (1 + percentOfIndexing / 100);
            employee.setSalary((int) increasedSalary);
        }
    }

    public StringBuilder salariesBILO() {
        Map<String, Employee> emloyeeSalaryesBILO = employees;
        StringBuilder rezString = new StringBuilder("");
        if (emloyeeSalaryesBILO.isEmpty()) {
            return rezString.append("интерфейс Map не содержит ни одного сотрудника");
        }
        for (Employee employee : emloyeeSalaryesBILO.values()) {
            rezString.append("- " + employee.getFullName() +
                    ", зарплата: " + employee.getSalary() +
                    ", отдел: " + employee.getDept() + "\n");
        }
        return rezString;
    }

    public StringBuilder salaryLessThan(int lessThanThisNum) {
        int lessSalariesCounter = 0;
        StringBuilder rezString = new StringBuilder("");
        for (Employee employee : employees.values()) {
            if (employee.getSalary() < lessThanThisNum) {
                rezString.append("- " + employee.getFullName() +
                        ", зарплата: " + employee.getSalary() +
                        ", отдел: " + employee.getDept() + "\n");
                lessSalariesCounter++;
            }

        }
        if (lessSalariesCounter == 0) {
            return rezString.append("Сотрудников с зарплатой ниже " + lessThanThisNum + " рублей " + " - нет");
        }
        return rezString;
    }

    public StringBuilder salaryMoreThan(int moreThanThisNum) {
        int moreSalariesCounter = 0;
        StringBuilder rezString = new StringBuilder("");
        for (Employee employee : employees.values()) {
            if (employee.getSalary() > moreThanThisNum) {
                rezString.append("- " + employee.getFullName() +
                        ", зарплата: " + employee.getSalary() +
                        ", отдел: " + employee.getDept() + "\n");
                moreSalariesCounter++;
            }

        }
        if (moreSalariesCounter == 0) {
            return rezString.append("Сотрудников с зарплатой выше " + moreThanThisNum + " рублей " + " - нет");
        }
        return rezString;
    }


    public String findEmployeesMinimalSalary() {
        int min = Integer.MAX_VALUE;
        String fullName = "";
        for (Employee employee : employees.values()) {
            if (employee.getSalary() <= min) {
                min = employee.getSalary();
                fullName = employee.getFullName();
            }
        }
        return fullName;
    }

    public String findEmployeesMaximalSalary() {
        int max = Integer.MIN_VALUE;
        String fullName = "";
        for (Employee employee : employees.values()) {
            if (employee.getSalary() > max) {
                max = employee.getSalary();
                fullName = employee.getFullName();
            }
        }
        return fullName;
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
