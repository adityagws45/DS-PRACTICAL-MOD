import CalculatorApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.Scanner;

class Client
{
    public static void main(String args[])
    {
        try
        {
            // Step 1: Initialize ORB
            ORB orb = ORB.init(args, null);

            // Step 2: Get naming service
            org.omg.CORBA.Object objRef =
                orb.resolve_initial_references("NameService");
            NamingContextExt ncRef =
                NamingContextExtHelper.narrow(objRef);

            // Step 3: Resolve calculator object
            String name = "Calculator";
            Calculator calc =
                CalculatorHelper.narrow(ncRef.resolve_str(name));

            // Step 4: Take input and call remote methods
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter first number: ");
            double a = sc.nextDouble();
            System.out.print("Enter second number: ");
            double b = sc.nextDouble();

            System.out.println("Addition       = " + calc.add(a, b));
            System.out.println("Subtraction    = " + calc.subtract(a, b));
            System.out.println("Multiplication = " + calc.multiply(a, b));
            System.out.println("Division       = " + calc.divide(a, b));
        }
        catch(Exception e)
        {
            System.out.println("ERROR: " + e);
            e.printStackTrace();
        }
    }
}