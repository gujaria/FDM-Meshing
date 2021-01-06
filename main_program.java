import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class main_program {

	public static void main(String[] args) throws IOException 
	{	
		node start=new node(0,0);
		node end=new node(100,100);
		input_shape shape=new input_shape(true);
		
		struck_mesh2 mesh1=new struck_mesh2(shape,0.2f);
		
//		struc_mesh mesh1=new struc_mesh(start,end,1f,1f,shape);
//		heat_diff solver;
//		solver=new heat_diff(mesh1,100,0.1f,0.5f);
	}
	
	
}
