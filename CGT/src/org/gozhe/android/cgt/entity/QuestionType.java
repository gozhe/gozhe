package org.gozhe.android.cgt.entity;

public class QuestionType {
	private String code;
	private String name;

	@Override
	public String toString() {
		// //ΪʲôҪ��дtoString()�أ���Ϊ����������ʾ���ݵ�ʱ����������������Ķ������ַ���������£�ֱ�Ӿ�ʹ�ö���.toString()
		// TODO Auto-generated method stub
		//return super.toString();
		return name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNamae() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
