
public class node_list{
	
	 float x;
	 float y;
	 node_list[] adj=new node_list[4];
	 float scalar=0;
	 boolean boundary=false;
	 boolean inside=false;
	 float per_dist;
	 
	 node_list(float x , float y)
	 {
		 this.set_point(x,y);
	 }
	 
	 void set_point(float x,float y)
	 {
		 this.x=x;
		 this.y=y;
	 }
	 
}
