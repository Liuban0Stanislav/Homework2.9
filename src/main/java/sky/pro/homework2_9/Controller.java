package sky.pro.homework2_9;

import com.skypro.homework2_8.execptions.FullMapException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private EmployeeBook employeeBook;

    public Controller(EmployeeBook employeeBook) {
        this.employeeBook = employeeBook;
    }

    @GetMapping("/")
    public String getEmployees() {
        return "<pre><h2><b>Список всех сотрудников:</b></h2>\n" + "" + employeeBook.printEmployees() + "<pre>";
    }

    @GetMapping("/add")
    public String addEmployee(@RequestParam("fullName") String fullName,
                              @RequestParam("salary") int salary,
                              @RequestParam("dept") int dept) {
        if (employeeBook.getEmployees().containsKey(fullName)) {
            return "такой сотрудник уже есть";
        }
        try {
            employeeBook.addEmployee(fullName, salary, dept);
        } catch (FullMapException e) {
            throw new RuntimeException("\u001B[31m Нельзя добавить сотрудника, " +
                    "достигнут искуственный предел заполнения интерфейса Map\u001B[0m");
        }
        return "Сотрудник: " + employeeBook.getEmployees().get(fullName) + " добавлен";
    }

    @GetMapping("/find")
    public String findEmployee(@RequestParam("fullName") String fullName) {
        if (employeeBook.getEmployees().containsKey(fullName)) {
            return "Сотрудник: " + employeeBook.getEmployees().get(fullName);
        } else {
            return "Сотрудник не найден";
        }
    }

    @GetMapping("/remove")
    public String removeEmployee(@RequestParam("fullName") String fullName) {
        try {
            employeeBook.removeEmployee(fullName);
        } catch (NullPointerException e) {
            throw new RuntimeException("\u001B[31m Такого сотрудника не существует \u001B[0m");
        }
        return "Сотрудник " + fullName + " удален";
    }

    @GetMapping("/change")
    public String changeEmployee(@RequestParam("fullNameDeletingEmployee") String fullNameDeletingEmployee,
                                 @RequestParam("fullNameNewEmployee") String fullNameNewEmployee,
                                 @RequestParam("newSalary") int newSalary,
                                 @RequestParam("newDept") int newDept) {
        try {
            employeeBook.changeEmployee(fullNameDeletingEmployee,
                    fullNameNewEmployee,
                    newSalary,
                    newDept);
        } catch (NullPointerException e) {
            throw new RuntimeException("\u001B[31m Такого сотрудника не существует \u001B[0m");
        }
        return "Сотрудник " + fullNameDeletingEmployee + " заменен сотрудником " + fullNameNewEmployee;
    }

    @GetMapping("/printAllEmployeesWithoutDept")
    public String printAllEmployeesWithoutDept() {
        return "<pre><h2><b>Список всех сотрудников без отделов:</b></h2>\n" +
                "" + employeeBook.printEmployeesWithoutDept() + "<pre>";
    }

    @GetMapping("/salaryIndexing")
    public String salaryIndexing(@RequestParam("percent") double percentOfIndexing) {
        StringBuilder sb = new StringBuilder("");
        String bilo = employeeBook.salariesBILO().toString();
        employeeBook.salaryIndexing(percentOfIndexing);
        return "<pre><h2><b>Зарплаты БЫЛО:</b></h2>\n" + "" + bilo + "<pre>" +
                "<pre><h2><b>Зарплаты СТАЛО:</b></h2>\n" + "" + employeeBook.printEmployees() + "<pre>" +
                "<pre><h2>Размер индексации - " + percentOfIndexing + "%</h2></pre>";
    }

    @GetMapping("/salaryLess")
    public String salaryLessThan(@RequestParam("lessThan") int salaryLessThan) {
        return "<pre><h2><b>Сотрудник с зарплатой меньше чем - " + salaryLessThan + " рублей</b></h2><pre>" +
                "<pre>" + employeeBook.salaryLessThan(salaryLessThan) + "<pre>";

    }

    @GetMapping("/salaryMore")
    public String salaryMoreThan(@RequestParam("moreThan") int salaryMoreThan) {
        return "<pre><h2><b>Сотрудник с зарплатой меньше чем - " + salaryMoreThan + " рублей</b></h2><pre>" +
                "<pre>" + employeeBook.salaryMoreThan(salaryMoreThan) + "<pre>";

    }

    @GetMapping("/findEmployeesMinMaxSalary")
    public String findEmployeesMinMaxSalary() {
        return "<pre><h2><b>Сотрудник с минимальной зарплатой - " +
                employeeBook.findEmployeesMinimalSalary() + " " +
                employeeBook.getEmployees().get(employeeBook.findEmployeesMinimalSalary()).getSalary() +
                " р. </b></h2><pre>" + "<pre><h2><b>Сотрудник с максимальной зарплатой - " +
                employeeBook.findEmployeesMaximalSalary() + " " +
                employeeBook.getEmployees().get(employeeBook.findEmployeesMaximalSalary()).getSalary() +
                " р.</b></h2><pre>";

    }

    @GetMapping("/findAndPrintEmployeeById")
    public String findAndPrintEmployeeById(@RequestParam("id") int id) {
        int idRez;
        try {
            idRez = Integer.valueOf(employeeBook.getEmployees().get(employeeBook.findAndPrintEmployeeById(id)).getId());
        } catch (NullPointerException e) {
            throw new RuntimeException("\u001B[31m Сотрудника с таким ID не существует \u001B[0m");
        }
        return "<pre><h2><b>Сотрудник чей id=" + idRez +
                " - " + employeeBook.findAndPrintEmployeeById(id) + "</b></h2><pre>";

    }

    @GetMapping("/monthMiddleSalary")
    public String monthMiddleSalary() {
        return "<pre><h2><b>Среднемесячная зарплата - " +
               employeeBook.monthMiddleSalary(employeeBook.monthSumSalary()) +
                " рублей</b></h2><pre>";

    }

    @GetMapping("/middleSalaryByDept")
    public String middleSalaryByDept(@RequestParam("dept") int dept) {
        String stringRez;
        try {
            stringRez = employeeBook.middleSalaryByDept(dept);
        } catch (RuntimeException e) {
            throw new RuntimeException("\u001B[31m Номера департаментов 1 - 5, введите корректный номер \u001B[0m");
        }
        return "<pre><h2><b>Средняя зарплата по отделу - " +
                stringRez +
                " рублей</b></h2><pre>";

    }

    @GetMapping("/max-salary")
    public String getMaxSalaryByDept(@RequestParam("departmentId") int dept) {
        String stringRez = employeeBook.getMaxSalaryByDept(dept);
        return "<pre><h2><b>Максимальная зарплата по отделу: " +
                stringRez +
                " рублей</b></h2><pre>";

    }
    @GetMapping("/min-salary")
    public String getMinSalaryByDept(@RequestParam("departmentId") int dept) {
        String stringRez = employeeBook.getMinSalaryByDept(dept);
        return "<pre><h2><b>Минимальная зарплата по отделу: " +
                stringRez +
                " рублей</b></h2><pre>";

    }
    @GetMapping(value = "/departments/allEmployeesInDept", params = "departmentId")
    public String findAllEmployeesAccordingDept(@RequestParam("departmentId") int dept) {
        String stringRez = employeeBook.findAllEmployeesAccordingDept(dept);
        return "<pre><h2><b>Сотрудники " + dept + "-го" + " отдела:\n" +
                stringRez + "</b></h2><pre>";

    }

    @GetMapping("/departments/all")
    public String printEmployeesAccordingToDept() {
        return "<pre><h2><b>Список всех сотрудников по отделам:</b></h2>\n" +
                "" + employeeBook.printEmployeesAccordingToDept() + "<pre>";
    }

}
