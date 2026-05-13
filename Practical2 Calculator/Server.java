import CalculatorApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

class Server
{
    public static void main(String[] args)
    {
        try
        {
            // Step 1: Initialize ORB
            ORB orb = ORB.init(args, null);

            // Step 2: Initialize POA
            POA rootPOA = POAHelper.narrow(
                orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            // Step 3: Create servant object
            CalculatorImpl calc = new CalculatorImpl();

            // Step 4: Get object reference
            org.omg.CORBA.Object ref =
                rootPOA.servant_to_reference(calc);
            Calculator href = CalculatorHelper.narrow(ref);

            // Step 5: Get naming service
            org.omg.CORBA.Object objRef =
                orb.resolve_initial_references("NameService");
            NamingContextExt ncRef =
                NamingContextExtHelper.narrow(objRef);

            // Step 6: Bind object with name
            String name = "Calculator";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);

            System.out.println("Server is ready and waiting...");
            orb.run();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}