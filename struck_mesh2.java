import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class struck_mesh2 {

	node[][] mesh;
	float x_nodes;
	struck_mesh2(input_shape shape,float delta_y) throws IOException
	{
//		mesh=new node[shape.mesh.length][shape.mesh.length];
		this.mesh=new node[shape.mesh.length][20];
		this.x_nodes=shape.mesh.length;
		
		for(int i=0;i<shape.mesh.length;i++)
		{
			mesh[i][0]=new node(shape.mesh[i].x,shape.mesh[i].y);
			mesh[i][0].scalar=99;
			mesh[i][0].setcounter();
			
		}
		float supp;
		for(int j=1;j<20;j++)
		{	
			supp=0;
			if(supp>=1)
			{
				supp=1.0f;
			}
			
			System.out.println(supp);
		
			for(int i=0;i<shape.mesh.length;i++)
			{
				
				if(i==0)
				{
//					float temp_x=(float) (Math.cos(vec(mesh[1][0],mesh[shape.mesh.length-1][0]))*delta_y) +mesh[i][j-1].x;
//					float temp_y=(float) (Math.sin(vec(mesh[1][0],mesh[shape.mesh.length-1][0]))*delta_y) +mesh[i][j-1].y;
//					mesh[i][j]=new node(temp_x,temp_y);
//					due to angles the 0 in both radians and in degrees 
					mesh[0][j]=new node(mesh[0][j-1].x+delta_y,0f);
				}
				else if(i==shape.mesh.length-1)
				{
					
					float temp_x=(float) (Math.cos(vec(mesh[0][0],mesh[shape.mesh.length-2][0],supp))*delta_y) +mesh[i][j-1].x;
					float temp_y=(float) (Math.sin(vec(mesh[0][0],mesh[shape.mesh.length-2][0],supp))*delta_y) +mesh[i][j-1].y;
					mesh[i][j]=new node(temp_x,temp_y);
//					mesh[shape.mesh.length-1][j]=new node(mesh[shape.mesh.length-1][j-1].x+delta_y,0f);
					
				}
				else
				{
				
					float temp_x=(float) (Math.cos(vec(mesh[i+1][0],mesh[i-1][0],supp))*delta_y) +mesh[i][j-1].x ;
					float temp_y=(float) (Math.sin(vec(mesh[i+1][0],mesh[i-1][0],supp))*delta_y) +mesh[i][j-1].y ;
					mesh[i][j]=new node(temp_x,temp_y);
				}
				mesh[i][j].setcounter();
				
			}
			
					
		}
		
		printfile(this);
//		printline(this);
		
		
	}
	
	float vec(node node_back, node node_front, float supp)
	{
		float[] vecB=new float[2];
		vecB[0]=node_back.x;
		vecB[1]=node_back.y;
		
		float[] vecF=new float[2];
		vecF[0]=node_front.x;
		vecF[1]=node_front.y;
		
		float[] diff=new float[2];
		diff[0]=vecB[0]-vecF[0];
		diff[1]=vecB[1]-vecF[1];
		
		float[] diff_sqr=new float[2];
		diff_sqr[0]=diff[0]*diff[0];
		diff_sqr[1]=diff[1]*diff[1];
		
		diff[0]=(float) (diff[0]/Math.sqrt(diff_sqr[0]+diff_sqr[1]));
		diff[1]=(float) (diff[1]/Math.sqrt(diff_sqr[0]+diff_sqr[1]));
		
		
		float per_1[]=new float[2];
		float per_2[]=new float[2];
		
		per_1[0]=diff[1];
		per_1[1]=-diff[0];
		
		per_2[0]=-diff[1];
		per_2[1]=diff[0];
		
		if(per_1[0]*vecB[0]+ per_1[1]*vecB[1]>0f)
			{
//			float temp=(float) Math.atan2(per_1[1],per_1[0]);
//			if(0<temp && temp<Math.PI/4.0f)
//			{
//			temp=temp-supp*temp;	
//			}
			return supp((float) Math.atan2(per_1[1],per_1[0]), supp);
			}
		
		else
			{
//			float temp=(float) Math.atan2(per_1[1],per_1[0]);
//			if(0<temp && temp<Math.PI/4.0f)
//			{
//			temp=temp-supp*temp;	
//			}
			return supp( (float) Math.atan2(per_2[1],per_2[0]),supp );
			}
	}
	
	float supp(float angle, float supp)
	{
		if(angle>0f && angle<=Math.PI/4f)
		{
			return angle-supp*angle;	
		}
		else if(angle<=3f*Math.PI/4f && angle>Math.PI/4f)
		{
			return (float) (supp*( (Math.PI/2) - angle ) + angle);	
		}
		else if(angle<=Math.PI && angle>3f*Math.PI/4f)
		{
			return (float) (supp*( Math.PI - angle ) + angle);	
		}
		else if(angle<0f && angle>=-Math.PI/4f)
		{
			return angle-supp*angle;	
		}
		else if(angle>=-3f*Math.PI/4f && angle<-Math.PI/4f)
		{
			return (float) (supp*( (-Math.PI/2) - angle ) + angle);	
		}
		else if(angle>-Math.PI && angle<-3f*Math.PI/4f)
		{
			return (float) (supp*( -Math.PI - angle ) + angle);	
		}
	
			return angle;
	}
	
	void printfile(struck_mesh2 mesh) throws IOException
	{
		File file=new File("D:/solver/mesh.txt");
		FileWriter fw=new FileWriter(file); //created a Filewriter object called fw
		PrintWriter pw=new PrintWriter(fw);
		pw.println("x coord, y coord, z coord, scalar");
		
		for(int i=0;i<mesh.x_nodes;i++)
			for(int j=0;j<20;j++)
			{
				pw.println( mesh.mesh[i][j].x+","+mesh.mesh[i][j].y+","+"0,"+mesh.mesh[i][j].counter);
			}
		pw.close();
	}
	
	void printline(struck_mesh2 mesh) throws IOException //this method is wrong
	{
		File file=new File("D:/solver/line.vtk");
		FileWriter fw=new FileWriter(file); //created a Filewriter object called fw
		PrintWriter pw=new PrintWriter(fw);
		pw.println("# vtk DataFile Version 3.0");
		pw.println("vtk output");
		pw.println("ASCII");
		pw.println("DATASET POLYDATA");
		pw.println("");
		pw.println("POINTS 8 double");
		
		for(int i=0;i<mesh.x_nodes;i++)
			for(int j=0;j<3;j++)
			{
				pw.println( mesh.mesh[i][j].x+" "+mesh.mesh[i][j].y+" "+"0");
			}
		
		pw.println("");
		pw.println("LINES numberoflines nubmeroflines*3");
		
		for(int i=1;i<mesh.x_nodes-1;i++)
			for(int j=1;j<3-1;j++)
			{
				int tempC=mesh.mesh[i][j].counter;
				int tempU=mesh.mesh[i][j+1].counter;
				int tempD=mesh.mesh[i][j-1].counter;
				int tempL=mesh.mesh[i-1][j].counter;
				int tempR=mesh.mesh[i+1][j].counter;
				
				pw.println("2 "+tempC+" "+tempU  );
				pw.println("2 "+tempC+" "+tempD  );
				pw.println("2 "+tempC+" "+tempL  );
				pw.println("2 "+tempC+" "+tempR  );
			}
		
		pw.close();
	}
	
	
}
