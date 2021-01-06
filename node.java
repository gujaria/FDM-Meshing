
public class node{
	 float x;
	 float y;
	 float scalar=0;
	 boolean boundary=false;
	 boolean inside=false;
	 int node_num;
	 static int counter_class=0;
	 int counter=0;
	 
	 node(float x , float y)
	 {
		 this.set_point(x,y);
		
	 }
	 
	 void setcounter()
	 {
		 this.counter=counter_class;
		 counter_class++;
	 }
	 
	 void set_point(float x,float y)
	 {
		 this.x=x;
		 this.y=y;
	 }
	 int get_bool()
	 {
		 if(boundary)
			 return 1;
		 else 
			 return 0;
	 }
	 
}
