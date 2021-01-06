import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class input_shape {
	
	node[] mesh;
	float delta_x;
	
	input_shape() throws IOException
	{
		mesh=new node[1000];
		
		for(int i=0;i<1000;i++)
		{
			float temp_x=(float) (50f+25f*Math.cos(i)*Math.cos(i)*Math.cos(i));
			float temp_y=(float) (50f+25f*Math.sin(i)*Math.sin(i)*Math.sin(i));
			mesh[i]=new node(temp_x,temp_y);
		}
		printfile(this);
	}
	
	
	
	input_shape(boolean flag) throws IOException
	{
		mesh=new node[360];
		delta_x=0.1f; //just for time pass sake
		float counter=0;
		for(int i=0;i<360;i++) //change the iteration according to delta x
		{	
			float temp_i=(float) Math.toRadians(i);
			counter++;
			float temp_x=(float) (25f*Math.cos(temp_i));
			float temp_y=(float) (25f*Math.sin(temp_i));
			mesh[i]=new node(temp_x,temp_y);
			mesh[i].scalar=counter;
		}
		
		printfile(this);
		
	}
	
	
	
	 
	 void printfile(input_shape mesh) throws IOException
		{
			File file=new File("D:/solver/inimesh.txt");
			FileWriter fw=new FileWriter(file); //created a Filewriter object called fw
			PrintWriter pw=new PrintWriter(fw);
			pw.println("x coord, y coord, z coord, scalar");
			
			for(int i=0;i<mesh.mesh.length;i++)
				{
					pw.println( mesh.mesh[i].x+","+mesh.mesh[i].y+","+"0,"+mesh.mesh[i].get_bool());
				}
			pw.close();
		}
		
}
