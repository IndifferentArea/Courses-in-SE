public class contrive {
     public static class Employee {
         static double base = 0;
         static double percent = 0; // 参数
         int PRP;
         
         Employee(int P){
             PRP = P;
         }

         public double cal_salary(){
             return base + percent * PRP;
         }
     }
     
     public class Salaried_employee extends Employee {
         Salaried_employee(int fixed) {
             super(0);
             base = fixed;
         }
     }

     public class Hourly_employee extends Employee{
         int hourly_salary;
         Hourly_employee(int h, int hourly_salary) {
             super(h);
             base = 0;
             percent = 0.5 * hourly_salary;
         }

         @Override
         public int cal_salary() {
             base = 40*PRP;
             if(PRP > 40) percent = PRP - 40;


         }
     }
    
}
