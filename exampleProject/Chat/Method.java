
public class Method {
	
	char c;
	
	public void f(Method y){  //传递的是引用，而不是复制的对象
								//方法里y.c实际上就是m对象的c，方法引用并改变了它，结果自然改变
		y.c='a';
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Method m=new Method();
		m.c='c';
		System.out.println(m.c);
		m.f(m);
		System.out.println(m.c);
	}

	
}
