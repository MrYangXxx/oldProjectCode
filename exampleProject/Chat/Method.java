
public class Method {
	
	char c;
	
	public void f(Method y){  //���ݵ������ã������Ǹ��ƵĶ���
								//������y.cʵ���Ͼ���m�����c���������ò��ı������������Ȼ�ı�
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
