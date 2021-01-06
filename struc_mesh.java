import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class struc_mesh {
	
	node[][] mesh;
	int x_nodes,y_nodes;
	float delta_x,delta_y;
	int mid_x;
	int mid_y;
	
	struc_mesh(node start, node end, float delta_x, float delta_y,input_shape shape) throws IOException 
	{
		float start_x=start.x;
		float start_y=start.y;
		float end_x=end.x;
		float end_y=end.y;
		this.x_nodes=(int) ((end.x-start.x)/delta_x) +1;
		this.y_nodes=(int) ((end.y-start.y)/delta_y) +1;
		this.delta_x=delta_x;
		this.delta_y=delta_y;
		mid_x=x_nodes/2;
		mid_y=y_nodes/2;
		mesh=new node[x_nodes][y_nodes];
		
		///////////Initial mesh////////////////////////////////////
		for(int i=0;i<x_nodes;i++)
			for(int j=0;j<y_nodes;j++)
			{
				mesh[i][j]=new node(i*delta_x,j*delta_y);
				mesh[i][j].setcounter();
			}

//		insert_shape(shape);
//		inside(this,mid_x,mid_y);
		printfile(this);
	}
	void insert_shape(input_shape shape)
	{ 
		node[] sh=shape.mesh;
		for(int k=0;k<shape.mesh.length;k++)
		{
			float sh_x=sh[k].x;
			float sh_y=sh[k].y;
			int i_back=(int)(sh_x/delta_x);
			int i_front=(int)(sh_x/delta_x)+1;
			int j_back=(int)(sh_y/delta_y);
			int j_front=(int)(sh_y/delta_y)+1;
			
			float temp[]=new float[4];
			
			temp[0]=dist(mesh[i_back][j_back],sh[k]); //one one
			temp[1]=dist(mesh[i_back][j_front],sh[k]);	//one_two
			temp[2] =dist(mesh[i_front][j_back],sh[k]);	//two_one
			temp[3]=dist(mesh[i_front][j_front],sh[k]); //two_two
			
			float min=Math.min(Math.min(temp[0], temp[1]) , Math.min(temp[2],temp[3]) );
			if(min==temp[0] && mesh[i_back][j_back].boundary==false)
			{
				mesh[i_back][j_back].x=sh_x;
				mesh[i_back][j_back].y=sh_y;
				mesh[i_back][j_back].boundary=true;
			}else if(min==temp[1] && mesh[i_back][j_front].boundary==false)
			{
				mesh[i_back][j_front].x=sh_x;
				mesh[i_back][j_front].y=sh_y;
				mesh[i_back][j_front].boundary=true;
				
			}else if(min==temp[2] && mesh[i_front][j_back].boundary==false )
			{
				mesh[i_front][j_back].x=sh_x;
				mesh[i_front][j_back].y=sh_y;
				mesh[i_front][j_back].boundary=true;
			}
			else if(min==temp[2]&& mesh[i_front][j_front].boundary==false)
			{
				mesh[i_front][j_front].x=sh_x;
				mesh[i_front][j_front].y=sh_y;
				mesh[i_front][j_front].boundary=true;
			}	
		}
		
	}
	void inside(struc_mesh mesh,int mid_x, int mid_y)
	{
		boolean flagA=false;
		boolean flagB=false;
		boolean flagC=false;
		boolean flagD=false;
		
		if(mesh.mesh[mid_x+1][mid_y].boundary==true || mesh.mesh[mid_x+1][mid_y].inside==true)
			flagA=true;
		else
		{
			mesh.mesh[mid_x+1][mid_y].inside=true;
			inside(mesh,mid_x+1,mid_y);
		}
			
		if(mesh.mesh[mid_x-1][mid_y].boundary==true || mesh.mesh[mid_x-1][mid_y].inside==true)
			flagB=true;
		else
		{
			mesh.mesh[mid_x-1][mid_y].inside=true;
			inside(mesh,mid_x-1,mid_y);
		}
		
		
		if(mesh.mesh[mid_x][mid_y+1].boundary==true || mesh.mesh[mid_x][mid_y+1].inside==true)
			flagC=true;
		else
		{
			mesh.mesh[mid_x][mid_y+1].inside=true;
			inside(mesh,mid_x,mid_y+1);
		}
		
		if(mesh.mesh[mid_x][mid_y-1].boundary==true || mesh.mesh[mid_x][mid_y-1].inside==true)
			flagD=true;
			else
			{
				mesh.mesh[mid_x][mid_y-1].inside=true;
				inside(mesh,mid_x,mid_y-1);
			}
		if(flagA==true && flagB==true && flagC==true && flagD==true )
			return;

	}
	void printfile(struc_mesh mesh) throws IOException
	{
		File file=new File("D:/solver/mesh.txt");
		FileWriter fw=new FileWriter(file); //created a Filewriter object called fw
		PrintWriter pw=new PrintWriter(fw);
		pw.println("x coord, y coord, z coord, scalar");
		
		for(int i=0;i<mesh.x_nodes;i++)
			for(int j=0;j<mesh.y_nodes;j++)
			{
				if(mesh.mesh[i][j].inside!=true)
				pw.println( mesh.mesh[i][j].x+","+mesh.mesh[i][j].y+","+"0,"+mesh.mesh[i][j].counter);
			}
		pw.close();
	}
	
	float dist(node A, node B)
	{
		return (A.x-B.x)*(A.x-B.x)+(A.y-B.y)*(A.y-B.y);
	}
	
	void printlines(struc_mesh mesh) throws IOException
	{
		File file=new File("D:/solver/mesh_lines.txt");
		FileWriter fw=new FileWriter(file); //created a Filewriter object called fw
		PrintWriter pw=new PrintWriter(fw);
		pw.println("x coord, y coord, z coord, scalar");
		
		for(int i=0;i<mesh.x_nodes;i++)
			for(int j=0;j<mesh.y_nodes;j++)
			{
				if(mesh.mesh[i][j].inside!=true)
				pw.println( mesh.mesh[i][j].x+","+mesh.mesh[i][j].y+","+"0,"+mesh.mesh[i][j].get_bool());
			}
		pw.close();
	}
	
	
}
