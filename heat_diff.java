import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class heat_diff {

	heat_diff(struc_mesh mesh,int time, float delta_t,float alpha) throws IOException
	{
		int steps=(int)(time/delta_t) +1;
		mesh=boundary(mesh);
		for(int i=0;i<=steps;i++) //for timesteps
		{
			mesh=set_temp(mesh,alpha,delta_t);
			printfile(mesh,i);
		}
	}
	
	struc_mesh boundary(struc_mesh mesh)
	{	
			for(int j=0;j<mesh.y_nodes;j++)
			{
				mesh.mesh[0][j].scalar=1000;
			}		
			for(int i=0;i<mesh.x_nodes;i++)
			{
				mesh.mesh[i][0].scalar=1000;
			}	
		return mesh;
	}
	
	struc_mesh set_temp(struc_mesh mesh,float alpha,float dt) //solver
	{
		struc_mesh mesh_new=mesh;
		for(int i=1;i<mesh.x_nodes-1;i++)
			for(int j=1;j<mesh.y_nodes-1;j++)
			{ //(alpha*dt/(mesh.delta_x*mesh.delta_x))
//				System.out.println(mesh.mesh[i][j].scalar+0.4f*(-4f*mesh.mesh[i][j].scalar+mesh.mesh[i+1][j].scalar+mesh.mesh[i-1][j].scalar+mesh.mesh[i][j+1].scalar+mesh.mesh[i][j-1].scalar));
				mesh_new.mesh[i][j].scalar=mesh.mesh[i][j].scalar+0.4f*(-4f*mesh.mesh[i][j].scalar+mesh.mesh[i+1][j].scalar+mesh.mesh[i-1][j].scalar+mesh.mesh[i][j+1].scalar+mesh.mesh[i][j-1].scalar) ; 
			}
		return mesh_new;
	}
	
	void printfile(struc_mesh mesh,int step) throws IOException
	{
		File file=new File("D:/solver/solved.txt."+step);
		FileWriter fw=new FileWriter(file); //created a Filewriter object called fw
		PrintWriter pw=new PrintWriter(fw);
		pw.println("x coord, y coord, z coord, scalar");
		
		for(int i=0;i<mesh.x_nodes;i++)
			for(int j=0;j<mesh.y_nodes;j++)
			{
				pw.println( mesh.mesh[i][j].x+","+mesh.mesh[i][j].y+","+"0,"+mesh.mesh[i][j].scalar);
			}
		pw.close();
	}
	
}
